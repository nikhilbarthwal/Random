''' Demonstrates how to compute the moving average '''

from datetime import datetime
from threading import Thread
import time
import collections

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class MovingAverage(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize members
        self.stock_vals = collections.deque(maxlen=100)
        self.avg_vals = []
        
        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def historicalData(self, reqId, bar):

        # Append the closing price to the deque
        self.stock_vals.append(bar.close)
        
        # Compute the average if 100 values are available
        if len(self.stock_vals) == 100:
            avg = sum(self.stock_vals)/len(self.stock_vals)
            self.avg_vals.append(avg)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('Moving average: {}'.format(self.avg_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = MovingAverage('127.0.0.1', 7497, 0)

    # Define a contract for IBM stock
    contract = Contract()
    contract.symbol = "IBM"
    contract.secType = "STK"
    contract.exchange = "SMART"
    contract.currency = "USD"

    # Request six months of historical data
    currentTime = datetime.today().strftime("%Y%m%d %H:%M:%S")
    client.reqHistoricalData(0, contract, currentTime, '6 M', '1 day', 'MIDPOINT', 1, 2, False, [])

    # Sleep while the request is processed
    time.sleep(5)

    # Disconnect from TWS
    client.disconnect()

if __name__ == '__main__':
    main()
