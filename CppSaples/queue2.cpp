#include <iostream>
#include <stdlib.h>

#define N 20

int a[N]={12,1,4,17,9,10,8,14,16,5,11,2,18,7,19,3,15,6,20,13};

using namespace std;

class queue
{
	private: struct node
	{
		int data;
		struct node* next;
	} *a, *b;

	public:
	queue()
	{
		a=NULL;
		b=NULL;	
	}

	void put(int k)
	{
		struct node* n;		
		
		n= (struct node*) malloc(sizeof(struct node));
		n->data=k;
		n->next=NULL;

		if (a==NULL)
		{
			a=n;
			b=n;			
		}		
		else
		{
			b->next=n;
			b=n;
		}

		return;
	}

	int get(void)
	{
		int k;
		struct node* t;
		
		if (a==NULL)
		{
			cout<<"Error: Empty stack!\n";
			return -1;
		}


		k=a->data;
		t=a;		
		a=a->next;
		free(t);

		if (a==NULL) b=NULL;

		return k;
	}


};


int main(void)
{
	queue q = queue();
	int k;

	for(k=0;k<N;k++)
		q.put(a[k]);

	for(k=0;k<=N;k++)
	{
		int p;
		p=q.get();
		cout<<p<<"\n";
	}


	return 0;
}
