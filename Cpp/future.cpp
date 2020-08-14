// g++ future.cpp -pthread -o future.exe

#include <iostream>
#include <ctime>
#include <future>

using namespace std;

double Calc(int a)
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

   return sum + a;
}


int main()
{
    const int m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, m7 = 7;

future<double> t1 = async(Calc, m1);
future<double> t2 = async(Calc, m2);
future<double> t3 = async(Calc, m3);
future<double> t4 = async(Calc, m4);
future<double> t5 = async(Calc, m5);
future<double> t6 = async(Calc, m6);
future<double> t7 = async(Calc, m7);


    return 0;
}
