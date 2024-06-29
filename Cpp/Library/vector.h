#include <vector>
#include <set>

#include "string.h"

using namespace std;

template<class T>
int FindInVector(const vector<T>& v, const T& x)
{
    for (int i = 0; i < (int) v.size(); ++i)
        if (v[i] == x) return i;
    return -1;
}

template<class T>
int RFindInVector(const vector<T>& v, const T& x)
{
    int l = (int) v.size();
    for (int i = l-1; i >= 0; --i)
        if (v[i] == x) return i;
    return -1;
}

template<class T>
int RemoveFromVector(vector<T>& v, const T& x)
{
    auto i = v.begin();
    int c = 0;
    while (i != v.end())
        if (*i == x) { v.erase(i); c++; } else i++;
    return c;
}

template<class T>
void AppendVector(vector<T>& v, const vector<T>& vv)
{
    for(auto& x: vv) v.push_back(x);
}

template<class T>
vector<T> Set2Vector(const set<T>& v)
{
    vector<T> r;
    for(auto& x: v) r.push_back(x);
    return r;
}


vector<string> SplitString(const string& str, const string& delim)
{
    int l = (int) str.length();
    int pos = 0;
    vector<string> r;
    while(pos < l)
    {
        while((FindCharInString(delim, str[pos]) >= 0))
            { pos++; if (pos == l) return r; }
        int start = pos;

        while((FindCharInString(delim, str[pos]) == -1))
            { pos++; if (pos == l) return r; }
        r.push_back(str.substr(start, pos - start));
    }
    return r;
}
