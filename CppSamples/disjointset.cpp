#include <iostream>
#include <list>
#include <map>
#include <vector>

using namespace std;

template <class T>
class disjointset
{
private:

    list<list<T>> data;

    typedef typename list<list<T>>::iterator listptr;

    listptr search(T e)
    {
        for(auto i = data.begin(); i != data.end(); i++)
            for(auto j : *i)
                if (e==j) return i;

        return data.end();
    }

public:

    disjointset(vector<T> n)
    {
        for(auto v: n)
        {
            list<T> x;
            x.push_back(v);
            data.push_back(x);
        }
    }

    void Join(T e1, T e2)
    {
        auto b1 = search(e1);
        auto b2 = search(e2);

        if ((b1 != data.end()) && (b2 != data.end()))
        {
            for(auto i = (*b2).begin(); i != (*b2).end(); i++)
                (*b1).push_back(*i);
            data.erase(b2);
        }
        else
            throw "Node does not exists!";
    }

    int Size()
    {
        return data.size();
    }
};


int main(void)
{
    try
    {
        disjointset<int> x({1,2,3,4,5,6});
        x.Join(3,4);
        cout<<"Size: "<<x.Size()<<endl;
    }
    catch(const char* s)
    {
        cout<<"ERROR: "<<s<<endl;
    }

    return 0;
}
