#define _CRT_SECURE_NO_WARNINGS

#include "SubmitOrder.h"

SubmitOrder::SubmitOrder(const char *host, int port, int clientId) :
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

SubmitOrder::~SubmitOrder() { delete reader; }

// Provide the ID of the next order
void SubmitOrder::nextValidId(OrderId id) {
orderId = id;
std::cout << "Order ID: " << id << std::endl;
}

// Respond when the order is placed
void SubmitOrder::openOrder(OrderId orderId, const Contract& contract,
const Order& order, const OrderState& state) {
std::cout << "Order status: " << state.status << std::endl;
std::cout << "Commission charged: " << state.commission << std::endl;
}

// Provide the order's status
void SubmitOrder::orderStatus(OrderId orderId, const std::string& status, double filled,
double remaining, double avgFillPrice, int permId, int parentId,
double lastFillPrice, int clientId, const std::string& whyHeld, double mktCapPrice) {
std::cout << "Number of filled positions: " << filled << std::endl;
std::cout << "Average fill price: " << avgFillPrice << std::endl;
}

// Provide data related to the account's open positions
void SubmitOrder::position(const std::string& account,
const Contract& contract, double pos, double avgCost) {
std::cout << "Position in " << contract.symbol << ": " << pos << std::endl;
}

// Provide data related to the account
void SubmitOrder::accountSummary(int reqId, const std::string& account, const std::string& tag,
const std::string& value, const std::string& curency) {
std::cout << "Account << " << account << ": " << tag << " = " << value << std::endl;
}

void SubmitOrder::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
