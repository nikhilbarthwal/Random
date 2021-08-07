''' Demonstrates how to compute the On-Balance Volume (OBV) '''

from datetime import datetime
from threading import Thread
import time

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class OBV(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize variables
        self.old_close = -1
        self.obv_vals = []

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
            update = bar.volume
        elif bar.close < self.old_close:
            update = -1 * bar.volume
        else:
            update = 0
        self.old_close = bar.close
        
        # Update container of OBV values
        if not self.obv_vals:
            self.obv_vals.append(update)
        else:
            self.obv_vals.append(self.obv_vals[-1] + update)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('OBV: {}'.format(self.obv_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = OBV('127.0.0.1', 7497, 0)

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
