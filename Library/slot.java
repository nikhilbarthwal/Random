// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Interface Data Structure - slot.java
//

import javax.swing.*;
import java.io.*;
import java.util.*;

class slot
{
	public String Name;

	public DefaultListModel SourceHosts = new DefaultListModel();
	public DefaultListModel TargetHosts = new DefaultListModel();
	public DefaultListModel ParamNames  = new DefaultListModel();
	public DefaultListModel ParamValues = new DefaultListModel();
	public DefaultListModel Ports       = new DefaultListModel();
	public DefaultListModel Bindings    = new DefaultListModel();
	public DefaultListModel Members     = new DefaultListModel();
	public DefaultListModel NonMembers  = new DefaultListModel();
	public DefaultListModel ProcServ    = new DefaultListModel();
	
	public Vector Inp = new Vector(); // Vector of Xiuping's Input port
	public Vector Out = new Vector(); // Vector of Xiuping's Output port

	public DefaultListModel ProcMatchSource  = new DefaultListModel(); // used to store processor matching
	public DefaultListModel ProcMatchTarget = new DefaultListModel(); // used to store processor matching

	slot()
	{
		SourceHosts.clear();
		TargetHosts.clear();
		ParamNames.clear();
		ParamValues.clear();
		Ports.clear();
		Bindings.clear();
		Members.clear();
		NonMembers.clear();
		return;
	}

	slot(String n,DefaultListModel X)
	{
		SourceHosts.clear();
		TargetHosts.clear();
		ParamNames.clear();
		ParamValues.clear();
		Ports.clear();
		Bindings.clear();
		Members.clear();
		NonMembers.clear();
		Name = n;
		Name.trim();

		int c= X.getSize();

		for(int i=0;i<c;i++)
		{
			String tmp = (String) X.getElementAt(i);
			tmp.trim();
			NonMembers.addElement(tmp);
		}

		return;
	}

	public void search(DefaultListModel C)
	{
		Bindings.clear();
		component c;

		xiuping_slot bind;

		int l = C.getSize();
		for(int i=0;i<l;i++)
		{
			c = (component) C.getElementAt(i);

			if ((c.isMember(Members))&&(Match(c.ProcServ)))
			//if ((c.isMember(Members))&&(Contains(c.Hosts,SourceHosts))) //NIKHIL: TEMPORARY
			{
				bind = new xiuping_slot(Name,i,ProcMatchSource,ProcMatchTarget,ParamNames,ParamValues,Inp,Out,c.Inp,c.Out);
				
				int m=Ports.getSize();
				int n=c.Interface.Ports.getSize();

				DefaultListModel X = new DefaultListModel();
				DefaultListModel Y = new DefaultListModel();

				//Check that all ports have a matching
				for(int j=0;j<m;j++)
				{
					port p1 = (port) Ports.getElementAt(j);
					if (n!=0)
					{
						for(int k=0;k<n;k++)
						{
							port p2 = (port) c.Interface.Ports.getElementAt(k);
							if (((p1.Type)&&(p2.Type))||((!p1.Type)&&(!p2.Type)))
							{
								if ((p1.isService(p2.Services))&&(!Y.contains(p2.Name))&&(!X.contains(p1.Name)))
								{
									//Binding p1.Name -> p2.Name
									X.addElement(p1.Name);
									Y.addElement(p2.Name);
								}
							}
						} // End of loop k
					}
				} // End of loop j

				if ((n!=0)&&(m==X.getSize()))
				{
					bind.addMatching(Y,X);
					Bindings.addElement(bind);
				}
			}
		} // End of component searching loop: i

		return;
	}

	/*private boolean Contains(DefaultListModel X,DefaultListModel Y)
	{
		// Checking if X (c.Hosts) is a subset of Y (SourceProcessor) ;

		String tmp;
		int index;
		
		ProcMatchSource.clear();
		ProcMatchTarget.clear();
		
		int n=X.getSize();
		for(int i=0;i<n;i++)
		{
			tmp = (String) X.getElementAt(i);
			index = Y.indexOf(tmp);
			if (index==-1) return false;
			
			ProcMatchSource.addElement(tmp);
			ProcMatchTarget.addElement(new String((String) TargetHosts.get(index)));
			if (!Y.contains(tmp)) return false;
		}

		return true;
	}*/

	private boolean Subset(ArrayList X, ArrayList Y)
	{
		// Checking if X is a subset of Y;

		String tmp;

		int n=X.size();
		for(int i=0;i<n;i++)
		{
			tmp = (String) X.get(i);
			if (!Y.contains(tmp)) return false;
		}

		return true;
	}

	private boolean Match(DefaultListModel H) // Will populate ProcMatch ***
	{
		// Checking if X is a subset of Y;

		String tmp1, tmp2;
		int n,m,i,j,k;
		ArrayList L1,L2;
		
		ProcMatchSource.clear();
		ProcMatchTarget.clear();
		
		n = H.size();
		m=ProcServ.size();
		for(i=0;i<n;i++)
		{
			L1 = new ArrayList((ArrayList) H.getElementAt(i));
			tmp1 = (String) L1.get(0);
			L1.remove(0);
			ProcMatchSource.addElement(tmp1);
			k=-1;
			for(j=0;j<m;j++)
			{
				L2 = new ArrayList((ArrayList) ProcServ.getElementAt(j));
				tmp2 = (String) L2.get(0);
				L2.remove(0);

				if (Subset(L1,L2))
				{
					ProcMatchTarget.addElement(tmp2);
					k=j;
					j=m;
				}
			}
			if (k==-1) return false;
		}
		
		return true;
	}
	
