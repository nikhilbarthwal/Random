#include <iostream>
#include <stdlib.h>

using namespace std;

struct node
{
	int data;
	struct node* next;
};

typedef struct node* nodeptr;

void insert(nodeptr& h, int d, int p)
{
	nodeptr n;
	nodeptr curr=h;

	if (p==0)
	{
		n = (nodeptr) new nodeptr;
		n->data=d;
		n->next=h;
		h = n;
		return;
	}

	while(p!=0)
	{
		curr=curr->next;
		p--;
	}

	n = (nodeptr) new nodeptr;
	n->data=d;
	n->next=curr->next;
	curr->next=n;

	return;
}

int main(void)
{
	int N=10;

	struct node* head;
	struct node* current=NULL;

	for(int i=0;i<N;i++)
	{
		head = (nodeptr) new nodeptr;
		head->data=i;
		head->next=current;
		current=head;
	}
	
	insert(head,-1,5);

	current=head;	
	while(current!=NULL)
	{
		cout<<(current->data)<<"\n";
		current=current->next;
	}

	return 0;
}


