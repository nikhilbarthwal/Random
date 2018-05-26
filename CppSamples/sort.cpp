#include <iostream>
#include <stdlib.h>
//#define DEBUG

using namespace std;

void sort(int* a,int n)
{
	// Run a loop form I = 1 to N
	for(int i=0;i<n;i++)
	{

#ifdef DEBUG
		for(int p=0;p<n;p++) cout<<a[p]<<" "; cout<<"\n";
#endif

		// Find the  Min in I to N
		int m=a[i];;
		int k=i;
		for(int j=i;j<n;j++)
			if (m>a[j])
			{
				m=a[j];
				k=j;
			}

#ifdef DEBUG
		cout<<"Min from "<<i<<" to N"<<" is "<<m<<" at "<<k<<"\n";
#endif

		// Put Min at I				
		a[k]=a[i];
		a[i]=m;

	}
}



void sort(int* a,int n)
{
	// Run a loop form I = 1 to N
	for(int i=0;i<n;i++)
	{

#ifdef DEBUG
		for(int p=0;p<n;p++) cout<<a[p]<<" "; cout<<"\n";
#endif

		// Find the  Min in I to N
		int m=a[i];;
		int k=i;
		for(int j=i;j<n;j++)
			if (m>a[j])
			{
				m=a[j];
				k=j;
			}

#ifdef DEBUG
		cout<<"Min from "<<i<<" to N"<<" is "<<m<<" at "<<k<<"\n";
#endif

		// Put Min at I				
		a[k]=a[i];
		a[i]=m;

	}
}

int main(void)
{
	int a[6]={1,6,4,3,2,7};
	sort(a,6);
	int n = 6;
	cout<<"Finally ";
		for(int p=0;p<n;p++) cout<<a[p]<<" "; cout<<"\n";

	return 0;
}

