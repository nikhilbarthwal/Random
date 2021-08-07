#include "SubmitOrder.h"

int main() {

// Connect to TWS or IB Gateway
SubmitOrder client("127.0.0.1", 7497, 0);

// Define a contract
Contract con = Contract();
con.symbol = "AAPL";
con.secType = "STK";
con.exchange = "SMART";
con.currency = "USD";

// Define the limit order
Order order = Order();
order.action = "BUY";
order.totalQuantity = 200;
order.orderType = "LMT";
order.lmtPrice = 150;
order.transmit = false;

// Obtain a valid ID for the order
client.reqIds(1);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Place the order
if (client.orderId != -1) {
client.placeOrder(client.orderId, con, order);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();
} else {
std::cout << "Order ID not received. Ending application." << std::endl;
exit(-1);
}

// Obtain information about open positions
client.reqPositions();
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Obtain information about account
client.reqAccountSummary(0, "All", "AccountType,AvailableFunds");
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
