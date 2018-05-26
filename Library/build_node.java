// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Build Component Data Structure - build_node.java
//

import java.io.File;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

class build_node
{
	public slot Slot;
	public component Component;
	public component ParentComponent;

	public boolean Root = false;
	public boolean Binding = false;

	xiuping_slot BindInfo;

	build_node Parent;

	public DefaultListModel BindList = new DefaultListModel();

	public build_node()
	{
		Root = true;
		return;
	}

	public build_node(component C)
	{
		Root = true;
		Parent = new build_node();
		Slot = new slot();
		Slot.Name = new String("Start");
		Component = C;
		ParentComponent = new component();
		ParentComponent.Name = new String("Root");
		Binding = true;

		int j = C.Slots.getSize();
		BindList = new DefaultListModel();
		BindList.ensureCapacity(j);
		for(int i=0;i<j;i++)
		{
			slot s = (slot) C.Slots.getElementAt(i);
			build_node b = new build_node(C,s,this);
			BindList.addElement(b);
		}

		return;
	}

	public build_node(component C,slot S,build_node B)
	{
		Root = false;
		Parent = B;
		Component = new component();
		ParentComponent = C;
		Slot = S;
		Binding = false;
		return;
	}

	public boolean IterCheck(component C,build_node B)
	{

			if (C == B.Component) return true;
			else if (B.Root) return false; else return IterCheck(C,B.Parent);
	}

	public boolean Check(component C)
	{
			boolean b = IterCheck(C,Parent);
			return b;
	}

	public void bind(component C,xiuping_slot X)
	{
		Component = C;
		Binding = true;
		BindInfo = X;
		int j = C.Slots.getSize();
		BindList = new DefaultListModel();
		BindList.ensureCapacity(j);
		for(int i=0;i<j;i++)
		{
			slot s = (slot) C.Slots.getElementAt(i);
			build_node b = new build_node(C,s,this);
			BindList.addElement(b);
		}
		return;
	}

	public void unbind()
	{
		BindList.clear();
		Binding = false;
		return;
	}
	
	public Document getXML(File f) throws Exception
	{
		File F = new File(f, Component.FileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document X = documentBuilder.parse(F);
		return X;
	}

	public String toString()
	{
		String tmp1 = Slot.Name;
		String tmp2 = Component.Name;
		String tmp;
		if (Binding) tmp = new String(tmp1+" -> "+tmp2);
		else tmp = new String(tmp1);
		return tmp;
	}
}

