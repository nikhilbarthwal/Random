import java.io.File
import java.io.PrintWriter

object Program
{
  var logger:PrintWriter = new PrintWriter("StockProgram.log")

  def Log(msg:String):Unit = logger.println("LOG " + msg)

  private def CreateStocks(e:List[Event]):Map[String, Stock] =
  {
    val m:MyMap[String, Stock] = new MyMap("Stocks portfolio")
    for(s <- CreateSymbols(e)) Process.Process(e, s, m)
    m.Value
  }

  private def CreateSymbols(e:List[Event]): List[String] =
  {
    val s:MyList[String] = new MyList[String]("List of symbols")
    for(x <- e) x.UpdateSymbol(s)
    s.Value.toSet.toList.filter( x => x != "SNAP") // TODO: DEBUG for SNAP
  }

  def Error(msg: String): Unit = System.err.println("ERROR:: " + msg)

  def main(args: Array[String]): Unit =
  {
    if (args.length < 1) { System.err.println("Error: No input file specified!"); System.exit(1) }

    val events = Parse.Read(args(0))
    for(e <- events) System.out.println("Event: " + e) // DEBUG

    this.logger.close()

    //val writer = new PrintWriter(new File("Report.html"))
    //writer.write(Account(Portfolio.CreateHistory(events), CreateStocks(events)).toString())
    //writer.close()
  }
}
