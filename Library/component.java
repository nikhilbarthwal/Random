// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Component Data Structure - component.java
//

import javax.swing.*;
import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

class component
{
	public String Name;
	public String FileName;

	public boolean Model;
	public Document XMLDocument;

	public slot Interface = new slot();

	public DefaultListModel Hosts = new DefaultListModel();
	public DefaultListModel Params = new DefaultListModel();
	public DefaultListModel Slots = new DefaultListModel();
	public DefaultListModel Members = new DefaultListModel();
	public DefaultListModel NonMembers = new DefaultListModel();
	public DefaultListModel ProcServ = new DefaultListModel();
	public DefaultListModel Processors = new DefaultListModel();

	public Vector Inp = new Vector(); // Vector of Xiuping's Input port
	public Vector Out = new Vector(); // Vector of Xiuping's Output port

	public boolean isMember(DefaultListModel X)
	{
		int i = X.size();
		boolean status = false;
		String tmp;

		for (int j = 0; j < i; j++)
		{
			tmp = (String) X.getElementAt(j);
			tmp.trim();
			if (Members.contains(tmp)) { status = true; break; }
		}

		return status;
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
		X.trim();

		if ((Members.contains(X)) || (NonMembers.contains(X)))
			return;
		NonMembers.addElement(X);

		Interface.addClass(X);

		int c = Slots.getSize();
		slot tmp = new slot();

		for(int i=0;i<c;i++)
		{
			tmp= (slot) Slots.getElementAt(i);
			tmp.addClass(X);
		}

		return;
	}

	public void editClass(String X,String Y)
	{

		int c = Slots.getSize();

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

		Interface.editClass(X,Y);

		slot tmp = new slot();
		for(int i=0;i<c;i++)
		{
			tmp= (slot) Slots.getElementAt(i);
			tmp.editClass(X,Y);
		}

		return;
	}

	public void removeClass(String X)
	{
		if (Members.contains(X)) Members.removeElement(X);
		if (NonMembers.contains(X)) NonMembers.removeElement(X);

		Interface.removeClass(X);

		int c = Slots.size();
		slot tmp = new slot();

		for(int i=0;i<c;i++)
		{
			tmp= (slot) Slots.getElementAt(i);
			tmp.removeClass(X);
		}

		return;
	}

