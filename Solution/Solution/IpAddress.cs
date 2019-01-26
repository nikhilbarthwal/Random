/* ***************************************************
    IPADDRESS.CS: Class for representing ip addresses
   *************************************************** */

using System;

class ipAddress: optional
{
    private readonly byte ip1;
    private readonly byte ip2;
    private readonly byte ip3;
    private readonly byte ip4;

    private ipAddress(byte[] ip): base(true)
    {
        ip1 = ip[0];
        ip2 = ip[1];
        ip3 = ip[2];
        ip4 = ip[3];
    }

    private ipAddress(): base(false) { }

    public override string ToString()
    {
        return Valid()? (ip1 + "." + ip2 + "." + ip3 + "." + ip4) : "-";
    }

    public static ipAddress Create(string str)
    {
        if (str == "-") return new ipAddress();
        string[] tokens = str.Split('.');
        if (tokens.Length != 4)
            throw new LogParseException("Exact 4 values should be present in ipaddress");

        byte[] ip = new byte[4];

        int j;
        for (int i = 0; i < 4; i++)
            if (Int32.TryParse(tokens[i], out j))
            {
                if ((j < 0) || (j > 255))
                    throw new LogParseException("index " + i + " out of range in ipaddress");
                ip[i] = (byte) j;
            }
            else
                throw new LogParseException("Could not parse ipaddress");

        return new ipAddress(ip);
    }
}
