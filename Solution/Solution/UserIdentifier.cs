/* ***********************************************
     USERIDENTIFIER.CS: Class for user identifier
   *********************************************** */

class userIdentifier : optional
{
    private readonly string data;
    private userIdentifier(string s): base(true) { data = s; }

    private userIdentifier(): base(false) { }

    public override string ToString() { return Valid()? data : "-"; }

    public static userIdentifier Create(string str)
    {
        if (str == "-") return new userIdentifier();
        if (Utils.CheckIdentifier(str)) return new userIdentifier(str);

        throw new LogParseException("Valid identifier not found for userIdentifier");
    }
}
