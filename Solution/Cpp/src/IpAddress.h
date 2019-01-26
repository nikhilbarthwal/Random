#ifndef IPADDRESS_H
#define IPADDRESS_H

#include <string>

#include "Utils.h"
#include "Optional.h"

class ipAddress : public optional
{
private:
    char ip1;
    char ip2;
    char ip3;
    char ip4;

    ipAddress();

public:
    string ToString();
    static ipAddress Create(string, errors&);
};

#endif /* IPADDRESS_H */
