package de.byjavadev.yaff.exception;

public class YaffEndException extends YaffException
{
    public YaffEndException()
    {
        super("End byte missing (data incomplete?)");
    }
}
