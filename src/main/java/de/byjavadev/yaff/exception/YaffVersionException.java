package de.byjavadev.yaff.exception;

import de.byjavadev.yaff.Yaff;

public class YaffVersionException extends YaffException
{
    public YaffVersionException(long containerVersion)
    {
        super("Version incompatibility not allowed, tried to parse container on version " + containerVersion + " (this version is " + Yaff.VERSION + ")");
    }
}
