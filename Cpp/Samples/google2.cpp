#include <iostream>

using namespace std;

/*
 * Program to find all the primes between 2 to 300
 */


int main(void)
{
    int p[299]={2};        // Array to store all the primes
     int n=1;        // Will store the length of p at any given point of time

    int q;            // Used as a key

    for(int j=3;j<300;j++)
    {
        q=0;
        for(int k=0;k<n;k++) if (j%p[k]==0) { q=1;k=n; }
        if (q==0) p[n++]=j;
    }

    for(int j=0;j<n;j++) cout<<p[j]<<"\n";

    return 0;
}
