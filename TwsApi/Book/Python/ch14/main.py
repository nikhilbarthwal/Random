''' Presents a simple algorithmic trading application '''
from datetime import datetime
import time
from enum import Enum

from ibapi.client import Contract
from ibapi.scanner import ScannerSubscription
from ibapi.order import Order

from simplealgo import SimpleAlgo, Sentiment

def check_sentiment(client):
    ''' Check SPY and VXX to determine sentiment '''

    # Create a contract for the SPY ETF
    spy_con = Contract()
    spy_con.symbol = 'SPY'
    spy_con.secType = 'STK'
    spy_con.exchange = 'SMART'
    spy_con.currency = 'USD'

    # Access SPY data
    now = datetime.now().strftime('%Y%m%d, %H:%M:%S')
    client.reqHistoricalData(2, spy_con, now, '1 d', '1 day',
        'MIDPOINT', False, 1, False, [])

    # Create a contract for the VXX ETN
    vxx_con = Contract()
    vxx_con.symbol = 'VXX'
    vxx_con.secType = 'STK'
    vxx_con.exchange = 'SMART'
    vxx_con.currency = 'USD'

    # Access VXX data
    client.reqHistoricalData(3, vxx_con, now, '1 d', '1 day',
        'MIDPOINT', False, 1, False, [])
    time.sleep(3)

    # Determine market sentiment
    return client.sentiment

def assemble_stock_list(client, sentiment):
    ''' Use scanner to obtain stock list '''

    # Define scanner subscription
    ss = ScannerSubscription()
    ss.instrument = 'STK'
    ss.locationCode = 'STK.US.MAJOR'
    ss.abovePrice = 10.0
    ss.belowPrice = client.funds/200.0
    ss.aboveVolume = 20000
    ss.numberOfRows = 5

    # Set scan code according to sentiment
    if sentiment == Sentiment.BULLISH:
        ss.scanCode = 'HIGH_VS_13W_HL'
    else:
        ss.scanCode = 'LOW_VS_13W_HL'

    # Request securities
    client.reqScannerSubscription(4, ss, [], [])
    time.sleep(3)

def compute_support_resistance(client):
    ''' Compute support/resistance for each stock '''

    # Create string for the date/time at midnight
    midnight = datetime.now().strftime('%Y%m%d, 00:00:00')

    # Request five minutes of price data for all stocks
    for i, contract in enumerate(client.scan_results):
        client.reqHistoricalData(i + 10, contract, midnight,
            '1 d', '1 day', 'MIDPOINT', False, 1, False, [])
        time.sleep(1)

def select_target_stock(client):
    ''' Choose the stock based on recent prices '''

    # Create string for the current date/time
    now = datetime.now().strftime('%Y%m%d, %H:%M:%S')

    # Create string for the current date/time
    for i, contract in enumerate(client.scan_results):
        client.reqHistoricalData(i + 100, contract, now,
            '600 S', '30 secs', 'MIDPOINT', False, 1, False, [])
        time.sleep(1)

    # Sort remaining stocks by diff, remove all but 10
    if client.short_list:
        client.short_list.sort(key=lambda rec: rec[1])
        if len(client.short_list) > 10:
            client.short_list = client.short_list[0:10]

        # Find stock with best quadratic regression coefficient
        con = None
        if client.sentiment == Sentiment.BULLISH:
            index = max(client.short_list, key=lambda rec: rec[2])[0]
        elif client.sentiment == Sentiment.BEARISH:
            index = min(client.short_list, key=lambda rec: rec[2])[0]
        con = client.scan_results[index]
        order_price = client.prices[index][-1]
        print('Selected stock: {}'.format(con.symbol))
        return (con, order_price)
    else:
        print('No stocks fit the criteria')
        return (None, None)

# Place an order for the selected stock
def place_order(client, con, price):

    # Get an order ID
    client.reqIds(1000)
    time.sleep(2)

    # Calculate prices
    qty = 100
    if client.sentiment == Sentiment.BULLISH:
        action = 'BUY'
        lmt_price = price * 1.25
        lmt_action = 'SELL'
        stop_price = price * 0.90
        stop_action = 'SELL'
    elif client.sentiment == Sentiment.BEARISH:
        action = 'SELL'
        lmt_price = price * 0.75
        lmt_action = 'BUY'
        stop_price = price * 1.10
        stop_action = 'BUY'

    # Create the bracket order
    main_order = Order()
    main_order.orderId = client.order_id
    main_order.action = action
    main_order.orderType = 'MKT'
    main_order.totalQuantity = qty
    main_order.transmit = False

    # Limit order child
    lmt_child = Order()
    lmt_child.orderId = client.order_id + 1
    lmt_child.action = lmt_action
    lmt_child.orderType = 'LMT'
    lmt_child.totalQuantity = qty
    lmt_child.lmtPrice = lmt_price
    lmt_child.parentId = client.order_id
    lmt_child.transmit = False

    # Stop order child
    stop_child = Order()
    stop_child.orderId = client.order_id + 2
    stop_child.action = stop_action
    stop_child.orderType = 'STP'
    stop_child.totalQuantity = qty
    stop_child.auxPrice = stop_price
    stop_child.parentId = client.order_id
    stop_child.transmit = False

    # Place the order
    client.placeOrder(client.order_id, con, main_order)
    time.sleep(2)

    # Request positions
    client.reqPositions()
    time.sleep(2)
        
def main():

    # Create the client and connect to TWS
    client = SimpleAlgo('127.0.0.1', 7497, 0)

    # Access available funds
    client.reqAccountSummary(0, 'All', 'AvailableFunds')
    time.sleep(3)
    client.cancelAccountSummary(0)

    # Determine market sentiment and process stocks
    sentiment = check_sentiment(client)
    if sentiment != Sentiment.MIXED:
        assemble_stock_list(client, sentiment)
        compute_support_resistance(client)
        (con, price) = select_target_stock(client)
        if con is not None:
            place_order(client, con, price)

    # Disconnect from TWS
    client.disconnect()

if __name__ == '__main__':
    main()
