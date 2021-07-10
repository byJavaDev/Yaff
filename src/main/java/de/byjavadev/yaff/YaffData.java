package de.byjavadev.yaff;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YaffData
{
    BOOLEAN((byte) 0x1),
    BYTE((byte) 0x2),
    SHORT((byte) 0x3),
    INTEGER((byte) 0x4),
    LONG((byte) 0x5),
    FLOAT((byte) 0x6),
    DOUBLE((byte) 0x7),
    OBJECT((byte) 0x8),
    LIST((byte) 0x9),
    STRING((byte) 0xA),
    ARRAY_BYTE((byte) 0xB);

    /** The identifier of this data flavor */
    private final byte id;

    public static YaffData getDataType(final byte id)
    {
        for (YaffData value : values())
        {
            if(value.getId() == id)
                return value;
        } return null;
    }
}
