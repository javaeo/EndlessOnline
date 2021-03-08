package eo.client.eodata.EMF.Structs;

public class Chest {
    public byte x;
    public byte y;
    public short key;
    public byte slot;
    public short item;
    public short time;
    public int amount;

    public Chest(byte x, byte y, short key, byte slot, short item, short time, int amount) {
        this.x = x;
        this.y = y;
        this.key = key;
        this.slot = slot;
        this.item = item;
        this.time = time;
        this.amount = amount;
    }
}
