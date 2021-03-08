package eo.client.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    /*

        Utility functions I can't find a place for anywhere else :shrug:

     */

    public static byte[] rangeToByteArray(List<Byte> b, int startpos, int count) {
        byte[] _b = new byte[count];
        for (int i = 0; i < count; i++) {
            _b[i] = b.get(startpos + i);
        }
        return _b;
    }

    public static List<Byte> byteArrayToList(byte[] b) {
        List<Byte> list = new ArrayList<Byte>();
        for (byte _b : b)
            list.add(_b);
        return list;
    }

    public static byte[] listToByteArray(List<Byte> list) {
        byte[] array = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static String getSerialNumber() {
        String result = "";
        String drive = "C"; // temporary, come back for this!
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    +"Set colDrives = objFSO.Drives\n"
                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    +"Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result.trim();
    }

}
