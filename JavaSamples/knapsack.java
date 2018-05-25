/*
 *  s[i,w] = 0 for w=0 or i = 0
 *  s[i,w] = s[i-1,w] for w(i)> W
 *         = max{ s[i-1,w], c(i) + s[i-1,w-w(i)] } for w(i) <= W 
 * 
 */

import java.io.*;
import java.util.*;

class knapsack
{
	private static int [][] s;
	private static int [][] q;
	
	private static int maxfind(int i, int W, int w, int c)
	{
		int m = 0, k = 0, t;

		//while ((W>=k*w)&&(k<2))
		while (W>=k*w)
		{
			t=k*c+s[i-1][W-k*w];
			if (t>m) { m=t; q[i][W]=k; }
			k++;
		}

		return m;
	}
	
	private static int maxcost(int N, int W, int[] c, int[] w)
	{
		s = new int[N][W+1];
		q = new int[N][W+1];
		
		int a, b;
		
		for(int i=0;i<N;i++)
			s[i][0]=0;
		
		for(int i=0;i<=W;i++)
			s[0][i]=0;
		
		for(int i=0;i<N;i++)
		for(int j=0;j<=W;j++)
			q[i][j]=0;
		
		for(int i=1;i<N;i++)
		for(int j=1;j<=W;j++)
		{
			if (w[i]>j)
			{
				s[i][j] = s[i-1][j];
				q[i][j] = 0;
			}
			else
				s[i][j] = maxfind(i,j,w[i],c[i]);
		}
		
		return s[N-1][W];
	}
	
	public static void main(String[] args)
	{
		int N=4;
		int W=10;
		int[] w = {5, 4, 6, 3};
		int[] c = {10, 40, 30, 50};
		
		int a = maxcost(N,W,c,w);
		
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<=W;j++)
				System.out.print("  "+q[i][j]);
			System.out.print("\n");
		}
		
		System.out.println("Final answer: "+a);
		
		return;
		
	}
}

	
