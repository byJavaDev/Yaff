package de.byjavadev.yaff;

import de.byjavadev.yaff.tag.*;
import lombok.SneakyThrows;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class YaffWriteTest
{

    @SneakyThrows
    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();

        final File file = new File("data.yf");
        if(file.exists())
            file.delete();
        file.createNewFile();
        final Yaff yaff = Yaff.builder().allowIncompatibility().file(new RandomAccessFile(file, "rw")).create();
        yaff.create("Test", new DoubleYaffTag(123.456D));
        yaff.create("AnotherTest", new BooleanYaffTag(true));
        //yaff.create("MyObject", new ObjectYaffTag(new YaffTest.TestClass()));

        final Map<String, YaffTag<?>> tagMap = new HashMap<>();
        tagMap.put("ListDouble", new DoubleYaffTag(12423057D));
        tagMap.put("StringValueInList", new StringYaffTag("Foo bar! This is a test which\nis now on another line"));
        tagMap.put("AnotherListEntry", new ByteYaffTag((byte) 32));

        yaff.create("MyList", new MapYaffTag(tagMap));

        yaff.create("MyArray", new ByteArrayYaffTag(new Byte[] {12, 42, 53}));
        yaff.save();

        System.out.println("Took " + (System.currentTimeMillis() - time) + "ms");
    }

}
