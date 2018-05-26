// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Port Data Structure - port.java
//

import javax.swing.*;
import java.io.*;

class port
{
	public String Name;
	public DefaultListModel Services =    new DefaultListModel();
	public boolean Type; // False for IN and True for OUT

	port()
	{
		return;
	}

	port(boolean t,String n)
	{
		Name = n;
		Type = t;
		Services.clear();
		return;
	}

	public boolean isService(DefaultListModel X)
		{
			int n = X.size();
			String tmp;

			for(int j=0;j<n;j++)
			{
				tmp = (String) X.getElementAt(j);
				tmp.trim();
				if (!Services.contains(tmp)) return false;
			}

			return true;
		}

	public DefaultListModel providesService(DefaultListModel X)
	{
		DefaultListModel Y = new DefaultListModel();

		int i = X.size();
		String tmp;

		for(int j=0;j<i;j++)
		{
			tmp = (String) X.getElementAt(j);
			tmp.trim();
			if (Services.contains(tmp)) Y.addElement(tmp);
		}

		return Y;
	}

	public void addService(String X)
	{
		Services.addElement(X);
		return;
	}

	public void removeService(String X)
	{
		Services.removeElement(X);
		return;
	}

	public boolean readPort(String X)
	{
		String Tmp;
		int c;

		X.trim();

		try
		{
			if (X.startsWith("in "))
			{
				c=X.indexOf(' ',3);

				if (c==-1) Name = new String(X.substring(3));
				else Name = new String(X.substring(3,c));
				Name.trim();

				Type=false;
			}
			else if (X.startsWith("out "))
			{
				c=X.indexOf(' ',4);

				if (c==-1) Name = new String(X.substring(4));
				else Name = new String(X.substring(4,c));
				Name.trim();

				Type=true;
			}
			else
			{
				return false;
			}

			if (c==-1) return true;

			int d=c+1;
			c=X.indexOf(' ',d);
			while (c!=-1)
			{
				Tmp=X.substring(d,c);
				Tmp.trim();
				Services.addElement(Tmp);

				d=c+1;
				c=X.indexOf(' ',d);
			}

				Tmp=X.substring(d);
				Tmp.trim();
				Services.addElement(Tmp);
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}

	public boolean writePort(PrintWriter x)
	{
		int c = Services.getSize();

		try
		{
			String Tmp = new String();

			if (Type)
			{
				x.print("out ");
			}
			else
			{
				x.print("in ");
			}

			x.print(Name);

			c=Services.size();
			for(int d=0;d<c;d++)
			{
				Tmp = (String) Services.getElementAt(d);
				x.print(" " + Tmp);
			}

			x.println();
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}
}
