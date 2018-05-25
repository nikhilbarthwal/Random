import java.util.*;

public class AddUp
{
	public static final int c = 9;
	public static final int[] s = {4, -4, 7, -7};

	public static int sum(ArrayList<Integer> Q)
	{
		int s=0;
		
		for(Integer i:Q)
			s+=i.intValue();
		return s;
	}

	public static void search(ArrayList<Integer> Q, int k)
	{
		if (k>4) return;
		
		ArrayList<Integer> QQ = new ArrayList<Integer>(Q.size()*s.length);
		
		for(Integer i:Q)
		{
			int z = i.intValue(); 
			if (z<=0) continue;
			if (z==c) return;
			
			//int S=sum(Q);
			System.out.println(z);
			QQ.add(new Integer(s[0]+i.intValue()));
			QQ.add(new Integer(s[1]+i.intValue()));
			QQ.add(new Integer(s[2]+i.intValue()));
			QQ.add(new Integer(s[3]+i.intValue()));
		}
		
		search(QQ,k+1);
		return;
	}
	
	public static void main(String [] ARGS)
	{
		
		ArrayList<Integer> A = new ArrayList<Integer>();
		A.add(new Integer(s[0]));
		A.add(new Integer(s[2]));
		
		search(A,0);
				
		return;
	}

}
