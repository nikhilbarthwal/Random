#include <iostream>
#define N 6

using namespace std;

int a[N] = {2,  -8, 3, -2, 4, -10};

int main(void)
{
	int min=0, sum=0, maxsum=0;

	for(int k=0;k<N;k++)
	{

		sum+=a[k];
		if (min>sum) min=sum;

		if ((sum-min)>maxsum) maxsum=sum-min;
	}

	cout<<maxsum<<"\n";

	return 0;
}
