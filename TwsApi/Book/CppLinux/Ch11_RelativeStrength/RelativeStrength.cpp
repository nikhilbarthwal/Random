#include "RelativeStrength.h"

#define RSI_PERIOD 14

RelativeStrength::RelativeStrength(const char *host, int port, int clientId) :
signal(1000),
oldUpAvg(-1.0),
oldDownAvg(-1.0),
oldClose(-1.0),
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

RelativeStrength::~RelativeStrength() { delete reader; }

// Called in response to reqHistoricalData
void RelativeStrength::historicalData(TickerId reqId, const Bar& bar) {

double m, upAvg, downAvg, rs;

if (oldClose == -1.0) {
oldClose = bar.close;
return;
}

// Store momentum according to sign
m = bar.close - oldClose;
if (m > 0) {
upPeriods.push_back(m);
downPeriods.push_back(0.0);
}
else {
upPeriods.push_back(0.0);
downPeriods.push_back(-1.0 * m);
}
oldClose = bar.close;

// Compute the SMMA of the up / down periods
if (upPeriods.size() == RSI_PERIOD) {
upAvg = std::accumulate(upPeriods.begin(),
upPeriods.end(), 0.0) / upPeriods.size();
downAvg = std::accumulate(downPeriods.begin(),
downPeriods.end(), 0.0) / downPeriods.size();
if (oldUpAvg != -1.0) {
upAvg += (RSI_PERIOD - 1) * oldUpAvg / RSI_PERIOD;
downAvg += (RSI_PERIOD - 1) * oldDownAvg / RSI_PERIOD;
}
oldUpAvg = upAvg;
oldDownAvg = downAvg;

// Compute the RS and the RSI
rs = upAvg / downAvg;
rsiVals.push_back(100.0 - 100.0 / (1.0 + rs));

upPeriods.pop_front();
downPeriods.pop_front();
}
}

// Called after all historical data has been received/processed
void RelativeStrength::historicalDataEnd(int reqId, const std::string& startDate,
const std::string& endDate) {

std::cout << "Relative Strength Index: ";
for (double val: rsiVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void RelativeStrength::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
