package eo.client.eodata.EMF.Structs;

public class Sign {
    public byte x;
    public byte y;
    public String title;
    public String message;

    public Sign(byte x, byte y, String title, String message) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.message = message;
    }
}
