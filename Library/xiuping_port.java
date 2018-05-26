// THIS PART OF THE CODE IS MODIFIED VERSION OF THE CODE
// DEVELOPED BY XIUPING WU FOR HER TOOL LQ-COMPOSER

/**
 *  This class is part of the package LQNAssemble.
 *  It records the port info for a slot or a sub-model.
 */
import java.util.Vector;

public class xiuping_port
{
	private String name;

	boolean Type; // True = Input ; False = Output

	Vector tofrom; // a list of entries names this port connects to/from

	private String description = ""; // this variable applies to sub-model ports

	public xiuping_port(String name, Vector tofrom, boolean b)
	{
		this.name = name;
		this.tofrom = tofrom;
		this.Type = b;
	}

	public String getName()
	{
		return name;
	}

	public Vector getToOrFrom()
	{
		return tofrom;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public String toString()
	{
		String s1 = new String();
		String s2 = new String();
		
		if (Type) s1 = new String ("Input "); else s2 = new String("Output ");
		s2 = new String("port :" + name + " " + tofrom + " ");
		
		return s1+s2;
	}
} // end of class Port