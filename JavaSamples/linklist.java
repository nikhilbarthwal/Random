import java.io.*;
import java.util.*;

class node
{
	public int data;
	public node next;
	
	node(int c)
	{
		data=c;
		next=null;
	}
	
}

public class linklist
{
	public static void main(String[] args)
	{
		int c=10, d=-1;
		if (args.length>0) c=Integer.valueOf(args[0]);
		if (args.length>1) d=Integer.valueOf(args[1]);
		
		node head=null;
		node current;
		
		for(int i=c;i>0;i--)
		{
			current = new node(i);
			current.next=head;
			head=current;
		}
		
		System.out.println("Normal form:");
		current=head;
		while(current!=null)
		{
			System.out.println(current.data);
			current=current.next;
		}
		
		deletion: if (d!=-1)
		{
			System.out.println("Deleted form:");

			if (head==null) break deletion;
			if (head.data==d)
			{
				head=head.next;
				break deletion;
			}

			current=head;
			while(current.next!=null)
			{
				if (current.data==d) 
				tmp=current;
				current=current.next;
			}
		}
		
		node a=head;
		node b=head.next;
		a.next=null;
		while(b!=null)
		{
			head=b;
			b=b.next;
			head.next=a;
			a=head;
		}
		
		System.out.println("\nReverse  form:");
		current=head;
		while(current!=null)
		{
			System.out.println(current.data);
			current=current.next;
		}

		Random r = new Random();
		
		for (int idx = 1; idx <= 10; ++idx)
			System.out.println(r.nextInt(100));
		
		return;
		
	}
	
}
