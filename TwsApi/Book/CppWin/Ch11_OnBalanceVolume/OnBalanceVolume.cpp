#define _CRT_SECURE_NO_WARNINGS

#include "OnBalanceVolume.h"

OnBalanceVolume::OnBalanceVolume(const char *host, int port, int clientId) :
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

OnBalanceVolume::~OnBalanceVolume() { delete reader; }

// Called in response to reqHistoricalData
void OnBalanceVolume::historicalData(TickerId reqId,
const Bar& bar) {

long long update, obv;

if (oldClose == -1.0) {
oldClose = bar.close;
return;
}

// Append values to up / down periods
if (bar.close > oldClose) {
update = bar.volume;
}
else if (bar.close < oldClose) {
update = -1 * bar.volume;
}
else {
update = 0;
}
oldClose = bar.close;

// Update container of OBV values
if (obvVals.empty()) {
obvVals.push_back(update);
}
else {
obv = obvVals.back() + update;
obvVals.push_back(obv);
}
}

// Called after all data has been processed
void OnBalanceVolume::historicalDataEnd(int reqId,
const std::string& startDate, const std::string& endDate) {
std::cout << "On-balance volume: ";
for (long long val: obvVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void OnBalanceVolume::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
