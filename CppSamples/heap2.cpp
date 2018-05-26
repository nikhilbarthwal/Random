#include <stdio.h>
#include <stdlib.h>

#define N 10

int A[N]={7,3,6,9,0,1,4,2,8,5};

struct node
{
	int data;
	struct node* left;
	struct node* right;
};

struct node* head=NULL;

void insert(struct node* h,struct node* n)
{
	int d,v;

	d=h->data;
	v=n->data;

	if(d>=v)
		if ((h->left)==NULL)
			h->left=n;
		else
			insert(h->left,n);
	else
		if ((h->right)==NULL)
			h->right=n;
		else
			insert(h->right,n);

	return;
}

void print(struct node* h)
{
	int v;

	if (h==NULL) return;
	
	v=h->data;

	print(h->left);

	printf("%d ",v);
	
	print(h->right);

	return;
}

void destroy(struct node* h)
{

	if (h==NULL) return;

	destroy(h->left);
	destroy(h->right);

	free(h);

	return;
}

void main(void)
{
	int i,d,v;

	struct node* n;
	struct node* h;

	for(i=0;i<N;i++)
	{
		//Insert here
		n = (node*) malloc(sizeof(struct node));		
		n->data = A[i];
		n->left=NULL;
		n->right=NULL;
		if (head==NULL)
		{
			head=n;
		}
		else
		{
			h=head;			
			
			while(1)
			{
				d=h->data;
			
				if(d>=A[i])
					if ((h->left)==NULL)
					{
						h->left=n;
						break;
					}
					else
						h=h->left;
				else
					if ((h->right)==NULL)
					{
						h->right=n;
						break;
					}
					else
						h=h->right;
			}

		}
	}

	print(head);
	destroy(head);

	printf("\n");

	return;
}
		


