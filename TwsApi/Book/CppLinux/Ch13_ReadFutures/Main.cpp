#include "ReadFutures.h"

int main() {

// Connect to TWS or IB Gateway
ReadFutures client("127.0.0.1", 7497, 0);

for (const auto &symbol : client.symbols) {

// Create contract
Contract con = Contract();
con.symbol = symbol.first;
con.secType = "CONTFUT";
con.exchange = symbol.second;
con.currency = "USD";
con.includeExpired = true;
client.reqContractDetails(0, con);
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request historical bars
if (!client.localSymbol.empty()) {

// Clear vectors
client.closeVec.clear();
client.lowVec.clear();
client.highVec.clear();
client.volVec.clear();

// Set additional contract data
con.localSymbol = client.localSymbol;
con.multiplier = client.multiplier;

// Request historical data
time_t tm = std::time(nullptr);
std::tm loc_tm = *std::localtime(&tm);
std::ostringstream ostr;
ostr << std::put_time(&loc_tm, "%Y%m%d, %H:%M:%S");
client.reqHistoricalData(1, con, ostr.str(), "1 Y", "1 day",
"TRADES", 1, 1, false, TagValueListSPtr());
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Write data to file
std::ofstream csvFile(symbol.first + ".csv");
csvFile << "CLOSE,LOW,HIGH,VOL" << std::endl;
for (unsigned int i = 0; i < client.closeVec.size(); i++) {
csvFile << client.closeVec[i] << "," << client.lowVec[i] <<
"," << client.highVec[i] << "," << client.volVec[i] << std::endl;
}
csvFile.close();
}
}

// Disconnect
client.eDisconnect();
return 0;
}
