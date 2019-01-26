/* *************************************************
    TIMESTAMP.CS: Class for representing timestamps
   ************************************************* */

using System;
using System.Globalization;
using System.Diagnostics;

class timestamp: optional
{
    private const string format = "dd/MMM/yyyy:HH:mm:ss zzz";
    private readonly DateTime data;
    private timestamp(DateTime t): base(true) { data = t; }
    private timestamp(): base(false) { }

    public override string ToString()
    {
        return Valid()? data.ToString(CultureInfo.InvariantCulture) : "-";
    }

    public static timestamp Create(string str)
    {
        if (str == "-") return new timestamp();

        try
        {
            return new timestamp(DateTime.ParseExact(str, format, CultureInfo.InvariantCulture));
        }
        catch (FormatException)
        {
            throw new LogParseException("Unable to parse Date Time value");
        }
    }

    public bool Edge()
    {
        return (Valid() && ((data.Second % 10) == 0));
    }

    public DateTime GetValue()
    {
        Debug.Assert(Valid(), "Trying to get a value from a invalid timestamp");
        return data;
    }
}
