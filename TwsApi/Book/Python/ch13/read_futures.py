''' Reads continuous futures contracts '''

from datetime import datetime, timedelta
from threading import Thread
import os
import time
import pandas as pd
import shutil

from ibapi.client import EClient, Contract
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

class ReadFutures(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EClient.__init__(self, self)

        # Initialize properties
        self.local_symbol = None
        self.multiplier = None
        self.symbols = {'GE':'GLOBEX', 'ES':'GLOBEX', 'CHF':'GLOBEX', 'GBP':'GLOBEX',
            'CAD':'GLOBEX', 'GC':'NYMEX', 'SI':'NYMEX', 'HG':'NYMEX', 'RB':'NYMEX'}
        self.price_dict = {}

        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def contractDetails(self, req_id, details):
        ''' Called in response to reqContractDetails '''

        # Obtain data for the contract
        self.local_symbol = details.contract.localSymbol
        self.multiplier = details.contract.multiplier

    @iswrapper
    def historicalData(self, req_id, bar):
        ''' Called in response to reqHistoricalData '''

        # Add the futures prices to the dictionary
        self.price_dict['CLOSE'].append(bar.close)
        self.price_dict['LOW'].append(bar.low)
        self.price_dict['HIGH'].append(bar.high)
        self.price_dict['VOL'].append(bar.volume)

    def error(self, req_id, code, msg):
        print('Error {}: {}'.format(code, msg))

def main():

    # Create the client and connect to TWS
    client = ReadFutures('127.0.0.1', 7497, 0)

    # Get expiration dates for contracts
    for symbol in client.symbols:

        # Define contract of interest
        con = Contract()
        con.symbol = symbol
        con.secType = "CONTFUT"
        con.exchange = client.symbols[symbol]
        con.currency = "USD"
        con.includeExpired = True
        client.reqContractDetails(0, con)
        time.sleep(3)

        # Request historical data for each contract
        if client.local_symbol:

            # Initialize price dict
            for v in ['CLOSE', 'LOW', 'HIGH', 'VOL']:
                client.price_dict[v] = []

            # Set additional contract data
            con.localSymbol = client.local_symbol
            con.multiplier = client.multiplier

            # Request historical data
            end_date = datetime.today().date() - timedelta(days=1)
            client.reqHistoricalData(1, con, end_date.strftime("%Y%m%d %H:%M:%S"),
                '1 Y', '1 day', 'TRADES', 1, 1, False, [])
            time.sleep(3)

            # Write data to a CSV file
            if client.price_dict['CLOSE']:
                df = pd.DataFrame(data=client.price_dict)
                df.to_csv(symbol + '.csv', encoding='utf-8', index=False)
                client.price_dict.clear()
        else:
            print('Could not access contract data')
            exit()

    # Disconnect from TWS
    client.disconnect()

if __name__ == '__main__':
    main()