#include "MovingAverage.h"

MovingAverage::MovingAverage(const char *host, int port, int clientId) :
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

MovingAverage::~MovingAverage() { delete reader; }

// Called in response to reqHistoricalData
void MovingAverage::historicalData(TickerId reqId,
const Bar& bar) {

double avg;

// Get the 100-day moving average
priceVals.push_back(bar.close);
if (priceVals.size() == 100) {
avg = std::accumulate(priceVals.begin(),
priceVals.end(), 0.0) / priceVals.size();
averageVals.push_back(avg);
priceVals.pop_front();
}
}

// Called after all data has been processed
void MovingAverage::historicalDataEnd(int reqId,
const std::string& startDate, const std::string& endDate) {
std::cout << "Moving Average: ";
for (double val: averageVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void MovingAverage::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
