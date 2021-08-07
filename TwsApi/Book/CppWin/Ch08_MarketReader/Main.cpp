#define _CRT_SECURE_NO_WARNINGS

#include "MarketReader.h"

int main() {

// Connect to TWS or IB Gateway
MarketReader client("127.0.0.1", 7497, 0);

// Request ten ticks containing midpoint data
Contract con = Contract();
con.symbol = "IBM";
con.secType = "STK";
con.exchange = "SMART";
con.currency = "USD";
client.reqTickByTickData(0, con, "MidPoint", 10, TRUE);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request market data
client.reqMktData(1, con, "", FALSE, FALSE, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request current bars
client.reqRealTimeBars(2, con, 5, "MIDPOINT", TRUE, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request historical bars
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, %H:%M:%S");
client.reqHistoricalData(3, con, ostr.str(), "2 w", "1 day",
"MIDPOINT", 1, 1, FALSE, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request fundamental data
client.reqFundamentalData(4, con, "ReportSnapshot", TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
