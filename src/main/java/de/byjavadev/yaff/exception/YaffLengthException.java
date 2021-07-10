package de.byjavadev.yaff.exception;

public class YaffLengthException extends YaffException
{
    public YaffLengthException(int expectedLength, int length)
    {
        super("Unexpected amount of tags, expected " + expectedLength + ", got " + length);
    }
}
