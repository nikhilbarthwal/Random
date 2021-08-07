#define _CRT_SECURE_NO_WARNINGS

#include "AccDist.h"

AccDist::AccDist(const char *host, int port, int clientId) :
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

AccDist::~AccDist() { delete reader; }

// Called in response to reqHistoricalData
void AccDist::historicalData(TickerId reqId, const Bar& bar) {

double ad;

// Compute the close location value(CLV) and multiply it by volume
double clv = ((bar.close - bar.low) - (bar.high - bar.close)) / (bar.high - bar.low);
clv *= bar.volume;

// Update container of results
if (accDistVals.empty()) {
accDistVals.push_back(clv);
}
else {
ad = accDistVals.back() + clv;
accDistVals.push_back(ad);
}
}

// Called after all historical data has been received/processed
void AccDist::historicalDataEnd(int reqId, const std::string& startDate, const std::string& endDate) {
std::cout << "Accumulation/Distribution: ";
for (double val: accDistVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void AccDist::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
