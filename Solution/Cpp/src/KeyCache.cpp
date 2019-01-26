//============================================================================
// Name        : LRU Cache
// Author      : Nikhil Barthwal
//============================================================================

#include <iostream>
#include <map>
#include <assert.h>

#include "KeyCache.h"

using namespace std;

bool keyCache::Put(string keyIn, string keyOut)
{
	assert(count < size);

	if (cache.find(keyIn) == cache.end())
	{
		struct node* tmp = new struct node(keyIn);
		cache[keyIn] = tmp;

		if (head == NULL)
		{
			assert(tail == NULL);
			head = tail = tmp;
		}
		else
	    {
			assert(tail != NULL);
			tmp->next = head;
			head->prev = tmp;
			head = tmp;
		}

		count++;
		assert(count <= size);

		if (count == size)
		{
			assert(head != NULL);
			assert(tail != NULL);
			cache.erase(cache.find(tail->key));
			tmp = tail;
			tail = tail->prev;
			keyOut = tmp->key;
			delete tmp;
			count--;
			return true;
		}
	}
	else // Key already exists
	{
		struct node* tmp = cache[keyIn];

		if (tmp == head)
			return false;

		if (count == 1)
			return false;

		assert(count > 1);

		if (tmp == tail)
			tail = tail->prev;
		else
        {
			tmp->prev->next = tmp->next;
			tmp->next->prev = tmp->prev;
		}

		tmp->prev = NULL;
		tmp->next = head;
		head = tmp;
	}

	return false;
}

#ifdef DEBUG
void Print()
{
	for(auto i = cache.begin(); i!=cache.end(); i++)
	cout<<"KEY: "<<i->first<<"("<<i->second->key<<")"<<endl;
}
#endif

keyCache::keyCache(int n) : size(n), count(0), head(NULL), tail(NULL)
{
	assert(n > 0);
}

int main()
{
	keyCache C(40);
	cout<<"Hello World!"<<endl;
	return 0;

}
