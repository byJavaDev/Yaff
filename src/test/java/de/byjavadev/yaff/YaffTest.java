package de.byjavadev.yaff;

import de.byjavadev.yaff.tag.ByteArrayYaffTag;
import lombok.SneakyThrows;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Arrays;

public class YaffTest
{

    @SneakyThrows
    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();

        final File file = new File("data.yf");
        if(!file.exists())
            throw new RuntimeException("File does not exist");

        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(0);

        final Yaff yaff = Yaff.builder().allowIncompatibility().file(randomAccessFile).readOnly().create();
        System.out.println(yaff.get("Test").getValue());
        System.out.println(yaff.get("AnotherTest").getValue());
        System.out.println(yaff.get("MyList").getValue());

        final ByteArrayYaffTag byteArrayYaffTag = (ByteArrayYaffTag) yaff.get("MyArray");
        System.out.println(Arrays.toString(byteArrayYaffTag.getValue()));

        System.out.println("Took " + (System.currentTimeMillis() - time) + "ms");
    }

    public static class TestClass implements Serializable
    {
        private static final long serialVersionUID = 23847235L;

        public final int value = 24;
        public String anotherValue = "foo bar";
    }

}
