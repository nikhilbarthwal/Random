#include <iostream>
#include <list>
#include <vector>

using namespace std;

class Base
{
private:

int i;

void Invalid()
{
  i = -1;
}




public:


class Nested {
  private:
    int x;
    Nested(int _x) : x(_x) { }
public:
  void Print() { cout<<"Nested: "<<x<<endl; }
  friend class Base;
};



static Base::Nested GetNested(int x) { return Base::Nested(x); }
    Base(int _i) : i(_i) {
        cout << "Base "<<i<<" Constructor Called\n";
    }

    Base(const Base& x) : i(x.i) {
      //x.Invalid();
        cout << "Base "<<i<<" Copy Constructor Called\n";
    }

    //Base(Base const &) = delete;
   void operator=(Base const &x) = delete;

    void Print()
    {
      cout << "Base "<<i<<" Value"<<endl;
    }

    virtual ~Base(){
        cout << "Base "<<i<<" Destructor called\n";
    }
};

class Derived1: public Base
{
  private: int j;
public:
    explicit Derived1(int _i, int _j) : Base(_i), j(_j) {
        cout << "Derived "<<j<<" constructor called\n";
    }
    ~Derived1(){
        cout << "Derived "<<j<<" destructor called\n";
    }
};

Base F(int x)
{
  Base b(x);
  //Base c = b;
  b.Print();
  return b;
}

bool Update(vector<int>& V)
{
	V = vector<int>(10,4);
	return true;
}

int main()
{

vector<int> z = {1,2,3};

list<int> L;
bool b = Update(z);

for(int zz:z) cout<<zz<< " "; cout<<endl;

for(int i=1; i< 1000; i++ ) L.push_back(i);

list<int>::iterator itr = L.begin();
itr++;itr++;itr++;itr++;itr++;itr++;itr++;itr++;
//cout<<*itr<<endl;

L.erase(itr);
//cout<<*itr<<endl;

Base::Nested x = Base::GetNested(11);
x.Print();


    //Base *b = new Derived1(3,4);
    //delete b;

    //Base f = F(10);
    //cout<<"END OF PROGRAM"<<endl;
    return 0;
}
