#include "SimpleAlgo.h"

SimpleAlgo::SimpleAlgo(const char *host, int port, int clientId) :
signal(1000), index(0), EClientSocket(this, &signal) {

// Compute values used in quadratic regression
for (int i=0; i<20; i++) {
xi[i] = 1.0 * i;
xiSqr[i] = 1.0 * i * i;
}
xiSum = std::accumulate(xi, xi+20, 0.0);
xiSqrSum = std::accumulate(xiSqr, xi+20, 0.0);

// Connect to TWS
bool conn = eConnect(host, port, clientId, false);
if (conn) {

// Launch the reader thread
reader = new EReader(this, &signal);
reader->start();
}
else
std::cout << "Failed to connect" << std::endl;
}

SimpleAlgo::~SimpleAlgo() {

delete reader;
delete rsLevels;
for (int i = 0; i < numStocks; i++) {
delete [] prices[i];
prices[i] = NULL;
}
delete [] prices;
prices = NULL;
}

// Access an ID for the order
void SimpleAlgo::nextValidId(OrderId id) {
orderId = id;
}

// Access the client's account
void SimpleAlgo::accountSummary(int reqId, const std::string& acct, const std::string& tag,
const std::string& val, const std::string& currency) {

if (tag == "AvailableFunds") {
std::cout << "Account " << acct << ": available funds = " << val << std::endl;
funds = std::stod(val);
}
}

// Access historical data
void SimpleAlgo::historicalData(TickerId reqId, const Bar& bar) {

bool vxxBullish;
double p;

if (reqId == 2) {

// Check if SPY is bullish or bearish
spyBullish = (bar.close > bar.open);
}
else if (reqId == 3) {

// Estimate market sentiment
vxxBullish = (bar.open > bar.close);
if (vxxBullish && spyBullish) {
sentiment = Sentiment::BULLISH;
std::cout << "SPY rising, VIX falling, market bullish" << std::endl;
}
else if (!vxxBullish && !spyBullish) {
sentiment = Sentiment::BEARISH;
std::cout << "SPY falling, VIX rising, market bearish" << std::endl;
}
else {
sentiment = Sentiment::MIXED;
std::cout << "Market sentiment can't be detected" << std::endl;
}
}
else if (reqId > 10 && reqId < 100) {

// Set resistance/support levels
p = (bar.high + bar.low + bar.close)/3.0;
if (sentiment == Sentiment::BULLISH)
rsLevels[reqId - 10] = 2.0 * p - bar.low;
else if (sentiment == Sentiment::BEARISH)
rsLevels[reqId - 10] = 2.0 * p - bar.high;
}
else if (reqId > 99) {

// Store recent price for later processing
prices[reqId - 100][index++] = bar.close;
index %= 20;
}
}

void SimpleAlgo::historicalDataEnd(int reqId,
const std::string& startDateStr,
const std::string& endDateStr) {

double levelDiff, priceSum, s1, s3, aVal;

if (reqId > 99) {

int i = reqId - 100;
if (prices[i][0] == 0.0 || rsLevels[i] == 0.0)
return;

// Compute diff between price and support/resistance
levelDiff = prices[i][19] - rsLevels[i];
if (sentiment == Sentiment::BULLISH && levelDiff > 0) {
priceSum = std::accumulate(prices[i], prices[i]+20, 0.0);
s1 = std::inner_product(xi, xi+20, prices[i], 0.0) - xiSum * priceSum/20;
s3 = std::inner_product(xiSqr, xiSqr+20, prices[i], 0.0) - xiSqrSum * priceSum/20;
aVal = (665.0 * s3 - 12635.0 * s1)/11674740.0;
if (aVal > 0)
shortList.push_back(std::make_tuple(i, levelDiff, aVal));
}
else if (sentiment == Sentiment::BEARISH and levelDiff < 0) {
priceSum = std::accumulate(prices[i], prices[i]+20, 0.0);
s1 = std::inner_product(xi, xi+20, prices[i], 0.0) - xiSum * priceSum/20;
s3 = std::inner_product(xiSqr, xiSqr+20, prices[i], 0.0) - xiSqrSum * priceSum/20;
aVal = (665.0 * s3 - 12635.0 * s1)/11674740.0;
if (aVal < 0)
shortList.push_back(std::make_tuple(i, levelDiff, aVal));
}
}
}

void SimpleAlgo::scannerData(int reqId, int rank, const ContractDetails& details,
const std::string& distance, const std::string& benchmark,
const std::string& projection, const std::string& legsStr) {

// Append scanned stock to vector
scanResults.push_back(details.contract);
}

void SimpleAlgo::scannerDataEnd(int reqId) {

std::cout << "Number of scan results: " << scanResults.size() << std::endl;
numStocks = scanResults.size();
rsLevels = new double[numStocks];
prices = new double*[numStocks];
for (int i = 0; i < numStocks; i++) {
prices[i] = new double[20];
}
}

// Respond to errors
void SimpleAlgo::error(int id, int code, const std::string& msg) {
if ((code != 162) && (code != 110))
std::cout << "Error: " << code << ": " << msg << std::endl;
}
