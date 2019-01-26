//============================================================================
// Name        : test2.cpp
// Author      : Nikhil
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================




#include "Common.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

import Config Values
//Config Values go here


int main(void)
{
    while(cin is good and not eof)
    {
        read s
        errros()
        LogStatement = Parse(string , errors)

        if (e.OK)
        {
            LogStatement.TimeStamp.EdgeTimestamp()
                LogStatement.Url.PrintStats()


            if CheckTraffic(Log.Statement, TimeStamp, 2 mins) > thresholds) && LowTraffic)





        }
        else
        {
            Print: X errors found for "-"
            Errors.Print()
        }
    }
    return 0;
}



/* ----------------- DEBUG -----------------
template <class T>
void Print(optional<T> x)
{
    if (x.GetStatus())
        cout<<"Yes("<<x.GetData()<<")"<<endl;
    else
        cout<<"No"<<endl;
}
// ------------------------------------------

void Main(string s)
{

    int p1, p2, p3, p4, p5, p6;
    optional<string> IpAddress = GetIdentifier(s, ' ',  0, p1);
    optional<string> UserIdentifier = GetIdentifier(s, ' ', p1+1, p2);
    optional<string> User = GetIdentifier(s, ' ', p2+1, p3);
    optional<timestamp> TimeStamp = GetTimeStamp(s, p3+2, p4);
    optional<string> Url = ProcessUrl(s, p4+3, p5);
    optional<int> Response = Convert2Int(GetIdentifier(s, ' ', p5+2, p6));
    optional<int> Size = Convert2Int(CreateOptional(s.substr(p6+1)));

    if (EdgeTimestamp(TimeStamp)) PrintStats();

    // Check Traffic Smonitoring

    //Print(IpAddress);
    //Print(UserIdentifier);
    //Print(User);
    //Print(TimeStamp);
    //Print(Url);
    //Print(Response);
    //Print(Size);
}

int main(void)
{
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cc.com/nikhil/apache_pb1.gif HTTP/1.0\" 200 -");
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cc.com/nikhil/apache_pb2.gif HTTP/1.0\" 200 -");
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cc.com/nikhil/apache_pb3.gif HTTP/1.0\" 200 -");
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cxc.com/nikhil/apache_pb.gif HTTP/1.0\" 200 -");
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cyc.com/nikhil/apache_pb_xx.gif HTTP/1.0\" 200 -");
    Main("127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET http://www.cxc.com/nikhil/apache_pb_xx.gif HTTP/1.0\" 200 -");
    cout<<endl<<endl;
    Main("127.0.0.1 user-identifier frank [10/Oct/2000:13:55:36 -0700] \"GET /apache_pb.gif HTTP/1.0\" 200 2326");
    cout<<endl<<endl;
    Main("127.0.0.1 user-identifier frank [10/Oct/2000:13:55:36 -0700] \"GET www.cnn.com/apache_pb.gif HTTP/1.0\" 200 2326");
    return 0;
}


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