	public boolean readComponent(BufferedReader x, DefaultListModel M, File f)
	{
		String s = new String("");
		String tmp = new String("");
		boolean ans = true;
		int a,b,c,d;

		slot I = new slot();

		Integer N;
		try
		{
			s=x.readLine();

			a = s.indexOf(' ');
			if (a == -1)
			{
				System.out.println("   Error in reading component: Unable to interpret name!");
				return false;
			}

			Name = new String(s.substring(0, a));

			b = s.indexOf(' ', a + 1);
			if (b == -1)
			{
				System.out.println("   Error in reading component: Unable to interpret file name!");
				return false;
			}

			FileName = new String(s.substring(a + 1, b));

			c = s.indexOf(' ', b + 1);
			if (c == -1)
			{
				System.out.println("   Error in reading component: Unable to number of processors!");
				return false;
			}
			int m = (Integer.valueOf(s.substring(b + 1, c))).intValue();

			b = c;
			c = s.indexOf(' ', b + 1);
			if (c == -1)
				N = Integer.valueOf(s.substring(b + 1));
			else
				N = Integer.valueOf(s.substring(b + 1, c));
			int n = N.intValue();

			if (c != -1)
			{
				c++;
				d = s.indexOf(' ', c);

				while (d != -1)
				{
					tmp = s.substring(c, d);
					tmp.trim();

					if (M.contains(tmp))
						Members.addElement(tmp);
					c = d + 1;
					d = s.indexOf(' ', c);
				}

				if (d == -1)
				{
					tmp = s.substring(c);
					if (M.contains(tmp))
						Members.addElement(tmp);
				}
			}

			// read processors
			for (int i = 0; i < m; i++)
			{
				String ss = (x.readLine()).trim();

				int j1 = 0;
				int j2 = ss.indexOf(' ');

				ArrayList v = new ArrayList();

				while (j2 != -1)
				{
					tmp = ss.substring(j1, j2);
					tmp.trim();
					v.add(tmp);
					j1 = j2 + 1;
					j2 = ss.indexOf(' ', j1);
				}
				tmp = ss.substring(j1);
				tmp.trim();
				v.add(tmp);

				ProcServ.addElement(v);
			}

			// Read the XML file
			File F = new File(f, FileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			XMLDocument = documentBuilder.parse(F);

			c = M.size();
			for (d = 0; d < c; d++)
			{
				s = (String) M.getElementAt(d);
				if (!(Members.contains(s)))
					NonMembers.addElement(s);
			}

			ans = Interface.read_slot(x, M);
			if (!ans)
			{
				System.out.println("   Unable to read external interface: Process stopped!");
				return false;
			}
			Interface.Name = "External-Interface";

			for (int i = 0; i < n; i++)
			{
				I = new slot();
				ans = I.read_slot(x, M);
				if (!ans)
				{
					System.out.println("   Unable to read interface: Process stopped!");
					return false;
				}
				Slots.addElement(I);
			}

			if (!readHosts(false))
			{
				System.out.println("   Unable to read host info: Process stopped!");
				return false;
			}

			n = ProcServ.size();
			m = Hosts.size();
			if (n == m)
				for (int i = 0; i < n; i++)
				{
					ArrayList v = (ArrayList) ProcServ.get(i);
					s = (String) v.get(0);
					if (!Hosts.contains(s))
					{
						System.out.println("   Error - Processor " + s + " not found: Process stopped!");
						return false;
					}
				}
			else
			{
				System.out.println("   Error - Processors list don't match: Process stopped!");
				return false;
			}

			n = Slots.size();
			for (int i = 0; i < n; i++)
			{
				I = (slot) Slots.get(i);
				if (I.wrong())
				{
					System.out.println("   Error - Processors in Slot " + I.Name + " don't match: Process stopped!");
					return false;
				}
			}
		}

		catch (Exception e)
		{
			System.out.println("   Error in reading component: Process stopped!");
			return false;
		}

		return true;
	}

	public boolean readHosts(boolean B)
	{
		String tmp;
		Hosts.clear();

		try
		{

			NodeList lst1 = XMLDocument.getElementsByTagName("lqn-model");
			NodeList lst2 = XMLDocument.getElementsByTagName("lqn-submodel");

			int lst1size = lst1.getLength();
			int lst2size = lst2.getLength();

			if ((lst1size == 0) && (lst2size == 0))
				return false;

			Node model_node;

			// Read all repleacable processors
			if (lst1size == 0)
			{

				Model = false;
				model_node = lst2.item(0);

				NodeList lst = ((Element) model_node).getElementsByTagName("Interface");
				for(int index=0;index<lst.getLength();index++)
				{
					Node main_node = lst.item(index);
				
					NodeList hst = ((Element) main_node).getElementsByTagName("Replaceable-Processor");
					for (int i = 0; i < hst.getLength(); i++)
					{
						Node proc = hst.item(i);
						tmp = ((Element) proc).getAttribute("name");
						tmp.trim();
						Hosts.addElement(tmp);
						Processors.addElement(tmp);
					}
	
					// Add xiuping's code
					NodeList inPortList  = ((Element) main_node).getElementsByTagName("in-port");
					NodeList outPortList = ((Element) main_node).getElementsByTagName("out-port");
					for (int xk = 0; xk < inPortList.getLength(); xk++)
					{
						Node inPort = inPortList.item(xk);
						String portName = ((Element) inPort).getAttribute("name");
						String fromSource = ((Element) inPort).getAttribute("connect-from");
						Vector fromSourceList = new Vector();
						int ki = 0;
						for (;;)
						{
							int kj = fromSource.indexOf(' ', ki);
							if (kj == -1)
								break;
							fromSourceList.addElement(fromSource.substring(ki, kj));
							ki = kj + 1;
						}
						fromSourceList.addElement(fromSource.substring(ki));
						Inp.addElement(new xiuping_port(portName, fromSourceList, true));
					} // end for k
		
					for (int xk = 0; xk < outPortList.getLength(); xk++)
					{
						Node outPort = outPortList.item(xk);
						String portName = ((Element) outPort).getAttribute("name");
						String toTarget = ((Element) outPort).getAttribute("connect-to");
						Vector toTargetList = new Vector();
						int ki = 0;
						toTarget.trim();
						for (;;)
						{
							int kj = toTarget.indexOf(' ', ki);
							if (kj == -1)
								break;
							toTargetList.addElement(toTarget.substring(ki, kj));
							ki = kj + 1;
							}
						toTargetList.addElement(toTarget.substring(ki));
						Out.addElement(new xiuping_port(portName, toTargetList, false));
					} // end for k
				
				}
			}
			else
			{
				Model = true;
				model_node = lst1.item(0);
			}

			// Read all processors
			NodeList hst = ((Element) model_node).getElementsByTagName("processor");
			for (int i = 0; i < hst.getLength(); i++)
			{
				Node proc = hst.item(i);
				tmp = ((Element) proc).getAttribute("name");
				tmp.trim();
				// Check for Processor services here
				if (!Processors.contains(tmp))
					Processors.addElement(tmp);
			}

			// Read slots
			NodeList slts = XMLDocument.getElementsByTagName("slot");
			for (int i = 0; i < slts.getLength(); i++)
			{
				Node slotNode = slts.item(i);
				String slot_name = ((Element) slotNode).getAttribute("id");

				slot s = new slot();
				boolean b = true;
				for (int j = 0; j < Slots.getSize(); j++)
				{
					slot ss = (slot) Slots.getElementAt(j);
					tmp = ss.Name;
					tmp.trim();
					if (tmp.equals(slot_name))
					{
						s = ss;
						b = false;
						break;
					}
				}
				if (b)
					return false;

				NodeList interface_lst = ((Element) slotNode).getElementsByTagName("Interface");
				Node child = interface_lst.item(0);
				{
					NodeList inPortList = ((Element) child).getElementsByTagName("in-port");
					NodeList outPortList = ((Element) child).getElementsByTagName("out-port");
					for (int xk = 0; xk < inPortList.getLength(); xk++)
					{
						Node inPort = inPortList.item(xk);
						String portName = ((Element) inPort).getAttribute("name");
						String fromSource = ((Element) inPort).getAttribute("connect-from");
						Vector fromSourceList = new Vector();
						int ki = 0;
						for (;;)
						{
							int kj = fromSource.indexOf(' ', ki);
							if (kj == -1)
								break;
							fromSourceList.addElement(fromSource.substring(ki, kj));
							ki = kj + 1;
						}
						fromSourceList.addElement(fromSource.substring(ki));
						s.Inp.addElement(new xiuping_port(portName, fromSourceList, true));
					} // end for k

					for (int xk = 0; xk < outPortList.getLength(); xk++)
					{
						Node outPort = outPortList.item(xk);
						String portName = ((Element) outPort).getAttribute("name");
						String toTarget = ((Element) outPort).getAttribute("connect-to");
						Vector toTargetList = new Vector();
						int ki = 0;
						toTarget.trim();
						for (;;)
						{
							int kj = toTarget.indexOf(' ', ki);
							if (kj == -1)
								break;
							toTargetList.addElement(toTarget.substring(ki, kj));
							ki = kj + 1;

						}
						toTargetList.addElement(toTarget.substring(ki));
						s.Out.addElement(new xiuping_port(portName, toTargetList, false));
					} // end for k
				}

				NodeList binding_lst = ((Element) slotNode).getElementsByTagName("binding");
				Node binding = binding_lst.item(0);

				NodeList proc_bind_lst = ((Element) binding).getElementsByTagName("processor-binding");

				for (int j = 0; j < proc_bind_lst.getLength(); j++)
				{
					Node proc_node = proc_bind_lst.item(j);
					String tmp1 = ((Element) proc_node).getAttribute("source");
					String tmp2 = ((Element) proc_node).getAttribute("target");
					tmp1.trim();
					tmp2.trim();
					s.SourceHosts.addElement(tmp1);
					s.TargetHosts.addElement(tmp2);
					if (B)
					{
						ArrayList A = new ArrayList();
						A.add(tmp2);
						s.ProcServ.addElement(A);
					}
				}

				NodeList param_bind_lst = ((Element) binding).getElementsByTagName("parameter");
				for (int j = 0; j < param_bind_lst.getLength(); j++)
				{
					Node param_node = param_bind_lst.item(j);
					String tmp1 = ((Element) param_node).getAttribute("name");
					String tmp2 = ((Element) param_node).getAttribute("value");
					tmp1.trim();
					tmp2.trim();
					s.ParamNames.addElement(tmp1);
					s.ParamValues.addElement(tmp2);
				}

				if (!Contains(s.TargetHosts, Processors))
				{
					System.out.println("   Error in component: Processors for slot " + s.Name + " are nor present in component!");
					return false;
				}
			}

			// Read parameters
			NodeList param1 = XMLDocument.getElementsByTagName("Parameter");
			if (param1.getLength() != 0)
			{
				Node param_node = param1.item(0);
				NodeList params = ((Element) param_node).getElementsByTagName("para");
				for (int i = 0; i < params.getLength(); i++)
				{
					Node param = params.item(i);
					String name = ((Element) param).getAttribute("name");
					Params.addElement(name);
				}
			}
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	private boolean Contains(DefaultListModel X, DefaultListModel Y)
	{
		String tmp;

		int n = X.getSize();
		for (int i = 0; i < n; i++)
		{
			tmp = (String) X.getElementAt(i);
			if (!Y.contains(tmp))
				return false;
		}

		return true;
	}

	public boolean readLQML(File F, DefaultListModel X)
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			XMLDocument = documentBuilder.parse(F);

			NodeList lst1 = XMLDocument.getElementsByTagName("lqn-model");
			NodeList lst2 = XMLDocument.getElementsByTagName("lqn-submodel");

			int lst1size = lst1.getLength();
			int lst2size = lst2.getLength();

			if (lst1size > 0)
			{
				Node mainNode = lst1.item(0);
				Name = ((Element) mainNode).getAttribute("name");
				Model = true;
			}
			else if (lst2size > 0)
			{
				Node mainNode = lst2.item(0);
				Name = ((Element) mainNode).getAttribute("name");
				Model = false;
			}
			else
				return false;

			FileName = F.getName();

			// Read External Interface
			Interface.Name = "External-Interface";

			NodeList int_lst = XMLDocument.getElementsByTagName("Interface");
			int intNum = int_lst.getLength();
			for (int i = 0; i < intNum; i++)
			{
				Node intNode = int_lst.item(i);

				Node parentNode = intNode.getParentNode();

				if (parentNode.getNodeName() == "slot")
					continue;

				NodeList inPortList = ((Element) intNode).getElementsByTagName("in-port");
				NodeList outPortList = ((Element) intNode).getElementsByTagName("out-port");
				NodeList repProcList = ((Element) intNode).getElementsByTagName("Replaceable-Processor");

				for (int k = 0; k < inPortList.getLength(); k++)
				{
					Node inPort = inPortList.item(k);
					String portName = ((Element) inPort).getAttribute("name");
					port P = new port(false, portName);
					Interface.Ports.addElement(P);
				} // end for k

				for (int k = 0; k < outPortList.getLength(); k++)
				{
					Node outPort = outPortList.item(k);
					String portName = ((Element) outPort).getAttribute("name");
					port P = new port(true, portName);
					Interface.Ports.addElement(P);
				} // end for k

				for (int k = 0; k < repProcList.getLength(); k++)
				{
					Node repProc = repProcList.item(k);
					String procName = ((Element) repProc).getAttribute("name");
					ArrayList A = new ArrayList();
					A.add(procName);
					ProcServ.addElement(A);
				} // end for k

			}

			// Read Slots

			NodeList slt_lst = XMLDocument.getElementsByTagName("slot");
			int slotsNum = slt_lst.getLength();
			for (int i = 0; i < slotsNum; i++)
			{
				Node slotNode = slt_lst.item(i);

				String tmp = ((Element) slotNode).getAttribute("id");
				slot S = new slot(tmp, X);
				NodeList childNodes = slotNode.getChildNodes();

				for (int j = 0; j < childNodes.getLength(); j++)
				{

					Node child = childNodes.item(j);

					if (child.getNodeName().equals("Interface")) // get ports
					// info
					{
						NodeList inPortList = ((Element) child).getElementsByTagName("in-port");
						NodeList outPortList = ((Element) child).getElementsByTagName("out-port");

						for (int k = 0; k < inPortList.getLength(); k++)
						{
							Node inPort = inPortList.item(k);
							String portName = ((Element) inPort).getAttribute("name");
							port P = new port(false, portName);
							S.Ports.addElement(P);
						} // end for k

						for (int k = 0; k < outPortList.getLength(); k++)
						{
							Node outPort = outPortList.item(k);
							String portName = ((Element) outPort).getAttribute("name");
							port P = new port(true, portName);
							S.Ports.addElement(P);
						} // end for k
					} // end if Interface
				}

				Slots.addElement(S);
			}

			for (int i = 0; i < X.size(); i++)
			{
				String tmp = (String) X.getElementAt(i);
				NonMembers.addElement(tmp);
			}

			if (!readHosts(true))
				return false;
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	public boolean writeComponent(PrintWriter x)
	{
		String s;
		boolean ans;
		slot S;
		try
		{
			int c = Slots.getSize();
			int d = ProcServ.getSize();
			int i, j;

			System.out.println("   Writing component " + Name + " [" + FileName + "] with " + c + " slots");

			x.print(Name + " " + FileName + " " + d + " " + c);

			int n = Members.getSize();
			for (i = 0; i < n; i++)
			{
				s = (String) Members.getElementAt(i);
				x.print(" " + s);
			}
			x.println("");

			for (i = 0; i < d; i++)
			{
				ArrayList v = (ArrayList) ProcServ.get(i);
				n = v.size();
				for (j = 0; j < n; j++)
				{
					s = (String) v.get(j);
					if (j != 0)
						x.print(" ");
					x.print(s);
				}
				x.println("");
			}

			ans = Interface.write_slot(x);
			if (!ans)
				return false;

			for (d = 0; d < c; d++)
			{
				S = (slot) Slots.getElementAt(d);
				ans = S.write_slot(x);
				if (!ans)
					return false;
			}
		}

		catch (Exception e)
		{
			System.out.println("   Error in writing component: Process stopped!");
			return false;
		}

		return true;
	}
	
	public String toString()
	{
		String z = new String(Name + " [" + FileName + "]");
		return z;
	}
}
