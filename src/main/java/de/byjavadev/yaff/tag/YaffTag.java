package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.Yaff;
import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;
import lombok.Data;

import java.io.IOException;

@Data
public abstract class YaffTag<T>
{
    /** The value */
    private T value;

    /** The parent or {@code null} */
    private Yaff parent;

    /** The type of data this tag holds */
    private final YaffData dataType;

    public YaffTag(T value, YaffData dataType)
    {
        this.value = value;
        this.dataType = dataType;
    }

    public void setValue(T value)
    {
        this.value = value;
        if(parent != null && parent.isAutoSave())
            parent.save();
    }

    /**
     * Reads the value
     * @param randomAccessFile the random access file to read from
     */

    public abstract void read(final YaffDataContainer randomAccessFile) throws IOException;

    /**
     * Writes the value
     * @param randomAccessFile the random access file to write to
     */

    public abstract void write(final YaffDataContainer randomAccessFile) throws IOException;
}
