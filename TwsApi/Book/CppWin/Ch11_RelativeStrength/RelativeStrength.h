#ifndef RELATIVE_STRENGTH
#define RELATIVE_STRENGTH

#include <chrono>
#include <ctime>
#include <iomanip>
#include <iostream>
#include <numeric>
#include <sstream>
#include <thread>
#include <vector>

#include "StdAfx.h"
#include "EReader.h"
#include "EReaderOSSignal.h"
#include "EWrapper.h"
#include "EClientSocket.h"

class RelativeStrength : public EWrapper, public EClientSocket {

public:
RelativeStrength(const char *host, int port, int clientId = 0);
~RelativeStrength();

EReader *reader;
EReaderOSSignal signal;
double oldUpAvg, oldDownAvg, oldClose;
std::deque<double> upPeriods, downPeriods;
std::vector<double> rsiVals;

// Event handling functions
void tickPrice(TickerId tickerId, TickType field, double price, const TickAttrib& attrib) {};
void tickSize(TickerId tickerId, TickType field, int size) {};
void tickOptionComputation(TickerId tickerId, TickType tickType, double impliedVol, double delta,
double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {};
void tickGeneric(TickerId tickerId, TickType tickType, double value) {};
void tickString(TickerId tickerId, TickType tickType, const std::string& value) {};
void tickEFP(TickerId tickerId, TickType tickType, double basisPoints, const std::string& formattedBasisPoints,
double totalDividends, int holdDays, const std::string& futureLastTradeDate, double dividendImpact, double dividendsToLastTradeDate) {};
void orderStatus(OrderId orderId, const std::string& status, double filled,
double remaining, double avgFillPrice, int permId, int parentId,
double lastFillPrice, int clientId, const std::string& whyHeld, double mktCapPrice) {};
void openOrder(OrderId orderId, const Contract&, const Order&, const OrderState&) {};
void openOrderEnd() {};
void winError(const std::string& str, int lastError) {};
void connectionClosed() {};
void updateAccountValue(const std::string& key, const std::string& val,
const std::string& currency, const std::string& accountName) {};
void updatePortfolio(const Contract& contract, double position,
double marketPrice, double marketValue, double averageCost,
double unrealizedPNL, double realizedPNL, const std::string& accountName) {};
void updateAccountTime(const std::string& timeStamp) {};
void accountDownloadEnd(const std::string& accountName) {};
void nextValidId(OrderId orderId) {};
void contractDetails(int reqId, const ContractDetails& contractDetails) {};
void bondContractDetails(int reqId, const ContractDetails& contractDetails) {};
void contractDetailsEnd(int reqId) {};
void execDetails(int reqId, const Contract& contract, const Execution& execution) {};
void execDetailsEnd(int reqId) {};
void error(int id, int errorCode, const std::string& errorString);
void updateMktDepth(TickerId id, int position, int operation, int side,
double price, int size) {};
void updateMktDepthL2(TickerId id, int position, const std::string& marketMaker, int operation,
int side, double price, int size) {};
void updateNewsBulletin(int msgId, int msgType, const std::string& newsMessage, const std::string& originExch) {};
void managedAccounts(const std::string& accountsList) {};
void receiveFA(faDataType pFaDataType, const std::string& cxml) {};
void historicalData(TickerId reqId, const Bar& bar);
void historicalDataEnd(int reqId, const std::string& startDateStr, const std::string& endDateStr);
void scannerParameters(const std::string& xml) {};
void scannerData(int reqId, int rank, const ContractDetails& contractDetails,
const std::string& distance, const std::string& benchmark, const std::string& projection,
const std::string& legsStr) {};
void scannerDataEnd(int reqId) {};
void realtimeBar(TickerId reqId, long time, double open, double high, double low, double close,
long volume, double wap, int count) {};
void fundamentalData(TickerId reqId, const std::string& data) {};
void deltaNeutralValidation(int reqId, const DeltaNeutralContract& deltaNeutralContract) {};
void tickSnapshotEnd(int reqId) {};
void marketDataType(TickerId reqId, int marketDataType) {};
void commissionReport(const CommissionReport& commissionReport) {};
void position(const std::string& account, const Contract& contract, double position, double avgCost) {};
void positionEnd() {};
void accountSummary(int reqId, const std::string& account, const std::string& tag, const std::string& value, const std::string& curency) {};
void accountSummaryEnd(int reqId) {};
void verifyMessageAPI(const std::string& apiData) {};
void verifyCompleted(bool isSuccessful, const std::string& errorText) {};
void displayGroupList(int reqId, const std::string& groups) {};
void displayGroupUpdated(int reqId, const std::string& contractInfo) {};
void verifyAndAuthMessageAPI(const std::string& apiData, const std::string& xyzChallange) {};
void verifyAndAuthCompleted(bool isSuccessful, const std::string& errorText) {};
void connectAck() {};
void positionMulti(int reqId, const std::string& account, const std::string& modelCode, const Contract& contract, double pos, double avgCost) {};
void positionMultiEnd(int reqId) {};
void accountUpdateMulti(int reqId, const std::string& account, const std::string& modelCode, const std::string& key, const std::string& value, const std::string& currency) {};
void accountUpdateMultiEnd(int reqId) {};
void securityDefinitionOptionalParameter(int reqId, const std::string& exchange, int underlyingConId, const std::string& tradingClass,
const std::string& multiplier, const std::set<std::string>& expirations, const std::set<double>& strikes) {};
void securityDefinitionOptionalParameterEnd(int reqId) {};
void softDollarTiers(int reqId, const std::vector<SoftDollarTier> &tiers) {};
void familyCodes(const std::vector<FamilyCode> &familyCodes) {};
void symbolSamples(int reqId, const std::vector<ContractDescription> &contractDescriptions) {};
void mktDepthExchanges(const std::vector<DepthMktDataDescription> &depthMktDataDescriptions) {};
void tickNews(int tickerId, time_t timeStamp, const std::string& providerCode, const std::string& articleId, const std::string& headline, const std::string& extraData) {};
void smartComponents(int reqId, const SmartComponentsMap& theMap) {};
void tickReqParams(int tickerId, double minTick, const std::string& bboExchange, int snapshotPermissions) {};
void newsProviders(const std::vector<NewsProvider> &newsProviders) {};
void newsArticle(int requestId, int articleType, const std::string& articleText) {};
void historicalNews(int requestId, const std::string& time, const std::string& providerCode, const std::string& articleId, const std::string& headline) {};
void historicalNewsEnd(int requestId, bool hasMore) {};
void headTimestamp(int reqId, const std::string& headTimestamp) {};
void histogramData(int reqId, const HistogramDataVector& data) {};
void historicalDataUpdate(TickerId reqId, const Bar& bar) {};
void rerouteMktDataReq(int reqId, int conid, const std::string& exchange) {};
void rerouteMktDepthReq(int reqId, int conid, const std::string& exchange) {};
void marketRule(int marketRuleId, const std::vector<PriceIncrement> &priceIncrements) {};
void pnl(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL) {};
void pnlSingle(int reqId, int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {};
void historicalTicks(int reqId, const std::vector<HistoricalTick> &ticks, bool done) {};
void historicalTicksBidAsk(int reqId, const std::vector<HistoricalTickBidAsk> &ticks, bool done) {};
void historicalTicksLast(int reqId, const std::vector<HistoricalTickLast> &ticks, bool done) {};
void tickByTickAllLast(int reqId, int tickType, time_t time, double price, int size, const TickAttribLast& tickAttribLast, const std::string& exchange, const std::string& specialConditions) {};
void tickByTickBidAsk(int reqId, time_t time, double bidPrice, double askPrice, int bidSize, int askSize, const TickAttribBidAsk& tickAttribBidAsk) {};
void tickByTickMidPoint(int reqId, time_t time, double midPoint) {};
void orderBound(long long orderId, int apiClientId, int apiOrderId) {};
void updateMktDepthL2(TickerId id, int position, const std::string& marketMaker, int operation,
int side, double price, int size, bool isSmartDepth) {};
void currentTime(long curTime) {};
void completedOrder(const Contract& contract, const Order& order, const OrderState& orderState) {};
void completedOrdersEnd() {};
};

#endif // RELATIVE_STRENGTH
