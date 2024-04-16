#include <stdio.h>
#include <stdlib.h>

#define N 10

int A[N]={7,3,6,9,0,1,4,2,8,5};

struct node
{
    int data;
    struct node* next;
};

struct node* head;

int main(void)
{
    int i;
    struct node* n;
    struct node* h;

    h=head;
    for(i=0;i<N;i++)
    {
        n = (struct node*) malloc(sizeof(struct node));
        n->data=A[i];
        n->next=NULL;

        if (h==NULL)
        {
            head=n;
            h=head;
        }
        else
        {
            h->next=n;
            h=n;
        }
    }

    h=head;
    while(h!=NULL)
    {
        printf("%d ",h->data);
        h=h->next;
    }
    printf("\n");


    if ((head!=NULL)&&(head->next!=NULL))
    {
        h=head;
        n=head->next;
        head->next=NULL;

        while(n!=NULL)
        {
            h=head;
            head=n;
            n=n->next;
            head->next=h;
        }
    }

    h=head;
    while(h!=NULL)
    {
        printf("%d ",h->data);
        h=h->next;
    }
    printf("\n");

    return 0;
}
