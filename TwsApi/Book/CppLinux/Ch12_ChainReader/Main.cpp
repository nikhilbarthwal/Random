#include "ChainReader.h"

int main() {

int reqId = 0;
std::array<char, 2> rights{ {'C', 'P'} };

// Connect to TWS or IB Gateway
ChainReader client("127.0.0.1", 7497, 0);

// Create contract
Contract con = Contract();
con.symbol = "IBM";
con.secType = "STK";
con.exchange = "SMART";
con.currency = "USD";
client.reqContractDetails(reqId++, con);
std::this_thread::sleep_for(std::chrono::seconds(3));
client.signal.waitForSignal();
client.reader->processMsgs();

// Get the current price of the stock
client.reqTickByTickData(reqId++, con, "MidPoint", 1, true);
std::this_thread::sleep_for(std::chrono::seconds(4));
client.signal.waitForSignal();
client.reader->processMsgs();

// Request strike prices and expirations
if (client.conId != -1) {
client.reqSecDefOptParams(reqId++, con.symbol, "", "STK", client.conId);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();
}
else {
std::cout << "Failed to obtain contract identfiier" << std::endl;
exit(-1);
}

// Create contract for stock option
if (!client.strikes.empty()) {
for (double strike : client.strikes) {
for (char right : rights) {

// Define the option contract
con.secType = "OPT";
con.right = right;
con.strike = strike;
con.exchange = client.exchange;
con.lastTradeDateOrContractMonth = client.expiration;

// Request option data
client.reqMktData(reqId++, con, "100", false, false, TagValueListSPtr());
}
}
std::this_thread::sleep_for(std::chrono::seconds(5));
client.signal.waitForSignal();
client.reader->processMsgs();
}
else {
std::cout << "Failed to access strike prices" << std::endl;
exit(-1);
}

// Remove empty elements
for (auto it = client.chain.cbegin(); it != client.chain.cend();) {
if (client.chain[it->first]['C'].bidSize == -99 &&
client.chain[it->first]['P'].bidSize == -99) {
client.chain.erase(it++);
}
else{
++it;
}
}

if(client.chain.empty()) {
client.eDisconnect();
exit(-1);
}

// Print results
for (auto const& opt : client.chain) {
std::cout << con.symbol << " call at " << opt.first << ":" << std::endl;
std::cout << "Bid price: " << client.chain[opt.first]['C'].bidPrice << std::endl;
std::cout << "Bid size: " << client.chain[opt.first]['C'].bidSize << std::endl;
std::cout << "Ask price: " << client.chain[opt.first]['C'].askPrice << std::endl;
std::cout << "Ask size: " << client.chain[opt.first]['C'].askSize << std::endl;
std::cout << con.symbol << " put at " << opt.first << ":" << std::endl;
std::cout << "Bid price: " << client.chain[opt.first]['P'].bidPrice << std::endl;
std::cout << "Bid size: " << client.chain[opt.first]['P'].bidSize << std::endl;
std::cout << "Ask price: " << client.chain[opt.first]['P'].askPrice << std::endl;
std::cout << "Ask size: " << client.chain[opt.first]['P'].askSize << std::endl;
}

// Disconnect
client.eDisconnect();
return 0;
}
