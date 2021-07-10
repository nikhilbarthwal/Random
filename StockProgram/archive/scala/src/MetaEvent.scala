import java.util.Date

sealed abstract class MetaEvent
case class E(Event:Event) extends MetaEvent
case class Cancel(Event:Event) extends MetaEvent
case class Ignore() extends MetaEvent
case class Grant(Symbol:String, Quantity:Integer, Date:Date) extends MetaEvent
{
  def Reduce(q:Int): Grant =
    if (q>Quantity)
      throw new Exception("Excessive reduction on item")
    else
      Grant(Symbol, Quantity-q, Date)
}


object MetaEvent
{
  private val Grants:MyList[Grant] = new MyList("Grants list")

  private def Match(x:Event, y:Event):Boolean =
    (x,y) match {
      case (Sell(s1, _, q1, _), Sell(s2, _, q2, _)) => (s1 == s2) && (q1 == q2)
      case _ => false
    }

  private def RemoveGrants(e:List[Event]):List[Event] =
  {
    @scala.annotation.tailrec // TODO: Make it state in & out
    def RemoveGrant(input:List[Event], g:Grant, output:List[Event]):List[Event] =
      if (g.Quantity == 0) output.reverse else
      input match {
        case Sell(s, _, q, d)::tail if (s == g.Symbol) && (d.compareTo(g.Date)==1) =>
          if (q > g.Quantity)
            ((Sell(s, p, q-g.Quantity, d)::output).reverse) ++ tail
            //RemoveGrant(t, g, Sell(s, p, q-g.Quantity, d)::output)
          else
            RemoveGrant(tail, g.Reduce(q), output)
        case h::t => RemoveGrant(t, g, h::output)
        case Nil => output.reverse
      }

    this.Grants.Value.foldLeft(e) { (s,x) => RemoveGrant(s, x, List())}
  }

  def Filter(input:List[MetaEvent]):List[Event] =
  {
    val output:MyList[Event] = new MyList("collection for filter")

    @scala.annotation.tailrec
    def FilterHelper(z:List[MetaEvent]):Unit =
      z match {
        case E(x)::Cancel(y)::t =>
          if (Match(x,y)) FilterHelper(t) // ADd log here
          else throw new Exception("Non-Matching Sell/Cancel event")
        case E(x)::t => output.Add(x); FilterHelper(t)
        case Ignore()::t => FilterHelper(t)
        case Grant(s, q, d)::t => Grants.Add(Grant(s,q,d)); FilterHelper(t)
        case Cancel(_)::_ => throw new Exception("Misplaced cancel event")
        case Nil => ()
      }

    FilterHelper(input)
    RemoveGrants(output.Value).filter( x => x.Filter)
  }
}
