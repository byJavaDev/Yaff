package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code short} consuming {@code 2} bytes
 */

public class ShortYaffTag extends YaffTag<Short>
{
    public ShortYaffTag(Short value)
    {
        super(value, YaffData.SHORT);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readShort());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeShort(getValue());
    }
}
