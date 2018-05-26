#include <iostream>
#include <cstdlib>


struct node
{
	int data;
	struct node* left;
	struct node* right;
};

int check(struct node* n, int* min, int* max)
{

	int i,j,k;
	if ((n->left)!=NULL)
	{
		if ((n->data)<((n->left)->data))
			return 1;

		k=check(n->left,&i,&j);
		if (k==1) return 1;
		*min=i;
	}
	else
		*min=n->data;

	if ((n->right)!=NULL)
	{
		if ((n->data)>((n->right)->data))
			return 1;

		k=check(n->right,&i,&j);
		if (k==1) return 1;
		*max=j;
	}
	else
		*max=n->data;

	return 0;
}

// Return 0 for Yes and 1 for No
int bcheck(struct node* n)
{
	int x, y, k;

	if (n==NULL) return 0;

	k=check(n,&x,&y);

	return k;
}

int main(void)
{
	return 0;
}
