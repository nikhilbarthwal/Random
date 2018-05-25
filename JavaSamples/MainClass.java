import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class MainClass {
  public static void main(String[] args) {
    String data;
    String msg;

    Hashtable<String,Object> h = new Hashtable<String,Object>(20);

    System.out.println(h.put("one", new Integer(1)));
    System.out.println(h.put("name", "A"));
    System.out.println(h.put("date", new Date()));
    System.out.println(h.put("one", new Integer(4)));

    Enumeration e = h.keys();
    while (e.hasMoreElements())
      System.out.println(e.nextElement());

    e = h.elements();
    while (e.hasMoreElements())
      System.out.println(e.nextElement());
  }
}
