''' Demonstrates how to compute the True Strength Index (TSI) '''

from datetime import datetime
from threading import Thread
import time
import collections

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

SLOW_PERIOD = 25
FAST_PERIOD = 13

class TSI(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize deques
        self.num_base = collections.deque(maxlen=SLOW_PERIOD)
        self.numerator = collections.deque(maxlen=FAST_PERIOD)
        self.den_base = collections.deque(maxlen=SLOW_PERIOD)
        self.denominator = collections.deque(maxlen=FAST_PERIOD)
        self.old_close = -1
        
        # Initialize alpha values for exponential weighting
        self.slow_alpha = 2/(SLOW_PERIOD + 1)
        self.fast_alpha = 2/(FAST_PERIOD + 1)
        
        # Initialize lists of values
        self.tsi_vals = []
        
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

        # Compute momentum and absolute momentum
        m = bar.close - self.old_close
        abs_m = abs(m)
        self.old_close = bar.close
    
        # Update the numerator base and denominator base
        self.num_base.append(self.slow_alpha * m)
        self.den_base.append(self.slow_alpha * abs_m)

        # Compute the averages if the slow deque is full
        if len(self.num_base) == SLOW_PERIOD:
            num_base_avg = sum(self.num_base)/len(self.num_base)
            den_base_avg = sum(self.den_base)/len(self.den_base)
            self.numerator.append(self.fast_alpha * num_base_avg)
            self.denominator.append(self.fast_alpha * den_base_avg)

        # Compute MACD and the signal line if the MACD deque is full
        if len(self.numerator) == FAST_PERIOD:
            num_avg = sum(self.numerator)/len(self.numerator)
            den_avg = sum(self.denominator)/len(self.denominator)
            self.tsi_vals.append(100.0 * num_avg/den_avg)

        # Update exponential weights
        self.slow_alpha *= 1 - 2/(SLOW_PERIOD + 1)
        self.fast_alpha *= 1 - 2/(FAST_PERIOD + 1)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('TSI: {}'.format(self.tsi_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = TSI('127.0.0.1', 7497, 0)

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
