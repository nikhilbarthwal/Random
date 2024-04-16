#include <iostream>

using namespace std;

int a[10]={6,3,8,9,0,2,1,4,7,5};

void swap(int& a, int& b)
{
	int t = a;
	a=b;
	b=t;
	return;
}

int partition(int p,int r)
{
	int q,t;

	int x;

	q=p;
	x=a[r];

	for(int i =p;i<r;i++)
		if (a[i]>=x)
		{
			swap(a[i],a[q]);
			q++;	
		}

	swap(a[q],a[r]);

	return q;
}

void quicksort(int p, int r)
{
	int q;

	if (p>=r) return;

	q=partition(p,r);
	quicksort(p,q-1);
	quicksort(q+1,r);
}

int main(void)
{

	cout<<"Before:  ";
	for(int i=0;i<10;i++) cout<<a[i]<<" ";
	cout<<"\n";

	quicksort(0,9);

	cout<<"After : ";
	for(int i=0;i<10;i++) cout<<a[i]<<" ";
	cout<<"\n";

	return 0;
}

