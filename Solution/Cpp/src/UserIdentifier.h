#ifndef USERIDENTIFIER_H
#define USERIDENTIFIER_H

#include <string>

#include "Utils.h"

class userIdentifier :  public optional
{
private:
    string data;

    userIdentifier();

public:

    string ToString();

    static userIdentifier Create(string, errors&);
};

#endif /* USERIDENTIFIER_H */
