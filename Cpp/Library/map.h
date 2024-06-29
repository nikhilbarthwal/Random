#include <map>
#include <set>

using namespace std;


// Find Key:  m.count(x) > 0
// Delete Key:  m.erase(x)

template<class K, class V>
int FindInMap(map<K,V>& m, const V& v)
{
    int c = 0;
    for(auto i = m.begin(); i != i.end(); i++)
        if (i->second == v) c++;
    return c;
}

template<class K, class V>
int DelValuesInMap(map<K,V>& m, const V& v)
{
    int c = 0;
    for(auto i = m.begin(); i != m.end(); i++)
        if (i->second == v) { m.erase(i->first); c++; }
    return c;
}

template<class K, class V>
void AppendMap(map<K,V>& m, const map<K,V>& mm)
{
    for(auto i = mm.begin(); i != mm.end(); i++)
        m[i->first] = m[i->second];
}

template<class K, class V>
set<K> KeysInMap(const map<K,V>& m)
{
    set<K> keys;
    for(auto i = m.begin(); i != m.end(); i++)
        keys.push_back(i->first);
    return keys;
}
