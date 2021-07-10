package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;
import lombok.SneakyThrows;

import java.io.*;

/**
 * This tag can store any {@code Serializable} object using {@code ObjectOutputStream} and {@code ObjectInputStream}
 * @see Serializable
 */

public class ObjectYaffTag extends YaffTag<Object>
{
    public ObjectYaffTag(Object value)
    {
        super(value, YaffData.OBJECT);
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        (new ObjectOutputStream(byteArrayOutputStream)).writeObject(getValue());

        final byte[] objectData = byteArrayOutputStream.toByteArray();
        randomAccessFile.writeInt(objectData.length);
        randomAccessFile.write(objectData);
    }

    @SneakyThrows
    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        final int objectLength = randomAccessFile.readInt();
        final byte[] objectData = new byte[objectLength];
        randomAccessFile.read(objectData);

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectData);
        setValue((new ObjectInputStream(byteArrayInputStream)).readObject());
    }
}
