module Main

open System
open System.IO
open Types
open Utils

let GetPrice (symbol:string) : float32 = 0.0f //TODO: Fix this!

let Adjustments:Map<string,int list> = Map.empty

let private ReadCsv (filename:string) : string array list =
    File.ReadAllLines(filename)
    |> Array.toList
    |> List.filter (fun x -> (x.Length > 0) && (x.[0] <> '#'))
    |> List.map (fun x -> x.Split [|','|])
    |> List.map (fun x -> Array.map Chop x)

let private ParseCsv (filename:string) : event list =
    let ProcessEntry (entry:string[]) : event option =
        if entry.Length > 2 then
            let d = ref System.DateTime.MinValue
            let e = Chop entry.[1]
            if (System.DateTime.TryParse(Chop entry.[0], d)) then
                if (e = "MoneyLink Transfer") then
                    let m:float32 = Convert2Float entry.[9]
                    Some(Cash(m,!d))
                else if (e = "Sell") || (e = "Buy") then
                    let ty = if (e = "Sell") then Sell else Buy
                    let s = Chop entry.[2]
                    let q = Convert2Int entry.[4]
                    let p = Convert2Float entry.[5]
                    let t = { Type = ty; Symbol = s; Price = p; Quantity = q; Date = !d}
                    Some(Transaction(t))
                else None
            else
                printfn " *** %A : " entry
                None
        else None

    filename |> ReadCsv |> List.map ProcessEntry |> FilterOptions

type private item = { Price:float32; Quantity:int; Date:DateTime } with
    member this.Reduce (q:int) =
        if q>this.Quantity then failwith "Illegal Operation"
        {this with Quantity=this.Quantity-q }

let Process (events:event list) (symbol:string) : string * stock =
    let filterItems (ty:transactionType) : item list=
        let MapFunction: event -> item option = function
            | Transaction(t) when (t.Type=ty) && (t.Symbol=symbol) ->
                Some({ Price=t.Price; Quantity=t.Quantity; Date=t.Date })
            | _ -> None
        events |> List.map MapFunction |> FilterOptions

    let adjust sellItems =
        let rec reduce (q:int) (items:item list) : item list =
            if (q=0) then
                items
            else
                match items with
                | [] -> failwith "Adjustments exceeds sell"
                | h::t -> if (h.Quantity>q) then (h.Reduce(q)::t) else (reduce (q-h.Quantity) t)

        match Adjustments.TryFind(symbol) with
        | Some(v) -> sellItems |> reduce (List.sum v)
        | None -> sellItems


    let rec ProcessItem ((buyItems, sales):item list * sale list) (sellItem:item) : item list * sale list =
        let q = sellItem.Quantity
        if q = 0 then (buyItems, sales) else
            match buyItems with
            | [] -> failwith "Sell exceeds Buying"
            | h::t ->
                let newSale x = {
                    Quantity = x;
                    BuyPrice = h.Price;
                    BuyDate = h.Date;
                    SellPrice = sellItem.Price;
                    SellDate = sellItem.Date }

                if (h.Quantity >= q) then
                    ((if (q=0) then t else h.Reduce(q)::t), (newSale q)::sales)
                else // h.Quantity < q
                    ProcessItem (t, (newSale h.Quantity)::sales) (sellItem.Reduce(q))

    let ((finalBuyItems, finalSales):item list * sale list) =
        Sell |> filterItems |> adjust |> List.fold ProcessItem ((filterItems Buy), [])

    (symbol, {
        Symbol = symbol;
        Quantity = finalBuyItems |> List.map (fun x -> x.Quantity) |> List.sum;
        CurrentPrice = GetPrice symbol
        Sales = finalSales })

let Account (filename:string) : account =
    let events = filename |> ParseCsv
    let processEvents f =  events |> List.map (fun e -> f e) |> FilterOptions
    let totalCashFun e = match e with Cash(x,_) -> Some(x) | _ -> None
    let totalCash = processEvents totalCashFun |> List.sum
    let symbolsFun e = match e with Transaction(x) -> Some(x.Symbol) | _ -> None
    let symbols = processEvents symbolsFun
    { TotalCash=totalCash; Symbols=Set.ofList symbols;
        Stocks=symbols |> List.map (Process events) |> Map.ofList }

[<EntryPoint>]
let main argv =
    for x in (ParseCsv argv.[0]) do (printfn "%A" x)

    //for x in (Account argv.[0]).Print do
    //    printfn "%s" x
    0 // return an integer exit code
