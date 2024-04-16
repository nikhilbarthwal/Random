#include <iostream>
#include <stdlib.h>

#define N 20

int a[N]={12,1,4,17,9,10,8,14,16,5,11,2,18,7,19,3,15,6,20,13};

using namespace std;

void mergesort(int* a, int n)
{
	int i, j, k, s1, s2, t;
	int *b, *c;	
	
	if (n<2) return;

	if (n==2)
	{
		if (a[0]>a[1])
		{
			t=a[0];
			a[0]=a[1];
			a[1]=t;
		}
		return;
	}	

	s1= (int) (n/2);
	s2= n-s1;

	b = (int*) malloc(s1*sizeof(int));
	c = (int*) malloc(s2*sizeof(int));

	
	for(t=0;t<s1;t++)
		b[t]=a[t];
	
	for(t=0;t<s2;t++)
		c[t]=a[s1+t];

	mergesort(b,s1);
	mergesort(c,s2);

	i=0;
	j=0;
	k=0;

	while ((i<s1)&&(j<s2))
		if (b[i]>c[j])
			a[k++]=c[j++];
		else
			a[k++]=b[i++];

	for(t=i;t<s1;t++)
		a[k++]=b[t];

	for(t=j;t<s2;t++)
		a[k++]=c[t];

	free(b);
	free(c);

	return;
}

int main(void)
{
	cout<<"Before:  ";
	for(int i=0;i<N;i++) cout<<a[i]<<" ";
	cout<<"\n";


	mergesort(a,N);

	cout<<"After: ";
	for(int i=0;i<N;i++) cout<<a[i]<<" ";
	cout<<"\n";

	return 0;
}



