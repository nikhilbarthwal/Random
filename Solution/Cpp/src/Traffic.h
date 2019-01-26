#ifndef TRAFFIC_H
#define TRAFFIC_H

#include <string>

#include "Registry.h"
#include "KeyCache.h"
#include "Url.h"
#include "Timestamp.h"

using namespace std;

class traffic {
private:
    registry Registry;
    keyCache KeyCache;
    bool status; // True for HIGH & False for LOW
    timestamp Timestamp; //Time when status change happened
    void SetHigh(const timestamp&);
    void SetLow(const timestamp&);

public:

    traffic(int);

    bool High()
    {
        return status;
    }

    bool Low()
    {
        return (!status);
    }

    void Update(url Url);

    int Check();

    void PrintStats();

    void PrintHighMessage(const timestamp&);

    void PrintLowMessage(const timestamp&);
};

#endif /* TRAFFIC_H */
