import java.io.*;

public class Test
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			String s = in.readLine();
			char c = s.charAt(0);
			int i = (int) c;
			System.out.print(i);
		}
		catch(Exception e)
		{
			
		}
	}
}
