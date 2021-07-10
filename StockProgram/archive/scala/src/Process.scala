import java.util.Date

object Process
{
  private case class Item(Price:Float, Quantity:Int, Date:Date)
  {
    def Reduce(q:Int): Item =
      if (q>Quantity)
        throw new Exception("Excessive reduction on item")
      else
        Item(Price, Quantity-q, Date)
  }

  private case class State(BuyItems:List[Item], Sales:List[Sale])

  def Process(e:List[Event], sym:String, m:MyMap[String, Stock]):Unit =
  {
    // Collect all the buy & sell items for stock sym
    val buy = new MyList[Item]("Set of Buy items")
    val sell = new MyList[Item]("Set of Sell items")
    for(x <- e)
      x match {
        case Buy(s,p,q,d) => if (sym == s) buy.Add(Item(p,q,d))
        case Sell(s,p,q,d) => if (sym == s) sell.Add(Item(p,q,d))
        case _ => ()
      }


    //Fold function doing the processing
    @scala.annotation.tailrec
    def ProcessItem(z:State, s:Item):State =
      if (s.Quantity == 0) z else
        z.BuyItems match {
          case Nil => throw new Exception("Sell exceeds Buying for Stock "+sym)
          case h :: t =>
            def NewState(x: Int, l: List[Item]) = State(l, Sale(x, h.Price, h.Date, s.Price, s.Date)::z.Sales)

            if (h.Date.compareTo(s.Date) == 1)
              ProcessItem(State(t, z.Sales), s)
            else {
              if (h.Quantity >= s.Quantity)
                NewState(s.Quantity, if (s.Quantity == 0) t else h.Reduce(s.Quantity) :: t)
              else
                ProcessItem(NewState(h.Quantity, t), s.Reduce(h.Quantity))
          }
        }

    val result = sell.Value.foldLeft(State(buy.Value, List())) {ProcessItem}
    val quantity = result.BuyItems.map(_.Quantity).sum
    val current = Portfolio.CurrentPrice(sym)
    m.Add(sym, Stock(sym, quantity, current, result.Sales))
  }
/*

  alias state = Tuple[List[Buy], List[Sale]]
  alias buys = List[Buy]
  alias sales = List[Sale]

  private def CalculateSales(buys:List[Buy], sells:List[Sell):Tuple[List[Buy], List[Sale]] =
  {
    val sales = new MyList[Sale]("Sales")

    del Finalize(input:List[Buy], residue:List[Buy]) = residue.reverse ++ input
    del Update(head:Buy, tail:List[Buy]) = if (h.quantity = 0) tail else head::tail

    @scala.annotation.tailrec
    def Calculate(input:List[Buy], sell:Sell, residue:List[Buy]):List[Buy] =
      if (sell.Quantity == 0)
        Finalize(input, residue)
      else
        input match {
          case Buy(s, p, q, d)::t if (s == g.Symbol) && (d.compareTo(g.Date)==1) =>
          {
             def min = Utils.Min(q, g.Quantity)
             sales.Add(Sale(min, p, Sell.price, Sell.date))
             Calculate(Update(Buy(s, p, min, d), t), Sell.Reduce(min), Residue)
          }
          case h::t => Calculate(t, sell, h::residue)
          case Nil => // Sell exceeds Buying
        }

    (sell.foldLeft(buys) { (s,x) => Calculate(s, x, List())}, sales.Value)
  }
  */
}
