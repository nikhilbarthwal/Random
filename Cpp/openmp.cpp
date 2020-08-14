// g++ openmp.cpp -fopenmp -o openmp.exe

#include <iostream>
#include <ctime>

#ifdef _OPENMP
#include "omp.h"
#endif

using namespace std;

// double Calc()
// void Calc(int a, int& b)
int Calc(int a)
{
   const int N = 1000000000;
   static double A[N];
   for ( int i = 0, sign = 1; i < N; i++, sign = -sign )
   {
      A[i] = sign * 4.0 / ( 1.0 + 2.0 * i );
   }

   double sum = 0.0;
   for (int i=0; i<N;i++)
       sum += A[i];

   //return sum;
   // b=a;
   return a;
}


int main()
{
    const int m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, m7 = 7, m8 = 8;
    int n1, n2, n3, n4 , n5, n6, n7, n8;

#ifdef _OPENMP
    omp_set_num_threads(8);
#endif


    #pragma omp parallel sections
    {
        #pragma omp section
        n1 = Calc(m1);

        #pragma omp section
        n2 = Calc(m2);

        /* #pragma omp section
        n3 = Calc(m3);

        #pragma omp section
        n4 = Calc(m4);

        #pragma omp section
        n5 = Calc(m5);

        #pragma omp section
        n6 = Calc(m6);

        #pragma omp section
        n7 = Calc(m7); */
    }

    cout<<n1<<n2<<n3<<n4<<n5<<n6<<n7<<endl;
    return 0;
}
