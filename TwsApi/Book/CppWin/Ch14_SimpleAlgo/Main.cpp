#define _CRT_SECURE_NO_WARNINGS

#include "SimpleAlgo.h"

// Estimate investor sentiment
Sentiment checkSentiment(SimpleAlgo& client) {

// Get the current time
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, %H:%M:%S");

// Contract for the SPY ETF
Contract spyCon = Contract();
spyCon.symbol = "SPY";
spyCon.secType = "STK";
spyCon.exchange = "SMART";
spyCon.currency = "USD";

// Access SPY data
client.reqHistoricalData(2, spyCon, ostr.str(), "1 d", "1 day",
"MIDPOINT", 1, 1, false, TagValueListSPtr());

// Contract for the VXX ETN
Contract vxxCon = Contract();
vxxCon.symbol = "VXX";
vxxCon.secType = "STK";
vxxCon.exchange = "SMART";
vxxCon.currency = "USD";

// Access VXX data and wait
client.reqHistoricalData(3, vxxCon, ostr.str(), "1 d", "1 day",
"MIDPOINT", 1, 1, false, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Determine market sentiment
return client.sentiment;
}

// Use scanner to obtain candidate stocks
void assembleStockList(SimpleAlgo& client, Sentiment sent) {

// Create scanner subscription
ScannerSubscription ss;
ss.instrument = "STK";
ss.locationCode = "STK.US.MAJOR";
ss.scanCode = "HOT_BY_VOLUME";
ss.abovePrice = 10.0;
ss.belowPrice = client.funds / 200.0;
ss.aboveVolume = 20000;
ss.numberOfRows = 10;

// Set scan code according to sentiment
if (sent == Sentiment::BULLISH)
ss.scanCode = "HIGH_VS_13W_HL";
else if (sent == Sentiment::BEARISH)
ss.scanCode = "LOW_VS_13W_HL";

// Submit a request for a scanner subscription
client.reqScannerSubscription(4, ss,
TagValueListSPtr(), TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.cancelScannerSubscription(4);
client.signal.waitForSignal();
client.reader->processMsgs();
}

// Compute support level or resistance
void computeSupportResistance(SimpleAlgo& client) {

// Get the time at midnight
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, 00:00:00");

// Find support or resistance for each stock
int i = 10;
for (Contract con : client.scanResults) {
client.reqHistoricalData(i++, con, ostr.str(), "1 d",
"1 day", "MIDPOINT", 1, 1, false, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();
}
}

// Choose the stock based on recent prices
std::pair <Contract, double> selectTargetStock(SimpleAlgo& client) {

int index;
double price;
Contract con;

// Get the current time
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, %H:%M:%S");

// Read recent prices for all contracts
int i = 100;
for (Contract con : client.scanResults) {
client.reqHistoricalData(i++, con, ostr.str(), "600 S",
"30 secs", "MIDPOINT", 1, 1, false, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();
}

// Sort remaining stocks by diff
if (!client.shortList.empty()) {

std::sort(client.shortList.begin(), client.shortList.end(),
[](std::tuple<int, double, double> x,
std::tuple<int, double, double> y) {
return std::get<1>(x) < std::get<1>(y); });

// Remove all elements but ten
if (client.shortList.size() > 10)
client.shortList.erase(client.shortList.begin() + 9,
client.shortList.end() - 1);

// Find stock with best quadratic regression coefficient
std::tuple<int, double, double> result;
if (client.sentiment == Sentiment::BULLISH) {
result = *std::max_element(client.shortList.begin(), client.shortList.end(),
[](const std::tuple<int, double, double>& x,
const std::tuple<int, double, double>& y) {
return std::get<2>(x) > std::get<2>(y); });
}
else if (client.sentiment == Sentiment::BEARISH) {
result = *std::min_element(client.shortList.begin(), client.shortList.end(),
[](const std::tuple<int, double, double>& x,
const std::tuple<int, double, double>& y) {
return std::get<2>(x) < std::get<2>(y); });
}
index = std::get<0>(result);
con = client.scanResults[index];
price = client.prices[index][19];
std::cout << "Selected stock: " << con.symbol << std::endl;
return std::make_pair(con, price);
}

// No stocks could be found
else {
std::cout << "No stocks fit the criteria" << std::endl;
return std::make_pair(con, 0.0);
}
}

void placeOrder(SimpleAlgo& client, Contract target, double price) {

Order mainOrder, lmtChild, stopChild;
std::string action, lmtAction, stopAction;
double lmtPrice, stopPrice;

// Get an order ID
client.reqIds(1000);
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();

// Calculate prices
int qty = 100;
if (client.sentiment == Sentiment::BULLISH) {
action = "BUY";
lmtPrice = price * 1.05;
lmtAction = "SELL";
stopPrice = price * 0.95;
stopAction = "SELL";
}
else if (client.sentiment == Sentiment::BEARISH) {
action = "SELL";
lmtPrice = price * 0.95;
lmtAction = "BUY";
stopPrice = price * 1.05;
stopAction = "BUY";
}

// Create the bracket order
mainOrder.orderId = client.orderId;
mainOrder.action = action;
mainOrder.orderType = "MKT";
mainOrder.totalQuantity = qty;
mainOrder.transmit = false;

// Limit order child
lmtChild.orderId = client.orderId + 1;
lmtChild.action = lmtAction;
lmtChild.orderType = "LMT";
lmtChild.totalQuantity = qty;
lmtChild.lmtPrice = lmtPrice;
lmtChild.parentId = client.orderId;
lmtChild.transmit = false;

// Stop order child
stopChild.orderId = client.orderId + 2;
stopChild.action = stopAction;
stopChild.orderType = "STP";
stopChild.totalQuantity = qty;
stopChild.auxPrice = stopPrice;
stopChild.parentId = client.orderId;
stopChild.transmit = false;

// Place the order
client.placeOrder(client.orderId, target, mainOrder);
client.placeOrder(client.orderId + 1, target, lmtChild);
client.placeOrder(client.orderId + 2, target, stopChild);
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request positions
client.reqPositions();
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();
}

int main() {

Sentiment sentiment;
std::pair <Contract, double> result;

// Connect to TWS or IB Gateway
SimpleAlgo client("127.0.0.1", 7497, 0);

// Obtain information about account
client.reqAccountSummary(0, "All", "AccountType, AvailableFunds");
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();
client.cancelAccountSummary(0);

sentiment = checkSentiment(client);
if (sentiment != Sentiment::MIXED) {
assembleStockList(client, sentiment);
computeSupportResistance(client);
result = selectTargetStock(client);
if (result.second != 0.0)
placeOrder(client, result.first, result.second);
}

// Disconnect
client.eDisconnect();
return 0;
}
