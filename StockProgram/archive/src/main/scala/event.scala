class Event
{
ToString

Update(portfolio) -> portfolio	
}

class Buy: Event
{
	var: date, stock, quantity, price
	
}

class Sell:Event
{
	var: date, stock, quantity, price
}

class Cash
{
	var: amount
	
}



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

class Event
{
ToString

Update(portfolio) -> portfolio	
}

class Buy: Event
{
	var: date, stock, quantity, price
	
}

class Sell:Event
{
	var: date, stock, quantity, price
}

class Cash
{
	var: amount
	
}
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
