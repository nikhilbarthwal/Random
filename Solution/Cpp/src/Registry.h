//============================================================================
// Name        : LRU Cache
// Author      : Nikhil Barthwal
//============================================================================

#ifndef REGISTRY_H
#define REGISTRY_H

#include <iostream>
#include <map>
#include <assert.h>
#include <cmath>
#include <climits>

using namespace std;

const int Decrease = 5; // Amount to be decreased during each normalization

class registry
{
private:

	int total;
	int max;

	struct node
    {
		string section;
		int count;
		struct node* next;
		struct node* prev;

		node(string k, int n) : section(k), count(n), next(NULL), prev(NULL) { }

		int reduce()
		{
			count =- Decrease; // Decrease the keys by some small amount
			return count;
		}
	};

	// Double Link list where all the keys are kept
	map<string, struct node*> cache;

	struct node* head;
	struct node* tail;

public:
	registry(int);

	void Remove(string);
	void Increment(string);

#ifdef DEBUG
	void Print();
#endif

	bool Put(string section);
};

#endif //REGISTRY_H