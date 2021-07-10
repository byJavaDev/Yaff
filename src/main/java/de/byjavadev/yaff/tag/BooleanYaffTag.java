package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code boolean} value (either {@code true} or {@code false}) consuming {@code 1} byte
 */

public class BooleanYaffTag extends YaffTag<Boolean>
{
    public BooleanYaffTag(Boolean value)
    {
        super(value, YaffData.BOOLEAN);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(randomAccessFile.readBoolean());
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeBoolean(getValue());
    }
}
