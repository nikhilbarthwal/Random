#include <iostream>

#include <stdlib.h>
#include <stdio.h>

using namespace std;

struct node
{
	int data;
	int num;
	struct node* left;
	struct node* right;
};

void balance(struct node** HEAD)
{
	int x, y, z, a, b, d;
	struct node* tmp;
	struct node* head= *HEAD;
	
	if (head==NULL) return;
	
	if ((head->left)==NULL)
		x=0;
	else
	{
		balance(&(head->left));
		x=(head->left)->num;
	}
	
	if ((head->right)==NULL)
		y=0;
	else
	{
		balance(&(head->right));
		y=(head->right)->num;
	}
	
	d=abs(x-y);
	z=head->num;

	while(d>1)
	{
		cout<<"d= "<<d<<" z="<<z<<" x="<<x<<" y="<<y<<" data="<<(head->data)<<"\n";
		
		if (x>y) // Rotate Right
		{
			tmp=head;
			head=head->left;
			if ((head->right)==NULL)
				b=0;
			else
				b=(head->right)->num;
				

			tmp->left=head->right;
			head->right=tmp;
			
			(head->right)->num = 1+b+y;
			head->num = z;
		}
		else // Rotate left
		{
			tmp=head;
			head=head->right;
			if ((head->left)==NULL)
				a=0;
			else
				a=(head->left)->num;
				
			tmp->right=head->left;
			head->left=tmp;
			
			(head->left)->num = 1+a+x;
			head->num = z;
		}
		
		// Calculate new values of x, y, d
		if ((head->left)==NULL)
			x=0;
		else
			x=(head->left)->num;
		
		if ((head->right)==NULL)
			y=0;
		else
			y=(head->right)->num;

		d=abs(x-y);
	}

	*HEAD=head;
	
	if ((head->left)!=NULL)
		balance(&(head->left));

	if ((head->right)!=NULL)
		balance(&(head->right));

	return;
}

void insert2(struct node** HEAD, int k)
{
	int z;
	struct node* head= *HEAD;

	z=head->data;
	head->num=(head->num)+1;	
	
	if (k>z)
	{
		if ((head->right)==NULL)
		{
			struct node* tmp = (struct node*) malloc(sizeof(struct node));
			tmp->data = k;
			tmp->num = 1;
			tmp->left=NULL;
			tmp->right=NULL;
			head->right=tmp;
		}
		else
			insert2(&(head->right),k);
	}
	else
	{
		if ((head->left)==NULL)
		{
			struct node* tmp = (struct node*) malloc(sizeof(struct node));
			tmp->data = k;
			tmp->num = 1;
			tmp->left=NULL;
			tmp->right=NULL;
			head->left=tmp;
		}
		else
			insert2(&(head->left),k);
	}
	
	*HEAD=head;
	
	return;
}

void insert(struct node** HEAD, int k)
{
	struct node* head = *HEAD;
	int z, x, y;
	
	if (head==NULL)
	{
		struct node* tmp = (struct node*) malloc(sizeof(struct node));
		tmp->data = k;
		tmp->num = 1;
		tmp->left=NULL;
		tmp->right=NULL;
		*HEAD=tmp;
		return;
	}
	else
		insert2(HEAD,k);
	
	return;
}

void print(struct node* head, int n, int p)
{
	int k=n;
	int a,b;
	char c;
	
	if (p==0) c=' ';
	if (p==1) c='.';
	if (p==2) c='-';
	
	if (head==NULL) return;
	
	while(k>0)
	{
		k--;
		if (k==0) cout<<c; else cout<<' ';
	}
	
	a=head->data;
	b=head->num;
	
	cout<<a<<"["<<b<<"]\n";
	
	if ((head->left)!=NULL)
		print(head->left,n+1,1);

	if ((head->right)!=NULL)
		print(head->right,n+1,2);

	return;
}

int main(void)
{
	struct node* head = NULL;
	 
	int A[10] = {6,3,8,9,0,2,1,4,7,5};
	
	for(int i=0;i<10;i++)
		insert(&head,A[i]);
	
	print(head,0,0);
	balance(&head);
	
	return 0;
}

