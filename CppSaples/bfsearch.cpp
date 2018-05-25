#include <iostream>
#include <queue>
#include <string>

using namespace std;

class node
{
public:
	string name;
	vector< class node* > links;

	node(string n)
	{
		name=n;
		return;
	}

	void add(class node& n)
	{
		links.push_back(&n);
	}

	void print(int k)
	{
		for(int i=0; i<k; i++) cout<<'\t';
		
		cout<<name<<endl;

		for(unsigned int i=0; i<links.size(); i++)
			(links[i])->print(k+1);
		return;
	}
} *h;

int main(void)
{
	queue<class node*> q;

	class node z("z");
	class node a("a"); class node b("b"); class node c("c");
	class node a1("a1"); class node a2("a2"); class node a3("a3");
	class node b1("b1"); class node b2("b2"); class node b3("b3");
	class node c1("c1"); class node c2("c2"); class node c3("c3");

	z.add(a); z.add(b); z.add(c);
	a.add(a1); a.add(a2); a.add(a3);
	b.add(b1); b.add(b2); b.add(b3);
	c.add(c1); c.add(c2); c.add(c3);

	//z.print(0);
	//cout<<endl<<endl;

	h=&z;

	q.push(h);

	while (!q.empty())
	{
		h=q.front();

		cout<<(h->name)<<endl;
		for(unsigned int i=0; i<(h->links).size(); i++)
			q.push((h->links)[i]);
		
		q.pop();
	}
	
	return 0;
}

