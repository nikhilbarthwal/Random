/* ************************************
     UTILS.CS: Class for all utilities
   ************************************ */

using System;

static class Utils
{
    public static void Trim(ref string str)
    {
        str = str.Trim();
        if (str.Length == 0) throw new LogParseException("Unexpected end of string");
    }

    public static string GetIdentifier(ref string statement)
    {
        Trim(ref statement);
        string token;
        int n = statement.Length;
        if (string.IsNullOrEmpty(statement))
            throw new LogParseException("Unexpected end of string");

        for (int i = 0; i < statement.Length; i++)
            if (Char.IsWhiteSpace(statement[i]))
            {
                token = statement.Substring(0,i);
                statement = statement.Substring(i);
                return token;
            }

        token = statement;
        statement = "";
        return token;
    }

    public static string GetIdentifier(ref string statement, char ch)
    {
        Trim(ref statement);
        int n = statement.Length;
        if (n == 0) throw new LogParseException("Unexpected end of string");

        for (int i = 0; i < n; i++)
            if (statement[i] == ch)
            {
                string token = statement.Substring(0,i);
                statement = statement.Substring(i+1);
                return token;
            }

        throw new LogParseException("Unexpected end of string, was looking for \'"+ch+"\'");
    }

    public static uint GetNumber(ref string statement, string msg)
    {
        int n = -1;
        string token = GetIdentifier(ref statement);
        if (Int32.TryParse(token, out n))
        {
            if (n>=0) return ((uint) n);
            throw new LogParseException("Cannot read " + msg + ", expecting a positive number as value");
        }
        throw new LogParseException("Cannot read " + msg + ", unable to parse the number");
    }

    public static bool CheckIdentifier(string s)
    {
        int n = s.Length;

        if (((s[0] <= 'a') || (s[0] >= 'z')) && ((s[0] <= 'A') || (s[0] >= 'Z')))
            return false;

        for (int i = 1; i < n; i++)
            if (((s[0] <= 'a') || (s[0] >= 'z')) && ((s[0] <= 'A') || (s[0] >= 'Z')) && ((s[0] <= '0') || (s[0] >= '9')))
                return false;

        return true;
    }
}
