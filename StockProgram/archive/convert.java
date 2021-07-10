import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class convert
{
    //private static boolean tab = true;

    public static void main(String[] args) throws IOException
    {
        if (args.length <1) { System.out.println("No files to process!"); }
        else { for(String f : args) process(f); }
    }

    private static void process(String f) throws IOException
    {
        System.out.println("Process File: " + f);

        int l = 1;
        int p = 1;
        byte sp = 32;
        byte nl = 10;
        byte tb = 9;

        ArrayList<Byte> data = new ArrayList<Byte>();

        for(String s : Files.readAllLines(Paths.get(f), Charset.defaultCharset()))
        {
            Stack<Byte> S = new Stack<Byte>();

            for(int i = 0; i<s.length(); i++)
            {
                int k = (int) s.charAt(i);
                    if (k==9)
                    {
                        if (f.equals("Makefile")||f.endsWith(".sln")) { p+=1; S.push(tb); }
                        else { p+=4; S.push(sp); S.push(sp); S.push(sp); S.push(sp); }
                    }
                else if (k==13) /* carriage return */  p++;
                else if ((k>31) && (k<128)) { p++; S.push((byte) k); }
                else if (k==nl) { System.out.println("Illegal new line at line: " + l + " position: " + p); System.exit(1); }
                else { System.out.println("Illegal char at line: " + l + " position: " + p + " with value " + k); p++; }
            }

            while ( (!S.empty()) && (S.peek() == sp) ) S.pop();
            Stack<Byte> SS = new Stack<Byte>();
            while (!S.empty()) SS.push(S.pop());
            while (!SS.empty()) data.add(SS.pop());

            data.add(nl); l++; p=1;
        }

        int n = data.size();
        while(data.get(n-1) == nl) n--;

        byte[] byteArray = new byte[1 + n];
        for(int i=0; i<n; i++) byteArray[i]= data.get(i); byteArray[n] = nl;
        FileOutputStream fos = new FileOutputStream(f); fos.write(byteArray); fos.close();
        System.out.println(data.size() + " bytes written to file " + f + "\n\n");
    }
}

