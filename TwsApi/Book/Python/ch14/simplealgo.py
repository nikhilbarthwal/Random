''' Defines the SimpleAlgo class and its callback methods '''
from threading import Thread
from enum import Enum
import numpy as np

from ibapi.client import EClient
from ibapi.wrapper import EWrapper
from ibapi.utils import iswrapper

# Set enumerated type for sentiment
Sentiment = Enum('Sentiment', 'BULLISH BEARISH MIXED')

class SimpleAlgo(EWrapper, EClient):
    ''' Serves as the client and the wrapper '''

    def __init__(self, addr, port, client_id):
        EWrapper.__init__(self)
        EClient.__init__(self, self)
        self.funds = 0.0
        self.index = 0
        self.spy_bullish = False
        self.scan_results = []
        self.short_list = []
        self.sentiment = Sentiment.MIXED

        # Compute values for quadratic regression
        self.xi = np.arange(20)
        self.xi_sqr = np.square(self.xi)
        self.xi_sum = np.sum(self.xi)
        self.xi_sqr_sum = np.sum(self.xi_sqr)

        # Connect to TWS
        self.connect(addr, port, client_id)

        # Launch the client thread
        thread = Thread(target=self.run)
        thread.start()

    @iswrapper
    def accountSummary(self, req_id, acct, tag, val, currency):
        ''' Called in response to reqAccountSummary '''

        if tag == 'AvailableFunds':
            print('Account {}: available funds = {}'.format(acct, val))
            self.funds = float(val)

    @iswrapper
    def historicalData(self, req_id, bar):
        ''' Called in response to reqHistoricalData '''

        if req_id == 2:

            # Check if SPY implies a bullish/bearish market
            self.spy_bullish = (bar.close > bar.open)

        elif req_id == 3:

            # Estimate if market is bullish or bearish
            vxx_bullish = (bar.close < bar.open)
            if self.spy_bullish and vxx_bullish:
                self.sentiment = Sentiment.BULLISH
                print('SPY rising, VIX falling - bull market')
            elif not self.spy_bullish and not vxx_bullish:
                self.sentiment = Sentiment.BEARISH
                print('SPY falling, VIX rising - bear market')
            else:
                self.sentiment = Sentiment.MIXED
                print('Mixed market - bad day for trading')

        elif req_id > 9 and req_id < 100:

            # Compute pivot point and resistance/support
            p = (bar.high + bar.low + bar.close)/3.0
            if self.sentiment == Sentiment.BULLISH:
                self.rs_levels[req_id - 10] = 2.0 * p - bar.low

            elif self.sentiment == Sentiment.BEARISH:
                self.rs_levels[req_id - 10] = 2.0 * p - bar.high

        elif req_id > 99:
        
            # Store recent price for later processing
            self.prices[req_id - 100, self.index] = bar.close
            self.index += 1
            self.index %= 20

    @iswrapper
    def historicalDataEnd(self, req_id, start, end):
        ''' Called after historical data has been received '''

        if req_id > 99:
            i = req_id - 100
            if self.prices[i][0] == 0.0 or self.rs_levels[i] == 0.0:
                return

            # Compute diff between price and support/resistance
            level_diff = self.prices[i][-1] - self.rs_levels[i]
            
            # Perform quadratic regression
            if self.sentiment == Sentiment.BULLISH and level_diff > 0:
                yi = np.array(self.prices[i])
                yi_sum = np.sum(yi)
                s1 = np.dot(self.xi, yi) - self.xi_sum * yi_sum/20
                s3 = np.dot(self.xi_sqr, yi) - self.xi_sqr_sum * yi_sum/20
                a_val = (665.0 * s3 - 12635.0 * s1)/11674740.0
                if a_val > 0:
                    self.short_list.append((i, level_diff, a_val))
            elif self.sentiment == Sentiment.BEARISH and level_diff < 0:
                yi = np.array(self.prices[i])
                yi_sum = np.sum(yi)
                s1 = np.dot(self.xi, yi) - self.xi_sum * yi_sum/20
                s3 = np.dot(self.xi_sqr, yi) - self.xi_sqr_sum * yi_sum/20
                a_val = (665.0 * s3 - 12635.0 * s1)/11674740.0
                print('a: {}'.format(a_val))
                if a_val < 0:
                    self.short_list.append((i, level_diff, a_val))

    @iswrapper
    def scannerData(self, req_id, rank, details, distance, benchmark,
        projection, legsStr):
        ''' Called in response to reqScannerSubscription '''

        # Append scanned stock to list
        self.scan_results.append(details.contract)

    @iswrapper
    def scannerDataEnd(self, req_id):
        ''' Called after scan results have been received '''

        self.num_stocks = len(self.scan_results)
        self.rs_levels = np.zeros(self.num_stocks)
        self.prices = np.zeros([self.num_stocks, 20])

    @iswrapper
    def openOrder(order_id, contract, order, state):
        ''' Called after order has been submitted '''

        print('Status of {} order: {}'.format(contract.symbol, state.status))

    @iswrapper
    def position(acct, con, position, avgCost):
        pass

    @iswrapper
    def error(self, req_id, code, msg):
        ''' Called if an error occurs '''

        print('Error {} for request {}: {}'.format(code, req_id, msg))
