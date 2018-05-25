import java.io.*;
import java.lang.*;
import java.util.*;


public class search
{
	private static boolean search(int k, ArrayList<Integer> l)
	{
		int s = l.size();
		int a = 0;
		int b = s-1;
		int p;
		
		while(b>=a)
		{
			int c = (a+b)/2;
			
			//System.out.println(a+" "+b+" "+c);
			
			p=(l.get(c)).intValue();
			
			if (k==p) return true;
			
			if (k>p)
				a=c+1;
			else
				b=c-1;
		}
		
		return false;
		
		
	}
	

	public static void main(String[] args)
	{
		ArrayList<Integer> L = new ArrayList<Integer>();
		
		for(String s: args)
			L.add(Integer.valueOf(s));
		
		//System.out.println(L);
		//boolean b = search(4,L);
		//System.out.println(b);
		
		ListIterator<Integer> I = L.listIterator();
		
		while(I.hasNext())
			System.out.println(I.next());
		
		return;
	}
	

}
