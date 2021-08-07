#include "MarketReader.h"

MarketReader::MarketReader(const char *host, int port, int clientId) :
signal(1000),
orderId(-1),
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

MarketReader::~MarketReader() { delete reader; }

// Called in response to reqTickByTickData
void MarketReader::tickByTickMidPoint(int reqId, time_t time, double midPoint) {
std::cout << "tickByTickMidPoint - Midpoint tick: " << midPoint << std::endl;
}

// Called in response to reqMktData
void MarketReader::tickPrice(TickerId tickerId, TickType field,
double price, const TickAttrib& attrib) {
std::cout << "tickPrice - field: " << field << ", price: " << price << std::endl;
}

// Called in response to reqMktData
void MarketReader::tickSize(TickerId tickerId, TickType field, int size) {
std::cout << "tickSize - field: " << field << ", size: " << size << std::endl;
}

// Called in response to reqRealTimeBars
void MarketReader::realtimeBar(TickerId reqId, long time, double open,
double high, double low, double close, long volume, double wap, int count) {
std::cout << "realtimeBar - Opening price: " << open << std::endl;
}

// Called in response to reqHistoricalData
void MarketReader::historicalData(TickerId reqId, const Bar& bar) {
std::cout << "historicalData - Close price: " << bar.close << std::endl;
}

// Called in response to reqFundamentalData
void MarketReader::fundamentalData(TickerId reqId, const std::string& data) {
std::cout << "Fundamental data: " << data << std::endl;
}

void MarketReader::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
