package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code byte} array
 */

public class ByteArrayYaffTag extends YaffTag<Byte[]>
{
    public ByteArrayYaffTag(Byte[] value)
    {
        super(value, YaffData.ARRAY_BYTE);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(new Byte[randomAccessFile.readInt()]);
        for(int i = 0; i < getValue().length; i++)
            getValue()[i] = randomAccessFile.readByte();
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeInt(getValue().length);
        for (Byte aByte : getValue())
            randomAccessFile.writeByte(aByte);
    }
}
