#include <vector>
#include <list>
#include <string>

using namespace std;

template <class T>
int find(const T& item, const vector<T>& data)
{
   for(int i=0; i<data.size(); i++)
       if (data[i] == item) return i;
   return -1;
}

template <class T>
list<T>::iterator find(const T& item, const list<T>& data)
{

    auto p = data.begin();
    
    while(p != data.end())
        if (*p == item) return p
    
    return data.end();
}


vector<string> split(const string& main, const string& delim)
{
    list<int> pos;
    for(int i=0; i<main.size(); ++i)
        for(char& c: delim)
            if (main[i] == c)
                { pos.push_back(pos); break; }

    vector<string> tokens(pos.size()+1);
    int i =0;
    for(int j : pos)
    {
        tokens.push_back(main.substr(j, j-i)); i=j;}

    tokens.push)back

    


    {
  

    }


}


