package de.byjavadev.yaff.exception;

import de.byjavadev.yaff.YaffUtils;

public class YaffDataTypeException extends YaffException
{
    public YaffDataTypeException(byte type)
    {
        super("Unknown data type " + type + " (" + YaffUtils.toHexString(type) + ")");
    }
}
