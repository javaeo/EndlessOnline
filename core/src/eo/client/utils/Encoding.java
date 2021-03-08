package eo.client.utils;

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
            if (b[i] == 0 || b[i] == (byte) 254)
                b[i] = 0;
            else
                --b[i];
        }

        int a = 0;

        for (int i = b.length - 1; i >= 1; --i) {
            a += b[i] * MAX[i - 1];
        }

        return a + b[0];
    }

    /*

    Packet Encoding functions

     */


}
