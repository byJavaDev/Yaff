package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code long} consuming {@code 8} bytes
 */

public class LongYaffTag extends YaffTag<Long>
{
    public LongYaffTag(Long value)
    {
        super(value, YaffData.LONG);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readLong());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeLong(getValue());
    }
}
