

class test {
  
}

/*
portfolio(invested, cash, infusion)
{
	invested: double
	cash: double
	infuion: double

	function Cash2Investment(double) -> portflio
	{
		return new portfolio(invested+x, cash - x, infution)
	}

	function Investment2Cash(double) -> portfolio
	{
		return new portfolio(invested-x, cash + x, infution)
	}

	function CashInfusion(double) -> portfolio
	{
		return new portfolio(invested, cash + x, infution +x)
	}

	total() -> double : cash + invested

	ToString() -> string

}
_________________________________________


_________________________________________

class Tracsaction
{
	Share Name
	Quantity
	Purchase price
	Sell Price

	ToString()
	Profit(): double
}

__________

class StocksInfo
{

new
Print
Profit() | double
	
}

object StocksInfo
{
	

	public function Process(Stock) -> StocksInfo
	{
		//Filter Stocks Sell & Buy List
		init = new This folder
		let result : state = ProcessList |> List.fold ProcessCell (init) 
		return new StockInfo(Stock, result.Tranacions())
	}

}


______________

private class StockInfoState
	{
		public Lis(Buy) BuyList
		private List(Tranctions) Tracacions

		public State(BuyList) { BuyList, Tracsaction= []}

		pirivate Add(tranactipns, BuyList)
		{
		     retreutn new (BuyList, [T|Tracntions])
		}

		private funtion ProcessSell sale (state)
		{
			match state.BuyList with
			[] -> Error
			[H|T] ->
				if (H.quantity = SaleQualtity) then:
				    X = NewTranactions
				    return State(T,X)
				else (H.quantity > SaleQualtity)
		              HH = H.recdude(q)
		              return State([HH|T], X)
		        else  //  (H.quantity < SaleQualtity)
		            newProcessSell = ProcessSell.reduce(H.quantity)
		            newTRactionList = soething|old
		            ProcessSell newProcessSell (T, Tr|tractionList)
		}
		
	}

______________

private class StockTraction
{
	name
	quantity
	buyDate
	buyPrice
	sellDate
	sellPrice

	ToStroing(): string
	Profile(): double
}

*/
