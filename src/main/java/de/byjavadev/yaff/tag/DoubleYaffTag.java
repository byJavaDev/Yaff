package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store one {@code double} consuming {@code 8} bytes
 */

public class DoubleYaffTag extends YaffTag<Double>
{
    public DoubleYaffTag(Double value)
    {
        super(value, YaffData.DOUBLE);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readDouble());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeDouble(getValue());
    }
}
