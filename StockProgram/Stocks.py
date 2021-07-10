import sys
import math
from datetime import datetime
from typing import List
from typing import Tuple


class Transaction:

    def __init__(self, symbol_: str, sell_timestamp_: datetime, buy_timestamp_: datetime, sell_price_: float,
                 buy_price_: float, quantity_: int) -> None:
        self.symbol: str = symbol_
        self.sell_timestamp: datetime = sell_timestamp_
        self.buy_timestamp: datetime = buy_timestamp_
        self.sell_price = sell_price_
        self.buy_price = buy_price_
        self.quantity = quantity_

    def profit(self) -> float:
        return (self.sell_price - self.buy_price) * self.quantity

    def __str__(self):
        return self.symbol + " , " + str(self.sell_price) + " , " + str(self.sell_timestamp.strftime("%m/%d/%y")) + " , " + str(
            self.buy_price) + " , " + str(self.buy_timestamp.strftime("%m/%d/%y")) + " , " + str(self.quantity) + " , " + str(
            round(self.profit(), 2))


class Event:

    def __init__(self, symbol_: str, timestamp_: datetime, price_: float, quantity_: int):
        self.symbol: str = symbol_
        self.timestamp: datetime = timestamp_
        self.price: float = price_
        self.quantity: int = quantity_

    def reduce(self, q: float):
        self.quantity -= q
        if self.quantity < 0:
            raise Exception("Exceeds reduction for %s on price %f" % (symbol, price))

    def nullify(self):
        self.quantity = 0

    def __str__(self):
        return self.symbol + ": " + str(self.quantity) + " @ " + str(self.price) + " on " + str(self.timestamp)


if __name__ == "__main__":
    if len(sys.argv) == 1:
        raise Exception("No input file provided!")

    file = open(sys.argv[1], 'r')
    contents: List[List[str]] = [line.split(",") for line in file.readlines() if not line.startswith("#")]
    file.close()

    # **** Populate buy and sell list ****

    buy_list: List[Event] = []
    sell_list: List[Event] = []

    for tokens in contents:
        if len(tokens) <= 1:
            continue

        token: str = (tokens[1]).strip('\'\"')
        timestamp_str: str = (tokens[0]).strip("'\"").split()[0]
        if timestamp_str == 'Date':
            continue
        timestamp: datetime = datetime.strptime(timestamp_str, "%m/%d/%Y")

        if token == "Sell" or token == "Buy":
            price: float = float(tokens[5].strip('\'\"$'))
            quantity: int = int(float(tokens[4].strip('\'\"')))
            symbol: str = tokens[2].strip('\'\"')

            if token == "Sell":
                sell_list.append(Event(symbol, timestamp, price, quantity))
            else:
                buy_list.append(Event(symbol, timestamp, price, quantity))

    buy_list.reverse()
    sell_list.reverse()

    # for e in buy_list:
    #     print("BUY " + str(e))

    # for e in sell_list:
    #    print("SELL " + str(e))


    # **** Calculate transactions ****
    transactions: List[Transaction] = []
    for buy in buy_list:
        for sell in sell_list:

            if sell.quantity == 0:
                continue

            if buy.quantity == 0:
                break

            if (buy.symbol == sell.symbol) and (sell.timestamp >= buy.timestamp):
                sold = min(sell.quantity, buy.quantity)
                transactions.append(
                    Transaction(sell.symbol, sell.timestamp, buy.timestamp, sell.price, buy.price, sold))

                if buy.quantity > sell.quantity:
                    buy.reduce(sell.quantity)
                    sell.nullify()
                else:
                    sell.reduce(buy.quantity)
                    buy.nullify()

    total_profit: float = 0
    for t in transactions:
        total_profit += t.profit()
        print(str(t) + " , " + str(round(total_profit, 2)))
    # print("\n ***** TOTAL: ", total_profit, " *****")
