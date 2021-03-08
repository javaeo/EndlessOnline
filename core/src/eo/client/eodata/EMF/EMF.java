package eo.client.eodata.EMF;

import eo.client.eodata.EMF.Structs.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static eo.client.utils.Encoding.DecodeString;
import static eo.client.utils.Utility.listToByteArray;

public class EMF {
    public Reader reader;

    public final short noDoor = 0;
    public final short Door = 1;
    public final int GFXLayers = 9;

    public List<NPC> npcs = new ArrayList<>();
    public List<Unknown> unknowns = new ArrayList<>();
    public List<Chest> chests = new ArrayList<>();
    public List<TileRow> tileRows = new ArrayList<>();
    public List<WarpRow> warpRows = new ArrayList<>();
    public ArrayList<GFXRow>[] gfxRow = new ArrayList[GFXLayers];
    public List<Sign> signs = new ArrayList<>();

    public byte[] revisionId; // = reader.GetBytes(4); // gets the 4 bytes of "revision id"
    public byte[] rawName; // = reader.GetBytes(24); // gets the full raw map name // DecodeString(rawName);
    public String name; // = reader.GetString();
    public byte type; // = reader.GetChar();
    public byte effect; // = reader.GetChar();
    public byte music; // = reader.GetChar();
    public byte musicExtra; // reader.GetChar();
    public short ambientNoise; // = reader.GetShort();
    public byte width; // = reader.GetChar();
    public byte height; // = reader.GetChar();
    public short fillTile; // = reader.GetShort();
    public byte mapAvailable; // = reader.GetChar();
    public byte canScroll; // = reader.GetChar();
    public byte relogX; // = reader.GetChar();
    public byte relogY; // = reader.GetChar();
    public byte unknown; // = reader.GetChar();

    public EMF(String filename) throws Exception {
        reader = new Reader(new File(filename));
        for (int i = 0; i < GFXLayers; i++) {
            gfxRow[i] = new ArrayList<GFXRow>(); // Initialize the 2d array of gfx rows
        }
        Read();
    }

    public void Read() throws Exception {
        int outersize;
        int innersize;

        String ext = new String(reader.GetBytes(3), StandardCharsets.US_ASCII);

        if (!ext.equals("EMF")) {
            throw new Exception("File corrupt or not EMF file");
        }

        revisionId = reader.GetBytes(4);
        rawName = reader.GetBytes(24);
        List<Byte> nameList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (rawName[i] != (byte) 0xFF) {
                nameList.add(rawName[i]);
            }
        }

        name = DecodeString(listToByteArray(nameList));
        type = reader.GetChar();
        effect = reader.GetChar();
        music = reader.GetChar();
        musicExtra = reader.GetChar();
        ambientNoise = reader.GetShort();
        width = reader.GetChar();
        height = reader.GetChar();
        fillTile = reader.GetShort();
        mapAvailable = reader.GetChar();
        canScroll = reader.GetChar();
        relogX = reader.GetChar();
        relogY = reader.GetChar();
        unknown = reader.GetChar();

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            npcs.add(new NPC(
                    reader.GetChar(), //x
                    reader.GetChar(), //y
                    reader.GetShort(), //id
                    reader.GetChar(), //spawntype
                    reader.GetShort(), //spawntime
                    reader.GetChar()) //amount
            );
        }

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            unknowns.add(new Unknown(reader.GetBytes(5)));
        }

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            chests.add(new Chest(
                    reader.GetChar(), //x
                    reader.GetChar(), //y
                    reader.GetShort(), //key
                    reader.GetChar(), //slot
                    reader.GetShort(), //item
                    reader.GetShort(), //time
                    reader.GetThree() //amount
            ));
        }

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            byte y = reader.GetChar();
            innersize = reader.GetChar();
            List<Tile> tiles = new ArrayList<>(innersize);

            for (int ii = 0; ii < innersize; ii++) {
                tiles.add(new Tile(
                        reader.GetChar(),
                        reader.GetChar()
                ));
            }

            TileRow row = new TileRow(y, tiles);
            tileRows.add(row);
        }

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            byte y = reader.GetChar();
            innersize = reader.GetChar();
            List<Warp> tiles = new ArrayList<>(innersize);

            for (int ii = 0; ii < innersize; ii++) {
                tiles.add(new Warp(
                        reader.GetChar(), // x
                        reader.GetShort(), //warpMap
                        reader.GetChar(), //warpX
                        reader.GetChar(), //warpY
                        reader.GetChar(), //levelReq
                        reader.GetShort() //door
                ));
            }

            WarpRow row = new WarpRow(y, tiles);
            warpRows.add(row);
        }

        // GFX
        for (int layer = 0; layer < GFXLayers; layer++) {
            outersize = reader.GetChar();
            gfxRow[layer] = new ArrayList<>(outersize);

            for (int i = 0; i < outersize; i++) {
                byte y = reader.GetChar();
                innersize = reader.GetChar();
                List<GFX> tiles = new ArrayList<>(innersize);

                for (int ii = 0; ii < innersize; ii++) {
                    tiles.add(new GFX(
                            reader.GetChar(), // x
                            reader.GetShort() // tile
                    ));
                }

                GFXRow row = new GFXRow(y, tiles);

                gfxRow[layer].add(row);
            }
        }

        outersize = reader.GetChar();

        for (int i = 0; i < outersize; i++) {
            byte x = reader.GetChar();
            byte y = reader.GetChar();
            int len = reader.GetShort() - 1;
            String data = new String(reader.GetBytes(len), StandardCharsets.US_ASCII); // Decode String!!!!!!!!
            int titlen = reader.GetChar();
            String title = data.substring(0, titlen);
            String msg = data.substring(titlen);

            signs.add(new Sign(x, y, title, msg));
        }
    }
}
