''' Uses the Turtle Trading system to make trades '''

from collections import deque
from enum import Enum
import os
import pandas as pd

ATR_PERIOD = 20
ENTER_PERIOD = 20
EXIT_PERIOD = 10

InvState = Enum('InvState', 'OUT LONG SHORT')
init_funds = 10000000.00

def main():

    # Define symbols and price/point
    symbols = {'GE': 2500, 'ES': 50, 'CHF': 125000, 'GBP': 62500,
        'CAD': 100000, 'GC': 100, 'SI': 5000, 'HG': 25000, 'RB': 42000}

    true_ranges = deque(maxlen=ATR_PERIOD)
    enter_deque = deque(maxlen=ENTER_PERIOD)
    exit_deque = deque(maxlen=EXIT_PERIOD)
    positions = {}

    csv_files = [f for f in os.listdir('.') if f.endswith('.csv')]
    for csv_file in csv_files:

        # Initialize values
        inv_state = InvState.OUT
        funds = init_funds
        last_price = 0.0
        old_close = -1
        old_atr = -1.0
        true_ranges.clear()
        enter_deque.clear()
        exit_deque.clear()
        positions.clear()

        # Contract-specific information
        symbol = csv_file.split('.')[0]
        contract_size = symbols[symbol]
        df = pd.read_csv(csv_file)

        # Iterate through bars
        for i, bar in df.iterrows():

            # Find true range
            if old_close != -1:
                true_range = max(bar['HIGH'] - bar['LOW'],
                    abs(bar['HIGH'] - old_close),
                    abs(bar['LOW'] - old_close))
                true_ranges.append(true_range)
                old_close = bar['CLOSE']
            else:
                old_close = bar['CLOSE']
                continue

            # Compute the average true range (ATR)
            if len(true_ranges) == ATR_PERIOD:
                N = ((ATR_PERIOD-1) * old_atr + true_range)/ATR_PERIOD
                old_atr = N
            else:
                old_atr = sum(true_ranges)/len(true_ranges)
                continue

            # Initialize parameters
            price = bar['CLOSE']
            unit_size = int(0.01 * funds/(N * contract_size))

            # Check for entry
            if inv_state == InvState.OUT and len(enter_deque) == ENTER_PERIOD:

                # Buy 1 unit at 20-day high
                if price > max(enter_deque):
                    positions[price] = unit_size
                    last_price = price
                    inv_state = InvState.LONG

                # Short 1 unit at 20-day low
                elif price < min(enter_deque):
                    positions[price] = unit_size
                    last_price = price
                    inv_state = InvState.SHORT

            # Exit position if price at 10-day low/high
            elif (inv_state == InvState.LONG and price < min(exit_deque)) or \
                (inv_state == InvState.SHORT and price > max(exit_deque)):

                for p in positions:
                    if inv_state == InvState.LONG:
                        change = positions[p] * contract_size * (price - p)
                    else:
                        change = positions[p] * contract_size * (p - price)
                    funds += change
                positions.clear()
                last_price = 0.0
                inv_state = InvState.OUT

            # Exit position if the price falls/rises by 2N
            elif (inv_state == InvState.LONG and price < last_price - 2*N) or \
                (inv_state == InvState.SHORT and price > last_price + 2*N):

                # Apply stop condition
                price = last_price - 2*N if inv_state == InvState.LONG \
                    else last_price + 2*N
                for p in positions:
                    if inv_state == InvState.LONG:
                        change = positions[p] * contract_size * (price - p)
                    elif inv_state == InvState.SHORT:
                        change = positions[p] * contract_size * (p - price)
                    funds += change
                positions.clear()
                last_price = 0.0
                inv_state = InvState.OUT

            # Increase position if the price rises/falls by N/2
            elif ((inv_state == InvState.LONG and price > last_price + N/2) or \
                (inv_state == InvState.SHORT and price < last_price - N/2)):

                # Make sure position doesn't exceed 4 units
                tot_position = sum(positions.values())
                if tot_position + unit_size < 4 * unit_size:
                    if price in positions:
                        positions[price] += unit_size
                    else:
                        positions[price] = unit_size
                    last_price = price

            enter_deque.append(price)
            exit_deque.append(price)

        # Determine return
        for p in positions:
            if inv_state == InvState.LONG:
                change = positions[p] * contract_size * (price - p)
            elif inv_state == InvState.SHORT:
                change = positions[p] * contract_size * (p - price)
            funds += change
        ret = funds/init_funds
        print('Return for {0}: {1:.4f}'.format(symbol, ret))

if __name__ == '__main__':
    main()