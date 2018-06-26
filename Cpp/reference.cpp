#include <iostream>
#include <list>

using namespace std;

class base
{
    public: virtual void update(int) = 0; void print();
};

class derived1 : public base
{
    private:
        int i, j;
    
    public:
        derived1(int _i, int _j) : i(_i), j(_j) { }

        void update(int x) { i=x; }

        void print()
        {
            cout<<"Derived1 : i = "<<i<<" / j = "<<j<<endl;
        }
};

class derived2 : public base
{
    private:
        int i;
    
    public:
        //derived2(const derived2&) = delete;

        derived2(int _i) : i(_i) { }

        void update(int x) { i=x; }

        void print()
        {
            cout<<"Derived2 : i = "<<i<<endl;
        }
};

void update(base& b )
{
    b.update(45);
}
int main(void)
{
    list<derived2> L;
    L.push_back(derived2(1));
    L.push_back(derived2(2));
    L.push_back(derived2(3));
    L.push_back(derived2(4));
    L.push_back(derived2(5));

    auto itr = L.begin();
    itr++; itr++;
    update(*itr);

    for(auto& x: L) x.print();   
        

}


