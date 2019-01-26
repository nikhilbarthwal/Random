/* *********************************************************************
    LOGSTATEMENT.CS: Parser for Log Statement strutcture and exceptions
   ********************************************************************* */

using System;

public class LogParseException: Exception
{
    public LogParseException(string s) : base(s) { }
    public override string ToString() { return base.Message; }
}

class logStatement
{
    public readonly ipAddress IpAddress;
    public readonly userIdentifier UserIdentifier;
    public readonly user User;
    public readonly timestamp Timestamp;
    public readonly url Url;
    public readonly uint Response;
    public readonly uint Size;

    public logStatement(string statement)
    {
        IpAddress = ipAddress.Create(Utils.GetIdentifier(ref statement));
        UserIdentifier = userIdentifier.Create(Utils.GetIdentifier(ref statement));
        User = user.Create(Utils.GetIdentifier(ref statement));

        Utils.Trim(ref statement);
        if (statement[0] == '[')
            statement = statement.Substring(1);
        else
            throw new LogParseException("Cannot read timestamp, expecting \'[\', but could not find");
        Timestamp = timestamp.Create(Utils.GetIdentifier(ref statement,']'));

        Utils.Trim(ref statement);
        if (statement[0] == '\"')
            statement = statement.Substring(1);
        else
            throw new LogParseException("Cannot read url, expecting \'\"\', but could not find");

        Url = url.Create(Utils.GetIdentifier(ref statement,'\"'));

        Response = Utils.GetNumber(ref statement, "Response");
        Size = Utils.GetNumber(ref statement, "Size");
    }

    public override string ToString()
    {
        return "{ IpAddress=" + IpAddress
             + ", UserIdentifier=" + UserIdentifier
             + ", User=" + User
             + ", Timestamp=" + Timestamp
             + ", Url=" + Url
             + ", Response=" + Response
             + ", Size=" + Size + " }";
    }
}
