#include <iostream>
#include <queue>
#include <map>
#include <string>

using namespace std;

const int N=15;

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
};

typedef vector<class node*> list;

void print(vector<string> r)
{
	for(unsigned int i=0;i<r.size();i++)
		cout<<r[i]<<" ";
	cout<<endl;
	
	return;
}

vector<string> path(node source, node dest)
{
	list r1, r2, r3;
	map<string,list> m;
	
	r1.push_back(&source);
	m[source.name] = r1;

	while(!r1.empty())
	{
		r2.clear();
		for(unsigned int i=0; i<r1.size(); i++)
		{
			class node* h=r1[i];
			list r=m[h->name];
			for(unsigned int j=0;j<(h->links).size();j++)
			{
				class node* hh = (h->links)[j];
				
				if (m.count(hh->name)>0) continue;
								
				int K = r.size();
				list rr(K+1);
				for(int k=0; k<K; k++)
					rr[k]=r[k];

				rr[K]=hh;
				m[hh->name]=rr;
				r2.push_back(hh);
			}
		}
		r1=r2;

	}

	list r=m[dest.name];
	vector<string> R(r.size());

	for(unsigned int i=0; i<r.size(); i++)
		R[i]=(r[i])->name;
		
	return R;
}


int main(void)
{
	class node a("a");
	class node b("b");
	class node c("c");
	class node d("d");
	class node e("e");
	class node f("f");
	class node g("g");
	class node h("h");
	class node p("p");
	class node q("q");
	class node r("r");
	class node t("t");
	class node x("x");
	class node y("y");
	class node z("z");

	a.add(b); a.add(c); a.add(d); a.add(e);
	b.add(a); b.add(z);
	c.add(a); c.add(y); c.add(z);
	d.add(a); d.add(g); d.add(x); d.add(h);
	e.add(a); e.add(g);
	f.add(y); f.add(r);
	g.add(e); g.add(d); g.add(h);
	h.add(g); h.add(d); h.add(x); h.add(t);
	p.add(y); p.add(z); p.add(r);
	q.add(y); q.add(r); q.add(x); q.add(t);
	r.add(p); r.add(f); r.add(q);
	t.add(h); t.add(q);
	x.add(d); x.add(y); x.add(h); x.add(q);
	y.add(c); y.add(z); y.add(p); y.add(f);
	z.add(b); z.add(c); z.add(y); z.add(p); 

	vector<string> P;
	P=path(a,r);
	print(P);

	return 0;
}
