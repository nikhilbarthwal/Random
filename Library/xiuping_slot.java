// THIS PART OF THE CODE IS MODIFIED VERSION OF THE CODE
// DEVELOPED BY XIUPING WU FOR HER TOOL LQ-COMPOSER

import java.util.Vector;
import javax.swing.*;

public class xiuping_slot
{
	public int bindTarget; // Nikhil: Changed from String to number
	public String id;

	// Input/Output ports of slots: elements in inPort or outPort are objects of Port
	public Vector inPort, outPort;

	// Input/Output ports of slots: elements in inPort or outPort are objects of Port
	public Vector inCPort, outCPort;
	
	// boolean bProcessed is the flag which indicates if this slot has been
	// processed
	private boolean bProcessed;

	private Vector sourceProcessors, targetProcessors; // sourceProcessor[i] is
	// replaced by
	// targetProcessor[i]

	private Vector paramNames, paramValues; // paramName[i] is assigned value of
	// paramValue[i]

	private Vector bindSourcePorts, bindTargetPorts; // bindSourcePorts[i]

	// connects to
	// bindTargetPorts[i].
	// Elements are Strings

	// element in bindSourcePorts and bindTargetPorts are the port names

	public xiuping_slot(String id, int bindtarget, DefaultListModel A, DefaultListModel B, DefaultListModel C, DefaultListModel D, Vector IP, Vector OP,  Vector IC, Vector OC)
	{
		this.bindTarget = bindtarget;
		this.id = id;

		inPort = new Vector(IP);
		outPort = new Vector(OP);

		inCPort = new Vector(IC);
		outCPort = new Vector(OC);

		int i, j;
		String tmp;

		i = A.getSize();
		sourceProcessors = new Vector(i);
		for (j = 0; j < i; j++)
		{
			tmp = (String) A.getElementAt(j);
			sourceProcessors.add(tmp);
		}

		i = B.getSize();
		targetProcessors = new Vector(i);
		for (j = 0; j < i; j++)
		{
			tmp = (String) B.getElementAt(j);
			targetProcessors.add(tmp);
		}

		i = C.getSize();
		paramNames = new Vector(i);
		for (j = 0; j < i; j++)
		{
			tmp = (String) C.getElementAt(j);
			paramNames.add(tmp);
		}

		i = D.getSize();
		paramValues = new Vector(i);
		for (j = 0; j < i; j++)
		{
			tmp = (String) D.getElementAt(j);
			paramValues.add(tmp);
		}

		bProcessed = false;
		
		return;
	}

	public void addMatching(DefaultListModel X, DefaultListModel Y)
	{
		String tmp1;
		String tmp2;

		int i = X.getSize();
		bindSourcePorts = new Vector(i);
		bindTargetPorts = new Vector(i);
		for (int j = 0; j < i; j++)
		{
			tmp1 = (String) X.getElementAt(j);
			tmp2 = (String) Y.getElementAt(j);
			bindSourcePorts.add(tmp1);
			bindTargetPorts.add(tmp2);
		}
		
		return;
	}

	public xiuping_slot()
	{
		return;
	}

	public void setBindSourcePorts(Vector ports)
	{
		bindSourcePorts = ports;
	}

	public Vector getBindSourcePorts()
	{
		return bindSourcePorts;
	}

	public void setBindTargetPorts(Vector ports)
	{
		bindTargetPorts = ports;
	}

	public Vector getBindTargetPorts()
	{
		return bindTargetPorts;
	}

	public Vector getInPort()
	{
		return inPort;
	}

	public Vector getOutPort()
	{
		return outPort;
	}

	public void setSourceProcessors(Vector processors)
	{
		sourceProcessors = processors;
	}

	public void setTargetProcessors(Vector processors)
	{
		targetProcessors = processors;
	}

	public Vector getSourceProcessors()
	{
		return sourceProcessors;
	}

	public Vector getTargetProcessors()
	{
		return targetProcessors;
	}

	public void setParamNames(Vector names)
	{
		paramNames = names;
	}

	public Vector getParamNames()
	{
		return paramNames;
	}

	public void setParamValues(Vector values)
	{
		paramValues = values;
	}

	public Vector getParamValues()
	{
		return paramValues;
	}

	public int getBindTarget() // Nikhil: Changed from String to number
	{
		return bindTarget;
	}

	public String getId()
	{
		return id;
	}

	public xiuping_port getPortByName(String portName)
	{
		xiuping_port rePort;
		for (int i = 0; i < inPort.size(); i++)
		{
			rePort = (xiuping_port) inPort.elementAt(i);
			if (rePort.getName().equals(portName))
				return rePort;
		}
		for (int i = 0; i < outPort.size(); i++)
		{
			rePort = (xiuping_port) outPort.elementAt(i);
			if (rePort.getName().equals(portName))
				return rePort;
		}
		return null;

	}

	public Vector getDest1(String s)
	{
		int n = inCPort.size();
		Vector v = new Vector();
		
		for(int i=0;i<n;i++)
		{
			xiuping_port p = (xiuping_port) inCPort.get(i);
			if (s.equals(p.getName()))
			{
				v=p.getToOrFrom();
				i=n;
			}			
		}
		return v;
	}

	public Vector getDest2(String s)
	{
		int n = outPort.size();
		Vector v = new Vector();
		
		for(int i=0;i<n;i++)
		{
			xiuping_port p = (xiuping_port) outPort.get(i);
			if (s.equals(p.getName()))
			{
				v=p.getToOrFrom();
				i=n;
			}			
		}
		return v;
	}

	public String toString()
	{
		String s;
		s = "*** Slot id:" + id + "\n";
		for (int i = 0; i < inCPort.size(); i++)
		{
			s += ((xiuping_port) inCPort.elementAt(i)).toString() + "\n";
		}
		
		for (int i = 0; i < outPort.size(); i++)
		{
			s += ((xiuping_port) outPort.elementAt(i)).toString() + "\n";
		}
		
		s = "C\n";
		for (int i = 0; i < inPort.size(); i++)
		{
			s += ((xiuping_port) inPort.elementAt(i)).toString() + "\n";
		}
		
		for (int i = 0; i < outPort.size(); i++)
		{
			s += ((xiuping_port) outPort.elementAt(i)).toString() + "\n";
		}
		
		s += "\n";
		
		for (int i = 0; i < sourceProcessors.size(); i++)
		{
			s += ((String) sourceProcessors.elementAt(i)) + " - ";
			s += ((String) targetProcessors.elementAt(i)).toString() + "\n";
		}
		
		s += "\n";

		for (int i = 0; i < sourceProcessors.size(); i++)
		{
			s += ((String) bindSourcePorts.elementAt(i)) + " - ";
			s += ((String) bindTargetPorts.elementAt(i)) + "\n";
		}

		s += "\n*****\n";
		

		return s;
	}
} // end of class Slot
