#define SLOW_PERIOD 25
#define FAST_PERIOD 13

#include "TrueStrength.h"

TrueStrength::TrueStrength(const char *host, int port, int clientId) :
signal(1000),
slowAlpha(2.0 / (SLOW_PERIOD + 1)),
fastAlpha(2.0 / (FAST_PERIOD + 1)),
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

TrueStrength::~TrueStrength() { delete reader; }

// Called in response to reqHistoricalData
void TrueStrength::historicalData(TickerId reqId, const Bar& bar) {

double m, absM, numBaseAvg, numAvg, denBaseAvg, denAvg;

if (oldClose == -1.0) {
oldClose = bar.close;
return;
}

// Compute momentum and absolute momentum
m = bar.close - oldClose;
absM = abs(m);
oldClose = bar.close;

// Add momentum values to the deques
numBase.push_back(slowAlpha * m);
denBase.push_back(slowAlpha * absM);

// Compute the nun/den base averages
if (numBase.size() == SLOW_PERIOD) {
numBaseAvg = std::accumulate(numBase.begin(),
numBase.end(), 0.0) / numBase.size();
num.push_back(fastAlpha * numBaseAvg);
denBaseAvg = std::accumulate(denBase.begin(),
denBase.end(), 0.0) / denBase.size();
den.push_back(fastAlpha * denBaseAvg);
numBase.pop_front();
denBase.pop_front();
}

// Compute TSI values
if (num.size() == FAST_PERIOD) {
numAvg = std::accumulate(num.begin(),
num.end(), 0.0) / num.size();
denAvg = std::accumulate(den.begin(),
den.end(), 0.0) / den.size();
tsiVals.push_back(numAvg/denAvg);
num.pop_front();
den.pop_front();
}

// Update exponential weights
slowAlpha *= 1.0 - 2.0 / (SLOW_PERIOD + 1);
fastAlpha *= 1.0 - 2.0 / (FAST_PERIOD + 1);
}

// Called after all historical data has been received/processed
void TrueStrength::historicalDataEnd(int reqId, const std::string& startDate,
const std::string& endDate) {

std::cout << "True Strength Index: ";
for (double val: tsiVals) {
std::cout << val << " ";
}
std::cout << std::endl;
}

void TrueStrength::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
