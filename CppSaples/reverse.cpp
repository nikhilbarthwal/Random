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
typedef struct list* itemptr;

void print(itemptr head)
{
	item* curr = head;
	while(curr)
	{
		cout<< (curr->val) << " ";
		curr = curr->next;
	}
	cout<<endl;
	return;
}

itemptr reverse(itemptr  h)
{
	item *a, *b, *curr, *H;

	if ((h==NULL)||(h->next==NULL)) return h;

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
	return h;
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

	print(head);
	head = reverse(head);
	print(head);

	return 0;
}
