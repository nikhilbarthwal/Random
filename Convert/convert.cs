using System;
using System.IO;
using System.Collections.Generic;

class Process
{
    static void ProcessFile(string f)
    {        
            Console.WriteLine("Process File: {0}", f);
            string[] lines = File.ReadAllLines(f);
            int l = 0;
            int p;
            List<byte> data = new List<byte>();
            foreach(string s in lines)
            {
               p=0;
               l++;
                 for(int i = 0; i< s.Length; i++)
                 {
                   p++;
                   int k = Convert.ToInt32(s[i]);
                   if (k==9) /* tab*/ { byte b = 32; data.Add(b);data.Add(b);data.Add(b);data.Add(b); }
                   else if (k==10) /* new Line */ { byte b = (byte) k; data.Add(b);  } 
                   else if (k==13) /* cariage return */  {  continue;    }
                   else if ((k>31) && (k<128)) { byte b = (byte) k; data.Add(b); }
                   else { Console.WriteLine("Illegal char at line: {0}, position: {1} with value {2}", l, p, k); }
                }
                byte bb = 10; data.Add(bb);

            }

            int c =0;
            using(FileStream  fileStream = new FileStream(f, FileMode.Create)) 
                  foreach(byte b  in data)  { fileStream.WriteByte(b); c++; }
            Console.WriteLine("Bytes written: {0}\n\n", c);  

    }    
    
    static void Main(string[] args)
    {        
        if (args.Length < 1) { Console.WriteLine("Usage: convert <file>"); }
        else
           foreach(string f in args) ProcessFile(f);
    }
}
