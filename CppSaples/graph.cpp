#include <iostream>
#include <vector>
#include <map>

using namespace std;

template <class T>
class graph
{
private:
    vector<T> nodes;
    map<T,vector<T>> links;

    bool search(T e)
    {
        for(auto n: nodes) if (e==n) return true;
        return false;
    }

public:
    graph() : nodes(vector<T>()), links(map<T,vector<T>>()) { }

    graph(vector<T> n) : links(map<T,vector<T>>())
    {
        for(auto v: n)
        {
            nodes.push_back(v);
            links[v] = vector<T>();
        }
    }

    void AddNode(T e)
    {
        if (search(e))
            throw "Node already exists!";
        else
            nodes.push_back(e);
        return;
    }

    vector<T> GetNodes()
    {
        return nodes;
    }

    void AddLink(T e1, T e2)
    {
        if (search(e1) && search(e2))
        {
            if (links.count(e1) == 0)
                links[e1] = {e2};
            else
            {
                auto v = links[e1];
                v.push_back(e2);
                links[e1] = v;
            }
        }
        else
            throw "Node don't exists!";

        return;
    }

    vector<T> GetLinks(T e)
    {
        if (search(e))
            return links[e];
        else
            throw "Node don't exists!";
    }
};


int main(void)
{
    try
    {
        graph<int> g;
        g.AddNode(5);
        g.AddNode(6);
        g.AddNode(3);
        g.AddLink(5,3);
        g.AddLink(5,6);

        for(auto v: g.GetNodes()) cout<<v<<endl;
        cout<<"---------------"<<endl;
        for(auto v: g.GetLinks(5)) cout<<v<<endl;
    }
    catch(const char* s)
    {
        cout<<"ERROR: "<<s<<endl;
    }

    return 0;
}
