#include <iostream>

#include<stdlib.h>
#include<stdio.h>

//#define DEBUG

using namespace std;

int a[6]={1,9,4,3,2,7};
int c,n = 6;	

int b[6];

struct tree
{

   int val;
   struct tree* prev;
   struct tree* next;
   struct tree* up;
};

void place(struct tree* r, int i)
{
	int v = r->val;
	if (v>i)
	{
#ifdef  DEBUG
		cout<<"Prev to "<<v<<"\n";
#endif

		if (r->prev==NULL)
		{
			struct tree* node = (tree*) malloc(sizeof(tree));
			node->val = i;
			node->prev = NULL;
			node->next = NULL;
			node->up=r;
			r->prev = node;

#ifdef DEBUG
			cout<<"Insert "<<i<<"\n";
#endif
		}
		else
		{
			r= r->prev;
			place(r,i);
		}
	}
	else
	{
#ifdef  DEBUG
		cout<<"Next to "<<v<<"\n";
#endif

		if (r->next==NULL)
		{
			struct tree* node = (tree*) malloc(sizeof(tree));
			node->val = i;
			node->prev = NULL;
			node->next = NULL;
			node->up=r;
			r->next = node;
#ifdef DEBUG
			cout<<"Insert "<<i<<"\n";
#endif
		}
		else
		{
			r = r->next;
			place(r,i);
		}
	}

	return;
}

int read(struct tree* r,int* a,int n0)
{
	int n1=0, n2=0, n3=0;
	int v=r->val;

	if (r->prev!=NULL) n1=read(r->prev,a,n0);
	else n1=n0;

	/*cout<<v<<" -  "<<n1<<"\n";*/
	a[n1]=v;n1++;

	if (r->next==NULL) return n1;
	else n2=read(r->next,a,n1);
	
	return n2;
}

void read1(struct tree* r)
{
	int v=r->val;

	if (r->prev!=NULL) read1(r->prev);

	cout<<v<<"\n";

	if (r->next==NULL) return;
	else read1(r->next);
	
	return;
}

void sort(int* a,int n)
{
	struct tree* root = NULL;
	struct tree* r;
	if (n<2) return;

	struct tree* node = (tree*) malloc(sizeof(tree));
	node->val = a[0];
	node->prev = NULL;
	node->next = NULL;
	node->up=NULL;
	root = node;

	for(int i=1;i<n;i++) place(root,a[i]);	
	int k=read(root,a,0);

	cout<<"Finally: ";
	for(int p=0;p<n;p++) cout<<a[p]<<" ";
	cout<<"\n";
	
	return;
}

int main(void)
{
	sort(a,n);
	
	return 0;
}

