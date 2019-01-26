/* ***************************************
     USER.CS: Class for representing user
   *************************************** */

class user : optional
{
    private readonly string data;
    private user(string s) : base(true) { data = s; }
    private user(): base(false) { }

    public override string ToString() { return Valid()? data: "-"; }

    public static user Create(string str)
    {
        if (str == "-") return new user();
        if (Utils.CheckIdentifier(str)) return new user(str);

        throw new LogParseException("Valid identifier not found for user");
    }
}
