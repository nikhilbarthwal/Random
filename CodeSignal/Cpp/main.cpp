#include <iostream>
#include <vector>
#include <string>
#include <cassert>
#include "solution.h"

using namespace std;


extern vector<vector<string>> queries;
extern vector<string> result;

int main() {
    auto r = solution(queries);
    const int l = r.size();
    // assert(r.size() == result.size());
    for(int i=0; i<l; ++i) if (r[i] != result[i]) cout<<i<<" -> "<<r[i]<<" [ "<<result[i]<<" ]"<<endl;
    return 0;
}
