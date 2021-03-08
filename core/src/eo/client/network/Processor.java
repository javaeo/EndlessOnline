package eo.client.network;

import java.io.IOException;
import java.util.Arrays;

import static eo.client.utils.Encoding.EncodeNumber;

public class Processor {

    /*

        This is largely for testing, and not to be used outside of testing.

        TODO:: Create an *actual* Packet Processor to be used in the game client

     */

    public static byte rmulti;
    public static byte smulti;
    public static int SequenceStart;
    public static int SequenceValue;
    public static short pid;
    public static int Increment = 1;

    public Packet data;
    public Packet empty;

    public Processor() {
        rmulti = 0;
        smulti = 0;
        SequenceStart = 0;
        SequenceValue = 0;
        pid = 0;
    }

    public void processPacket(Packet packet) { data = packet; } // For testing
    public Packet getPacket() { return data; }

    public static byte getRMulti() { return rmulti; }
    public static byte getSMulti() { return smulti; }

    public static void setMulti(byte rm, byte sm) throws Exception {
        if (rmulti != 0 || smulti != 0) {
            throw new Exception("Multiples set already");
        }

        rmulti = rm;
        smulti = sm;
    }

    public void interleave() {
        byte[] newpacket = new byte[data.Get().size()];
        int i = 0;
        int ii = 00;

        for (; i < data.Get().size(); i += 2) {
            newpacket[i] = data.Get().get(ii);
            ii++;
        }

        --i;

        if (data.Get().size() % 2 != 0) {
            i -= 2;
        }

        for (; i >= 0; i -= 2)
            newpacket[i] = data.Get().get(ii++);

        data = new Packet(newpacket);
    }

    public void deinterleave() {
        byte[] newpacket = new byte[data.Get().size()];
        int i = 0;
        int ii = 0;

        for (; i < data.Get().size(); i +=2)
            newpacket[ii++] = data.Get().get(i);

        --i;

        if (data.Get().size() %2 != 0) {
            i -= 2;
        }


        for(; i >=0; i -= 2)
            newpacket[ii++] = data.Get().get(i);

        data = new Packet(newpacket);
    }

    public void flipMSB() {
        for (int i = 0; i < data.Get().size(); ++i) {
            data.Get().set(i, (byte)(data.Get().get(i) ^ 0x80));
        }
    }

    public void swapMultiples(int multi) {
        int sequenceLength = 0;

        if (multi <= 0)
            return;

        for (int i = 0; i <= data.Get().size(); ++ i) {
            if ( i != data.Get().size() && data.Get().get(i) % multi == 0) {
                ++sequenceLength;
            } else {
                if (sequenceLength > 1) {
                    for (int ii = 0; ii < sequenceLength / 2; ++ii) {
                        byte temp = data.Get().get(i - sequenceLength + ii);
                        data.Get().set(i - sequenceLength + ii, data.Get().get(i - ii - 1));
                        data.Get().set(i - ii - 1, temp);
                    }
                }

                sequenceLength = 0;
            }
        }
    }


    public int getNextSeq(int seq) {
        int old = Increment;
        int next = getNextSeqInc();
        return seq + Increment;
    }

    public int getNewInitialSeq(int seq1, int seq2) {
        return seq1 - seq2;
    }

    public int getNextSeqInc() {
        return (Increment + 1) % 10;
    }

    public void addSeqBytes() {
        int num = SequenceStart >= 253 ? 2 : 1;
        byte[] encodedBytes = EncodeNumber(SequenceStart + Increment, num);
        System.out.println(SequenceStart + " start, " + Arrays.toString(encodedBytes) + " encoded");
        data.InsertBytes(2, encodedBytes);
        Increment++;
    }

    public static short getPid() {
        return pid;
    }

    public static void setPid(short p) {
        pid = p;
    }

    public byte getSeqByte() {
        int val = SequenceValue;

        if (++SequenceValue == 10) {
            SequenceValue = 0;
        }

        return (byte)(SequenceStart + val);
    }

    public static void setSequence(byte b1, byte b2) {
        //return seq1*7 - 11 + seq2 - 2;
        System.out.println(b1 + ", " + b2);
        SequenceStart = Byte.toUnsignedInt(b1) * 7 - 11 + Byte.toUnsignedInt(b2) - 2;
        System.out.println(SequenceStart);
    }

    public static void updateSequence(int b1, int b2) {
        SequenceStart = b1 - b2;
    }


    public void Encode() throws IOException {
        if (smulti == 0 || data.Get().get(1) == (byte)255)
            return;

        addSeqBytes();
        swapMultiples(smulti);
        interleave();
        flipMSB();
    }

    public void Decode() {
        if (rmulti == 0 || data.Get().get(1) == (byte)255)
            return;

        flipMSB();
        deinterleave();
        swapMultiples(rmulti);
    }

    public void Clear() {
        data = empty;
    }

}
