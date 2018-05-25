import java.util.*;

class node
{
	public String Name;
	public int Size;
	public ArrayList<node> Links;
	public ArrayList<float>  Dist;

	public float min=0;
	public ArrayList<String> path;
	public boolean visit;

	node(String s)
	{
		Name=new String(s);
		Links = ArrayList<node>();
		Dist = ArrayList<int>();
		min=-1;
		path = new ArrayList<String>();
		visit=false;

		return;
	}

	public ArrayList<node> update(float d,ArrayList<String> p)
	{

		ArrayList<node> r = new ArrayList<node>();

		if (min>d)
		{
			min=d;
			path=new ArrayList<String>(p);
			path.add(Name);
		}
	
		visit=true;

		for(int i=0;i<Size;i++)
		{
			node n = Links.get(i);
			if (!n.visit) r.add(n);
		}

		return r;
	}
};

