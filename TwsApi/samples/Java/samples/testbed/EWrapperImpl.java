/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ib.client.*;

public class Wrapper implements EWrapper {

	final public EReaderSignal readerSignal;
	final public EClientSocket clientSocket;
	protected int currentOrderId = -1;
	//! [socket_declare]
	
	//! [socket_init]
	public EWrapperImpl() {
		readerSignal = new EJavaSignal();
		clientSocket = new EClientSocket(this, readerSignal);
	}

	
	public int getCurrentOrderId() {
		return currentOrderId;
	}
	
    public void tickPrice( int tickerId, int field, double price, TickAttrib attrib);
    public void tickSize( int tickerId, int field, int size);
    public void tickOptionComputation( int tickerId, int field, double impliedVol,
    		double delta, double optPrice, double pvDividend,
    		double gamma, double vega, double theta, double undPrice);
	public void tickGeneric(int tickerId, int tickType, double value);
	public void tickString(int tickerId, int tickType, String value);
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureLastTradeDate, double dividendImpact, double dividendsToLastTradeDate);
    public void orderStatus( int orderId, String status, double filled, double remaining,
            double avgFillPrice, int permId, int parentId, double lastFillPrice,
            int clientId, String whyHeld, double mktCapPrice);
    public void openOrder( int orderId, Contract contract, Order order, OrderState orderState);
    public void openOrderEnd();
    public void updateAccountValue(String key, String value, String currency, String accountName);
    public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
            double averageCost, double unrealizedPNL, double realizedPNL, String accountName);
    public void updateAccountTime(String timeStamp);
    public void accountDownloadEnd(String accountName);
    public void nextValidId( int orderId);
    public void contractDetails(int reqId, ContractDetails contractDetails);
    public void bondContractDetails(int reqId, ContractDetails contractDetails);
    public void contractDetailsEnd(int reqId);
    public void execDetails( int reqId, Contract contract, Execution execution);
    public void execDetailsEnd( int reqId);
    public void updateMktDepth( int tickerId, int position, int operation, int side, double price, int size);
    public void updateMktDepthL2( int tickerId, int position, String marketMaker, int operation,
    		int side, double price, int size, boolean isSmartDepth);
    public void updateNewsBulletin( int msgId, int msgType, String message, String origExchange);
    public void managedAccounts( String accountsList);
    public void receiveFA(int faDataType, String xml);
    public void historicalData(int reqId, Bar bar);
    public void scannerParameters(String xml);
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance,
    		String benchmark, String projection, String legsStr);
    public void scannerDataEnd(int reqId);
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count);
    public void currentTime(long time);
    public void fundamentalData(int reqId, String data);
    public void deltaNeutralValidation(int reqId, DeltaNeutralContract deltaNeutralContract);
    public void tickSnapshotEnd(int reqId);
    public void marketDataType(int reqId, int marketDataType);
    public void commissionReport(CommissionReport commissionReport);
    public void position(String account, Contract contract, double pos, double avgCost);
    public void positionEnd();
    public void accountSummary(int reqId, String account, String tag, String value, String currency);
    public void accountSummaryEnd(int reqId);
    public void verifyMessageAPI( String apiData);
    public void verifyCompleted( boolean isSuccessful, String errorText);
    public void verifyAndAuthMessageAPI( String apiData, String xyzChallenge);
    public void verifyAndAuthCompleted( boolean isSuccessful, String errorText);
    public void displayGroupList( int reqId, String groups);
    public void displayGroupUpdated( int reqId, String contractInfo);
    public void error( Exception e);
    public void error( String str);
    public void error(int id, int errorCode, String errorMsg);
    public void connectionClosed();
    public void connectAck();
    public void positionMulti( int reqId, String account, String modelCode, Contract contract, double pos, double avgCost);
    public void positionMultiEnd( int reqId);
    public void accountUpdateMulti( int reqId, String account, String modelCode, String key, String value, String currency);
    public void accountUpdateMultiEnd( int reqId);
    public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId, String tradingClass, String multiplier, Set<String> expirations, Set<Double> strikes);
    public void securityDefinitionOptionalParameterEnd(int reqId);
	public void softDollarTiers(int reqId, SoftDollarTier[] tiers);
    public void familyCodes(FamilyCode[] familyCodes);
    public void symbolSamples(int reqId, ContractDescription[] contractDescriptions);
	public void historicalDataEnd(int reqId, String startDateStr, String endDateStr);
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions);
    public void tickNews(int tickerId, long timeStamp, String providerCode, String articleId, String headline, String extraData);
	public void smartComponents(int reqId, Map<Integer, Entry<String, Character>> theMap);
	public void tickReqParams(int tickerId, double minTick, String bboExchange, int snapshotPermissions);
    public void newsProviders(NewsProvider[] newsProviders);
    public void newsArticle(int requestId, int articleType, String articleText);
    public void historicalNews(int requestId, String time, String providerCode, String articleId, String headline);
    public void historicalNewsEnd(int requestId, boolean hasMore);
	public void headTimestamp(int reqId, String headTimestamp);
	public void histogramData(int reqId, List<HistogramEntry> items);
    public void historicalDataUpdate(int reqId, Bar bar);
	public void rerouteMktDataReq(int reqId, int conId, String exchange);
	public void rerouteMktDepthReq(int reqId, int conId, String exchange);
    public void marketRule(int marketRuleId, PriceIncrement[] priceIncrements);
	public void pnl(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL);
	public void pnlSingle(int reqId, int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value);
    public void historicalTicks(int reqId, List<HistoricalTick> ticks, boolean done);
    public void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done);
    public void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done);
    public void tickByTickAllLast(int reqId, int tickType, long time, double price, int size, TickAttribLast tickAttribLast, String exchange, String specialConditions);
    public void tickByTickBidAsk(int reqId, long time, double bidPrice, double askPrice, int bidSize, int askSize, TickAttribBidAsk tickAttribBidAsk);
    public void tickByTickMidPoint(int reqId, long time, double midPoint);
    public void orderBound(long orderId, int apiClientId, int apiOrderId);
    public void completedOrder(Contract contract, Order order, OrderState orderState);
    public void completedOrdersEnd();
	
	
}
