#include "Common.h"
#include <iostream>
#include <string>
#include <map>
#include <list>

using namespace std;

struct node
{
    timestamp data;
    struct node* next;
    struct node* prev;

    node(timestamp t)
    {
        data = t;
        next = NULL;
        prev = NULL;
    }
};

bool timestamp::compare(timestamp t)
{
    return true;
}

typedef struct node* itr;
static itr head = NULL, tail = NULL;

static int timestamps = 0;
static bool status = false; // TODO: Record Status

optional<timestamp> GetTimeStamp(string s, int p1, int& p2)
{
    optional<string> identifier = GetIdentifier(s, ']', p1, p2);

    if (identifier.GetStatus())
    {
        string ss = identifier.GetData();
        int j1 = Find(ss, ':', 0);
        int j2 = Find(ss, ':', j1+1);
        int j3 = Find(ss, ':', j2+1);
        int j4 = Find(ss, ' ', j3+1);

        timestamp t;
        //TODO: Verify the time within limits
        t.date = ss.substr(0,j1);
        t.hr = Convert2Int(ss.substr(j1+1, j2-j1-1));
        t.min = Convert2Int(ss.substr(j2+1, j3-j2-1));
        t.sec = Convert2Int(ss.substr(j3+1, j4-j3-1));

        // Add a new timestamp to head
        itr n = new node(t);
        n->next=head;
        head=n;
        timestamps++;
        if (tail == NULL) tail = n;

        // Trim old timestamps
        // TODO: Complete the trimming part
        /* while (tail != NULL)
        {
            if (tail->compare(t))
            {
                itr n=tail;
                tail=tail->prev;
                tail->next=NULL;
                delete n;
            }
            else
                break;
        }*/

        // TODO: Print warning if it exceeds/reduces

        return optional<timestamp>(t);
    }
    return optional<timestamp>();
}

bool EdgeTimestamp(optional<timestamp> t)
{
    if (t.GetStatus())
        if ((t.GetData().sec % 10) == 0)
            return true;

    return false;
}
