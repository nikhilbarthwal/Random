#include <iostream>
#include <stdlib.h>

#define N 20

int a[N]={12,1,4,17,9,10,8,14,16,5,11,2,18,7,19,3,15,6,20,13};

using namespace std;

struct node
{
	int data;
	struct node* next;
};


void push(int k, struct node** s)
{
	struct node* n;

	n= (struct node*) malloc(sizeof(struct node));
	n->data=k;
	n->next=*s;
	*s=n;

	return;
}

int pop(struct node** s, int* k)
{
	struct node* head= *s;

	if (head==NULL)
	{
		cout<<"Error: Empty stack!\n";
		return 1;
	}

	*k=head->data;
	*s=head->next;

	return 0;
}

int top(struct node* s, int* k)
{
	if (s==NULL)
	{
		cout<<"Error: Empty stack!\n";
		return 1;
	}

	*k=s->data;

	return 0;
}

int main(void)
{
	struct node* s = NULL;
	int k;

	for(k=0;k<N;k++)
		push(a[k],&s);

	for(k=0;k<6;k++)
	{
		int p;
		top(s,&p);
		cout<<p<<"\n";
	}

	cout<<"--------\n";

	for(k=0;k<=N;k++)
	{
		int p;
		pop(&s,&p);
		cout<<p<<"\n";
	}


	return 0;
}
