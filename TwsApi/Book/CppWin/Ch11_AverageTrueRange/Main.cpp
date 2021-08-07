#define _CRT_SECURE_NO_WARNINGS

#include "AverageTrueRange.h"

int main() {

// Connect to TWS or IB Gateway
AverageTrueRange client("127.0.0.1", 7497, 0);

// Create contract
Contract con = Contract();
con.symbol = "IBM";
con.secType = "STK";
con.exchange = "SMART";
con.currency = "USD";

// Request historical bars
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, %H:%M:%S");
client.reqHistoricalData(3, con, ostr.str(), "6 M", "1 day",
"MIDPOINT", 1, 1, FALSE, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(5));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
