#include <iostream>
#include <vector>
#include <cassert>

using namespace std;

class number
{
private:
    int n;
public:
    explicit number(int i) { n = i; }
    explicit number() { n = 0; }
    int val() { return n; }
};

typedef number T;

class heap
{
private:

    vector<T> v;
    int sz = 0;

    void swap(int a, int b)
    {
        assert ((a <= sz) && (b <= sz));
        T t = v[sz - a];
        v[sz - a] = v[sz - b];
        v[sz - b] = t;
    }

    T pos(int i) { assert (i <= sz); return v[sz - i]; }

    bool compare(int p1, int p2)
    {
        // For Mqx heap: >
        // For Min heap: <

        T e1 = pos(p1);
        T e2 = pos(p2);

        return e1.val() > e2.val();
        // return e1.val() < e2.val();
    }

    void heapify(int i)
    {
        if (2*i > sz) return; // Left & Right does not exist

        if (2*i+1 > sz) // if Right does not exist
        {
            if (compare(2*i-1, i-1))
            {
                // Swap left & root and max i heapify
                swap(i,2*i);
                heapify(2*i);
            }
            return;
        }

        if ((compare(2*i, i)) || (compare(2*i+1, i)))
        {
                int j = (compare(2*i, 2*i+1)) ? 2 * i : 2 * i + 1;
                swap(i,j);
                heapify(j);
        }
    }

    bool check_pos(int k)
    {
        bool b1 = true;
        if (2*k <= sz)
        {
            if (compare(2*k, k)) b1 = false;
            b1 = check_pos(2*k);
        }

        bool b2 = true;
        if (2*k + 1 <= sz)
        {
            if (compare(2*k+1, k)) b2 = false;
            b2 = check_pos(2*k + 1);
        }
        return (b1 && b2);
    }

    bool check() { return (sz > 0)? check_pos(1) : true; }

public:

    explicit heap(int n): v(n), sz(0) { }

    void insert(T x)
    {
        assert(sz < v.size());
        v[sz++] = x;
        heapify(1);
        assert (check());
    }

    T top() { return pos(1); }

    T pop()
    {
        T z = pos(1);
        sz--;
        heapify(1);
        assert (check());
        return z;
    }

    inline int size() { return sz; }
};

int main()
{
    vector<int> A = {6, 3, 8, 9, 1, 2, 0, 4, 7, 5};
    int size = (int) A.size();
    heap h(size);

    for(int i : A) h.insert(number(i));
    while(h.size() > 0) cout<<h.pop().val()<<endl;

    return 0;
}

