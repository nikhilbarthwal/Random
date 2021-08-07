''' Demonstrates how to determine the vertical spread with the best expected return '''

from chain_reader import ChainReader, read_option_chain

def compute_probabilities(chain, current_price):

    # Initialize beliefs
    beliefs = {}
    for strike in chain:
        if strike < current_price:
            price = chain[strike]['P']['ask_price']
            beliefs[strike + price] = 0.0
        elif strike > current_price:
            price = chain[strike]['C']['ask_price']
            beliefs[strike - price] = 0.0

    # Update probabilities
    prob_len = len(beliefs)
    prob_keys = list(beliefs.keys())
    for i, strike in enumerate(chain):

        # Process OTM puts
        if strike < current_price:
            size = chain[strike]['P']['ask_size']
            for j in range(i, prob_len):
                beliefs[prob_keys[j]] += size

        # Process OTM calls
        elif strike > current_price:
            size = chain[strike]['C']['ask_size']
            for j in range(0, i):
                beliefs[prob_keys[j]] += size

    # Replace beliefs with probabilities
    total = sum(list(beliefs.values()))
    for key in beliefs:
        beliefs[key] /= total
    return beliefs

def best_spread(probs, chain, spreads):

    profits = []
    max_profit = -1000.0
    max_index = -1
    for i, spread in enumerate(spreads):

        # Strike prices: K1 for buy, K2 for sell
        K1 = spread[1]
        K2 = spread[2]

        # Premiums
        right = 'C' if spread[0] == 'bear call' or spread[0] == 'bull call' else 'P'
        P1 = chain[K1][right]['ask_price']
        P2 = chain[K2][right]['ask_price']

        # Iterate through probabilities
        profit = 0.0
        for j, belief in enumerate(probs):

            if spread[0] == 'bull call':
                if belief < K1:
                    profit += -(P1 - P2) * probs[belief]
                elif belief > K1 and belief < K2:
                    profit += ((belief - K1) - (P1 - P2)) * probs[belief]
                else:
                    profit += ((K2 - K1) - (P1 - P2)) * probs[belief]

            elif spread[0] == 'bear call':
                if belief < K1:
                    profit += (P1 - P2) * probs[belief]
                elif belief > K1 and belief < K2:
                    profit += ((P1 - P2) - (belief - K1)) * probs[belief]
                else:
                    profit += ((P1 - P2) - (K2 - K1)) * probs[belief]

            elif spread[0] == 'bull put':
                if belief < K2:
                    profit += ((P1 - P2) - (K1 - K2)) * probs[belief]
                elif belief > K2 and belief < K1:
                    profit += ((P1 - P2) - (belief - K2)) * probs[belief]
                else:
                    profit += (P1 - P2) * probs[belief]

            elif spread[0] == 'bear put':
                if belief < K2:
                    profit += ((K1 - K2) - (P1 - P2)) * probs[belief]
                elif belief > K2 and belief < K1:
                    profit += ((belief - K2) - (P1 - P2)) * probs[belief]
                else:
                    profit += -(P1 - P2) * probs[belief]

        print('{} with K1 = {}, K2 = {}: profit = {}'.format(spread[0], K1, K2, profit))
        profits.append(profit)
        if profit > max_profit:
            max_profit = profit
            max_index = i

    return max_profit, max_index

def main():

    # Create the client and connect to TWS
    client = ChainReader('127.0.0.1', 7497, 0)
    chain, atm_price = read_option_chain(client, 'IBM')
    client.disconnect()

    # Compute probabilities at different prices
    probs = compute_probabilities(chain, atm_price)

    # Create and process vertical spreads
    strikes = list(chain.keys())
    rev = strikes[::-1]
    atm_index = strikes.index(atm_price)
    spreads = []
    for type in ['bull call', 'bear call', 'bull put', 'bear put']:
        for i in range(0, atm_index):
            for j in range(i + 1, atm_index):
                if type == 'bull put' or type == 'bear put':
                    spreads.append([type, strikes[j], strikes[i]])
                else:
                    spreads.append([type, rev[j], rev[i]])

    # Find the best spread
    max_profit, max_index = best_spread(probs, chain, spreads)
    print('Maximum profit: {} for {}'.format(max_profit, spreads[max_index]))

if __name__ == '__main__':
    main()