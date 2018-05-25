/*

1. List -> Grapph
2. Graph: no. of stesp in takes from Source to the node
3. Trace path from Source to Target

All words are eequal length in L
source S is element of L
Destination D is element of L

*/

import java.util.*;

class path
{
	static ArrayList<String> find(String[] L, String s, String d)
	{
		ArrayList<graph> NodeList = new ArrayList<graph>(L.length);
		
		graph S = new graph(" ",0);
		graph D = new graph(" ",0);
		
		for(String str:L)
		{
			graph N = new graph(str,L.length);

			if (str.equals(s)) S = N;
			if (str.equals(d)) D = N;
		
			NodeList.add(N);
		}
		
		S.init();

		ArrayList<graph> R1 = new ArrayList<graph>(L.length);
		ArrayList<graph> R2 = new ArrayList<graph>(L.length);
		
		R1.add(S);
		while(!R1.isEmpty())
		{
			R2.clear();
			for(graph n: R1)
				R2.addAll(n.crawl(NodeList));
		
			R1.clear();
			R1.addAll(R2);
		}
		
		return D.path;
	}
	
	public static void main(String args[])
	{
		//for( String s:args) System.out.println(s);
		ArrayList<String> R=find(args, args[0], args[1]);
		System.out.println(R);
		
		return;
	}

}
