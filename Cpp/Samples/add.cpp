#include <iostream>

struct node
{
	char data;
	struct node* next;
};

struct node* add(struct node* n1, struct node* n2)
{
	struct node *head = NULL, *current, *tmp;
	char d, dx=0, d1, d2;
	
	while ((n1!=NULL)||(n2!=NULL)
	{
		if (n1==NULL)
			d1=0;
		else
		{
			d1=n1->data;
			n1=n1->next;
		}

		if (n1==NULL)
			d1=0;
		else
		{
			d1=n1->data;
			n1=n1->next;
		}
	
		d=(d1+d2+dx);
		if (d>=10) { dx=(int)(d/10); d=d-dx*10; }
		
		tmp = (struct node*) malloc(sizeof(struct node));
		tmp->data=d;
		tmp->next=NULL;
		
		if (head==NULL)
		{
			head=tmp;
			current=tmp;
		}
		else
		{
			curent->next=tmp;
			current=tmp;
		}
	}
	
	return head;
}
