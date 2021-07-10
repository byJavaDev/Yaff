package de.byjavadev.yaff;

import de.byjavadev.yaff.exception.YaffEndException;
import de.byjavadev.yaff.exception.YaffInternalException;
import de.byjavadev.yaff.exception.YaffLengthException;
import de.byjavadev.yaff.exception.YaffVersionException;
import de.byjavadev.yaff.storage.YaffDataContainer;
import de.byjavadev.yaff.storage.YaffFile;
import de.byjavadev.yaff.storage.YaffVirtual;
import de.byjavadev.yaff.tag.YaffTag;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Yaff</h1>
 * <i><b>Y</b>et <b>A</b>nother <b>F</b>ile <b>F</b>ormat</i>
 * <br />
 * Tagged data container format
 */

public class Yaff
{
    /** The current version of yaff */
    public static final long VERSION = 1L;

    /** The data type signature */
    private static final byte[] SIGNATURE = {0x0, 0x59, 0x41, 0x46, 0x46, 0x2, 0x5B};

    /** The byte at the end of the file */
    private static final byte END_BYTE = 0x5D;

    /** All tags in this yaff file */
    private final Map<String, YaffTag<?>> yaffTags = new HashMap<>();

    /** The data container */
    @Getter
    private final YaffDataContainer container;

    /** The amount of tags in this tag container */
    @Getter
    private int tagCount;

    /** {@code true} if yaff should allow files from a newer/older version */
    @Getter
    private final boolean allowIncompatibility;

    /** {@code true} if the data should be saved automatically */
    @Getter
    private final boolean autoSave;

    /** {@code true} if this yaff file should be opened in read only */
    @Getter
    private final boolean readOnly;

    /** {@code true} if this yaff file should not contain a header */
    @Getter
    private final boolean headerless;

    /**
     * Please use the builder
     * @see Yaff#builder()
     */

    Yaff(final YaffDataContainer container, final boolean allowIncompatibility, final boolean autoSave, final boolean readOnly, final boolean headerless)
    {
        this.container = container;
        this.allowIncompatibility = allowIncompatibility;
        this.autoSave = autoSave;
        this.readOnly = readOnly;
        this.headerless = headerless;
        read();
    }

    /**
     * Retrieves a tag
     * @param key the key
     * @param <T> the type of tag
     * @return the tag or {@code null} if not found
     */

    public YaffTag<?> get(final String key)
    {
        for (Map.Entry<String, YaffTag<?>> entry : yaffTags.entrySet())
        {
            final String aKey = entry.getKey();
            final YaffTag<?> value = entry.getValue();

            if(aKey.equals(key))
                return value;
        } return null;
    }

    /**
     * Inserts a new tag
     * @param key the key
     * @param tag the tag
     * @param <T> the type of tag
     * @return the tag
     */

    public <T> YaffTag<T> create(final String key, final YaffTag<T> tag)
    {
        yaffTags.put(key, tag);
        tag.setParent(this);
        if(isAutoSave())
            save();
        return tag;
    }

    @SneakyThrows
    private void read()
    {
        container.seek(0);

        if(container.length() == 0L)
        {
            //This is an empty container
            save();
            return;
        }

        if(!isHeaderless())
        {
            final byte[] signatureCheck = new byte[SIGNATURE.length];
            container.read(signatureCheck);
            if(!Arrays.equals(SIGNATURE, signatureCheck))
            {
                throw new YaffInternalException("Not a yaff container (" + YaffUtils.toHexString(signatureCheck) + ")");
            }

            long version = container.readLong();
            if(!allowIncompatibility && version != VERSION)
            {
                //This is on another version and does not accept that
                throw new YaffVersionException(version);
            }
        }

        //The amount of tags
        tagCount = container.readInt();

        //Reads all entries
        for(int i = 0; i < tagCount; i++)
        {
            final Map.Entry<String, YaffTag<?>> entry = YaffUtils.readTag(container);
            this.create(entry.getKey(), entry.getValue());
        }

        if(yaffTags.size() != tagCount)
        {
            throw new YaffLengthException(tagCount, yaffTags.size());
        }

        if(container.readByte() != END_BYTE)
        {
            throw new YaffEndException();
        }
    }

    /**
     * Saves the (modified) version of this container
     */

    @SneakyThrows
    public void save()
    {
        if(readOnly)
            throw new IllegalStateException("This yaff container is opened in read only mode");

        container.clear();
        container.seek(0);

        if(!isHeaderless())
        {
            container.write(SIGNATURE);
            container.writeLong(VERSION);
        }

        container.writeInt(yaffTags.size());

        yaffTags.forEach((key, value) -> YaffUtils.writeTag(container, key, value));
        container.writeByte(END_BYTE);

        container.seek(0);
    }

    public static YaffBuilder builder()
    {
        return new YaffBuilder();
    }

    public static class YaffBuilder
    {
        private boolean allowIncompatibility;
        private boolean autoSave;
        private boolean readOnly;
        private RandomAccessFile randomAccessFile;
        private YaffVirtual.YaffChannel yaffChannel;
        private boolean headerless;

        YaffBuilder()
        {
        }

        /**
         * Disables incompatibility checking
         */

        public YaffBuilder allowIncompatibility()
        {
            this.allowIncompatibility = true;
            return this;
        }

        /**
         * Enables autosaving the file after creating a tag
         */

        public YaffBuilder autoSave()
        {
            this.autoSave = true;
            return this;
        }

        /**
         * Enables the readonly mode
         */

        public YaffBuilder readOnly()
        {
            this.readOnly = true;
            return this;
        }

        /**
         * Enables headerless mode
         */

        public YaffBuilder headerless()
        {
            this.headerless = true;
            return this;
        }

        /**
         * Uses a file
         * @param randomAccessFile the random access file to use
         */

        public YaffBuilder file(RandomAccessFile randomAccessFile)
        {
            this.randomAccessFile = randomAccessFile;
            return this;
        }

        /**
         * Uses a file
         * @param file the file to use as a random access file
         */

        @SneakyThrows
        public YaffBuilder file(File file)
        {
            return file(new RandomAccessFile(file, "r" + (!readOnly ? "w" : "")));
        }

        /**
         * Uses a yaff channel
         * @param yaffChannel the channel
         */

        public YaffBuilder channel()
        {
            this.yaffChannel = new YaffVirtual.YaffChannel();
            return this;
        }

        /**
         * Uses a yaff channel
         * @param bytes the bytes to read
         */

        public YaffBuilder channel(byte[] bytes)
        {
            this.yaffChannel = new YaffVirtual.YaffChannel(bytes);
            return this;
        }

        /**
         * Builds yaff
         * @return the new instance of yaff
         */

        public Yaff create()
        {
            if(autoSave && readOnly)
                throw new IllegalStateException("Conflicting options: Can't select read only with autosave");

            if(randomAccessFile != null && yaffChannel != null)
                throw new IllegalStateException("Conflicting sources: File as well as yaff channel specified");

            return new Yaff(randomAccessFile != null ? new YaffFile(randomAccessFile) : (yaffChannel != null ? new YaffVirtual(yaffChannel) : new YaffVirtual()), allowIncompatibility, autoSave, readOnly, headerless);
        }
    }

}
