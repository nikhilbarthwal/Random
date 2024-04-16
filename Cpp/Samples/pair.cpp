#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

template<class X, class Y, class Z>

class Item
{
private:
    X first;
    Y second;
    Z third;

public:
    Item(X x, Y y, Z z): first(x), second(y), third(z) {}

    [[nodiscard]] X First() const { return first; }
    [[nodiscard]] Y Second() const { return second; }
    [[nodiscard]] Y Third() const { return third; }

    bool operator>(const Item& other) const
    {
        return first > other.First();
    }

    bool operator<(const Item& other) const
    {
        return first < other.First();
    }

    bool operator==(const Item& other) const
    {
        return first == other.First();
    }
};

typedef Item<int, int, int> item;

void Sort(vector<item>& l) { sort(l.begin(), l.end());}



int main()
{
    item p1(21,1,4);
    item p2(25,2,5);
    item p3(15,3,6);

    vector<item> v = { p1, p2, p3};
    Sort(v);
    for (auto x: v) cout<<x.First()<<" "<<x.Second()<<" "<<x.Third()<<endl;
    return 0;
}

