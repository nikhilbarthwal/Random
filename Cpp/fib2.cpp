#include <iostream>
#include <thread>

using namespace std;


struct arg
{
    long n;
    long r;
    int depth;
};

void f(struct arg& z)
{
    if ((z.n==0L) || (z.n==1L)) { z.r = z.n; return; }


    struct arg z1 = { z.n - 1, 0, z.depth + 1};
    struct arg z2 = { z.n - 2, 0, z.depth + 1};

    f(z1);
    f(z2);

    z.r = z1.r + z2.r;
}

void f_thread(struct arg& z)
{
    if ((z.n==0L) || (z.n==1L)) { z.r = z.n; return; }


    struct arg z1 = { z.n - 1, 0, z.depth + 1};
    struct arg z2 = { z.n - 2, 0, z.depth + 1};

#ifdef MULTICORE
    thread t1(f, ref(z1));
    thread t2(f, ref(z2));

    t1.join();
    t2.join();
#else

    f(z1);
    f(z2);

#endif


    z.r = z1.r + z2.r;
}


int main()
{
    const long N = 50L;

    struct arg Z = { N, 0, 0};
    f(Z);
    cout << Z.r <<endl;

    //cout<<ff(N)<<endl;
    return 0;
}
