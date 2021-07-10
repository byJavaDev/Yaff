package de.byjavadev.yaff.storage;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.RandomAccessFile;

@RequiredArgsConstructor
public class YaffFile implements YaffDataContainer
{
    private final RandomAccessFile randomAccessFile;

    @Override
    public void seek(long bytes) throws IOException
    {
        randomAccessFile.seek(bytes);
    }

    @Override
    public long getPointer() throws IOException
    {
        return randomAccessFile.getFilePointer();
    }

    @Override
    public long length() throws IOException
    {
        return randomAccessFile.length();
    }

    @Override
    public void clear() throws IOException
    {
        randomAccessFile.setLength(0);
    }

    @Override
    public double readDouble() throws IOException
    {
        return randomAccessFile.readDouble();
    }

    @Override
    public float readFloat() throws IOException
    {
        return randomAccessFile.readFloat();
    }

    @Override
    public long readLong() throws IOException
    {
        return randomAccessFile.readLong();
    }

    @Override
    public int readInt() throws IOException
    {
        return randomAccessFile.readInt();
    }

    @Override
    public short readShort() throws IOException
    {
        return randomAccessFile.readShort();
    }

    @Override
    public byte readByte() throws IOException
    {
        return randomAccessFile.readByte();
    }

    @Override
    public boolean readBoolean() throws IOException
    {
        return randomAccessFile.readBoolean();
    }

    @Override
    public void read(byte[] bytes) throws IOException
    {
        randomAccessFile.read(bytes);
    }

    @Override
    public void writeDouble(double aDouble) throws IOException
    {
        randomAccessFile.writeDouble(aDouble);
    }

    @Override
    public void writeFloat(float aFloat) throws IOException
    {
        randomAccessFile.writeFloat(aFloat);
    }

    @Override
    public void writeLong(long aLong) throws IOException
    {
        randomAccessFile.writeLong(aLong);
    }

    @Override
    public void writeInt(int anInt) throws IOException
    {
        randomAccessFile.writeInt(anInt);
    }

    @Override
    public void writeShort(short aShort) throws IOException
    {
        randomAccessFile.writeShort(aShort);
    }

    @Override
    public void writeByte(byte aByte) throws IOException
    {
        randomAccessFile.writeByte(aByte);
    }

    @Override
    public void writeBoolean(boolean aBoolean) throws IOException
    {
        randomAccessFile.writeBoolean(aBoolean);
    }

    @Override
    public void writeBytes(String string) throws IOException
    {
        randomAccessFile.writeBytes(string);
    }

    @Override
    public void write(byte[] bytes) throws IOException
    {
        randomAccessFile.write(bytes);
    }
}
