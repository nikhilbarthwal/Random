// Class to handle Graph for the Solution
// Nikhil Barthwal (Fri, Dec 16, 2011)

import java.util.*;

public class graph
{
	public String name;
	public ArrayList<graph> links;
	public ArrayList<String> path;
	public boolean visit; // Only for Path
	
	private int max; // Opt
	graph(String s, int n) // Opt: graph(String s)
	{
		name=new String(s);
		links = new ArrayList<graph>();
		visit=false;
		n = max; // Opt:
	}
	
	public void init()
	{
		visit=true;
		path = new ArrayList<String>(1);
		path.add(name);
		return;
	}
	
	public boolean exists(String s)
	{
		if (s.equals(name)) return true;
		
		for(graph t:links)
			if (t.exists(s)) return true;
		
		return false;
	}

	private boolean transform(String s1, String s2)
	{
		int l=s1.length();
		int k=0;
		for(int i=0;i<l;i++)
		{
			if (s1.charAt(i)!=s2.charAt(i)) k++;
			if (k>2) return false;
		}
		
		if (k==1) return true;
	
		return false;
	}
	
	public ArrayList<graph> crawl(ArrayList<graph> L)
	{
		ArrayList<graph> R = new ArrayList<graph>(max); // Opt: max only in opt
		
		for(graph N: L)
			if (transform(name,N.name))
			{
				links.add(N);
				
				if (!N.visit)
				{
					ArrayList<String> P = new ArrayList<String>(path.size()+1);
					P.addAll(path);
					P.add(N.name);
					N.path = P;
					N.visit=true;
					R.add(N);
				}
			}
		
		return R;
	}
	
	public void output(ArrayList<String> R)
	{
		R.add(name);
		
		for(graph n:links)
			if (!R.contains(n.name))
				n.output(R);
		
		return;
	}
	
	public String toString()
	{
		String S = new String();
		S = name + " [";
		for(graph g:links) S=S+" "+g.name;
		S = S+ " ]";
		
		return S;
	}
}

