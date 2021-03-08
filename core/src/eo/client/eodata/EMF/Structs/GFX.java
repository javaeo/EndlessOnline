package eo.client.eodata.EMF.Structs;

public class GFX {
    public byte x;
    public short tile;

    public GFX(byte x, short tile) {
        this.x = x;
        this.tile = tile;
    }

    public short GfxID() {
        return tile;
    }
}
