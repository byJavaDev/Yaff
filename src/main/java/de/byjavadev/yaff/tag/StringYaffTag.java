package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;

import java.io.IOException;

/**
 * This tag can store a {@code String}
 */

public class StringYaffTag extends YaffTag<String>
{
    public StringYaffTag(String value)
    {
        super(value, YaffData.STRING);
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        byte[] stringBytes = new byte[randomAccessFile.readInt()];
        randomAccessFile.read(stringBytes);
        setValue(new String(stringBytes));
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeInt(getValue().length());
        randomAccessFile.writeBytes(getValue());
    }
}
