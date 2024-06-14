#include <iostream>
#include <vector>

#include "vector.h"

using namespace std;


int main()
{
    vector<int> v = {1, 2, 3, 5, 6, 7, 3, 5, 6, 7, 4, 5, 5, 5};
    cout<<remove<int>(v, 5)<<"____"<<endl;
    for(auto i : v)
        cout<<i<<endl;
    return 0;
}
