package eo.client.eodata.EMF.Structs;

import java.util.List;

public class WarpRow {
    public byte y;
    public List<Warp> tiles;

    public WarpRow(byte y, List<Warp> tiles) {
        this.y = y;
        this.tiles = tiles;
    }
}
