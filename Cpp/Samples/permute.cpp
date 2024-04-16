#include <iostream>
#include <vector>
#include <string>

#define N 3

using namespace std;

vector<char> v;

void permute(vector<char> &t, int k)
{
	if (k==N)
	{
		for(int i=0; i<N; i++) cout<<t[i];
		cout<<endl;
		return;
	}

	for(int i=0; i<v.size(); i++)
	{
		t[k]=v[i];
		permute(t,k+1);
	}
}

int main(void)
{

	v.push_back('a');v.push_back('b');v.push_back('c');v.push_back('d');

	vector<char> t(N,' ');

	permute(t,0);

	string s;
	cin>>s;

	return 0;
}
