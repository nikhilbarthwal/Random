''' Demonstrates how to compute the Accumulation/Distribution Line '''

from datetime import datetime
from threading import Thread
import time

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class AccDist(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Initialize variables
        self.acc_dist_vals = []

        # Connect to TWS
        self.connect(addr, port, client_id)
        
        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def historicalData(self, reqId, bar):
    
        # Compute the close location value(CLV) and multiply it by volume
        clv = ((bar.close - bar.low) - (bar.high - bar.close))/(bar.high - bar.low)
        clv *= bar.volume

        # Update container of results
        if not self.acc_dist_vals:
            self.acc_dist_vals.append(clv)
        else:
            self.acc_dist_vals.append(self.acc_dist_vals[-1] + clv)

    @iswrapper
    def historicalDataEnd(self, reqId, start, end):
        print('Accumulation/Distribution: {}'.format(self.acc_dist_vals))

    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = AccDist('127.0.0.1', 7497, 0)

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
