package eo.client.network;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eo.client.utils.Encoding.DecodeNumber;
import static eo.client.utils.Encoding.EncodeNumber;
import static eo.client.utils.Utility.*;

public class Packet {

    public List<Byte> data;
    private int readpos = 2;
    private int writepos = 2;

    public static byte Break = (byte) 0xFF;

    // Constructors

    /*

    Creates a new packet with the family and action specified.

     */

    public Packet(byte family, byte action) {
        data = new ArrayList<>(2);
        data.add(action);
        data.add(family);
    }

    /*

    Creates a new packet using a byte array.

     */
    public Packet(byte[] data) {
        Byte[] dataObj = new Byte[data.length];
        int i = 0;
        for (byte b: data)
            dataObj[i++] = b;
        this.data = Arrays.asList(dataObj);
    }

    /*

    Creates a new packet using a Byte List

     */

    public Packet(List<Byte> data) {
        this.data = data;
    }

    /*

        Set read/write position for GetX/AddX functions

     */

    public void setReadPos(int pos) {
        if (pos < 0 || pos > data.size()) {
            throw new IndexOutOfBoundsException();
        }

        readpos = pos;
    }

    public void setWritepos(int pos) {
        if (pos < 0 || pos > data.size()) {
            throw new IndexOutOfBoundsException();
        }

        writepos = pos;
    }

    /*

        Add data to packets

     */

    public void AddByte(byte b) {
        data.add(writepos, b);
        writepos += 1;
    }

    public void AddBytes(byte[] b) {
        data.addAll(writepos, byteArrayToList(b));
        writepos += b.length;
    }

    public void AddChar(byte c) {
        byte[] _c = EncodeNumber(Byte.valueOf(c).intValue(), 1);
        data.addAll(writepos, byteArrayToList(_c));
        writepos += 1;
    }

    public void AddShort(short s) {
        byte[] _s = EncodeNumber(Short.valueOf(s).intValue(), 2);
        data.addAll(writepos, byteArrayToList(_s));
        writepos += 2;
    }

    public void AddThree(int t) {
        byte[] _t = EncodeNumber(t, 3);
        data.addAll(writepos, byteArrayToList(_t));
        writepos += 3;
    }

    public void AddInt(int i) {
        byte[] _i = EncodeNumber(i, 4);
        data.addAll(writepos, byteArrayToList(_i));
        writepos += 4;
    }

    public void AddString(String s) {
        byte[] b = s.getBytes(StandardCharsets.US_ASCII);
        AddBytes(b);
    }

    public void AddBreakString(String s) {
        byte[] b = s.getBytes(StandardCharsets.US_ASCII);
        for (int i = 0; i < b.length ; ++i) {
            if (b[i] == (byte) 255 ) {
                b[i] = (byte)'y';
                System.out.println("XXXXXXX" + b[i]);
            }
        }
        AddBytes(b);
        AddBreak();
    }

    public void AddBreak() {
        AddByte(Break);
    }

    /*

        Insert bytes at position x

     */

    public void InsertByte(int pos, byte b) {
        data.add(pos, b);
    }

    public void InsertBytes(int pos, byte[] b) {
        data.addAll(pos, byteArrayToList(b));
    }

    /*

        Get data from packets

     */

    public byte GetByte() {
        byte b = data.get(readpos);
        return b;
    }

    public byte[] GetBytes(int length) {
        byte[] b = rangeToByteArray(data, readpos, length);
        readpos += length;
        return b;
    }

    public byte GetChar() {
        byte temp = (byte) DecodeNumber(rangeToByteArray(data, readpos, 1));
        readpos += 1;
        return temp;
    }

    public short GetShort() {
        short temp = (short) DecodeNumber(rangeToByteArray(data, readpos, 2));
        readpos += 2;
        return temp;
    }

    public int GetThree() {
        int temp = DecodeNumber(rangeToByteArray(data, readpos, 3));
        readpos += 3;
        return temp;
    }

    public int GetInt() {
        int temp = DecodeNumber(rangeToByteArray(data, readpos, 4));
        readpos += 4;
        return temp;
    }

    public String GetFixedString(int length) {
        return new String(GetBytes(length), StandardCharsets.US_ASCII);
    }

    public String GetBreakString() {
        String tmp = PeekBreakString();
        readpos += tmp.getBytes(StandardCharsets.US_ASCII).length + 1;
        return tmp;
    }

    public String GetEndString() {
        return GetFixedString(data.size() - readpos);
    }

    /*

        Peek functions, or your Get functions without moving the read position.

     */

    public byte[] PeekBytes(int length) {
        byte[] b = rangeToByteArray(data, readpos, length);
        return b;
    }

    public String PeekBreakString() {
        int breakpos = readpos;
        while (breakpos < data.size() && data.get(breakpos) != Break)
            ++breakpos;
        return PeekFixedString(breakpos - readpos);
    }

    public String PeekFixedString(int length) {
        return new String(PeekBytes(length), StandardCharsets.US_ASCII);
    }

    /*

        Extra functions

     */

    //skip readpos
    public void Skip(int bytes) {
        readpos += bytes;
    }

    public void JumpTo(char breaker) {
        int length = readpos;

        for (int i = readpos; i < data.size(); i++) {
            if (Get().get(i) == breaker) {
                length = i;
                break;
            }
        }
        readpos = length + 1;
    }

    // returns the Byte List of the packet
    public List<Byte> Get() {
        return data;
    }

    //returns the raw byte array of the packet
    public byte[] GetRaw() {
        return listToByteArray(data);
    }
}
