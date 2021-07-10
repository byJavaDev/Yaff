package de.byjavadev.yaff.channel;

import de.byjavadev.yaff.storage.YaffVirtual;

import java.util.Arrays;

public class YaffTestChannel
{

    public static void main(String[] args)
    {
        final YaffVirtual.YaffChannel yaffChannel = new YaffVirtual.YaffChannel();
        yaffChannel.write((byte) 10);
        yaffChannel.clear();
        yaffChannel.write((byte) 4);
        yaffChannel.write((byte) 12);
        yaffChannel.revert(1);
        yaffChannel.write((byte) 3);
        yaffChannel.write((byte) 5);
        System.out.println(Arrays.toString(yaffChannel.array()));
    }

}
