#define ATR_PERIOD 20
#define ENTER_PERIOD 20
#define EXIT_PERIOD 10

#include <algorithm>
#include <deque>
#include <filesystem>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <map>
#include <numeric>
#include <string>
#include <sstream>
#include <vector>

enum InvState { outPos, longPos, shortPos };

int main() {

// Containers
std::map<std::string, int> symbols = { {"GE", 2500},
{"ES", 50}, {"CHF", 125000}, {"GBP", 62500},
{"CAD", 100000}, {"GC", 100}, {"SI" , 5000},
{"HG" , 25000}, {"RB" , 42000} };
std::map<double, int> positions;
std::deque<double> trueRanges, enters, exits;

double funds, initFunds = 10000000.00;
double close, low, high, trueRange, oldClose, oldAtr, N;
double contractSize, lastPrice, totPosition, ret;
int unitSize, nameLen, lineCount;
std::string fileName, line, sym, str;
InvState invState;
std::ifstream csvFile;
std::stringstream ss;
size_t pos;
bool foundSymbol;

// Iterate through files
for (const auto &f : std::filesystem::directory_iterator(".")) {

// Check if CSV file starts with symbol
fileName = f.path().string();
pos = fileName.find_last_of(".");
if ((pos != std::string::npos) && (pos > 1)) {
pos = fileName.find("csv", pos);
if (pos != std::string::npos) {
foundSymbol = false;
for(auto s: symbols) {
if (fileName.find(s.first) != std::string::npos) {
sym = s.first;
foundSymbol = true;
break;
}
}
if (!foundSymbol)
continue;
} else {
continue;
}
} else {
continue;
}

// Initialize values and containers
funds = initFunds;
oldAtr = -1.0;
oldClose = -1.0;
invState = InvState::outPos;
trueRanges.clear();
enters.clear();
exits.clear();
positions.clear();

// Open file and read lines
lineCount = 0;
csvFile.open(fileName, std::ios::in);
std::getline(csvFile, line);
while (std::getline(csvFile, line)) {

// Read values
lineCount++;
ss << line;
std::getline(ss, str, ',');
close = std::stod(str);
std::getline(ss, str, ',');
low = std::stod(str);
std::getline(ss, str, ',');
high = std::stod(str);
ss.str("");

// Find true range
if (oldClose != -1.0) {
trueRange = std::max({high - low, std::abs(high - oldClose),
std::abs(low - oldClose)});
trueRanges.push_back(trueRange);
oldClose = close;
}
else {
oldClose = close;
continue;
}

// Compute average true range
if (trueRanges.size() == ATR_PERIOD) {
N = ((ATR_PERIOD - 1) * oldAtr + trueRange) / ATR_PERIOD;
oldAtr = N;
trueRanges.pop_front();
}
else {
oldAtr = std::accumulate(trueRanges.begin(),
trueRanges.end(), 0.0) / trueRanges.size();
continue;
}

// Compute unit size
contractSize = symbols[sym];
unitSize = static_cast<int>(0.01 * funds/(N * contractSize));

// Check for entry
if ((invState == InvState::outPos) && (enters.size() == ENTER_PERIOD)) {

// Buy 1 unit at 20-day high
if (close > *std::max_element(enters.begin(), enters.end())) {
positions[close] = unitSize;
lastPrice = close;
invState = InvState::longPos;
}

// Short 1 unit at 20-day low
else if (close < *std::min_element(enters.begin(), enters.end())) {
positions[close] = unitSize;
lastPrice = close;
invState = InvState::shortPos;
}
}

// Exit position if price at 10-day low/high
else if (((invState == InvState::longPos) &&
(close < *std::min_element(exits.begin(), exits.end()))) ||
((invState == InvState::shortPos) &&
(close > *std::max_element(exits.begin(), exits.end())))) {

for (const auto &pos : positions) {
if (invState == InvState::longPos) {
funds += pos.second * contractSize * (close - pos.first);
}
else {
funds += pos.second * contractSize * (pos.first - close);
}
}
positions.clear();
lastPrice = 0.0;
invState = InvState::outPos;
}

// Exit position if price falls/rises by 2N
else if (((invState == InvState::longPos) &&
(close < lastPrice - 2*N)) ||
((invState == InvState::shortPos) &&
(close > lastPrice + 2*N))) {

// Apply stop condition
if (invState == InvState::longPos) {
close = lastPrice - 2 * N;
}
else {
close = lastPrice + 2 * N;
}

// Exit position
for (const auto &pos : positions) {
if (invState == InvState::longPos) {
funds += pos.second * contractSize * (close - pos.first);
}
else {
funds += pos.second * contractSize * (pos.first - close);
}
}
positions.clear();
lastPrice = 0.0;
invState = InvState::outPos;
}

// Increase position if price rises/falls by N/2
else if (((invState == InvState::longPos) &&
(close > lastPrice + N / 2)) ||
((invState == InvState::shortPos) &&
(close < lastPrice - N / 2))) {

// Make sure position doesn't exceed 4 units
totPosition = 0.0;
for (const auto &pos : positions) {
totPosition += pos.second;
}
if (totPosition + unitSize < 4 * unitSize) {

if (positions.count(close) > 0) {
positions[close] += unitSize;
}
else {
positions[close] = unitSize;
}
lastPrice = close;
}
}

// Update containers
if (enters.size() == ENTER_PERIOD) {
enters.pop_front();
}
enters.push_back(close);
if (exits.size() == EXIT_PERIOD) {
exits.pop_front();
}
exits.push_back(close);
}

// Exit position and print result
for (const auto &pos : positions) {
if (invState == InvState::longPos) {
funds += pos.second * contractSize * (close - pos.first);
}
else {
funds += pos.second * contractSize * (pos.first - close);
}
}
ret = funds / initFunds;
if (lineCount > 0) {
std::cout << "Return for " << sym << ": " << std::setprecision(4) << ret << std::endl;
}
csvFile.close();
}
return 0;
}
