package eo.client.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static eo.client.utils.Utility.listToByteArray;

public class Encoding {

    public static final int[] MAX = { 253, 64009, 16194277 };

    /*

        Encoding numbers to EO Format

     */

    public static byte[] EncodeNumber(Integer number, int size) {
        byte[] b = new byte[size];
        for (int i = 3; i >= 1; --i) {
            if (i >= b.length) {
                if (number>= MAX[i - 1]) {
                    number = number % MAX[i - 1];
                }
            } else if (number >= MAX[i - 1]) {
                b[i] = (byte) (Integer.divideUnsigned(number, MAX[i - 1]) + 1);
                number = number % MAX[i - 1];
            }
            else {
                b[i] = Integer.valueOf(254).byteValue();
            }
        }
        b[0] = (byte)(number + 1);
        return b;
    }

    /*

        Decode numbers from EO Format

     */

    public static int DecodeNumber(byte[] b) {
        for (int i = 0; i < b.length; ++i) {
            if (b[i] == 0 || Byte.toUnsignedInt(b[i]) == Byte.toUnsignedInt((byte) 254))
                b[i] = 0;
            else
                --b[i];
        }

        int a = 0;

        for (int i = b.length - 1; i >= 1; --i) {
            a += Byte.toUnsignedInt(b[i]) * MAX[i - 1];
        }

        return a + Byte.toUnsignedInt(b[0]);
    }

    //

    public static byte[] EncodeString(String str) {
        return new String(DecodeString(str.getBytes(StandardCharsets.US_ASCII)).getBytes(), StandardCharsets.US_ASCII).getBytes(StandardCharsets.US_ASCII);
    }

    public static String DecodeString(byte[] chars) {
        List<Byte> list = new ArrayList<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            list.add(chars[i]);
        }

        Collections.reverse(list);

        boolean flippy = (list.size() % 2) == 1;

        for (int i = 0; i < list.size(); i++) {
            byte c = list.get(i);
            if (flippy) {
                if (c >= 0x22 && c <= 0x4F)
                    c = (byte) (0x71 - c);
                else if (c >= 0x50 && c <= 0x7E)
                    c = (byte) (0xCD - c);
            } else {
                if (c >= 0x22 && c <= 0x7E)
                    c = (byte)(0x9F - c);
            }

            list.set(i, c);
            flippy = !flippy;
        }

        return new String(listToByteArray(list), StandardCharsets.US_ASCII);
    }

}
