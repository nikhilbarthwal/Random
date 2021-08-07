''' Demonstrates how an application can submit orders and request information '''

from threading import Thread
import sys
import time

from ibapi.client import EClient, Contract
from ibapi.order import Order
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class SubmitOrder(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)
        self.order_id = None

        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def nextValidId(self, order_id):
        ''' Provides the next order ID '''
        self.order_id = order_id
        print('Order ID: '.format(order_id))

    @iswrapper
    def openOrder(self,order_id, contract, order, state):
        ''' Called in response to the submitted order '''
        print('Order status: '.format(state.status))
        print('Commission charged: '.format(state.commission))

    @iswrapper
    def orderStatus(self,order_id, status, filled, remaining, avgFillPrice, \
        permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice):
        ''' Check the status of the subnitted order '''
        print('Number of filled positions: {}'.format(filled))
        print('Average fill price: {}'.format(avgFillPrice))

    @iswrapper
    def position(self,account, contract, pos, avgCost):
        ''' Read information about the account's open positions '''
        print('Position in {}: {}'.format(contract.symbol, pos))

    @iswrapper
    def accountSummary(self, req_id, account, tag, value, currency):
        ''' Read information about the account '''
        print('Account {}: {} = {}'.format(account, tag, value))

    @iswrapper
    def error(self, req_id, code, msg):
        print('Error {}: {}'.format(code, msg))

def main():

    # Create the client and connect to TWS
    client = SubmitOrder('127.0.0.1', 7497, 0)

    # Define a contract for Apple stock
    contract = Contract()
    contract.symbol = 'AAPL'
    contract.secType = 'STK'
    contract.exchange = 'SMART'
    contract.currency = 'USD'

    # Define the limit order
    order = Order()
    order.action = 'BUY'
    order.totalQuantity = 200
    order.orderType = 'LMT'
    order.lmtPrice = 150
    order.transmit = False

    # Obtain a valid ID for the order
    client.reqIds(1)
    time.sleep(2)

    # Place the order
    if client.order_id:
        client.placeOrder(client.order_id, contract, order)
        time.sleep(5)
    else:
        print('Order ID not received. Ending application.')
        sys.exit()

    # Obtain information about open positions
    client.reqPositions()
    time.sleep(2)

    # Obtain information about account
    client.reqAccountSummary(0, 'All', 'AccountType,AvailableFunds')
    time.sleep(2)

    # Disconnect from TWS
    client.disconnect()

if __name__ == '__main__':
    main()
