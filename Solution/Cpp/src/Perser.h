

struct for LogStatement
{
    points for all stuff

    Contructor for Null
}



LogStatement ParseLogStatement(string, errors&)


//TODO: Create an error class here

------------------------




/*
 * Common.h
 *
 *  Created on: Apr 7, 2018
 *      Author: nikhil
 */

#ifndef HEADER
#define HEADER

#include <iostream>
#include <string>
#include <sstream>
#include "Config.h"

using namespace std;

template <class T>
class optional
{
private:
    bool value;
    T data;

public:
    optional()
    {
        value = false;
    }

    optional(T x)
    {
        data = x;
        value = true;
    }

    bool GetStatus()
    {
        return value;
    }

    T GetData()
    {
        if (!value) throw "Attempted to get data from a empty optional";
        return data;
    }
};

/* ******** Header for Time.cpp ******** */

struct timestamp
{
    string date; //TODO: Date split
    int hr;
    int min;
    int sec;

    bool compare(timestamp t);
};

bool EdgeTimestamp(optional<timestamp>);


/* ******** Header for Utils.cpp ******** */

int Find(string, char, int);
optional<string> CreateOptional(string);
optional<string> GetIdentifier(const string&, char, int, int&);
optional<int> Convert2Int(optional<string>);
optional<timestamp> GetTimeStamp(string, int, int&);
int Convert2Int(string);


/* ******** Header for Url.cpp ******** */

optional<string> ProcessUrl(string, int, int&);
void PrintStats(void);

#endif /* HEADER */
