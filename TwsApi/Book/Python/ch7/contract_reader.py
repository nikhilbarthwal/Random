''' Demonstrates how an application can access details for a contract '''

from threading import Thread
import time

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class ContractReader(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient. __init__(self, self)

        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def symbolSamples(self, reqId, descs):
    
        # Print the symbols in the returned results
        print('Number of descriptions: {}'.format(len(descs)))
        for desc in descs:
            print('Symbol: {}'.format(desc.contract.symbol))

        # Choose the first symbol
        self.symbol = descs[0].contract.symbol

    @iswrapper
    def contractDetails(self, reqId, details):
        print('Long name: {}'.format(details.longName))
        print('Category: {}'.format(details.category))
        print('Subcategory: {}'.format(details.subcategory))        
        print('Contract ID: {}\n'.format(details.contract.conId))

    @iswrapper
    def contractDetailsEnd(self, reqId):
        print('The End')

    @iswrapper        
    def error(self, reqId, code, msg):
        print('Error {}: {}'.format(code, msg))
        
def main():

    # Create the client and connect to TWS
    client = ContractReader('127.0.0.1', 7497, 0)
    time.sleep(0.5)
    
    # Request descriptions of contracts related to cheesecake    
    client.reqMatchingSymbols(0, 'Cheesecake')    
    time.sleep(3)   
    
    # Request details for the stock
    contract = Contract()
    contract.symbol = client.symbol
    contract.secType = "OPT"
    contract.exchange = "SMART"
    contract.currency = "USD"
    client.reqContractDetails(1, contract)
    
     # Sleep while the request is processed
    time.sleep(3)       
    client.disconnect()

if __name__ == '__main__':
    main()
