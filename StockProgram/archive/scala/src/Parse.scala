import java.util.Date

object Parse
{
  private def ReadFile(f:String):List[String] =
    io.Source.fromFile(f, "utf-8").getLines.toList.map(_.trim).filter(x => !x.startsWith("#")).filter(_.length>0)

  private def Chop(l:List[String]):List[String] =
    l match {
      case _::_::t => t
      case _ => throw new Exception("Nothing found beyond header")
    }

  private def GetEvent(tokens:Array[String]):Option[MetaEvent] = if (tokens.length <= 1) None else
    ParseDate(tokens(0)) match {
      case None => None
      case Some(d) => tokens(1) match {
       case "MoneyLink Transfer" => Some(E(Cash(Utils.Convert2Float(tokens(9)), d)))
       case "Misc Credits" => Some(E(Misc(Utils.Convert2Float(tokens(7)), d)))
       case "Sell" => Some(E(Sell(tokens(2), Utils.Convert2Float(tokens(5)), Utils.Convert2Int(tokens(4)), d)))
       case "Buy" => Some(E(Buy(tokens(2), Utils.Convert2Float(tokens(5)), Utils.Convert2Int(tokens(4)), d)))
       case "Bank Interest" => Some(E(Interest(Utils.Convert2Float(tokens(7)), d)))
       case "Qualified Dividend" => Some(E(Dividend(tokens(2), Utils.Convert2Float(tokens(7)), d)))
       case "Stock Plan Activity" => Some(E(Dividend(tokens(2), Utils.Convert2Int(tokens(4)), d)))
       case "Cash Dividend" => Some(E(Dividend(tokens(2), Utils.Convert2Float(tokens(7)), d)))
       case "Cancel Sell" => Some(Cancel(Sell(tokens(2), Utils.Convert2Float(tokens(5)), Utils.Convert2Int(tokens(4)), d)))
       case "Journal" => Some(Ignore())
       case _ => None
      }
    }

  private def ParseDate(s:String):Option[Date] =
    {
      val n = s.indexOf(" as of ")
      try Some(Format.parse(if (n == -1) s else s.substring(0,n)))
      catch { case _:java.text.ParseException => None }
    }

  def Format = new java.text.SimpleDateFormat("MM/dd/yyyy")

  def Read(f:String):List[Event] =
  {
    val e = new MyList[MetaEvent]("List of all events")
    for(s <- Chop(ReadFile(f)))
      GetEvent(s.split(",").map(Utils.Chop)) match {
        case Some(x) => Program.Log("Parsed \"" + s + "\" as " + x) ; e.Add(x)
        case None => throw new Exception("Could not parse \""+ s + "\"")
      }

    MetaEvent.Filter(e.Value)
  }
}
