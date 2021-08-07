#include "AdvOrder.h"

int main() {

Order mainOrder, firstChild, secondChild;

// Connect to TWS or IB Gateway
AdvOrder client("127.0.0.1", 7497, 0);

// Define the contract
Contract con = Contract();
con.symbol = "IBM";
con.secType = "STK";
con.currency = "USD";
con.exchange = "SMART";

// Access contract details
client.reqContractDetails(0, con);
std::this_thread::sleep_for(std::chrono::seconds(2));
client.signal.waitForSignal();
client.reader->processMsgs();

// Create a volume condition
VolumeCondition* volumeCondition =
dynamic_cast<VolumeCondition*>(OrderCondition::create(
OrderCondition::OrderConditionType::Volume));
volumeCondition->isMore(true);
volumeCondition->volume(20000);
volumeCondition->conId(client.conId);
volumeCondition->exchange(client.exch);
std::shared_ptr<OrderCondition>
condition(dynamic_cast<OrderCondition *>(volumeCondition));

// Create the bracket order
mainOrder.orderId = client.orderId;
mainOrder.action = "BUY";
mainOrder.orderType = "MKT";
mainOrder.totalQuantity = 100;
mainOrder.transmit = false;
mainOrder.conditions.push_back(condition);

// Define the algorithm for the order
mainOrder.algoStrategy = "Adaptive";
mainOrder.algoParams.reset(new TagValueList());
TagValueSPtr tag(new TagValue("adaptivePriority", "Patient"));
mainOrder.algoParams->push_back(tag);

// Limit order child
firstChild.orderId = client.orderId + 1;
firstChild.action = "SELL";
firstChild.orderType = "LMT";
firstChild.totalQuantity = 100;
firstChild.lmtPrice = 170;
firstChild.parentId = client.orderId;
firstChild.transmit = false;

// Stop order child
secondChild.orderId = client.orderId + 2;
secondChild.action = "SELL";
secondChild.orderType = "STP";
secondChild.totalQuantity = 100;
secondChild.auxPrice = 120;
secondChild.parentId = client.orderId;
secondChild.transmit = false;

// Get an order ID
client.reqIds(1000);
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();

// Place the order
client.placeOrder(client.orderId, con, mainOrder);
client.placeOrder(client.orderId+1, con, firstChild);
client.placeOrder(client.orderId+2, con, secondChild);
std::this_thread::sleep_for(std::chrono::seconds(1));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
