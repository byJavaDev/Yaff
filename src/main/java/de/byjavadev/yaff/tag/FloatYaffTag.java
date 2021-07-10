package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code float} consuming {@code 4} bytes
 */

public class FloatYaffTag extends YaffTag<Float>
{
    public FloatYaffTag(Float value)
    {
        super(value, YaffData.FLOAT);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readFloat());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeFloat(getValue());
    }
}
