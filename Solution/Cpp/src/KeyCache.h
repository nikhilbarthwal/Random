//============================================================================
// Name        : LRU Cache
// Author      : Nikhil Barthwal
//============================================================================

#ifndef KEYCACHE_H
#define KEYCACHE_H

#include <iostream>
#include <map>
#include <assert.h>

using namespace std;

class keyCache
{
private:
	int size;
	int count;

	struct node
    {
		string key;
		struct node* next;
		struct node* prev;

		node(string k) : key(k), next(NULL), prev(NULL) { }
	};

	// Double Link list where all the keys are kept
	map<string, struct node*> cache;

	struct node* head;
	struct node* tail;

public:
	keyCache(int);

#ifdef DEBUG
	void Print();
#endif

	bool Put(string, string);
};

#endif //KEYCACHE_H