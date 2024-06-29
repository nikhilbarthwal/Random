#include <iostream>
#include <string>
#include <climits>

using namespace std;


// Convert to int: string to_string(int)
// Convert to int: string to_string(float)
// Compare string: string s1 > string s2


int FindCharInString(const string& str, const char c)
{
    const int l = (int) str.length();
    for(int i=0; i<l; i++)
        if (str[i] == c) return i;
    return -1;
}

int RFindCharInString(const string& str, const char c)
{
    const int l = ((int) str.length()) - 1;
    for(int i=l; i>=0; i--)
        if (str[i] == c) return i;
    return -1;
}

string DeleteChar(const string& str, const char c)
{
    string r;
    for(char x:str)
        if (x != c) r.push_back(x);
    return r;
}

bool ReplaceSubstring(string& str, const string& oldStr, const string& newStr)
{
    // Find the position of the old substring
    size_t pos = str.find(oldStr);

    // If found, replace with the new substring
    if (pos == std::string::npos) return false;
    str.replace(pos, oldStr.length(), newStr);
    return true;
}

int FindSubstring(const string& s, const string& sub)
{
    int p = (int) s.find(sub);

    if (p == std::string::npos) return -1;
    return (int) p;
}

string Reverse(const string& s)
{
    string r(s);
    int i = 0;
    int j = (int) s.length() -1;
    while (i < j) {
        char c = r[i];
        r[i] = r[j];
        r[j] = c;
        i++; j--;
    }
    return r;
}

string Trim(const string &str) {
    int i = 0, j = 0;
    while (isspace(str[i]))
        i++;

    while (!isspace(str[i + j]))
        if ((i + j) == str.length() - 1)
            return str.substr(i);
        else
            j++;

    return str.substr(i, j);
}

int Convert(const string &str) {
    string s = Trim(str);
    const int n = s.length();

    if (n == 0)
        throw runtime_error("Empty string");

    bool sign = true;
    int p = 0;
    const char c = s[0];

    if (c == '-') {
        p++;
        sign = false;
    } else {
        if (c == '+')
            p++;
    }

    for (int i = p; i < n; i++)
        if ((s[i] < '0') || (s[i] > '9'))
            throw runtime_error("Invalid format");

    while (s[p] == '0') p++;

    const int limit = sign ? INT_MAX : INT_MIN;
    int result = 0;
    int exp = (sign) ? 1 : -1;
    for (int i = n - 1; i >= p; i--) {
        int delta = exp * (s[i] - '0');

        if ((sign) ? ((limit - result) >= delta) : ((limit - result) <= delta))
            result += delta;
        else
            throw overflow_error("Integer overflow");

        if ((((double) limit / exp) < (double) 10) && (i > p))
            throw overflow_error("Integer overflow");
        else
            exp *= 10;
    }

    return result;
}
