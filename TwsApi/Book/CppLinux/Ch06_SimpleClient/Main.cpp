#include "SimpleClient.h"

int main() {

// Connect to TWS or IB Gateway
SimpleClient sc("127.0.0.1", 7497, 0);

// Request the current time
sc.reqCurrentTime();

// Sleep while the message is received
std::this_thread::sleep_for(std::chrono::seconds(1));

// Read the message
sc.signal.waitForSignal();
sc.reader->processMsgs();

// Disconnect
sc.eDisconnect();
return 0;
}
