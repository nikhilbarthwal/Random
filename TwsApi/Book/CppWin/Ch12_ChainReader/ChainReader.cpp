#define _CRT_SECURE_NO_WARNINGS

#include "ChainReader.h"

ChainReader::ChainReader(const char *host, int port, int clientId) :
signal(1000),
conId(-1),
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

ChainReader::~ChainReader() { delete reader; }

// Obtain contract ID
void ChainReader::contractDetails(int reqId, const ContractDetails& details) {
conId = details.contract.conId;
}

// Obtain current price
void ChainReader::tickByTickMidPoint(int reqId, time_t time, double midPoint) {
currentPrice = midPoint;
}

// Read strike prices and expiration dates
void ChainReader::securityDefinitionOptionalParameter(int reqId, const std::string& exch,
int underlyingConId, const std::string& tradingClass, const std::string& multiplier,
const std::set<std::string>& exp, const std::set<double>& optStrikes) {

exchange = exch;
expirations = exp;
strikes = optStrikes;
}

// Process data after receiving strikes/expirations
void ChainReader::securityDefinitionOptionalParameterEnd(int reqId) {

// std::cout << "Current price: " << currentPrice << std::endl;
double minDist = 99999.0;
int atmIndex, i = 0;
std::set<double>::iterator strikeIter;
struct std::tm tmpTime = { 0 };
long int expTime;
int year, month, day;

// Find the index of the strike nearest ATM
for (double strike : strikes) {
if (strike - currentPrice < minDist) {
minDist = abs(strike - currentPrice);
atmIndex = i;
}
i++;
}

// Limit strike prices to +7/-7 around ATM
int front = atmIndex - 7;
int back = -1 * (strikes.size() - (atmIndex + 7));

// Update strike prices
if (front > 0) {
strikeIter = strikes.begin();
std::advance(strikeIter, front);
strikes.erase(strikes.begin(), strikeIter);
}
if (back < 0) {
strikeIter = strikes.end();
std::advance(strikeIter, back);
strikes.erase(strikeIter, strikes.end());
}

// Initialize structures in option chain
for (double strike : strikes) {
chain[strike]['C'].bidSize = -99;
chain[strike]['P'].bidSize = -99;
}

// Find the nearest expiration date over 21 days away
long int monthTime = static_cast<long int> (std::time(nullptr)) + 60 * 60 * 24 * 21;
for (std::string exp : expirations) {

// Can't use std::get_line because of Visual Studio issues
sscanf(exp.c_str(), "%4d%2d%2d", &year, &month, &day);
tmpTime.tm_sec = 0;
tmpTime.tm_min = 0;
tmpTime.tm_hour = 0;
tmpTime.tm_year = year - 1900;
tmpTime.tm_mon = month - 1;
tmpTime.tm_mday = day;
expTime = static_cast<long int>(std::mktime(&tmpTime));

// Compare expiration date to three weeks away
if (expTime > monthTime) {
expiration = exp;
break;
}
}
}

// Access ask price/bid price
void ChainReader::tickPrice(TickerId reqId, TickType field,
double price, const TickAttrib& attrib) {

// Determine the strike price and right
int index = (reqId - 3)/2;
double strike = *std::next(strikes.begin(), index);
char right = (reqId & 1) ? 'C' : 'P';

// Update the option chain
if (field == 1) {
chain[strike][right].bidPrice = price;
}
else if (field == 2) {
chain[strike][right].askPrice = price;
}
}

// Access ask size/bid size
void ChainReader::tickSize(TickerId reqId, TickType field, int size) {

// Determine the strike price and right
int index = (reqId - 3) / 2;
double strike = *std::next(strikes.begin(), index);
char right = (reqId & 1) ? 'C' : 'P';

// Update the option chain
if (field == 0) {
chain[strike][right].bidSize = size;
}
else if (field == 3) {
chain[strike][right].askSize = size;
}
}

void ChainReader::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
