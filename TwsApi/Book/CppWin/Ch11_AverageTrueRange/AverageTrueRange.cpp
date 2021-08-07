#define _CRT_SECURE_NO_WARNINGS

#define ATR_PERIOD 14
#define NOMINMAX

#include "AverageTrueRange.h"

AverageTrueRange::AverageTrueRange(const char *host, int port, int clientId) :
oldClose(-1.0),
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

AverageTrueRange::~AverageTrueRange() { delete reader; }

// Called in response to reqHistoricalData
void AverageTrueRange::historicalData(TickerId reqId, const Bar& bar) {

double trueRange, atrAvg;

if (oldClose == -1.0) {
oldClose = bar.close;
return;
}

// Compute the true range
trueRange = std::max({ bar.high - bar.low, abs(bar.high - oldClose), abs(bar.low - oldClose) });
trueRanges.push_back(trueRange);
oldClose = bar.close;

// Compute the SMMA of the true range
if (trueRanges.size() == ATR_PERIOD) {
if (atrVals.empty()) {
atrAvg = std::accumulate(trueRanges.begin(),
trueRanges.end(), 0.0) / trueRanges.size();
}
else {
atrAvg = ((ATR_PERIOD - 1) * atrVals.back() + trueRange) / ATR_PERIOD;
}
atrVals.push_back(atrAvg);
trueRanges.pop_front();
}
}

// Called after all historical data has been received/processed
void AverageTrueRange::historicalDataEnd(int reqId, const std::string& startDate, const std::string& endDate) {
std::cout << "Average true range: ";
for (double val: atrVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void AverageTrueRange::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
