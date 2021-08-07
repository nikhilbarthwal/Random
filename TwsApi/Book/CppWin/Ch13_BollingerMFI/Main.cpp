#define _CRT_SECURE_NO_WARNINGS

#define BOLLINGER_PERIOD 5
#define MFI_PERIOD 10

#include <algorithm>
#include <cmath>
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

int main() {

// Containers
std::map<std::string, int> symbols = { {"GE", 2500},
{"ES", 50}, {"CHF", 125000}, {"GBP", 62500},
{"CAD", 100000}, {"GC", 100}, {"SI" , 5000},
{"HG" , 25000}, {"RB" , 42000} };
std::map<double, int> positions;
std::deque<double> moneyFlows, prices;

double avg, stdDev;
enum InvState { outPos, longPos, shortPos };
double funds, initFunds = 10000000.00;
double close, low, high, moneyFlow, posFlow, negFlow, mfi, percentB;
double contractSize, oldTypical, typical, upper, lower, ret;
int unitSize, lineCount;
long long vol;
std::string fileName, line, str, sym;
InvState invState;
std::ifstream csvFile;
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
for (auto s : symbols) {
if (fileName.find(s.first) != std::string::npos) {
sym = s.first;
foundSymbol = true;
break;
}
}
if (!foundSymbol)
continue;
}
else {
continue;
}
}
else {
continue;
}

// Initialize values and containers
funds = initFunds;
oldTypical = -1.0;
invState = InvState::outPos;
moneyFlows.clear();
positions.clear();
prices.clear();

// Compute unit size
contractSize = symbols[sym];
unitSize = static_cast<int>(0.01 * funds / contractSize);

// Open file and read lines
lineCount = 0;
csvFile.open(fileName, std::ios::in);
std::getline(csvFile, line);
while (std::getline(csvFile, line)) {

// Read values
lineCount++;
std::stringstream ss(line);
std::getline(ss, str, ',');
close = std::stod(str);
std::getline(ss, str, ',');
low = std::stod(str);
std::getline(ss, str, ',');
high = std::stod(str);
std::getline(ss, str);
vol = std::stoi(str);

// Compute the money flow
typical = (high + low + close) / 3.0;
if (oldTypical > typical) {
oldTypical = typical;
typical *= -1.0;
}
else {
oldTypical = typical;
}
moneyFlow = typical * vol;

// Compute the money flow index
moneyFlows.push_back(moneyFlow);
if (moneyFlows.size() == MFI_PERIOD) {

// Compute positive/negative money flows
posFlow = 0.0;
negFlow = 0.0;
for (double flow : moneyFlows) {
if (flow > 0) {
posFlow += flow;
}
else {
negFlow += -1.0 * flow;
}
}
mfi = 100.0 * posFlow / (posFlow + negFlow);
moneyFlows.pop_front();
}
else {
continue;
}

// Compute upper, lower, and %b
prices.push_back(close);
if (prices.size() == BOLLINGER_PERIOD) {

// Compute the average
avg = std::accumulate(prices.begin(),
prices.end(), 0.0) / prices.size();

// Compute the standard deviation
auto devFunc = [&avg](double acc, const double& p) {
return acc + (p - avg)*(p - avg);
};
stdDev = std::accumulate(prices.begin(), prices.end(), 0.0, devFunc);
stdDev = std::sqrt(stdDev / BOLLINGER_PERIOD);

// Compute %b
upper = avg + 2 * stdDev;
lower = avg - 2 * stdDev;
percentB = 100.0 * (close - lower) / (upper - lower);

// Check for buy signal
if ((percentB > 80.0) && (mfi > 80.0)) {

// If out, enter long position
if (invState == InvState::outPos) {
positions[close] = unitSize;
invState = InvState::longPos;
}

// If long, increase position
else if (invState == InvState::longPos) {
if (positions.count(close) > 0) {
positions[close] += unitSize;
}
else {
positions[close] = unitSize;
}
}

// If short, exit position
else if (invState == InvState::shortPos) {
for (const auto &pos : positions) {
funds += pos.second * contractSize * (pos.first - close);
}
positions.clear();
invState = InvState::outPos;
}
}

// Check for sell signal
else if ((percentB < 20.0) && (mfi < 20.0)) {

// If out, enter long position
if (invState == InvState::outPos) {
positions[close] = unitSize;
invState = InvState::shortPos;
}

// If long, exit position
else if (invState == InvState::longPos) {
for (const auto &pos : positions) {
funds += pos.second * contractSize * (close - pos.first);
}
positions.clear();
invState = InvState::outPos;
}

// If short, increase position
else if (invState == InvState::shortPos) {
if (positions.count(close) > 0) {
positions[close] += unitSize;
}
else {
positions[close] = unitSize;
}
}
}
prices.pop_front();
}
}

// Exit position and print result
for (const auto &pos : positions) {
if (invState == InvState::longPos) {
funds += pos.second * contractSize * (close - pos.first);
}
else if (invState == InvState::shortPos) {
funds += pos.second * contractSize * (pos.first - close);
}
}
ret = funds / initFunds;
if (lineCount > 1) {
std::cout << "Return for " << sym << ": " << std::setprecision(4) << ret << std::endl;
}
csvFile.close();
}
return 0;
}
