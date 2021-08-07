#include "BestNeutral.h"

void computeProbabilities(BestNeutral& client) {

double price, strike, total;
int probSize, count, size;
std::vector<double> keys;

// Initialize client.beliefs
for (auto const& opt : client.chain) {
strike = opt.first;
if (strike < client.currentPrice) {
price = client.chain[strike]['P'].askPrice;
client.beliefs[strike + price] = 0.0;
keys.push_back(strike + price);
}
else if (strike > client.currentPrice) {
price = client.chain[strike]['C'].askPrice;
client.beliefs[strike - price] = 0.0;
keys.push_back(strike + price);
}
}

count = 0;
probSize = client.beliefs.size();
for (auto const& opt : client.chain) {
strike = opt.first;

// Process OTM puts
if (strike < client.currentPrice) {
size = client.chain[strike]['P'].askSize;
for (int i=count; i<probSize; i++) {
client.beliefs[keys[i]] += size;
}
}

// Process OTM calls
else if (strike > client.currentPrice) {
price = client.chain[strike]['C'].askSize;
for (int i=0; i<count; i++) {
client.beliefs[keys[i]] += size;
}
}
count++;
}

// Replace beliefs with probabilities
total = std::accumulate(std::begin(client.beliefs),
std::end(client.beliefs), 0.0,
[] (double t, const std::map<double, double>::value_type& m)
{return t + m.second;});
for (double key: keys)
client.beliefs[key] /= total;
}

std::pair <double, int> bestNeutral(BestNeutral& client,
std::vector<Spread>& spreads) {

std::vector<double> profits;
double k1, k2, p1, p2, profit, maxProfit = -1000.0;
double belief, prob;
int count = 0, maxIndex = -1;

for (Spread spread: spreads) {

// Premiums and strike prices
p1 = client.chain[k1]['P'].askPrice;
p2 = client.chain[k2]['C'].askPrice;
k1 = spread.k1;
k2 = spread.k2;

// Iterate through probabilities
profit = 0.0;
for (auto el: client.beliefs) {
belief = el.first;
prob = el.second;

if (belief < k1)
profit += ((k1 - belief) - (p1 + p2)) * prob/(p1 + p2);
else if (belief > k2)
profit += ((belief - k2) - (p1 + p2)) * prob/(p1 + p2);
else
profit += -(p1 + p2) * prob/(p1 + p2);
}

// Store profit
profits.push_back(profit);
if (profit > maxProfit) {
maxProfit = profit;
maxIndex = count;
}
count++;
}
return std::make_pair(maxProfit, maxIndex);
}

int main() {

int maxIndex, reqId = 0;
std::array<char, 2> rights{ {'C', 'P'} };
std::pair <double, int> result;
std::vector<Spread> spreads;
std::string spreadTypes[4] =
{"bull call", "bear call", "bull put", "bear put"};

// Connect to TWS or IB Gateway
BestNeutral client("127.0.0.1", 7497, 0);

// Create contract
Contract con = Contract();
con.symbol = "IBM";
con.secType = "STK";
con.exchange = "SMART";
con.currency = "USD";
client.reqContractDetails(reqId++, con);
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Get the current price of the stock
client.reqTickByTickData(reqId++, con, "MidPoint", 1, true);
std::this_thread::sleep_for(std::chrono::seconds(4));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request strike prices and expirations
if (client.conId != -1) {
client.reqSecDefOptParams(reqId++, con.symbol, "",
"STK", client.conId);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();
}
else {
std::cout << "Failed to obtain contract identfiier" << std::endl;
client.eDisconnect();
exit(-1);
}

// Create contract for stock option
if (!client.strikes.empty()) {
for (double strike : client.strikes) {
for (char right : rights) {

// Define the option contract
con.secType = "OPT";
con.right = right;
con.strike = strike;
con.exchange = client.exchange;
con.lastTradeDateOrContractMonth = client.expiration;

// Request option data
client.reqMktData(reqId++, con, "100", false,
false, TagValueListSPtr());
}
}
std::this_thread::sleep_for(std::chrono::seconds(5));
client.signal.waitForSignal();
client.reader->processMsgs();
}
else {
std::cout << "Failed to access strike prices" << std::endl;
client.eDisconnect();
exit(-1);
}

// Remove empty elements
for (auto it = client.chain.cbegin(); it != client.chain.cend();) {
if (client.chain[it->first]['C'].bidSize == -99 &&
client.chain[it->first]['P'].bidSize == -99) {
client.chain.erase(it++);
}
else{
++it;
}
}

if(client.chain.empty()) {
client.eDisconnect();
exit(-1);
}

// Compute stock probabilities
computeProbabilities(client);

// Create and process vertical spreads
std::vector<double> strikeVec(client.strikes.begin(),
client.strikes.end());
for (int i=0; i<client.atmIndex-1; i++) {
spreads.push_back({strikeVec[client.atmIndex-i],
strikeVec[client.atmIndex+i]});
}

// Find the best straddle/strangle
result = bestNeutral(client, spreads);
maxIndex = result.second;
if (result.first != -1000.0) {
std::cout << "Maximum profit of " << result.first
<< " for a straddle/strangle " << " has strikes "
<< spreads[maxIndex].k1 << " and "
<< spreads[maxIndex].k2 << std::endl;
}
else {
std::cout << "Failed to read option chain data" << std::endl;
}

// Disconnect
client.eDisconnect();
return 0;
}
