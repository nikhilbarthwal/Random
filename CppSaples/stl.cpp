#include <string.h>  
#include <iostream>  
#include <vector>  
#include <map>
#include <stack>
#include <queue>
#include <set>
#include <list>

using namespace std;  

void fmap(void)  
{  
	map<int, string> m;  

	m[1000] = "Hello!";  
	m[1500] = "Test!";
	m[2000] = "Mike C.";  
	m[3000] = "Charlie M.";  
	m[4000] = "Nikhil B.";  

	cout << "m[2000]=" << m[2000] << endl;  
	cout << "Map size: " << m.size() << endl;  

	int c=4000;
	cout<<"Count of "<<c<<" is "<<m.count(c)<<endl;

	c=5000;
	cout<<"Count of "<<c<<" is "<<m.count(c)<<endl;

	map<int,string>::iterator j = m.find(2000);
	cout<<"Deleting "<<(j->first)<<":"<<(j->second)<<endl;
	m.erase (j);
	
	map<int,string>::iterator k=m.begin();
	k++;k++;
	m.erase(k);

	cout<<"List of all:"<<endl;
	for( map<int,string>::iterator i=m.begin(); i!=m.end(); i++)  
	{  
		cout << i->first << ": " << i->second << endl;
	}  
} 

void fvector(void)
{  
	vector<string> v1;
	v1.push_back("The number is 10");
	v1.push_back("The number is 20");
	v1.push_back("The number is 30");
	
	v1.erase(v1.begin()+1);
	
	cout << "Loop by index:" << endl;
	
	for(int i=0;i<v1.size();i++)  
		cout<<v1[i] << endl;  
	
	cout <<endl<<"Iterator:"<<endl;  

	vector<string>::iterator it;
	for (it=v1.begin() ; it<v1.end(); it++ )
		cout<<(*it)<<endl;
	
	int I=6, J=5;

	vector< vector<int> > v2(I, vector<int>(J,0));      
	
	cout << "Loop by index:" << endl;  
	for(int i=0; i<I; i++)
	for(int j=0; j<J; j++)
		v2[i][j]=(i+1)*(j+1);

	for(int i=0; i<I; i++)
	for(int j=0; j<J; j++)
		cout<<"["<<i<<","<<j<<"] is "<<v2[i][j]<<endl;
	
	return;
}

int fstack(void)
{
	stack<int> s;
	
	int sum=0;

	for (int i=1;i<=10;i++)
		s.push(i);

	while (!s.empty())
	{
		sum += s.top();
		s.pop();
	}

	return sum;
}

void fqueue(void)
{
	queue<int> q;

	for(int i=0;i<10;i++)
	{
		q.push(i+1);
		cout<<q.front()<<" : "<<q.back()<<endl;
	}

	cout<<endl<<"Pop:"<<endl;
	while (!q.empty())
	{
		cout<<q.front()<<endl;
		q.pop();
	}
	
	return;
}

void fset(int j, int k)
{
	set<int> s;
	set<int>::iterator it;

	for(int i=0;i<10;i++)
		s.insert(i+1);

	it=s.find(j);

	if (it==s.end())
		cout<<"Element "<<j<<" not found!"<<endl;
	else
	{
		s.erase(it);
		cout<<"Element "<<j<<" found and removed!"<<endl;
	}

	if (s.count(k)==0)
		cout<<"Element "<<k<<" not found!"<<endl;
	else
		cout<<"Element "<<k<<" found!"<<endl;

	cout<<"Set of size "<<s.size()<<" elements: ";
	for (it=s.begin(); it!=s.end(); it++)
		cout<<" "<<(*it);
	cout<<endl;
	
	return;
}

void flist(int j, int k)
{
	int i;
	list<int> l;
	list<int>::iterator it;
	list<int>::iterator it2;

	for (i=1; i<11; i++)
		l.push_back(i);

	l.remove(j);

	for (it=l.begin(); it!=l.end(); it++)
		if ((*it)==k) it2=it;
	
	l.erase(it2);

	cout<<"List "<<l.size()<<" contains:";
	for (it=l.begin(); it!=l.end(); ++it)
		cout<<" "<<(*it);
	
	cout<<endl;

	return;
}

int main()
{
	fmap();
	fvector();
	//cout<<fstack()<<endl;
	//fqueue();
	//fset(3,5);
	//flist(3,5);

	//string s;
	//cin>>s;

	return 0;
}

