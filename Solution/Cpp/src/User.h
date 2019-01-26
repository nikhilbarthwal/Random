#ifndef USER_H
#define USER_H

#include <string>

#include "util.h"

class user :  public optional
{
private:
    string data;

    user();

public:

    string ToString();

    static user Create(string, errors&);
};

#endif /* USER_H */
