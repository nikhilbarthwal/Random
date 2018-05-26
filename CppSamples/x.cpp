#define DEBUG

#include <iostream>
#include <stdlib.h>

using namespace std;

#ifdef DEBUG
// Do something
#endif

int main(void)
{
	return 0;
}

typedef struct node* nodeptr;

struct list
{
	int val;
	struct list * next;
};

struct tree
{
	int val;
	struct tree* left;
	struct tree* right;
	struct tree* up;
};

// Assign a list
for(i=1;i<=10;i++)
{
	curr = (list *)malloc(sizeof(list));
	curr->val = i;
	curr->next  = head;
	head = curr;
}

// Print the list
while(curr)
{
	printf("%d\n", curr->val);
	curr = curr->next ;
}

//Reverse a link list
void reverse(void)
{
	struct list *a, *b;

	if ((head==NULL)||((head->next)==NULL)) return;

	a=head;
	b=head->next;
	a->next=NULL;

	while(b!=NULL)
	{
		head=b;
		b=head->next;
		head->next=a;
		a=head;
	}

	return;
}

void add_in_order(struct tree* h, struct tree* hh, int d)
{
	h->data=c++;
	if (d==0) h->up=NULL; else h->up=hh;

	if (d==5)
	{
		h->left=NULL;
		h->right=NULL;
	}
	else
	{
		d++;

		h->left=(struct tree*) malloc(sizeof(tree));
		h->right=(struct tree*) malloc(sizeof(tree));

		add_in_order(h->left,h,d);
		add_in_order(h->right,h,d);
	}

	return;
}

void pre_order_print(struct tree* h)
{
	if ((h->left)!=NULL) pre_order_print(h->left);
	cout<<(h->data)<<" ";
	if ((h->right)!=NULL) pre_order_print(h->right);
	return;
}

void post_order_print(struct tree* h)
{
	if ((h->right)!=NULL) post_order_print(h->right);
	cout<<(h->data)<<" ";
	if ((h->left)!=NULL) post_order_print(h->left);
	return;
}

struct stack
{
	int data;
	struct stack *next;
};


struct stack *top = NULL;

void push(int i)
{
	struct stack *p = (struct stack*) new stack;
	p->data = i;
	p->next = top;
	top=p;
}

int pop(void)
{
	struct stack *p = top;
	int i;
	if (top==NULL) return 0;
	i=p->data;
	top=p->next;
	delete(p);
	return i;
}
