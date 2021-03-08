package eo.client.eodata.EMF.Structs;

public class Warp {
    public byte x;
    public short warpMap;
    public byte warpX;
    public byte warpY;
    public byte levelReq;
    public short door;

    public Warp(byte x, short warpMap, byte warpX, byte warpY, byte levelReq, short door) {
        this.x = x;
        this.warpMap = warpMap;
        this.warpX = warpX;
        this.warpY = warpY;
        this.levelReq = levelReq;
        this.door = door;
    }
}
