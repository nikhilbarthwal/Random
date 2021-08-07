#include "AdvOrder.h"

AdvOrder::AdvOrder(const char *host, int port, int clientId):
signal(1000),
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

AdvOrder::~AdvOrder() { delete reader; }

// Obtain the contract's ID and exchange
void AdvOrder::contractDetails(int reqId,
const ContractDetails& details) {

conId = details.contract.conId;
exch = details.contract.exchange;
}

// Provide the ID of the next order
void AdvOrder::nextValidId(OrderId id) {
orderId = id;
std::cout << "Order ID: " << id << std::endl;
}

// Provide the order's status
void AdvOrder::orderStatus(OrderId orderId,
const std::string& status, double filled,
double remaining, double avgFillPrice,
int permId, int parentId, double lastFillPrice,
int clientId, const std::string& whyHeld,
double mktCapPrice) {

std::cout << "Number of filled positions: "
<< filled << std::endl;
std::cout << "Average fill price: " << avgFillPrice
<< std::endl;
}

// Respond to TWS API errors
void AdvOrder::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
