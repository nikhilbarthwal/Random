#include<iostream>
using namespace std;

class filter
{
public:
    virtual bool check(const string&, string&) = 0;
};

class filter_password: public filter
{
public:
    bool check(const string& line, string& ip)
    {
        if (/* line contains "Failed password for ___ from " */)
        {
            ip = /* Use regex to extract IP address */
            return true;
        }
        return false;
    }
};
