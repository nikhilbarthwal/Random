''' Demonstrates how to compute the Average True Range (ATR) '''

from datetime import datetime
from threading import Thread
import time
import collections

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

ATR_PERIOD = 14

class ATR(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize members
        self.true_ranges = collections.deque(maxlen=ATR_PERIOD)
        self.old_atr = -1
        self.old_close = -1

        # Initialize lists of values
        self.atr_vals = []

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

        # Compute the true range
        true_range = max(bar.high - bar.low, 
            abs(bar.high - self.old_close), 
            abs(bar.low - self.old_close))
        self.true_ranges.append(true_range)
        self.old_close = bar.close

        # Compute the SMMA of the true range
        if len(self.true_ranges) == ATR_PERIOD:
            if not self.atr_vals:        
                atr = sum(self.true_ranges)/ATR_PERIOD
            else:
                atr = ((ATR_PERIOD-1) * self.atr_vals[-1] + 
                    true_range)/ATR_PERIOD
            self.atr_vals.append(atr)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('ATR: {}'.format(self.atr_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))

def main():

    # Create the client and connect to TWS
    client = ATR('127.0.0.1', 7497, 0)

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