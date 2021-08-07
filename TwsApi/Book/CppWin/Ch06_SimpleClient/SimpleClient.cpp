#define _CRT_SECURE_NO_WARNINGS

#include "SimpleClient.h"

SimpleClient::SimpleClient(const char *host, int port, int clientId) :
signal(1000), EClientSocket(this, &signal) {

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

SimpleClient::~SimpleClient() { delete reader; }

// Receive and display the current time
void SimpleClient::currentTime(long curTime) {
time_t epoch = curTime;
std::cout << "Current time: " << asctime(localtime(&epoch)) << std::endl;
}

// Respond to errors
void SimpleClient::error(int id, int code, const std::string& msg) {
std::cout << "Error: " << code << ": " << msg << std::endl;
}