	public void permit(String X)
	{
		NonMembers.removeElement(X);
		Members.addElement(X);
		return;
	}

	public void depermit(String X)
	{
		NonMembers.addElement(X);
		Members.removeElement(X);
		return;
	}

	public void addClass(String X)
	{
		// Group X added in Slot's Non-Member list
		if ((Members.contains(X))||(NonMembers.contains(X))) return;
		NonMembers.addElement(X);
		return;
	}

	public void editClass(String X,String Y)
	{
		// Group X changed to Y in Slot's Member and non-Member's list
		if (Members.contains(X))
		{
			Members.removeElement(X);
			Members.addElement(Y);
		}

		if (NonMembers.contains(X))
		{
			NonMembers.removeElement(X);
			NonMembers.addElement(Y);
		}
		return;
	}

	public void removeClass(String X)
	{
		if (Members.contains(X)) Members.removeElement(X);
		if (NonMembers.contains(X)) NonMembers.removeElement(X);
		return;
	}

	public boolean wrong()
	{
		int p1 = TargetHosts.size(); // REPLACE
		int p2 = ProcServ.size();
		
		String s;
		if (p1==p2)
			for(int i=0;i<p1;i++)
			{
				ArrayList v = (ArrayList) ProcServ.get(i);
				s = (String) v.get(0);
				if (!TargetHosts.contains(s))
				{
					System.out.println("   Error - Processor "+s+" not found: Process stopped!");
					return true;
				}
			}
		else
		{
			System.out.println("   Error - Processors list don't match: Process stopped!");
			return true;
		}
		
		return false;
	}
	

	public boolean read_slot(BufferedReader x,DefaultListModel M)
	{
		String s;
		boolean ans;
		String tmp;
		int c,d,p,e,p2;

		try
		{
			s=x.readLine();

			c=s.indexOf(' ');
			if (c==-1) return false; // Error in interpreting slot name

			Name=new String(s.substring(0,c));

			d=s.indexOf(' ',c+1);
			e=s.indexOf(' ',d+1);
			
			if (d==-1) return false; // Error: Unexpected end of line

			tmp = new String(s.substring(c+1,d));
			Integer pp=Integer.valueOf(tmp);
			p2 = pp.intValue(); // # of processors in Slot

			if (e==-1)
			{
				pp=Integer.valueOf(s.substring(d+1));
				p = pp.intValue(); // # of ports in Slot
			}
			else
			{
				tmp = new String(s.substring(d+1,e));
				pp=Integer.valueOf(tmp);
				p = pp.intValue();

				d++;
				c=s.indexOf(' ',d);

				while (c!=-1)
				{
					tmp=s.substring(d,c);
					tmp.trim();

					if (M.contains(tmp)) Members.addElement(tmp);

					d=c+1;
					c=s.indexOf(' ',d);
				}

				if (c==-1)
				{
					tmp=s.substring(d);
					if (M.contains(tmp)) Members.addElement(tmp);
				}
			}

			c=M.getSize();
			for(d=0;d<c;d++)
			{
				tmp = (String) M.getElementAt(d);
				if (!(Members.contains(tmp))) NonMembers.addElement(tmp);
			}
		
			for(int i=0;i<p2;i++)
			{
				String ss=(x.readLine()).trim();
				
				int j1=0;
				int j2=ss.indexOf(' ');
				
				ArrayList v = new ArrayList();
				
				while (j2!=-1)
				{
					tmp=ss.substring(j1,j2);
					tmp.trim();
					v.add(tmp);
					j1 = j2+1;
					j2 = ss.indexOf(' ',j1);
				}
				tmp=ss.substring(j1);
				tmp.trim();
				v.add(tmp);
				ProcServ.addElement(v);
			}

			for(int i=0;i<p;i++)
			{
				port P = new port();
				s=x.readLine();

				ans = P.readPort(s);
				if (!ans)
				{
					System.out.println("   Failed to interpret "+s+"!");
					return false;
				}
				else Ports.addElement(P);
			}
		}

		catch(Exception E)
		{
			System.out.println("   Error in reading slot: Process stopped!");
			return false;
		}

		return true;
	}

	public boolean write_slot(PrintWriter x)
	{
		try
		{
			String s;
			int c = Ports.getSize();
			int d=ProcServ.getSize();
			
			port P = new port();

			x.print(Name+" "+d+" "+c);

			int n = Members.getSize();
			for(int i=0;i<n;i++)
			{
				s = (String) Members.getElementAt(i);
				x.print(" "+s);
			}
			x.println("");
			
			for(int i=0;i<d;i++)
			{
				ArrayList v = (ArrayList) ProcServ.get(i);
				n = v.size();
				for(int j=0;j<n;j++)
				{
					s = (String) v.get(j);
					if (j!=0) x.print(" ");
					x.print(s);
				}
				x.println("");
			}
			
			for(int i=0;i<c;i++)
			{
				P = (port) Ports.getElementAt(i);
				P.writePort(x);
			}
		}
		catch(Exception e)
		{
			System.out.println("   Error in writing slot: Process stopped!");
			return false;
		}

		return true;
	}
}
