import java.io._



class Point(val xc: Int, val yc: Int) extends Expression
{
   val x: Int = xc
   val y: Int = yc

}

class Point2(val yc: Int) extends Expression
{
   val x: Int = yc
   val y: Int = yc

}

abstract class Expression { }

object Demo {
   def main(args: Array[String]) {
      //val pt: Expression = new Point(10, 20);
val pt: Expression = new Point2(10);

      pt match
      {
         case s:Point => println("point1")
         case _:Point2 => println("point2")
      }

      // Move to a new location
      //pt.move(10, 10);
   }
}
