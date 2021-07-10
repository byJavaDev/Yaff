package de.byjavadev.yaff.virtual;

import de.byjavadev.yaff.Yaff;
import de.byjavadev.yaff.storage.YaffVirtual;
import de.byjavadev.yaff.tag.BooleanYaffTag;
import de.byjavadev.yaff.tag.StringYaffTag;

public class YaffTestVirtual
{

    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();

        final Yaff yaff = Yaff.builder().channel().autoSave().allowIncompatibility().create();
        yaff.create("Test", new BooleanYaffTag(true));
        yaff.create("Test_String", new StringYaffTag("foo bar"));
        ((BooleanYaffTag) yaff.get("Test")).setValue(false);
        final byte[] dataOut = ((YaffVirtual) yaff.getContainer()).getYaffChannel().array();
        read(dataOut);

        System.out.println("Took " + (System.currentTimeMillis() - time) + "ms");
    }

    private static void read(final byte[] bytes)
    {
        final Yaff yaffRead = Yaff.builder().channel(bytes).readOnly().allowIncompatibility().create();
        System.out.println(yaffRead.getTagCount());
        System.out.println(yaffRead.get("Test").getValue());
        System.out.println(yaffRead.get("Test_String").getValue());
    }
}
