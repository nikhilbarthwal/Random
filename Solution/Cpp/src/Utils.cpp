//============================================================================
// Name        : test2.cpp
// Author      : Nikhil
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

/*
 *
    Consume an actively written-to w3c-formatted HTTP access log (https://en.wikipedia.org/wiki/Common_Log_Format)
    Every 10s, display in the console the sections of the web site with the most hits (a section is defined as being what's before the second '/' in a URL. i.e. the section for "http://my.site.com/pages/create' is "http://my.site.com/pages"), as well as interesting summary statistics on the traffic as a whole.
    Make sure a user can keep the console app running and monitor traffic on their machine
    Whenever total traffic for the past 2 minutes exceeds a certain number on average, add a message saying that High traffic generated an alert - hits = {value}, triggered at {time}
    Whenever the total traffic drops again below that value on average for the past 2 minutes, add another message detailing when the alert recovered

    Make sure all messages showing when alerting thresholds are crossed remain visible on the page for historical reasons.
    Write a test for the alerting logic
    Explain how youd improve on this application design
 *
 */


#include "Common.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;


int Find(string s, char c, int j)
{
    for (int i = j; i < (int) s.length(); i++)
        if (s[i] == c) return i;

    return -1;
}

optional<string> CreateOptional(string s)
{
    if (s == "-") return optional<string>();
    return optional<string>(s);
}

optional<string> GetIdentifier(const string& s, char c, int p1, int& p2)
{
    p2 = Find(s, c, p1);
    return CreateOptional(s.substr(p1, p2-p1));
}

optional<int> Convert2Int(optional<string> s)
{
    if (s.GetStatus())
    {
        if (s.GetData() == "-") return optional<int>();
        return optional<int>(Convert2Int(s.GetData()));
    }

    return optional<int>();
}

int Convert2Int(string s)
{
    stringstream cnv(s);
    int x;
    cnv>>x;
    return x;
}

void Trim(string& s)
{
    stringstream cnv(s);
    int x;
    cnv>>x;
    return x;
}


/*
*
Consume an actively written-to w3c-formatted HTTP access log (https://en.wikipedia.org/wiki/Common_Log_Format)
Every 10s, display in the console the sections of the web site with the most hits (a section is defined as being what's before the second '/' in
a URL. i.e. the section for "http://my.site.com/pages/create' is "http://my.site.com/pages"), as well as interesting summary statistics on the traffic as a whole.
Make sure a user can keep the console app running and monitor traffic on their machine
Whenever total traffic for the past 2 minutes exceeds a certain number on average, add a message saying that High
traffic generated an alert - hits = {value}, triggered at {time}
Whenever the total traffic drops again below that value on average for the past 2 minutes, add another message detailing when the alert recovered

Make sure all messages showing when alerting thresholds are crossed remain visible on the page for historical reasons.
Write a test for the alerting logic
Explain how youd improve on this application design

*/
