package eo.client.eodata.EMF.Structs;

import java.util.List;

public class GFXRow {
    public byte y;
    public List<GFX> tiles;

    public GFXRow(byte y, List<GFX> tiles) {
        this.y = y;
        this.tiles = tiles;
    }
}
