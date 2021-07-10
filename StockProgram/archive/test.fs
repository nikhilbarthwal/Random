type event =
{ 
    date
    ToString()

    UpdatePortoflio(portfolio) -> portfolio
}

type cash : event = (ammount)

enum actionType = buy/sell
type acton : event = (buy/sell, stock, quantity, price)



type trasaction = (buy_price, sell_price, buy_date, sell_date, quantity) // stock is the key

type portfolio = (cash, invested, profit, event, date ) { total() }

type investment = (date, price, quantity) // Stock is Map key

class accout
{
    list(portfolio)
    map[stock, investemnt]
    map [ stock, list(transcaroin)]

    profit()

    ToString() -> string[]

}

GetActions() : list(action)

GetDeposits(): list(cash)

GetPortfolio(): list(portfolio)


GetAccount()


___________________________

Let rec ProcessSell saleTranscation (BuyTransltionList traactionList) =
    match ButTranscationList with
        [] -> Error
    [H|T] -> if (H.quantity = SaleQualtity) then:
        (T, newTranaciotnList)
        else (H.quantity > SaleQualtity)
        HH = H.recdude(q)
    ([HH|T], newTractoinList)
    else // (H.quantity < SaleQualtity)
    newProcessSell = ProcessSell.reduce(H.quantity)
    newTRactionList = soething|old
    ProcessSell newProcessSell (T, Tr|tractionList)

ProcessList |> List.fold ProcessCell (init) |> snd |> sort

Stock :
* Symbol
* Quantity
* Current Price
* Total Invested
* Total Gain or Loss
* Transcations[]:

Portfolio:
* TotalInvested
* TotalCash
* TotalGainLoss
* TotalCashPutIn
* Stocks[]: Stock

Transanction:
* Date
* QuantityPurchased
* BuyPrice
* SellPrice
* Gain/Loss