import java.io.*;
import java.util.*;

public class stdio2
{
  public static void main(String[] args) throws IOException
  {
	String s1 = new String();

	ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();

	
	int[][] a = { {1,2,3}, {4,5} };
	int[] b = new int[args.length]; // = {1,2,3,4};
	
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	System.out.print("Enter text : ");
	s1 = in.readLine();
	System.out.println("You entered String : "+s1);
	
	
	for(String e: args)
			System.out.println(e);
			
	in = new BufferedReader(new FileReader("a.txt"));

	while ((s3 = in.readLine()) != null)
		System.out.println(s3);

	in.close();

	System.out.println(tb);		

	return;
  }
}

 String
--------
char charAt(int index)
boolean endsWith(String suffix)
boolean equals(Object anObject)
int indexOf(int ch)
int indexOf(int ch, int fromIndex)
boolean isEmpty()
int length()
boolean startsWith(String prefix)
static  String  valueOf(double/int/float/long/Object x)

 ArrayList
-----------
boolean 	add(E e)
void 	add(int index, E element)
boolean 	addAll(Collection c)
boolean 	addAll(int index, Collection c)
void 	clear()
boolean 	contains(Object o)
E 	get(int index)
int 	indexOf(Object o)
boolean 	isEmpty()
E 	remove(int index)
boolean 	remove(Object o)
int 	lastIndexOf(Object o)
int 	size()


 Hashtable
-----------

void clear()
V get(Object key)
boolean 	contains(Object value)
boolean 	containsKey(Object key)
Enumeration<K> 	keys()
Set<K> 	keySet()
Collection<V> 	values()
V 	remove(Object key)
int 	size()
V 	put(K key, V value)

Hashtable(int initialCapacity, float loadFactor)

