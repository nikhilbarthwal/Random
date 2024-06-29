#include <iostream>
#include <vector>

#include "vector.h"

using namespace std;


void test1()
{
    vector<int> v = {1, 2, 3, 5, 6, 7, 3, 5, 6, 7, 4, 5, 5, 5};
    cout<<"["<<RemoveFromVector<int>(v, 5)<<"]"<<endl;
    for(auto i : v)
        cout<<i<<endl;
}

void test2()
{
    vector<string> v = SplitString("     Nikhil   ;   Barthwal  ", " ;");
    for(auto& i : v)
        cout<<"["<<i<<"]"<<endl;
}

void test3()
{
    string v(" -- << --*Nikhil*-- >> --");
    ReplaceSubstring(v, "Nikhil", "Barthwal");
    cout<<"-["<<v<<"]-"<<endl;
}

void test4()
{
    string v(" -- << --*Nikhil*-- >> --");
    cout<<DeleteChar(v, '*')<<endl;
}

int main()
{
    test1();
    test2();
    test3();
    test4();
    return 0;
}
