//============================================================================
// Name        : LRU Cache
// Author      : Nikhil Barthwal
//============================================================================

#include <iostream>
#include <map>
#include <assert.h>
#include <cmath>
#include <climits>

#include "Registry.h"

using namespace std;

void registry::Increment(string section)
{
	if (cache.find(section) == cache.end())
	{
		struct node* tmp = new struct node(section, 1);
		cache[section] = tmp;
		total++;

		if (head == NULL)
		{
			assert(tail == NULL);
			head = tail = tmp;
		}
		else
	    {
			assert(tail != NULL);
			tmp->prev = tail;
			tail->next = tmp;
			tail = tmp;
		}
	}
	else // Key already exists
	{
		struct node* tmp = cache[section];

		if ((tmp->count) >= max) // If the key exceeds Max, then Normalize
		{
			total = 0;
			for(struct node* p=head; p != tail; p = p->next)
				total += p->reduce();
		}

		tmp->count = tmp->count + 1;
	}

	return ;
}

#ifdef DEBUG
void registry::Print()
{
	for(auto i = cache.begin(); i!=cache.end(); i++)
	cout<<"KEY: "<<i->first<<"("<<i->second->key<<")"<<endl;
}
#endif

void registry::Remove(string section)
{
	assert(cache.find(section) != cache.end());

	struct node* tmp = cache[section];
	total -= tmp->count;

	if (head==tmp)
	{
		head=tmp->next;
		head->prev = NULL;
	}
	else if (head==tail)
	{
		tail = tail->prev;
		tail->next = NULL;
	}
	else
	{
		tmp->prev->next = tmp->next;
		tmp->next->prev = tmp->prev;
	}

	delete tmp;
	return ;
}

registry::registry(int n) : head(NULL), tail(NULL), total(0)
{
	assert(n > 0);
	max = floor(INT_MAX/n+1); // TODO: Replace n with config value
}
