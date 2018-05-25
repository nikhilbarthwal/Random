public class Mapping
{
	/* This problem would deal with Keypad mapping
	   but it can be easily adapted to other cases
		2 - ABC
		3 - DEF
		4 - GHI
		5 - JKL
		6 - MNO
		7 - PQRS
		8 - TUV
		9 - WXYZ
	*/
	
	// Length of this array is number of char mappings
	private static char[] Chars = {'2','3','4','5','6','7','8','9'};
	
	// Mapping of each charated Char[i] is stored in Mappings[i] - REORDER to MATCH FREQUENCY
	private static char[][] Mappings = { 
			{'A','B','C'},
			{'D','E','F'},
			{'G','H','I'},
			{'J','K','L'},
			{'M','N','O'},
			{'P','Q','R','S'},
			{'T','U','V'},
			{'W','X','Y','Z'},
	};

	// Used to store words to be matched
	private static String[] Words = {"CAT", "RAT", "BAT", "MONKEY", "LION"};

	private static boolean checkWord(String word)
	{
		for(String s:Words)
			if (s.equals(word)) return true;
			
		return false;
	}
	
	// String S and N are passed by Reference to it is allowed to modify S
	private static boolean search(int k, char[] S, String N) throws Exception
	{
		Exception e = new Exception();
		
		if (k==N.length())
			return (checkWord(new String(S)));
		

	    // Find the position of char from N in Chars
		char c = N.charAt(k);
		int j=-1;
		for(int i=0;i<Chars.length;i++)
			if (c==Chars[i]) { j=i; break; }
		
		// Enumerate through values
		if(j==-1) throw e;
		
		for(char d:Mappings[j])
		{
			S[k]=d;
			if (search(k+1,S,N)) return true;
		}
		
		return false;
	}

	public static boolean checkNumber(String Numbers) throws Exception 
	{
		Exception e = new Exception();
		
		int k = Numbers.length();

		if (k==0) throw e;
				
		char[] S = new char[k];
		
		return (search(0,S,Numbers));
	}
	
	public static void main(String[] args)
	{
		String[] S = {"","92a", "228", "229","956", "5466"};
		
		for(String s:S)
		{
			System.out.println("Input: "+s+"\n");
			try
			{
				if (checkNumber(s))
					System.out.println("YES!");
				else
					System.out.println("No!");
			}
			catch(Exception e)
			{
				System.out.println("Error in Input!!!");
			}
			System.out.println("--------------\n");
		}
		
		return;		
	}
}
