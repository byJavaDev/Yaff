package de.byjavadev.yaff;

import de.byjavadev.yaff.storage.YaffDataContainer;
import de.byjavadev.yaff.tag.*;
import de.byjavadev.yaff.exception.YaffDataTypeException;
import lombok.SneakyThrows;

import java.util.AbstractMap;
import java.util.Map.Entry;

public class YaffUtils
{
    @SneakyThrows
    public static void writeTag(final YaffDataContainer container, final String key, final YaffTag<?> yaffTag)
    {
        //The type of data
        container.writeByte(yaffTag.getDataType().getId());

        //The length of the data
        container.writeShort((short) key.length());

        //The key
        container.writeBytes(key);

        //The value
        yaffTag.write(container);
    }

    @SneakyThrows
    public static Entry<String, YaffTag<?>> readTag(final YaffDataContainer container)
    {
        //The type of data
        final byte typeId = container.readByte();
        final YaffData dataType = YaffData.getDataType(typeId);
        if(dataType == null)
            throw new YaffDataTypeException(typeId);

        //The key
        final byte[] keyBuffer = new byte[/* The length of the key */ container.readShort()];
        container.read(keyBuffer);
        final String key = new String(keyBuffer);

        // The tag
        final YaffTag<?> yaffTag;
        switch (dataType)
        {
            case BOOLEAN:
                yaffTag = new BooleanYaffTag(false);
                break;
            case BYTE:
                yaffTag = new ByteYaffTag((byte) 0);
                break;
            case SHORT:
                yaffTag = new ShortYaffTag((short) 0);
                break;
            case INTEGER:
                yaffTag = new IntegerYaffTag(0);
                break;
            case LONG:
                yaffTag = new LongYaffTag(0L);
                break;
            case FLOAT:
                yaffTag = new FloatYaffTag(0F);
                break;
            case DOUBLE:
                yaffTag = new DoubleYaffTag(0D);
                break;
            case OBJECT:
                yaffTag = new ObjectYaffTag(null);
                break;
            case LIST:
                yaffTag = new MapYaffTag(null);
                break;
            case STRING:
                yaffTag = new StringYaffTag(null);
                break;
            case ARRAY_BYTE:
                yaffTag = new ByteArrayYaffTag(null);
                break;
            default:
                throw new UnsupportedOperationException("Data type " + dataType + " is not implemented yet");
        }

        //Reads the value
        yaffTag.read(container);

        return new AbstractMap.SimpleEntry<>(key, yaffTag);
    }

    public static String toHexString(byte... byteInput)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        for(byte byteValue : byteInput)
            stringBuilder.append(String.format("%02x", byteValue));
        return stringBuilder.toString();
    }
}
