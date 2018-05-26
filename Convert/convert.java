import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class convert
{
    private static boolean tab = false;

    public static void main(String[] args) throws IOException
    {
        if (args.length <1) { System.out.println("No files to process!"); }
        else { for(String f : args) process(f); }
    }
    
    private static void process(String f) throws IOException
    {
        System.out.println("Process File: " + f);
        String s = new String(Files.readAllBytes(Paths.get(f)));
        
        int l = 1;
        int p = 1;
        ArrayList<Byte> data = new ArrayList<Byte>();
        for(int i = 0; i< s.length(); i++)
        {
               int k = (int) s.charAt(i);
               if (k==9) /* tab*/ { p++;if (tab) data.add((byte)9);
                   else {byte b = 32; data.add(b);data.add(b);data.add(b);data.add(b);} }
               else if (k==10) /* new Line */ { data.add((byte) k); l++; p=1;  } 
               else if (k==13) /* carriage return */  {  p++; continue;  }
               else if ((k>31) && (k<128)) { p++; data.add((byte) k); }
               else { System.out.println("Illegal char at line: "+l+" position: "+p+" with value "+k); }
        }

        byte[] byteArray = new byte[data.size()];
        for(int i=0; i<data.size(); i++) byteArray[i]= data.get(i);
        FileOutputStream fos = new FileOutputStream(f); fos.write(byteArray); fos.close();
        System.out.println(data.size()+" bytes written to file "+f+"\n\n");
    }
}
