''' Demonstrates how to compute the Relative Strength Index (RSI) '''

from datetime import datetime
from threading import Thread
import time
import collections

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

RSI_PERIOD = 14

class RSI(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize deques
        self.up_periods = collections.deque(maxlen=RSI_PERIOD)
        self.down_periods = collections.deque(maxlen=RSI_PERIOD)
        self.old_close = -1
        self.old_up_avg = -1
        self.old_down_avg = -1

        # Initialize lists of values
        self.rsi_vals = []

        # Connect to TWS
        self.connect(addr, port, client_id)
        
        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def historicalData(self, reqId, bar):
    
        if self.old_close == -1:
            self.old_close = bar.close
            return

        # Append values to up/down periods          
        if bar.close > self.old_close:
            self.up_periods.append(bar.close - self.old_close)
            self.down_periods.append(0.0)
        else:
            self.up_periods.append(0.0)    
            self.down_periods.append(self.old_close - bar.close)
        self.old_close = bar.close

        # Compute the SMMA of the up/down periods
        if len(self.up_periods) == RSI_PERIOD:
            up_avg = sum(self.up_periods)/RSI_PERIOD
            down_avg = sum(self.down_periods)/RSI_PERIOD
            if self.old_up_avg != -1:
                up_avg += (RSI_PERIOD-1) * self.old_up_avg/RSI_PERIOD
                down_avg += (RSI_PERIOD-1) * self.old_down_avg/RSI_PERIOD
            self.old_up_avg = up_avg
            self.old_down_avg = down_avg

            # Compute the RS and the RSI
            rs = up_avg/down_avg
            self.rsi_vals.append(100 - 100/(1 + rs))

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('RSI: {}'.format(self.rsi_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = RSI('127.0.0.1', 7497, 0)

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
