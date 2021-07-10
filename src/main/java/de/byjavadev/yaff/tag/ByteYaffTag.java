package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store one single {@code byte}, consuming {@code 1} byte
 */

public class ByteYaffTag extends YaffTag<Byte>
{
    public ByteYaffTag(Byte value)
    {
        super(value, YaffData.BYTE);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readByte());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeByte(getValue());
    }
}
