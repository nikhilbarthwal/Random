#ifndef URL_H
#define URL_H

#include <string>

#include "Utils.h"

using namespace std;

class url : public optional
{
private:
    //  10/Oct/2000:13:55:36 -0700
    string method;
    string section;
    string postSection;
    string protocol;

    url();

    long epoch();
    long diff(); // Show the difference

public:

    string ToString();

    static url Create(string, errors&);
};

#endif /* URL_H */


/*


#include "Common.h"
#include <iostream>
#include <string>
#include <map>
#include <list>

using namespace std;

struct node
{
    int rank;
    struct node* next;
    struct node* prev;

    node(int i)
    {
        rank = i;
        next = NULL;
        prev = NULL;
    }

    void incr(void)
    {
        rank++;
    }
};

typedef struct node* itr;

static itr head = NULL, tail = NULL;
static map<string, itr> registry;

// ----------------- DEBUG -----------------
void Print()
{
    cout<<"MAP: "<<endl;
    for(map<string, itr>::iterator i = registry.begin(); i != registry.end(); i++)
    {
        itr x = i->second;
        cout<<"\t"<<(i->first)<<" : "<<(x->rank)<<endl;
    }

    cout<<"List Forward: ";
    itr x = head;
    while(x != NULL)
    {
        cout<<x->rank<<" ";
        x = x->next;
    }
    cout<<endl;

    cout<<"List Reverse: ";
    x = tail;
    while(x != NULL)
    {
        cout<<x->rank<<" ";
        x = x->prev;
    }
    cout<<endl;
    cout<<"***********************"<<endl;
}
// ------------------------------------------

/*void append(string s)
{
    if ((head == NULL) && (tail == NULL))
        head = tail = new node(1);
    else
    {
        itr n  = new node(1);
        tail->next = n;
        n->prev = tail;
        tail = n;
    }

    registry[s] = tail;
}

void update(string s)
{
    itr n  = registry[s];
    n->incr();

    while(n != head)
    {
        itr p = n -> prev;
        if (p->rank > n->rank) break;
        p->next = n->next;
        n->prev = p->prev;
        p->prev = n;
        n->next = p;
    }

    if (head->prev != NULL)
        head = head->prev;

    return;
}

optional<string> ProcessUrl(string s, int p1, int& p2)
{
    optional<string> url = GetIdentifier(s, '\"', p1, p2);

    if (url.GetStatus())
    {
        string u = url.GetData();
        int i1 = Find(u, ' ', 0);
        int i2 = Find(u, ' ', i1+1);
        string method = u.substr(0,i1);
        string link = u.substr(i1+1,i2-i1-1);
        string protocol = u.substr(i2+1);

        //cout<<"Method: \""<<method<<"\""<<endl;
        //cout<<"Url: \""<<link<<"\""<<endl;
        //cout<<"Protocol: \""<<protocol<<"\""<<endl;

        int j0 = (link.substr(0,7) == "http://") ? 7: 0;
        int j1 = Find(link, '/', j0);
        int j2 = Find(link, '/', j1+1);
        //cout<<j0<<" "<<j1<<" "<<j2<<endl;

        if (j2 != -1)
        {
            string section = link.substr(j0, j2-j0);

            if (registry.find(section) == registry.end())
            {
                if ((head == NULL) && (tail == NULL))
                    head = tail = new node(1);
                else
                {
                    itr n  = new node(1);
                    tail->next = n;
                    n->prev = tail;
                    tail = n;
                }

                registry[s] = tail;
            }
            else
            {
                itr n  = registry[s];
                n->incr();

                while(n != head)
                {
                    itr p = n -> prev;
                    if (p->rank > n->rank) break;
                    p->next = n->next;
                    n->prev = p->prev;
                    p->prev = n;
                    n->next = p;
                }

                if (head->prev != NULL)
                    head = head->prev;
            }
        }
    }

    //Print();

    return url;
}

void PrintStats(void)
{
    return;
}

*/
