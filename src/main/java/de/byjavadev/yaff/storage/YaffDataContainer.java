package de.byjavadev.yaff.storage;

import java.io.IOException;

public interface YaffDataContainer
{
    void seek(long bytes) throws IOException;
    long getPointer() throws IOException;
    long length() throws IOException;
    void clear() throws IOException;

    double readDouble() throws IOException;
    float readFloat() throws IOException;
    long readLong() throws IOException;
    int readInt() throws IOException;
    short readShort() throws IOException;
    boolean readBoolean() throws IOException;
    byte readByte() throws IOException;
    void read(byte[] bytes) throws IOException;

    void writeDouble(double aDouble) throws IOException;
    void writeFloat(float aFloat) throws IOException;
    void writeLong(long aLong) throws IOException;
    void writeInt(int anInt) throws IOException;
    void writeShort(short aShort) throws IOException;
    void writeByte(byte aByte) throws IOException;
    void writeBoolean(boolean aBoolean) throws IOException;
    void writeBytes(String string) throws IOException;
    void write(byte[] bytes) throws IOException;
}
