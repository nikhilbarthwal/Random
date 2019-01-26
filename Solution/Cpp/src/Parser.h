#ifndef LOGSTATEMENT_H
#define LOGSTATEMENT_H

#include "IpAddress.h"
#include "UserIdentifier.h"
#include "User.h"
#include "Timestamp.h"
#include "Url.h"

struct logStatement
{
    ipAddress IpAddress;
    userIdentifier UserIdentifier;
    user User;
    timestamp Timestamp;
    url Url;
    int Response;
    int Size;
};

logStatement ParseStatement(string, errors&);

#endif /* LOGSTATEMENT_H */
