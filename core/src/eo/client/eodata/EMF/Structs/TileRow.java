package eo.client.eodata.EMF.Structs;

import java.util.List;

public class TileRow {
    public byte y;
    public List<Tile> tiles;

    public TileRow(byte y, List<Tile> tiles) {
        this.y = y;
        this.tiles = tiles;
    }
}
