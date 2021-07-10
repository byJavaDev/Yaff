package de.byjavadev.yaff.storage;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class YaffVirtual implements YaffDataContainer
{
    /** The channel */
    @Getter
    private final YaffChannel yaffChannel;

    public YaffVirtual()
    {
        this(new YaffChannel());
    }

    public YaffVirtual(final YaffChannel yaffChannel)
    {
        this.yaffChannel = yaffChannel;
    }

    @Override
    public void seek(long bytes)
    {
        yaffChannel.seek((int) bytes);
    }

    @Override
    public long getPointer()
    {
        return yaffChannel.getPointer();
    }

    @Override
    public long length()
    {
        return yaffChannel.length();
    }

    @Override
    public void clear()
    {
        yaffChannel.clear();
    }

    @Override
    public double readDouble() throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        final byte[] dataIn = new byte[Double.BYTES];
        read(dataIn);
        byteBuffer.put(dataIn);
        byteBuffer.flip();
        return byteBuffer.getDouble();
    }

    @Override
    public float readFloat() throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        final byte[] dataIn = new byte[Float.BYTES];
        read(dataIn);
        byteBuffer.put(dataIn);
        byteBuffer.flip();
        return byteBuffer.getFloat();
    }

    @Override
    public long readLong() throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        final byte[] dataIn = new byte[Long.BYTES];
        read(dataIn);
        byteBuffer.put(dataIn);
        byteBuffer.flip();
        return byteBuffer.getLong();
    }

    @Override
    public int readInt() throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        final byte[] dataIn = new byte[Integer.BYTES];
        read(dataIn);
        byteBuffer.put(dataIn);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    @Override
    public short readShort() throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
        final byte[] dataIn = new byte[Short.BYTES];
        read(dataIn);
        byteBuffer.put(dataIn);
        byteBuffer.flip();
        return byteBuffer.getShort();
    }

    @SneakyThrows
    @Override
    public boolean readBoolean()
    {
        return readByte() == 1;
    }

    @Override
    public byte readByte()
    {
        return yaffChannel.read();
    }

    @Override
    public void read(byte[] target) throws IOException
    {
        for(int i = 0; i < target.length; i++)
        {
            target[i] = readByte();
        }
    }

    @Override
    public void writeDouble(double aDouble) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(aDouble);
        write(byteBuffer.array());
    }

    @Override
    public void writeFloat(float aFloat) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(aFloat);
        write(byteBuffer.array());
    }

    @Override
    public void writeLong(long aLong) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(aLong);
        write(byteBuffer.array());
    }

    @Override
    public void writeInt(int anInt) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(anInt);
        write(byteBuffer.array());
    }

    @Override
    public void writeShort(short aShort) throws IOException
    {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
        byteBuffer.putShort(aShort);
        write(byteBuffer.array());
    }

    @Override
    public void writeBoolean(boolean aBoolean)
    {
        writeByte((byte) (aBoolean ? 1 : 0));
    }

    @Override
    public void writeByte(byte aByte)
    {
        yaffChannel.write(aByte);
    }

    @Override
    public void writeBytes(String string) throws IOException
    {
        write(string.getBytes());
    }

    @Override
    public void write(byte[] bytes) throws IOException
    {
        for (byte aByte : bytes)
        {
            writeByte(aByte);
        }
    }

    public static class YaffChannel
    {
        /** The bytes in this channel */
        private final List<Byte> bytes = new ArrayList<>();

        /** This pointer */
        @Getter
        private int pointer = 0;

        public YaffChannel(byte[] bytes)
        {
            for (byte aByte : bytes)
            {
                this.bytes.add(aByte);
            }
        }

        public YaffChannel()
        {
        }

        /**
         * Reads a byte
         * @return the byte
         */

        public byte read()
        {
            byte value = bytes.get(getPointer());
            pointer++;
            return value;
        }

        /**
         * Writes a byte
         * @param byteValue the byte
         */

        public void write(byte byteValue)
        {
            if(getPointer() >= bytes.size())
            {
                bytes.add(byteValue);
            }
            else bytes.set(getPointer(), byteValue);

            pointer++;
        }

        /**
         * Seeks the channel
         * @param position the position of the pointer
         */

        public void seek(int position)
        {
            this.pointer = position;
            if(pointer < 0 || pointer > bytes.size())
                throw new ArrayIndexOutOfBoundsException("Yaff channel out of bounds");
        }

        /**
         * Moves the pointer forwards
         * @param steps the steps
         */

        public void skip(int steps)
        {
            seek(pointer + steps);
        }

        /**
         * Moves the pointer backwards
         * @param steps the step
         */

        public void revert(int steps)
        {
            skip(-steps);
        }

        /**
         * Creates a byte array out of this channel
         * @return the byte array
         */

        public byte[] array()
        {
            byte[] array = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++)
                array[i] = bytes.get(i);
            return array;
        }

        /**
         * Resets this channel
         */

        public void clear()
        {
            pointer = 0;
            bytes.clear();
        }

        /**
         * @return the length of this channel
         */

        public int length()
        {
            return bytes.size();
        }
    }
}
