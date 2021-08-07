#define _CRT_SECURE_NO_WARNINGS

#include "ContractReader.h"

int main() {

// Connect to TWS or IB Gateway
ContractReader client("127.0.0.1", 7497, 0);

// Request symbols associated with cheesecake
client.reqMatchingSymbols(0, "Cheesecake");
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Define a contract
Contract con = Contract();
con.symbol = client.symbol;
con.secType = "OPT";
con.exchange = "SMART";
con.currency = "USD";

// Read the message
client.reqContractDetails(1, con);
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
