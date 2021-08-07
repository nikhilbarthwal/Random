''' Demonstrates how an application can scan for securities '''

from threading import Thread
import time

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper
from ibapi.scanner import ScannerSubscription
from ibapi.tag_value import TagValue

class StockScanner(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Connect to TWS
        self.connect(addr, port, client_id)
        self.count = 0

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def scannerData(self, reqId, rank, details, distance, benchmark, projection, legsStr):

        # Print the symbols in the returned results
        print('{}: {}'.format(rank, details.contract.symbol))
        self.count += 1
        
    @iswrapper
    def scannerDataEnd(self, reqId):
    
        # Print the number of results
        print('Number of results: {}'.format(self.count))
    
    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = StockScanner('127.0.0.1', 7497, 0)
    time.sleep(0.5)

    # Create the ScannerSubscription object    
    ss = ScannerSubscription()
    ss.instrument = 'STK'
    ss.locationCode = 'STK.US.MAJOR'
    ss.scanCode = 'HOT_BY_VOLUME'

    # Set additional filter criteria
    tagvalues = []
    tagvalues.append(TagValue('avgVolumeAbove', '500000'))
    tagvalues.append(TagValue('marketCapAbove1e6', '10'))

    # Requet the scanner subscription
    client.reqScannerSubscription(0, ss, [], tagvalues)
    
     # Sleep while the request is processed
    time.sleep(5)       
    client.disconnect()

if __name__ == '__main__':
    main()
