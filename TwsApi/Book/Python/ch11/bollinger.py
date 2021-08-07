''' Demonstrates how to compute the moving average '''

from datetime import datetime
from threading import Thread
import time
import collections
import numpy as np

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

AVERAGE_LENGTH = 20

class Bollinger(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize members
        self.prices = collections.deque(maxlen=AVERAGE_LENGTH)
        self.avg_vals = []
        self.upper_band = []
        self.lower_band = []
        
        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def historicalData(self, reqId, bar):

        # Append the closing price to the deque
        self.prices.append(bar.close)
        
        # Compute the average if 100 values are available
        if len(self.prices) == AVERAGE_LENGTH:
            avg = sum(self.prices)/len(self.prices)
            
            # Compute the standard deviation
            avg_array = np.array(self.prices)
            sigma = np.std(avg_array)
            
            # Update the containers
            self.avg_vals.append(avg)
            self.upper_band.append(avg + 2*sigma)
            self.lower_band.append(avg - 2*sigma)           

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('Moving average: {}'.format(self.avg_vals))
        print('Upper band: {}'.format(self.upper_band))
        print('Lower band: {}'.format(self.lower_band))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = Bollinger('127.0.0.1', 7497, 0)

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
