//import java.util.Date

case class Account(History:List[Portfolio], Stocks:Map[String, Stock])
{
  def TotalCash:Float = History.head.Cash
  def TotalMoney:Float = History.head.Money
  def TotalInvested:Float = Stocks.values.toList.map(_.Value).sum
  def TotalValue:Float = this.TotalMoney + this.TotalInvested
  def TotalProfit:Float = this.TotalValue - this.TotalCash

  override def toString(): String =
    s"Total Cash: <B>$TotalCash%.3f</B><BR/>\n" +
    s"Total Invested: <B>$TotalInvested%.3f</B><BR/>" +
    s"Total Value: <B>$TotalValue%.3f</B><BR/>" +
    s"Total Profit: <B>$TotalProfit%.3f</B><BR/><BR/>" +
    Portfolio.toString(History) +
    Stocks.values.map(_.toString()).mkString("\n")
}

/*
type account = {
  TotalCash:Float,
  Stocks:Map<string,stock>
} with
member private this.StocksAggregate (f:stock->float32) =
  let mapFun x = let z = this.Stocks.[x] in (f z)
this.Symbols |> Set.toList|> List.map mapFun |> List.sum

member this.TotalProfit:float32 =
this.StocksAggregate (fun (x:stock) -> x.Profit)

member this.TotalInvested:float32 =
this.StocksAggregate (fun (x:stock) -> x.Value)

member this.TotalValue:float32 =
this.TotalInvested + this.TotalCash

member this.Print:string list =
let header =
[ (sprintf "Total Cash: <B>%.3f</B><BR/>" this.TotalCash);
(sprintf "Total Profit: <B>%.3f</B><BR/>" this.TotalProfit);
(sprintf "Total Invested: <B>%.3f</B><BR/>" this.TotalInvested);
(sprintf "Total Value: <B>%.3f</B><BR/>" this.TotalValue);
"<HR/><BR/>"]

let body = List.collect (fun x -> (this.Stocks.[x]).Print) (Set.toList this.Symbols)

header @ body
*/
