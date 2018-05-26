#include <iostream>
#define N 10

using namespace std;

int A[N] = {6,3,8,9,1,2,0,4,7,5};

void swap(int a, int b)
{
	int t = A[a-1];
	A[a-1] = A[b-1];
	A[b-1] = t;

	return;
}

void heapify(int i, int n)
{

	if (2*i>n) return; // Left & Right does not exists

	if (2*i+1>n) // if Right does not exists
	{
		if (A[2*i-1]>A[i-1])
		{
			// Swap left & root and maxi heapify
			swap(i,2*i);
			heapify(2*i,n);
		}
	}
	else // Right & Left exists
	{
		if ( (A[i-1]<A[2*i-1]) || (A[i-1]<A[2*i]) )
		{
			if (A[2*i-1]>A[2*i]) // Left si greater than right
			{
				swap(i,2*i);
				heapify(2*i,n);
			}
			else
			{
				swap(i,2*i+1);
				heapify(2*i+1,n);
			}
		}
	}

	return;
}

void build(void)
{
	int k = (int) (N/2);

	for(int i=k;i>0;i--)
		heapify(i,N);

	return;
}


void sort(void)
{
	int i;
	build(); // Build the Max heap

	int n=N;
	for(i=N;i>1;i--)
	{
		swap(1,i);
		heapify(1,i-1);
	}
}

int main(void)
{
	cout<<"Before:  ";
	for(int i=0;i<10;i++) cout<<A[i]<<" ";
	cout<<"\n";

	sort();

	cout<<"After: ";
	for(int i=0;i<10;i++) cout<<A[i]<<" ";
	cout<<"\n";

	return 0;
}
