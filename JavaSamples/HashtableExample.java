import java.util.*;
import java.io.*;

public class HashtableExample
{
	public static void main(String[] s)
	{
		Hashtable<String,String> table = new Hashtable<String,String>();
		table.put("key1", "wedfg_value1");
		table.put("key1", "wedfg_value0");
		table.put("key2", "asdfr_value2");
		table.put("key3", "pouie_value3");
	
		for(String key : table.keySet())
			System.out.println(key + " : " + table.get(key));
    
		ArrayList<String> v = new ArrayList<String>(table.values());
		Collections.sort(v);
		System.out.println(v);
	
		//for(String val : table.values())
		//	System.out.println(val);
    
		return;
	}
}
