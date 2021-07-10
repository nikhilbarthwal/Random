import java.util.Date

sealed abstract class Event
{
  def UpdatePortfolio(x:Portfolio):Portfolio
  def UpdateSymbol(x:MyList[String]):Unit
  def Filter:Boolean
}

case class Cash(Money:Float, Date: Date) extends Event
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash + Money, x.Money + Money, x.Invested)
  def UpdateSymbol(x:MyList[String]) = ()
  def Filter = Money > 0
}

case class Buy(Symbol:String, Price:Float, Quantity:Integer, Date:Date) extends Event // Add reduce
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash, x.Money - (Price * Quantity), x.Invested + (Price * Quantity))
  def UpdateSymbol(x:MyList[String]) = x.Add(Symbol)
  def Filter = Quantity > 0
}

case class Sell(Symbol:String, Price:Float, Quantity:Integer, Date:Date) extends Event // Add reduce
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash, x.Money + (Price * Quantity), x.Invested - (Price * Quantity))
  def UpdateSymbol(x:MyList[String]) = x.Add(Symbol)
  def Filter = Quantity > 0
}

case class Interest(Money:Float, Date: Date) extends Event
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash, x.Money + Money, x.Invested)
  def UpdateSymbol(x:MyList[String]) = ()
  def Filter = Money > 0
}

case class Dividend(Symbol:String, Money:Float, Date:Date) extends Event
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash, x.Money + Money, x.Invested)
  def UpdateSymbol(x:MyList[String]) = x.Add(Symbol)
  def Filter = Money > 0
}

case class Misc(Money:Float, Date: Date) extends Event
{
  def UpdatePortfolio(x:Portfolio) = Portfolio(x.Cash, x.Money + Money, x.Invested)
  def UpdateSymbol(x:MyList[String]) = ()
  def Filter = Money > 0
}

object Event
{
  def PrintList(l:List[Event]):Unit = for(e <- l) Program.Log(" -" + e)
}
