package de.byjavadev.yaff.tag;

import de.byjavadev.yaff.YaffData;
import de.byjavadev.yaff.storage.YaffDataContainer;
import de.byjavadev.yaff.YaffUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This tag can store an entire map of tags
 */

public class MapYaffTag extends YaffTag<Map<String, YaffTag<?>>>
{
    public MapYaffTag(Map<String, YaffTag<?>> tags)
    {
        super(tags, YaffData.LIST);
    }

    @Override
    public void write(YaffDataContainer randomAccessFile) throws IOException
    {
        randomAccessFile.writeInt(getValue().size());
        getValue().forEach((key, value) -> YaffUtils.writeTag(randomAccessFile, key, value));
    }

    @Override
    public void read(YaffDataContainer randomAccessFile) throws IOException
    {
        setValue(new HashMap<>());

        final int entries = randomAccessFile.readInt();
        for(int i = 0; i < entries; i++)
        {
            final Map.Entry<String, YaffTag<?>> entry = YaffUtils.readTag(randomAccessFile);
            getValue().put(entry.getKey(), entry.getValue());
        }
    }
}
