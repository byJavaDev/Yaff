package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store an {@code integer} consuming {@code 4} bytes
 */

public class IntegerYaffTag extends YaffTag<Integer>
{
    public IntegerYaffTag(Integer value)
    {
        super(value, YaffData.INTEGER);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readInt());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeInt(getValue());
    }
}
