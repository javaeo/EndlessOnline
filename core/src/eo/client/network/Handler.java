package eo.client.network;

public class Handler {

    /*

        I don't know what to do with this lol.

     */

    public Handler() {

    }

    public void handleInit(Packet packet, Processor p) throws Exception {
        int id = packet.GetByte();
        byte emulti_e;
        byte emulti_d;

        switch (id)
        {
            case (1):
                packet.GetByte();
                packet.GetByte();

                byte minvers = packet.GetChar();
                System.out.println("[Connection Refused] The client you are using is out of date, this server requires version 0.000.0."+minvers);
                break;
            case (2):
            {
                byte sm = packet.GetByte();
                byte sm1 = packet.GetByte();

                emulti_d = packet.GetByte();
                emulti_e = packet.GetByte();
                short pid = packet.GetShort();
                int response = packet.GetThree();

                System.out.println("Connection accepted  (" + sm + ", " + sm1 + ") (" + emulti_d + ", " + emulti_e + ") PID: " + pid + " , response: " + response);
                Processor.setMulti(emulti_d, emulti_e);
                Processor.setSequence(sm, sm1);
                Processor.setPid(pid);

                // Open login menu
                break;
            }
            case (3):
                int bid = packet.GetByte();
                int time = packet.GetByte();
                if (bid == 0) {
                    System.out.println("[Connection is blocked] You are banned for " + time + " minutes");
                } else if (bid == 2) {
                    System.out.println("[Connection is blocked] You are permanently banned.");
                }
                break;
            default:
            {
                System.out.println("Connection Error (" + id + "), Invalid initialization packet");
                break;
            }
        }
    }
}
