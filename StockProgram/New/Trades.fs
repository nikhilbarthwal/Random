module Trades

open System
open Transaction

type private Event(symbol: string, timestamp: DateTime, price: float, q: int) =
    let mutable quantity = q
    member this.Symbol = symbol
    member this.Quantity = quantity
    member this.Timestamp = timestamp
    member this.Empty = quantity <- 0
    member this.Reduce(e:Event) =
        quantity <- quantity - e.Quantity
        //    Debug.Assert q > 0 "Exceeds reduction for %s on price %f" % (symbol, price))
        e.Empty()
        // member this.ToOrder =

let Process (filename:string) =

    let transactions = Transaction.Parse(filename)

    let buyFilter  =
        function Buy(s, t, p, q)  -> (Some(Event(s, t, p, q))) | _ -> None

    let sellFilter =
        function Sell(s, t, p, q) -> (Some(Event(s, t, p, q))) | _ -> None

    let buyList  = transactions |> List.choose buyFilter
    let sellList = transactions |> List.choose sellFilter

    let mutable trades = []
    let mutable stocks = []

    for buy in buyList do
        for sell in sellList do
            if ((buy.Quantity > 0) &&
                (sell.quantity > 0) &&
                (buy.Symbol = sell.Symbol) &&
                (sell.Timestamp >= buy.Timestamp)) then
                    trades <- Transaction(
                        sell.symbol,
                        sell.timestamp, buy.timestamp,
                        sell.price, buy.price,
                        Math.Min(sell.Quantity, buy.Quantity)
                    )::trades

                    if (buy.Quantity > sell.Quantity) then
                        buy.Reduce(sell)
                    else
                        sell.Reduce(buy)

        if (buy.Quantity > 0) then stocks <- buy.ToOrder()::stocks
    (List.rev trades, List.rev stocks)




class sellState
{
    transactions
    buy option
    sell option


    new SellList(buyoption) = buy option = Some buy,sell = [], transactions = []

    porcessSell(sell) ->

    match buyOpiotn ->
      Some buy ->
        assert(buy>0)
        if (sell > 0) & others
           new trabsaction
           if (quanity) updateBuy(quantiy transactions) else updateSell quantiytransactions
        else
           if (sell = 0) {sellState} else {sellState with update ell}
      None ->
        if (sell = 0) {sellState} else {sellState with update ell}

}

class buyState
{
    transactions list
    buy List
    sell lis

    new (sell option)

    processBuy(buy) :-
       let reuslt = sell list |> List.fold (fun x - > s.process(x)) (new SellState(buy))
       let new buy = match result.buy with Some(x) -> x::buy -> buy list
       newSellList - result.sell
       newTransaction list = buyStore @ result.transactions

    resukt :- transactions list, List.rev buy list
}




(buyList |> List.fold (fun s x -> s.processBuy(x)) (new buyState(sellList))).Result









}
