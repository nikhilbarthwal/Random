import java.util.Date

case class Stock(Symbol:String, Quantity:Integer, CurrentPrice:Float, Sales:List[Sale])
{
  def Value:Float = Quantity.floatValue() * CurrentPrice

  def Profit:Float = Sales.map( x => x.Profit).sum

  override def toString(): String =
    List(
      s"Symbol: <B>$Symbol%s</B><BR/>",
      s"Quantity: <B>$Quantity%d</B><BR/>",
      s"Current Price: <B>$CurrentPrice%.3f</B><BR/>",
      s"Value: <B>$Value%.3f</B><BR/>",
      s"Profit: <B>$Profit%.3f</B><BR/>",
      s"<TABLE BORDER=2><TR>$Sale.Headline%s</TR>").mkString("\n") +
    Sales.map(x => "<TR>" + x + "</TR>").mkString("\n") +
    "</TABLE><BR/><HR/><BR/>"
}


// _________________________________________________________________________________________________


case class Sale(Quantity:Integer, BuyPrice:Float, BuyDate:Date, SellPrice:Float, SellDate:Date)
{
  def Profit: Float = Quantity.floatValue() * (SellPrice - BuyPrice)

  override def toString(): String =
  {
    s"<TD>$this.Quantity%d</TD><TD>$this.BuyPrice%.3f</TD><TD>$this.BuyDate%s</TD>" +
      s"<TD>$SellPrice%.3f</TD><TD>$SellDate%s</TD><TD>$Profit%.3f</TD>"
  }
}

object Sale
{
  def Headline =
    "<TH>Quantity</TH><TH>Buy Price</TH><TH>Buy Date</TH><TH>Sell Price</TH><TH>Sell Date</TH><TH>Profit</TH>"
}
