#include <iostream>
#include <vector>
#include <set>

using namespace std;


template<class T>
bool FindSet(const set<T>& v, const T& x)
{
    for (auto& y: v) if (y == x) return true;
    return false;
}

template<class T>
bool RemoveSet(set<T>& v, const T& x)
{
    auto i = v.begin();
    while (i != v.end())
        if (*i == x) { v.erase(i); return true; } else i++;
    return false;
}

template<class T>
set<T> UnionSet(set<T>& v1, set<T>& v2)
{
    set<T> r(v1);
    for(auto& x: v2) r.push_back(x);
    return r;
}

template<class T>
set<T> IntersectSet(set<T>& v1, set<T>& v2)
{
    set<T> r;
    for(auto& x: v1)
        if (FindSet(v2, x)) r.push_back(x);
    return r;
}

template<class T>
set<T> Vector2Set(const vector<T>& v)
{
    set<T> r;
    for(auto& x: v) r.push_back(x);
    return r;
}
