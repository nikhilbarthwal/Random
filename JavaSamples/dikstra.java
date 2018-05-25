import java.io.*;
import java.util.*;

class node
{
	public String Name;
	public ArrayList<String> Links = ArrayList<String>();
	public ArrayList<int> Distance = ArrayList<int>();

	public int Val;
	public float Min;
	ArrayList<String> Path ;

	public node(String n)
	{
		Name=new String(n);
		Min=-1;
		Val=-1;
	}

	public void update(fload d, ArrayList<String> p)
	{
		if (min>d)
		{
			min=d;
			Path=new ArrayList(p);
			Path.add(Name);
		}

		return;
	}
	
	public crawl(ArrayList<node> N)
	{
		int k=Links.size();
		int d;
		String s;
				
		for(i=0;i<k;i++)
		{
			s = Links.get(i);
			d=Distance.get(i);
			
			loop: for(node n:N)
			{
				if (n.Name.equal(s))
				{
					n.update(min+d,Path);
					break loop;
				}
			}			
		}

		return;
	}
}



class dikstra
{
	ArrayList<node> N = new ArrayList<node>();

	float Min;
	ArrayList<String> Path = new ArrayList<Path>;

	boolean check(void)
	{
		for(node n:N)
			if (n.Val==-1) return true;

		return false;
	}


	void find(String root)
	{
		loop1: for(node n:N)
		{
			if (n.Name.equals(root))
			{
				n.update(0,new ArrayList<String>());
				break loop1;
			}
		}

		int k=0;
		while(check())
		{
			for(node n:N)
				if (n.Val==k)
				{
					ArrayList<String> m=n.Links;
					for(String s:m)
						loop2: for(node n2:N)
							if (n2.Name.equals(m))
							{
								n2.Val=k+1;
								break loop1;
							}
				}

			k++;		
		}	

		for(int i=0;i<k;i++)
		{
			for(node n:N)
			{
				if (n.Val==i)
				{
					n.crawl(N);
				}
			}
		}

	}
	
}

____________________________________________________________________________________


class node
{
	public String Name;
	public ArrayList<node> Links   = ArrayList<node>();
	public ArrayList<int> Distance = ArrayList<int>();
	public ArrayList<String> VisitFrom = new ArrayList<String>();

	public float Min;

	public node(String n)
	{
		Name=new String(n);
		Min=-1;
		visit=false;
	}

	public ArrayList<String> list(ArrayList<String> R)
	{
		R.add(Name);
		
		for(node N: Links)
		{
			if (R.contains(N.Name)) continue;

			R.add(N.Name);
			N.list(R)
	
		}
		
		return;
	}


	______


	do it:

	ArrayList<String> S = new ArrayList<String>();

	root.list(S);

	int sz = S.size();

	Hastable<String,float>             MinDist  = new Hashtable<String,float>(s);
	Hastable<String,ArrayList<String>> Path     = new Hashtable<String,ArrayList<String>>(s);
	Hastable<String,boolean>           Visits   = new Hashtable<String,boolean>(s);
	Hastable<String,ArrayList<String>> From     = new Hashtable<String,ArrayList<String>>(s);
	
	for(String s: S)
	{
		Distance.put(S,-1);
		Visit.put(S,false);
		From.put(S,new ArrayList<String>());
	}

	crawl(root, new String(), 0, new ArrayList<String>())


	private void crawl(node N, String O, float D, ArrayList<String> P)
	{
		float r;		
		
		if (D==0)
		{
			Visits.put(N.Name,true);
			MinDist.put(N.Name,0);
			MinPath=new ArrayList(1);
			MinPath.add(N.Name);
			Path.put(N.Name,MinPath);

			ArrayList<String> NewPath = ArrayList<String>(1);
			NewPath.add(N.Name);

			int k = N.Links.size();
			node n;
			float d;
			boolean b;

			for(int i=0; i<k; i++)
			{
				n = N.Links.get(i);				
				b = Visits.get(n.Name);				
				if (b) continue;
				d = N.Distance.get(i);
				crawl(n,N.Name,d,NewPath); 				
			}

		}
		else
		{
			float min = Hashtable.get(N.Name);

			if (min>D)
			{
				MinDist(N.Name,D);
				ArrayList MinPath=new ArrayList(Path.size()+1);
				MinPath.addAll(Path);
				MinDist(N.Name,D);
				Path.add(N.Name,MinPath);
			}

			ArrayList p = From.get(N.Name);
			p.add(n.Name);

			if (p.size()==N.Links.size())
				Visits.put(N.Name,true);

			int k=n.size();
			int m=P.size();

			ArrayList<String> NewPath = ArrayList<String>(m+1);
			Newpath.addAll(P);				
			NewPath.add(N.Name);

			int k = N.Links.size();
			node n;
			float d;
			boolean b;

			for(int i=0;i<k;i++)
			{
				n = N.Links.get(i);				
				b = Visits.get(n.Name);				
				if (b) continue;
				d = N.Distance.get(i);
				crawl(n,N.Name,D+d,NewPath); 				
			}
		} // End of if (D==0)

		return;

	} // End of crawl method

}




