#include <iostream>

using namespace std;

/*
 * Program to find all the primes between 2 to 300
 */


int main(void)
{
    int p[147];                        // Array to store all the primes
     int n;                            // Will store the length of p at any given point of time
    int q;                            // Store the answer

    p[0]=3;                            // Initializing the content of p
     n=1;                            // Initializing the index of p

    cout<<"2\n3\n";                        // Print the first two prime numbers

    for(int j=5;j<30000;j+=2)                    // Run a loop for all remaining odd no.
    {
        q=0;
        for(int k=0;k<n;k++)                // Look up all previous prime no. (Except 2)
            if (j%p[k]==0) { q=1;k=n; }         // Check if j is prime

        if (q==0)                    // If the number is indentified as prime no. then ...
        {
            cout<<j<<"\n";                // Print print number
            p[n++]=j;                // Store the prime no.
        }                         // End of If
    }                            // End of For loop

    return 0;
}
