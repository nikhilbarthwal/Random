''' Demonstrates how to compute the Moving Average Convergence/Divergence (MACD) '''

from datetime import datetime
from threading import Thread
import time
import collections

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

SLOW_PERIOD = 26
FAST_PERIOD = 12
MACD_PERIOD = 9

class MACD(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize deques
        self.slow_ema = collections.deque(maxlen=SLOW_PERIOD)
        self.fast_ema = collections.deque(maxlen=FAST_PERIOD)
        self.macd_ema = collections.deque(maxlen=MACD_PERIOD)
        
        # Initialize alpha values for exponential weighting
        self.slow_alpha = 2/(SLOW_PERIOD + 1)
        self.fast_alpha = 2/(FAST_PERIOD + 1)
        self.macd_alpha = 2/(MACD_PERIOD + 1)
        
        # Initialize lists of values
        self.macd_vals = []
        self.signal_vals = []
        
        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def historicalData(self, reqId, bar):

        # Append the closing price to the deques
        self.slow_ema.append(self.slow_alpha * bar.close)
        self.fast_ema.append(self.fast_alpha * bar.close)

        # Compute the averages if the slow deque is full
        if len(self.slow_ema) == SLOW_PERIOD:
            slow_avg = sum(self.slow_ema)/len(self.slow_ema)
            fast_avg = sum(self.fast_ema)/len(self.fast_ema)
            self.macd_ema.append(self.macd_alpha * (fast_avg - slow_avg))

        # Compute MACD and the signal line if the MACD deque is full
        if len(self.macd_ema) == MACD_PERIOD:
            self.macd_vals.append(self.macd_ema[-1]) 
            self.signal_vals.append(sum(self.macd_ema)/len(self.macd_ema))

        # Update exponential weights
        self.slow_alpha *= 1 - 2/(SLOW_PERIOD + 1)
        self.fast_alpha *= 1 - 2/(FAST_PERIOD + 1)
        self.macd_alpha *= 1 - 2/(MACD_PERIOD + 1)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('MACD: {}'.format(self.macd_vals))
        print('Signal Line: {}'.format(self.signal_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = MACD('127.0.0.1', 7497, 0)

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
