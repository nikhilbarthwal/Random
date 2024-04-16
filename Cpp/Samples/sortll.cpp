#include <iostream>

#include <stdlib.h>
#include <stdio.h>

#define DEBUG

using namespace std;

struct list
{
	int val;
	struct list* next;
};

typedef struct list item;


void reverse(item* h)
{
	item *a, *b, *curr, *H;

	if ((h==NULL)||(h->next==NULL)) return;

	H=h;
	do
	{
		a=h;
		if (h==H)
		{
			h = h->next;
			a->next = NULL;
		}
		else
		{
			h = b;
		}

		b = h->next;
		h->next = a;
	}
	while(b);

	curr = h;

	while(curr)
	{
		cout<<curr->val;
		curr = curr->next;
	}

	return;
}


int main()
{
	item * curr, * head, *h;
	int i;

	head = NULL;

	for(i=1;i<=10;i++)
	{
		curr = (item *)malloc(sizeof(item));
		curr->val = i;
		curr->next  = head;
		head = curr;
	}

	curr = head;

	reverse(head);

	return 0;
}


