import java.io.*;
import java.util.*;

public class stdio
{
 
  public static void main(String[] args) throws IOException
  {
	String s1 = new String();
	String s2 = "nikhil";
	String s3;

	ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();

	
	int[][] a = { {1,2,3}, {4,5} };
	int[] b = new int[args.length]; // = {1,2,3,4};
	
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	System.out.print("Enter text : ");
	s1 = in.readLine();
	System.out.println("You entered String : "+s1);
	
	if (s1.equals(s2))
		System.out.println("Equal");
	else
		System.out.println("Not equal");
			
	int i=s1.length();
	
	char c=s1.charAt(1);

	
	/*for(char d='a';d<='z';d++)
			System.out.println(d);*/

	System.out.println("Length : "+i+" "+args.length);
	
	for(String e: args)
			System.out.println(e);
			

	in = new BufferedReader(new FileReader("a.txt"));

	while ((s3 = in.readLine()) != null)
		System.out.println(s3);

	in.close();

	String ta="nikhil";
	String tb=ta;
	ta=new String("ni");

	System.out.println(tb);		

	return;
  }
}
