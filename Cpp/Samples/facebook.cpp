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

struct list
{
    int val;
    struct list * next;
};


struct tree
{
    int val;
    struct tree* prev;
    struct tree* next;
    struct tree* up;
};

/*
// Assign a list
for(i=1;i<=10;i++)
{
    curr = (item *)malloc(sizeof(item));
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


*/
