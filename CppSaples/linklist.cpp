#include <iostream>

#define N 10
#define D 30
#define R

using namespace std;

int A[10] = {6,3,8,9,0,2,1,4,7,5};

struct node
{
	int data;
	struct node* next;
};

typedef struct node* nodeptr;

nodeptr remove(int n, nodeptr h)
{
	struct node *head, *a, *b;

	if (h==NULL) return NULL;

	if ((h->data)==n)
	{
		head=h->next;
		return head;
	}

	a=h;
	b=a->next;
	while (b!=NULL)
		if ((b->data)==n)
		{
			a->next=b->next;
			delete b;
			return h;
		}
		else
		{
			a=b;
			b=b->next;
		}

	return h;
}

nodeptr add(int n, nodeptr h)
{
	nodeptr head, a, b;

	if (((h->data)>n)||(h==NULL))
	{
		head = (nodeptr) new nodeptr;
		head->data=n;
		head->next=h;
		return head;
	}

	a=h;
	while(a!=NULL)
		if ((a->data)>=n)
		{
			if ((a->next)==NULL)
			{
				b = (nodeptr) new nodeptr;
				b->data=n;
				b->next=NULL;
				a->next=b;
				return h;
			}
			else
				a=a->next;
		}
		else
		{
			b = (nodeptr) new nodeptr;
			b->next=a->next;
			a->next=b;
			return h;
		}

	return h;
}

nodeptr reverse(nodeptr h)
{
	struct node* a, *b;

	if (h==NULL) return h;

	a=h;
	b=a->next;
	a->next=NULL;
	while(b!=NULL)
	{
		h=b;
		b=b->next;
		h->next=a;
		a=h;
	}

	return h;
}


int main(void)
{
	nodeptr head;
	nodeptr current;

	int i;

	head=NULL;
	for(i=N;i>0;i--)
	{
		current = (nodeptr) new nodeptr;
		current->data=i*10;
		current->next=head;
		head=current;
	}

	cout<<"Normal form: \n";
	current=head;
	while(current!=NULL)
	{
		int p;
		p=current->data;
		cout<<p<<"\n";

		current=current->next;
	}

#ifdef D
	current=remove(D,head);
	cout<<"Deleted form: \n";
	while(current!=NULL)
	{
		int p;
		p=current->data;
		cout<<p<<"\n";
		current=current->next;
	}

#endif

#ifdef R
	current=reverse(head);
	cout<<"Reverse form: \n";
	while(current!=NULL)
	{
		int p;
		p=current->data;
		cout<<p<<"\n";
		current=current->next;
	}
#endif

	return 0;
}
