/* *************************************
     URL.CS: Class for representing Url
   ************************************* */

class url : optional
{
    private readonly string method;
    private readonly string section;
    private readonly string postSection;
    private readonly string protocol;

    private url(): base(false) { }

    private url(string m, string s, string ps, string pr): base(true)
    {
        method = m;
        section = s;
        postSection = ps;
        protocol = pr;
    }

    public string GetSection()
    {
        return section;
    }

    public static url Create(string str)
    {
        if (str == "-") return new url();
        string method = Utils.GetIdentifier(ref str);
        string url = Utils.GetIdentifier(ref str);
        string protocol = Utils.GetIdentifier(ref str);

        string u = (url.StartsWith("http://")) ? url.Substring(7): url;
        int p = ((u[0]=='/') ? u.IndexOf('/', 1) : u.IndexOf('/', 1+u.IndexOf('/', 0)));

        return (p==-1) ? (new url()) : // Subsection does not exists!
            (new url(method, u.Substring(0,p), u.Substring(p+1), protocol));
    }

    public override string ToString()
    {
        return Valid() ? ( "{ Method=" + method
                         + ", Section=" + section
                         + ", PostSection=" + postSection
                         + ", Protocol=" + protocol + " }") : "-";
    }
}
