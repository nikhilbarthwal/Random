// Solution to the interview problem
// Nikhil Barthwal (Fri, Dec 16, 2011)

import java.util.*;

public class find
{
	private static ArrayList<String> S;
	
	// True if difference between s1 and s2 is one char only	
	private static boolean transform(String s1,String s2)
	{
		int k=0;
		
		for(int i=0;i<s1.length();i++)
			if (s1.charAt(i)!=s2.charAt(i)) k++;
		
		if (k==1) return true;
		
		return false;
	}
	
	// Computing the disjoint set for String W
	public static ArrayList<String> compute_disjoint_sets(String W)
	{
		ArrayList<String> R1 = new ArrayList<String>(S.size());
		ArrayList<String> R2 = new ArrayList<String>(S.size());
		ArrayList<String> R3 = new ArrayList<String>(S.size());
		
		// Define G as graph		
		graph G = new graph(W);
		
		String N, T;
		
		// Add W to R2
		R2.add(W);
		
		do
		{
			R3.clear();
			
			for(int i=0;i<R2.size();i++)
			for(int j=0;j<S.size();j++)
			{
				N = (String) R2.get(i); // For all words N in R2
				T = (String) S.get(j);  // For all words T in S
				
				// If (T can be obtained from N in 1 char transformation)
				// and (T does not exists in G)
				if ( (transform(N,T)) && (!G.exists(T)) )
				{
					G.attach(N,T); // Attach node T to node N in graph G
					R3.add(T); // Add this word T to set R3
				}
			}
			
			// R2 = R3
			R2=new ArrayList<String> (R3);
		}
		while(R3.size()>0); // Continue till R3 is not empty
	
		R1 = G.output(); // R1 is set of all nodes in G
		
		return R1;
		
	}
	
	public static ArrayList<String> find_answer()
	{
		ArrayList<String> R = new ArrayList<String>(S.size());
		ArrayList<String> M = new ArrayList<String>(S.size());
		ArrayList<String> P = new ArrayList<String>(S.size());

		int n, max;
		String W;
		
		max=-1; // Size of maximal set M
		
		for(int i=0;i<S.size();i++)
		{
			W = S.get(i);

			if (P.contains(W)) continue; // Optimization: Don't compute if W is present in P
			R = compute_disjoint_sets(W);
			P.addAll(R);		
			n = R.size();
			
			// If R is bigger than M, then M = R
			if (n>max) { max=n; M = new ArrayList<String> (R); }
		}
		
		return M;
	}
	
	public static void main(String args[])
	{
		int n=args.length;

		ArrayList<String> R = new ArrayList<String>(n);
		
		// Copy arguments on command line into S		
		S = new ArrayList<String>(n);
		for(int i=0;i<n;i++) S.add(args[i]);
		
		// Find the answer and print it
		R=find_answer();
		System.out.println(R);
		
		return;
	}
}
