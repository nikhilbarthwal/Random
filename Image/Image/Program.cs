using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            var inp = (Bitmap) Image.FromFile("in2.bmp");
            var size = inp.Size;
            var height = size.Height;
            var width = size.Width;
            var output = new Bitmap(2*width, 2*height);

            Console.WriteLine("Top-Left corner");
            for (int y=0; y<height; y++)
                for(int x=0; x<width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x; int y2 = y;
                    var c = inp.GetPixel(x1,y1);
                    output.SetPixel(x2,y2,c);
                }

            Console.WriteLine("Bottom-Left corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x; int y2 = 2*height - y -1;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }

            Console.WriteLine("Top-Right corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x + width; int y2 = y;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }

            Console.WriteLine("Bottom-Right corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x + width; int y2 = 2 * height - y - 1;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }
 
            output.Save("out2.bmp");
        }
    }

    class Program2
    {
        static void Main2(string[] args)
        {
            var inp = (Bitmap) Image.FromFile("in1.bmp");
            var size = inp.Size;
            var height = size.Height;
            var width = size.Width;
            var output = new Bitmap(2*width, 2*height);

            Console.WriteLine("Top-Left corner");
            for (int y=0; y<height; y++)
                for(int x=0; x<width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x; int y2 = y;
                    var c = inp.GetPixel(x1,y1);
                    output.SetPixel(x2,y2,c);
                }

            Console.WriteLine("Bottom-Left corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = x; int y2 = y + height;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }

            Console.WriteLine("Top-Right corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = 2*width - x - 1; int y2 = y;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }

            Console.WriteLine("Bottom-Right corner");
            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++)
                {
                    int x1 = x; int y1 = y;
                    int x2 = 2 * width - x - 1; int y2 = y + height;
                    var c = inp.GetPixel(x1, y1);
                    output.SetPixel(x2, y2, c);
                }
 
            output.Save("out1.bmp");
        }
    }
}
