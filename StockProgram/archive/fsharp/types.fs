module Types

open System

type transactionType = Sell | Buy

type Transaction =
    { Type:transactionType; Symbol:string; Price:float32; Quantity:int; Date:DateTime }
    // with TODO total

type event =
    | Cash of Money:float32 * Date:DateTime
    | Transaction of t:Transaction
    with
        member this.Date = function Cash(_,d) -> d | Transaction(t) -> t.Date

type sale = {
    Quantity: int;
    BuyPrice: float32;
    BuyDate: DateTime;
    SellPrice: float32;
    SellDate: DateTime
} with
    member this.Profit = float32(this.Quantity) * (this.SellPrice-this.BuyPrice)
    member this.Print :string =
        (sprintf "<TD>%d</TD><TD>%.3f</TD><TD>%s</TD>" this.Quantity this.BuyPrice (this.BuyDate.ToString()) ) +
        (sprintf "<TD>%.3f</TD><TD>%s</TD><TD>%.3f</TD>" this.SellPrice (this.SellDate.ToString()) this.Profit)

    static member Headline =
        "<TH>Quantity</TH><TH>Buy Price</TH><TH>Buy Date</TH>" +
        "<TH>Sell Price</TH><TH>Sell Date</TH><TH>Profit</TH>"

type stock = {
    Symbol:string; 
    Quantity:int;
    CurrentPrice:float32
    Sales:sale list
} with
    member this.Value:float32 = float32(this.Quantity) * (this.CurrentPrice)
    member this.Profit:float32= this.Sales |> List.map (fun x -> x.Profit) |> List.sum
    member this.Print:string list =
        let header = 
            [ (sprintf "Symbol: <B>%s</B><BR/>" this.Symbol);
              (sprintf "Quantity: <B>%d</B><BR/>" this.Quantity);
              (sprintf "Current Price: <B>%.3f</B><BR/>" this.CurrentPrice);
              (sprintf "Value: <B>%.3f</B><BR/>" this.Value);
              (sprintf "Profit: <B>%.3f</B><BR/>" this.Profit);
              (sprintf "<TABLE BORDER=\"2\"><TR>%s</TR>" sale.Headline)]
        
        let footer = ["</TABLE><BR/><HR/><BR/>"]

        let body = List.map (fun (x:sale) -> "<TR>" + x.Print + "</TR>" ) this.Sales

        header @ body @ footer


type account = {
    TotalCash:float32;
    Symbols:Set<string>;
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
