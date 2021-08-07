#define SLOW_PERIOD 26
#define FAST_PERIOD 12
#define MACD_PERIOD 9

#include "Macd.h"

Macd::Macd(const char *host, int port, int clientId) :
signal(1000),
slowAlpha(2.0 / (SLOW_PERIOD + 1)),
fastAlpha(2.0 / (FAST_PERIOD + 1)),
macdAlpha(2.0 / (MACD_PERIOD + 1)),
EClientSocket(this, &signal) {

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

Macd::~Macd() { delete reader; }

// Called in response to reqHistoricalData
void Macd::historicalData(TickerId reqId, const Bar& bar) {

double slowAvg, fastAvg, macdAvg;

// Append the closing price to the deques
slowEma.push_back(slowAlpha * bar.close);
fastEma.push_back(fastAlpha * bar.close);

// Compute the fast, slow, and MACD averages
if (fastEma.size() == FAST_PERIOD + 1) {
fastEma.pop_front();
}
if (slowEma.size() == SLOW_PERIOD) {
fastAvg = std::accumulate(fastEma.begin(),
fastEma.end(), 0.0) / fastEma.size();
slowAvg = std::accumulate(slowEma.begin(),
slowEma.end(), 0.0) / slowEma.size();
macdEma.push_back(macdAlpha * (fastAvg - slowAvg));
slowEma.pop_front();
}

// Compute MACD and the signal line if the MACD deque is full
if (macdEma.size() == MACD_PERIOD) {
macdVals.push_back(macdEma.back());
macdAvg = std::accumulate(macdEma.begin(),
macdEma.end(), 0.0) / macdEma.size();
signalVals.push_back(macdAvg);
macdEma.pop_front();
}

// Update exponential weights
slowAlpha *= 1.0 - 2.0 / (SLOW_PERIOD + 1);
fastAlpha *= 1.0 - 2.0 / (FAST_PERIOD + 1);
macdAlpha *= 1.0 - 2.0 / (MACD_PERIOD + 1);
}

// Called after all historical data has been received/processed
void Macd::historicalDataEnd(int reqId, const std::string& startDate,
const std::string& endDate) {

std::cout << "MACD: ";
for (double val: macdVals) {
std::cout << val << " ";
}
std::cout << std::endl << "MACD Signal Line: ";
for (double val : signalVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void Macd::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
