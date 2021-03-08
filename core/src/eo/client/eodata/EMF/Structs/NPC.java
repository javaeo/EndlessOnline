package eo.client.eodata.EMF.Structs;

public class NPC {
    public byte x;
    public byte y;
    public short id;

    public byte spawnType;
    public short spawnTime;
    public byte amount;

    public NPC(byte x, byte y, short id, byte spawnType, short spawnTime, byte amount) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.spawnType = spawnType;
        this.spawnTime = spawnTime;
        this.amount = amount;
    }
}
