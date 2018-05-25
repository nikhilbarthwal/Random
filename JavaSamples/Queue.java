public class Queue
{

	private int[] Q;
	private int N;
	
	Queue(int n)
	{
		N=0;
		Q = new int[n];
	}
	
	public void put(int r)
	{
		if (N==Q.length) return;
		
		Q[N]=r;
		N++;
	}
	
	public int get()
	{
		if (N==0) return -1;
		
		int n=Q[0];
		for(int i=0;i<N-1;i++)
			Q[i]=Q[i+1];
		N--;
		return n;
	}
	
	public boolean empty()
	{
		if (N==0) return true;
		return false;
	}
}
