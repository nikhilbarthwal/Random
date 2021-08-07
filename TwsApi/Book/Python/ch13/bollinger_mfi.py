''' Uses the Bollinger-MFI system to make trades '''

from collections import deque
from enum import Enum

import os
import numpy as np
import pandas as pd

BOLLINGER_PERIOD = 5
MFI_PERIOD = 10

InvState = Enum('InvState', 'OUT LONG SHORT')
init_funds = 10000000.00

def main():

    # Define symbols of interest
    symbols = {'GE': 2500, 'ES': 50, 'CHF': 125000, 'GBP': 62500,
        'CAD': 100000, 'GC': 100, 'SI': 5000, 'HG': 25000, 'RB': 42000}

    # Load data
    prices = deque(maxlen=BOLLINGER_PERIOD)
    money_flows = deque(maxlen=MFI_PERIOD)
    positions = {}

    csv_files = [f for f in os.listdir('.') if f.endswith('.csv')]
    for csv_file in csv_files:
    
        # Initialize values
        old_typical = -1.0
        prices.clear()
        money_flows.clear()
        funds = init_funds
        inv_state = InvState.OUT
        positions.clear()

        # Contract-specific information
        symbol = csv_file.split('.')[0]
        contract_size = symbols[symbol]
        unit_size = int(0.01 * funds/contract_size)
        df = pd.read_csv(csv_file)
        
        # Iterate through prices
        for i, bar in df.iterrows():

            # Compute the money flow
            typical = (bar['HIGH'] + bar['LOW'] + bar['CLOSE'])/3.0
            if old_typical > typical:
                old_typical = typical
                typical *= -1.0
            else:
                old_typical = typical
            money_flow = bar['VOL'] * typical
            
            # Compute the money flow index
            money_flows.append(money_flow)
            if len(money_flows) == MFI_PERIOD:
                mf_array = np.array(money_flows)
                pos_flow = np.sum(mf_array[mf_array > 0])
                neg_flow = -1.0 * np.sum(mf_array[mf_array < 0])
                mfi = 100.0 * pos_flow/(pos_flow + neg_flow)
            else:
                continue
                
            # Compute the upper/lower bands
            prices.append(bar['CLOSE'])
            if len(prices) == BOLLINGER_PERIOD:
                avg = sum(prices)/len(prices)
                
                # Compute the standard deviation, bands, and %b
                price_array = np.array(prices)
                sigma = np.std(price_array)
                
                upper = avg + 2*sigma
                lower = avg - 2*sigma
                percent_b = 100.0 * (bar['CLOSE'] - lower)/(upper - lower)           
                
                # Check buy signal
                price = bar['CLOSE']
                if percent_b > 80 and mfi > 80:

                    # If out, enter long position
                    if inv_state == InvState.OUT:
                        positions[price] = unit_size
                        inv_state = InvState.LONG

                    # If long, increase long position
                    elif inv_state == InvState.LONG:
                        if price in positions:
                            positions[price] += unit_size
                        else:
                            positions[price] = unit_size

                    # If short, exit position
                    elif inv_state == InvState.SHORT:
                        for p in positions:
                            funds += positions[p] * contract_size * (p - price)
                        positions.clear()
                        inv_state = InvState.OUT

                # Check sell signal
                elif percent_b < 20 and mfi < 20:

                    # If out, enter short position
                    if inv_state == InvState.OUT:
                        positions[price] = unit_size
                        inv_state = InvState.SHORT

                    # If long, exit position
                    elif inv_state == InvState.LONG:
                        for p in positions:
                            funds += positions[p] * contract_size * (price - p)
                        positions.clear()
                        inv_state = InvState.OUT

                    # If short, increase short position
                    elif inv_state == InvState.SHORT:
                        if price in positions:
                            positions[price] += unit_size
                        else:
                            positions[price] = unit_size

        # Compute return
        for p in positions:
            if inv_state == InvState.LONG:
                funds += positions[p] * contract_size * (price - p)
            elif inv_state == InvState.SHORT:
                funds += positions[p] * contract_size * (p - price)
        ret = funds/init_funds
        print('Return for {0}: {1:.4f}'.format(symbol, ret))
        
if __name__ == '__main__':
    main()