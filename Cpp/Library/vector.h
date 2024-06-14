#include <iostream>
#include <vector>

using namespace std;

template<class T>
int find(const std::vector<T>& v, T x)
{
    for (int i = 0; i < (int) v.size(); ++i)
        if (v[i] == x) return i;
    return -1;
}

template<class T>
int rfind(const std::vector<T>& v, T x)
{
    int l = (int) v.size();
    for (int i = l-1; i >= 0; --i)
        if (v[i] == x) return i;
    return -1;
}

template<class T>
int remove(std::vector<T>& v, T x)
{
    auto i = v.begin();
    int c = 0;
    while (i != v.end())
        if (*i == x) { v.erase(i); c++; } else i++;
    return c;
}
