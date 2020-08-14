// g++ threads.cpp -thread -o thread.exe

#include <iostream>
#include <ctime>
#include <thread>

using namespace std;

void Calc(int a, int& b)
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

   // return sum;
   b=a;
   // return a;
}


int main()
{
    const int m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, m7 = 7;
    int n1, n2, n3, n4 , n5, n6, n7;

    thread t1(Calc, m1, ref(n1));
    thread t2(Calc, m2, ref(n2));
    thread t3(Calc, m3, ref(n3));
    thread t4(Calc, m4, ref(n4));
    thread t5(Calc, m5, ref(n5));
    thread t6(Calc, m6, ref(n6));
    thread t7(Calc, m7, ref(n7));

    t1.join();
    t2.join();
    t3.join();
    t4.join();
    t5.join();
    t6.join();
    t7.join();

    cout<<n1<<n2<<n3<<n4<<n5<<n6<<n7<<endl;
    return 0;
}
