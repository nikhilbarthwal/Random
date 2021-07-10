
case class Portfolio(Cash:Float, Money:Float, Invested:Float)
{
  def Total:Float = Money + Invested

  override def toString: String = s"<TD>$Cash</TD><TD>$Money</TD><TD>$Invested</TD>"
}

object Portfolio
{
  def CurrentPrice(symbol:String):Float = 0 // TODO: Complete this!

  def CreateHistory(events:List[Event]):List[Portfolio] =
  {
    @scala.annotation.tailrec
    def Process(l:List[Event], head0:Portfolio, tail:List[Portfolio]):List[Portfolio] =
      l match {
        case h::t => {
          def head1 = h.UpdatePortfolio(head0)
          Program.Log(h + ": " + head0 + " -> " + head1)
          Process(t, head1, head0::tail)
        }
        case Nil => head0::tail
      }

    Process(events, Portfolio(0, 0, 0), List[Portfolio]()).reverse
  }

  def toString(l:List[Portfolio]): String =
    "<BR/><BR/><TABLE BORDER=2><TR><TH>Cash</TH><TH>Money</TH><TH>Invested</TH></TR>" +
    l.map(x => "<TR>" + x + "</TR>").mkString("\n") +
    "</TABLE><BR/><HR/><BR/>"
}
