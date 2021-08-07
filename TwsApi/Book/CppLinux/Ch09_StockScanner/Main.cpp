#include "StockScanner.h"

int main() {

int reqId = 0;

// Connect to TWS or IB Gateway
StockScanner client("127.0.0.1", 7497, 0);

// Create scanner subscription
ScannerSubscription ss;
ss.instrument = "STK";
ss.locationCode = "STK.US.MAJOR";
ss.scanCode = "HOT_BY_VOLUME";

// Create a pointer to a new TagValue
TagValueSPtr tag1(new TagValue("avgVolumeAbove", "500000"));
TagValueSPtr tag2(new TagValue("marketCapAbove1e6", "10"));

// Create a pointer to a TagValueList and push the TagValue
TagValueListSPtr tagList(new TagValueList());
tagList->push_back(tag1);
tagList->push_back(tag2);

// Submit a request for a scanner subscription
client.reqScannerSubscription(0, ss,
TagValueListSPtr(), tagList);
std::this_thread::sleep_for(std::chrono::seconds(5));
client.signal.waitForSignal();
client.reader->processMsgs();

// Disconnect
client.eDisconnect();
return 0;
}
