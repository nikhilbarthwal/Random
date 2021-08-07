#define BOLLINGER_PERIOD 20

#include "Bollinger.h"

Bollinger::Bollinger(const char *host, int port, int clientId) :
signal(1000),
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

Bollinger::~Bollinger() { delete reader; }

// Called in response to reqHistoricalData
void Bollinger::historicalData(TickerId reqId, const Bar& bar) {

double avg, stdDev;

// Compute the moving average
prices.push_back(bar.close);
if (prices.size() == BOLLINGER_PERIOD) {

// Compute the average
avg = std::accumulate(prices.begin(),
prices.end(), 0.0) / prices.size();
avgVals.push_back(avg);

// Compute the standard deviation
auto devFunc = [&avg](double acc, const double& p) {
return acc + (p - avg)*(p - avg);
};
stdDev = std::accumulate(prices.begin(), prices.end(), 0.0, devFunc);
stdDev = std::sqrt(stdDev / BOLLINGER_PERIOD);

// Compute the upper and lower bands
upperVals.push_back(avg + 2 * stdDev);
lowerVals.push_back(avg - 2 * stdDev);

prices.pop_front();
}
}

// Called after all historical data has been received/processed
void Bollinger::historicalDataEnd(int reqId, const std::string& startDate, const std::string& endDate) {
std::cout << "Moving Average: ";
for (double val: avgVals) {
std::cout << val << " ";
}
std::cout << std::endl;
std::cout << "Upper Band: ";
for (double val : upperVals) {
std::cout << val << " ";
}
std::cout << std::endl;
std::cout << "Lower Band: ";
for (double val : lowerVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void Bollinger::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
