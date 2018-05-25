#include <vector>
#include <iostream>

using namespace std;

template <class T>
class array2D
{
private:
	int m, n;
	vector<T> v;

public:
	array2D(int i, int j)
	{
		m = i; n = j;
		v = vector<T>(i*j);
	}

	array2D(int i, int j, T e)
	{
		m = i; n = j;
		v = vector<T>(i*j, e);
	}

	int getX()
	{
		return m;
	}

	int getY()
	{
		return n;
	}

	T get(int i, int j)
	{
		if (i >= m) throw "Index out of bound";
		if (j >= n) throw "Index out of bound";

		T t = v[m*i + j];

		return t;
	}

	void set(int i, int j, T d)
	{
		if (i >= m) throw "Index out of bound";
		if (j >= n) throw "Index out of bound";

		v[m*i + j] = d;

		return;
	}

	void print()
	{
		for (int i = 0; i<m; i++)
		{
			for (int j = 0; j<n; j++) cout << this->get(i, j) << " ";
			cout << endl;
		}
	}
};

int main(void)
{
	array2D<int> a(4,4);
	vector<vector<string> > b(6,vector<string>(6,"d"));
	vector<vector<int> > v(10,vector<int>(10,2));

	try
	{
		int i,j;
		a.set(4,2,1);
		i=a.get(2,1);
		string s=b[3][4];
		s[0]='m';
		cout<<i<<endl;
		j=a.get(1,1);
		cout<<j<<endl;
		cout<<b[3][4]<<endl;
	}
	catch(const char* s)
	{
		cout<<"ERROR: "<<s<<endl;
	}

	return 0;
}
