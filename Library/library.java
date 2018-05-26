// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Dec 17,2003
//
// ******************************************************
//
//    Main File - library.java
//

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.w3c.dom.*;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;


public class library  extends JFrame implements ActionListener,	ListSelectionListener,DocumentListener
{

	File Lib_Dir;

	JButton LibNew   = new JButton("Clear Lib");
	JButton LibLoad  = new JButton("Load Lib");
	JButton LibMerge = new JButton("Merge Lib");
	JButton LibSave  = new JButton("Save Lib");
	JButton LibExit  = new JButton("Exit");

	JButton ClassAdd    = new JButton("Add Group");
	JButton ClassEdit   = new JButton("Edit Group");
	JButton ClassDelete = new JButton("Delete Group");

	JButton ComponentAdd            = new JButton("Add Component");
	JButton ComponentDelete         = new JButton("Delete Component");
	JButton ComponentSearch         = new JButton("Search Library");
	JButton ComponentClassification = new JButton("Group Settings");
	JButton ComponentSlots          = new JButton("Slots Settings");
	JButton ComponentPorts          = new JButton("Port Settings");
	JButton ComponentProcessors     = new JButton("Processor Settings");
	JButton ComponentBuild          = new JButton("Build Component");

	JTextField ClassName = new JTextField(20);

	JPanel ButtonsPanel = new JPanel(new GridLayout(1,5,5,5));

	JPanel ClassPanel        = new JPanel(new BorderLayout(0,10));
	JPanel ComponentPanel    = new JPanel(new BorderLayout(0,10));

	JPanel ClassPanelControl     = new JPanel(new GridLayout(1,3,5,0));
	JPanel ComponentPanelControl = new JPanel(new GridLayout(8,1,0,5));

	JTabbedPane Pane = new JTabbedPane();

	DefaultListModel ClassList     = new DefaultListModel();
	DefaultListModel ComponentList = new DefaultListModel();
	DefaultListModel ResultsList     = new DefaultListModel();

	JList ClassJList     = new JList(ClassList);
	JList ComponentJList = new JList(ComponentList);

	JScrollPane ClassScroll     = new JScrollPane(ClassJList);
	JScrollPane ComponentScroll = new JScrollPane(ComponentJList);

	JLabel ClassMsg      = new JLabel("Enter Group name: ");
	JPanel ClassMsgPanel = new JPanel(new BorderLayout(0,10));

	JLabel Msg1     = new JLabel("                                                                             List of Groups   ");
	JLabel Msg2     = new JLabel("                                                                         List of Components   ");
	JLabel Msg      = new JLabel("RADS Labs - Department of Systems & Computer Engineering, Carleton University; Ottawa CANADA");

	String SportPortEdit;
	String SslotPortEdit;
	String SslotSlotEdit;

	port portPortEdit;

	build_node BuildNode;


	slot slotPortEdit;
	slot slotSlotEdit;
	
	ArrayList procEdit;
	
	component buildComponent;
	component classifyComponent;
	component slotlistComponent;
	component portlistComponent;
	component proclistComponent;

	xiuping_asm XIUPING = new xiuping_asm();

	build        Build        = new build();
	search       Search       = new search();
	classify     Classify     = new classify();
	slotlist     SlotList     = new slotlist();
	slotedit     SlotEdit     = new slotedit();
	portlist     PortList     = new portlist();
	portedit     PortEdit     = new portedit();
	proclist     ProcList     = new proclist();
	procedit     ProcEdit     = new procedit();
	buildoption  BuildOption  = new buildoption();
	searchresult SearchResult = new searchresult();

	public library(File f1,File f2)
	{
		super();
		Lib_Dir = f1;

		ButtonsPanel.add(LibNew);
		ButtonsPanel.add(LibLoad);
		ButtonsPanel.add(LibMerge);
		ButtonsPanel.add(LibSave);
		ButtonsPanel.add(LibExit);


		ClassMsgPanel.add(ClassMsg,BorderLayout.WEST);
		ClassMsgPanel.add(ClassName,BorderLayout.CENTER);
		ClassMsgPanel.add(Msg1,BorderLayout.SOUTH);

		ClassPanel.add(ClassMsgPanel,BorderLayout.NORTH);
		ClassPanel.add(ClassScroll,BorderLayout.CENTER);
		ClassPanel.add(ClassPanelControl,BorderLayout.SOUTH);

		ComponentPanel.add(Msg2,BorderLayout.NORTH);
		ComponentPanel.add(ComponentScroll,BorderLayout.CENTER);
		ComponentPanel.add(ComponentPanelControl,BorderLayout.EAST);

		ClassPanelControl.add(ClassAdd);
		ClassPanelControl.add(ClassEdit);
		ClassPanelControl.add(ClassDelete);

		ComponentPanelControl.add(ComponentAdd);
		ComponentPanelControl.add(ComponentDelete);
		ComponentPanelControl.add(ComponentSearch);
		ComponentPanelControl.add(ComponentClassification);
		ComponentPanelControl.add(ComponentSlots);
		ComponentPanelControl.add(ComponentPorts);
		ComponentPanelControl.add(ComponentProcessors);
		ComponentPanelControl.add(ComponentBuild);

		Msg.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
		Pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ButtonsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ClassPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ComponentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		LibNew.addActionListener(this);
		LibLoad.addActionListener(this);
		LibMerge.addActionListener(this);
		LibSave.addActionListener(this);
		LibExit.addActionListener(this);

		ClassAdd.addActionListener(this);
		ClassEdit.addActionListener(this);
		ClassDelete.addActionListener(this);

		ComponentAdd.addActionListener(this);
		ComponentDelete.addActionListener(this);
		ComponentSearch.addActionListener(this);
		ComponentClassification.addActionListener(this);
		ComponentSlots.addActionListener(this);
		ComponentPorts.addActionListener(this);
		ComponentProcessors.addActionListener(this);
		ComponentBuild.addActionListener(this);

		Pane.add("           Groups           ",ClassPanel);
		Pane.add("   Components   ",ComponentPanel);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(10,0));
		content.add(ButtonsPanel,BorderLayout.NORTH);
		content.add(Pane,BorderLayout.CENTER);
		content.add(Msg,BorderLayout.SOUTH);

		ClassJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ClassJList.setVisibleRowCount(10);
		ClassJList.addListSelectionListener(this);

		ComponentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ComponentJList.setVisibleRowCount(10);
		ComponentJList.addListSelectionListener(this);

		ClassName.addActionListener(this);
		ClassName.getDocument().addDocumentListener(this);

		if (f2==null)
		{
			setTitle("Component Library");
		}
		else
		{
			boolean ans;
			ans = this.LoadFile(f2);
			String F = f2.getName();
			if (ans)
			{
				setTitle("Component Library - ["+F+"]");
				System.out.println("\n * Loaded library "+F);
			}
			else
			{
				System.out.println("\n * Failed to load library "+F);
				Msg.setText("ERROR: Unable to load a component file "+F);
				setTitle("Component Library");
			}
		}

		ControlPad(0,0);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.out.println("\nThank you and Good Bye!!!\n");
					System.exit(0);
				}
			});

		setSize(603,420);
		setVisible(true);
		setLocation(50,50);

		Build.L        = this;
		Search.L       = this;
		Classify.L     = this;
		SlotList.L     = this;
		SlotEdit.L     = this;
		PortList.L     = this;
		PortEdit.L     = this;
		ProcList.L     = this;
		ProcEdit.L     = this;
		BuildOption.L  = this;
		SearchResult.L = this;

		Build.setLocation(200,200);
		Search.setLocation(200,200);
		Classify.setLocation(200,200);
		SlotList.setLocation(200,200);
		SlotEdit.setLocation(350,350);
		PortList.setLocation(200,200);
		PortEdit.setLocation(350,350);
		ProcList.setLocation(200,200);
		ProcEdit.setLocation(350,350);
		BuildOption.setLocation(350,350);
		SearchResult.setLocation(350,350);


	} // End of Contructor for Library

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == LibNew)
		{
			ClassList.clear();
			ComponentList.clear();
			System.out.println("\n * Library cleared");
			return;
		}

		if (source == LibLoad)
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Load Library ...");
			fc.setApproveButtonText("Load");

			int r = fc.showOpenDialog(this);

			if (r == JFileChooser.APPROVE_OPTION)
			{
				File FL = fc.getSelectedFile();
				String F = FL.getName();
				System.out.println("\n * Loading Library file "+F+" ...");
				boolean ans = LoadFile(FL);

				if (ans)
				{
					System.out.println("   Library file loaded");
					Msg.setText("Library file "+F+" loaded");
					setTitle("Component Library - ["+F+"]");
				}
				else
				{
					System.out.println("   Unable to load Library file");
					Msg.setText("ERROR: Unable to load library file "+F);
				}
			}
			return;
		}

		if (source == LibMerge)
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Merge Library ...");
			fc.setApproveButtonText("Merge");

			int r = fc.showOpenDialog(this);

			if (r == JFileChooser.APPROVE_OPTION)
			{
				File FL = fc.getSelectedFile();
				String F = FL.getName();
				System.out.println("\n * Merging Library file "+F+" ...");
				boolean ans = MergeFile(FL);

				if (ans)
				{
					System.out.println("   Library file merged");
					Msg.setText("Library file "+F+" merged");
				}
				else
				{
					System.out.println("   Unable to merge library file");
					Msg.setText("ERROR: Unable to merge library file "+F);
				}
			}
			return;
		}

		if (source == LibSave)
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Save Library ...");
			fc.setApproveButtonText("Save");

			int r = fc.showOpenDialog(this);

			if (r == JFileChooser.APPROVE_OPTION)
			{
				File FL = fc.getSelectedFile();
				String F = FL.getName();
				System.out.println("\n * Saving Library file "+F+" ...");
				boolean ans = SaveFile(FL);

				if (ans)
				{
					System.out.println("   Library file saved");
					Msg.setText("Library file "+F+" saved");
					setTitle("Component Library - ["+F+"]");
				}
				else
				{
					System.out.println("   Unable to save Library file");
					Msg.setText("ERROR: Unable to save library file "+F);
					setTitle("Component Library");
				}
			}
			return;
		}

		if (source == LibExit)
		{
			System.out.println("\n\nThank you and Good Bye !!!\n");
			System.exit(0);
		}

		if ((source == ClassAdd)||(source == ClassName))
		{
			// Code for adding a class

			String name = ClassName.getText();

			System.out.println("\n * Adding Group " + name + " ...");

			if ( name.equals(""))
			{
				System.out.println("   No group name entered");
				Msg.setText("ERROR: No group name entered");
			}
			else if (ClassList.contains(name))
			{
				ClassName.setText("");
				System.out.println("   Group " + name + " already present in group list");
				Msg.setText("ERROR: Group " + name + " already present in group list");
				ControlPad(-1,-1);
			}
			else
			{
			    int index = ClassList.getSize();
				int d = ComponentList.getSize();

				if (index==-1) return;
				component tmp;

			    ClassList.addElement(name);

			    ClassName.requestFocusInWindow();
				ClassName.setText("");

				if (d>0)
				{
					for(int i=0;i<d;i++)
					{
						tmp = (component) ComponentList.getElementAt(i);
						tmp.addClass(name);
					}
				}

				ControlPad(index,-1);
				System.out.println("   Group "+name+" added");
				Msg.setText("Group " + name + " added!");
			}
			return;
		}

		if (source == ClassEdit)
		{
			int index = ClassJList.getSelectedIndex();
			int d = ComponentList.size();

			if (index==-1) return;
			
			String oldname = (String) ClassJList.getSelectedValue();
			String newname = ClassName.getText();
			System.out.println("\n * Editing Group "+oldname+" to "+newname+" ...");

			if (newname.equals(""))
			{
				ControlPad(-1,-1);
				System.out.println("   Group name is empty");
				Msg.setText("ERROR: Group  name is empty");
				return;
			}
			else if (ClassList.contains(newname))
			{
				ClassName.setText("");
				ControlPad(-1,-1);
				System.out.println("   Group " + newname + " already present in class list");
				Msg.setText("ERROR: Group " + newname + " already present in class list");
			}
			else
			{
				ClassList.removeElementAt(index);
				ClassList.insertElementAt(newname,index);
				ClassName.setText("");

				ClassJList.setSelectedIndex(index);
			    ClassJList.ensureIndexIsVisible(index);

				for(int i=0;i<d;i++)
					((component) ComponentList.getElementAt(i)).editClass(oldname,newname);

				ControlPad(index,-1);
				System.out.println("   Group "+oldname+" changed to "+newname);
				Msg.setText("Group " + oldname + " changed to " + newname + "!");
			}
			return;
		}

		if (source == ClassDelete)
		{
			int index = ClassJList.getSelectedIndex();
			if (index==-1) return;

			String name = (String) ClassList.getElementAt(index);

			ClassList.remove(index);

			int d = ComponentList.getSize();

			component tmp;

			if (d>0)
			{
				for(int i=0;i<d;i++)
				{
					tmp = (component) ComponentList.getElementAt(i);
					tmp.removeClass(name);
				}
			}

			int size = ClassList.getSize();
			if (size == index) index--;
			ControlPad(index,-1);

			System.out.println("   Group "+name+" deleted");
			Msg.setText("Group " + name + " deleted!");
			return;
		}

		if (source == ComponentAdd)
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Add Component ...");
			fc.setApproveButtonText("Add");

			int r = fc.showOpenDialog(this);

			if (r == JFileChooser.APPROVE_OPTION)
			{
				File F = fc.getSelectedFile();
				String f = F.getName();
				System.out.println("\n * Add component file "+f+" ...");
				component tmp = new component();

				try
				{
					boolean ans = tmp.readLQML(F,ClassList);
					if (ans)
					{
						int index = ComponentList.getSize();
						ComponentList.addElement(tmp);
						ControlPad(-1,index);

						System.out.println("   Component " + tmp + " added in Library");
						Msg.setText("Component " + tmp + " added!");
					}
					else
					{
						System.out.println("   Unable to load Component in file "+f);
						Msg.setText("ERROR: Failed to read/interpret component file");
					}
				}
				catch(Exception ex)
				{
					System.out.println("   Unable to load Component in file "+f);
					Msg.setText("ERROR: Failed to read/interpret component file");
				}
			}
			return;
		}

		if (source == ComponentDelete)
		{
			int index = ComponentJList.getSelectedIndex();
			component name = (component) ComponentJList.getSelectedValue();

			if (index==-1) return;
			ComponentList.remove(index);

			int size = ComponentList.getSize();
			if (size == index) index--;
			ControlPad(-1,index);

			System.out.println("\n * Component "+name+" deleted");
			Msg.setText("Component "+name+" deleted!");

			return;
		}

		if (source == ComponentSlots)
		{
			int index = ComponentJList.getSelectedIndex();
			if (index==-1) return;

			slot s;

			slotlistComponent = (component) ComponentList.getElementAt(index);

			System.out.println("\n * Editing Slot settings for component "+slotlistComponent+" ...");
			SlotList.setTitle("Edit Slot settings - "+slotlistComponent);
			setEnabled(false);
			SlotList.SlotList.clear();

			int c = slotlistComponent.Slots.getSize();
			for(int i=0;i<c;i++)
			{
				s = (slot) slotlistComponent.Slots.getElementAt(i);
				String tmp = new String(slotlistComponent.Name+"."+s.Name+" ["+slotlistComponent.FileName+"]");
				SlotList.SlotList.addElement(tmp);
			}

			if (c>0)
			{
				SlotList.SlotJList.setSelectedIndex(0);
			    SlotList.SlotJList.ensureIndexIsVisible(0);
			}

			SlotList.setVisible(true);
			SlotList.requestFocus();
			return;
		}

		if (source == ComponentPorts)
		{
			String tmp;

			int index = ComponentJList.getSelectedIndex();
			if (index==-1) return;

			int c,d,i,j;
			slot s = new slot();
			port p = new port();

			portlistComponent = (component) ComponentList.getElementAt(index);

			System.out.println("\n * Editing Port settings for component "+portlistComponent+" ...");
			PortList.setTitle("Edit Port settings - "+portlistComponent);
			setEnabled(false);

			PortList.PortList.clear();
			PortList.SlotIndexList.clear();
			PortList.PortIndexList.clear();


			s = portlistComponent.Interface;
			c = s.Ports.getSize();
			for(i=0;i<c;i++)
			{
				p = (port) s.Ports.getElementAt(i);

				if (p.Type) tmp = new String("out "+portlistComponent.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" providing services "+p.Services);
				else tmp = new String("in "+portlistComponent.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" requiring services "+p.Services);

				PortList.PortList.addElement(tmp);
				PortList.SlotIndexList.addElement(new Integer(-1));
				PortList.PortIndexList.addElement(new Integer(i));
			}

			c = portlistComponent.Slots.getSize();
			for(i=0;i<c;i++)
			{
				s = (slot) portlistComponent.Slots.getElementAt(i);
				d = s.Ports.getSize();
				for(j=0;j<d;j++)
				{
					p = (port) s.Ports.getElementAt(j);

					if (p.Type) tmp = new String("out "+portlistComponent.Name+"."+s.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" providing services "+p.Services);
					else tmp = new String("in "+portlistComponent.Name+"."+s.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" requiring services "+p.Services);

					PortList.PortList.addElement(tmp);
					PortList.SlotIndexList.addElement(new Integer(i));
					PortList.PortIndexList.addElement(new Integer(j));
				}
			}

			if (c>0)
			{
				PortList.PortJList.setSelectedIndex(0);
				PortList.PortJList.ensureIndexIsVisible(0);
			}

			PortList.setVisible(true);
			PortList.requestFocus();
			return;
		}

		if (source == ComponentClassification)
		{
			int index = ComponentJList.getSelectedIndex();
			if (index==-1) return;

			String tmp;

			classifyComponent = (component) ComponentList.getElementAt(index);
			setEnabled(false);

			System.out.println("\n * Editing Group setting for component "+classifyComponent+" ...");

			Classify.setTitle("Edit Group settings - "+classifyComponent);
			Classify.Members.clear();
			Classify.NonMembers.clear();

			int c= classifyComponent.Members.getSize();
			int d= classifyComponent.NonMembers.getSize();

			if (c>0)
			{
				Classify.Bremove.setEnabled(true);

				for(int i=0;i<c;i++)
				{
					tmp = (String) classifyComponent.Members.getElementAt(i);
					Classify.Members.addElement(tmp);
				}

				Classify.MembersJList.setSelectedIndex(0);
				Classify.MembersJList.ensureIndexIsVisible(0);
			}
			else Classify.Bremove.setEnabled(false);


			if (d>0)
			{
				Classify.Badd.setEnabled(true);

				for(int i=0;i<d;i++)
				{
					tmp = (String) classifyComponent.NonMembers.getElementAt(i);
					Classify.NonMembers.addElement(tmp);
				}

				Classify.NonMembersJList.setSelectedIndex(0);
				Classify.NonMembersJList.ensureIndexIsVisible(0);
			}
			else Classify.Badd.setEnabled(false);

			Classify.setVisible(true);
			Classify.requestFocus();

			return;
		}

		if (source == ComponentSearch)
		{
			// Add code editing port in component

			String tmp;
			int c,d,m,n,i,j,k;

			System.out.println("\n * Searching for components ...");
			setEnabled(false);

			Search.ClassMembers.clear();
			Search.ClassNonMembers.clear();

			Search.ServiceMembers.clear();
			Search.ServiceNonMembers.clear();

			c= ClassList.getSize();
			Search.BClassRemove.setEnabled(false);

			if (c==0) Search.BClassAdd.setEnabled(false);
			else
			{
				Search.BClassAdd.setEnabled(true);

				for(d=0;d<c;d++)
				{
					tmp = (String) ClassList.getElementAt(d);
					Search.ClassNonMembers.addElement(tmp);
				}
			}

			c = ComponentList.getSize();

			for(d=0;d<c;d++)
			{
				component C = (component) ComponentList.getElementAt(d);

				m = C.Interface.Ports.getSize();
				for(j=0;j<m;j++)
				{
					port P = (port) C.Interface.Ports.getElementAt(j);
					n = P.Services.getSize();
					for(k=0;k<n;k++)
					{
						tmp = (String) P.Services.getElementAt(k);
						Search.addService(tmp);
					}
				}

				n = C.Slots.getSize();
				for(i=0;i<n;i++)
				{
					slot S = (slot) C.Slots.getElementAt(i);
					m = S.Ports.getSize();
					for(j=0;j<m;j++)
					{
						port P = (port) S.Ports.getElementAt(j);
						n = P.Services.getSize();
						for(k=0;k<n;k++)
						{
							tmp = (String) P.Services.getElementAt(k);
							Search.addService(tmp);
						}
					}
				}
			}

			if (Search.ServiceMembers.isEmpty()) Search.BServiceRemove.setEnabled(false);
			else  Search.BServiceRemove.setEnabled(true);

			if (Search.ServiceNonMembers.isEmpty()) Search.BServiceAdd.setEnabled(false);
			else  Search.BServiceAdd.setEnabled(true);

			Search.setVisible(true);
			Search.requestFocus();
		}

		if (source == ComponentProcessors)
		{
			String tmp,tmp2;
			int c,d;
			
			slot S = new slot();

			int index = ComponentJList.getSelectedIndex();
			if (index==-1) return;

			proclistComponent = (component) ComponentList.getElementAt(index);
			
			System.out.println("\n * Editing Processor settings for component "+proclistComponent+" ...");
			ProcList.setTitle("Edit Processor settings - "+proclistComponent);
			setEnabled(false);
			
			ProcList.ProcList.clear();
			ProcList.SlotIndexList.clear();
			ProcList.ProcIndexList.clear();

			ArrayList s = new ArrayList();
			c = proclistComponent.ProcServ.getSize();
			for(int i=0;i<c;i++)
			{
				s = new ArrayList((ArrayList) proclistComponent.ProcServ.getElementAt(i));
				tmp2 = (String) s.get(0);
				s.remove(0);
				tmp = new String(tmp2 + " requiring services "+s);
				ProcList.ProcList.addElement(tmp);
				ProcList.SlotIndexList.addElement(new Integer(-1));
				ProcList.ProcIndexList.addElement(new Integer(i));
			}

			c = proclistComponent.Slots.size();
			for(int i=0;i<c;i++)
			{
				
				S = (slot) proclistComponent.Slots.getElementAt(i);
				d = S.ProcServ.size();
				for(int j=0;j<d;j++)
				{
					s = new ArrayList((ArrayList) S.ProcServ.getElementAt(j));
					tmp2 = new String(S.Name + "." + (String) s.get(0));
					s.remove(0);
					tmp = new String(tmp2 + " providing services "+s);
					ProcList.ProcList.addElement(tmp);
					ProcList.SlotIndexList.addElement(new Integer(i));
					ProcList.ProcIndexList.addElement(new Integer(j));
				}
			}


			if (ProcList.ProcList.size()>1)
			{
				ProcList.ProcJList.setSelectedIndex(0);
				ProcList.ProcJList.ensureIndexIsVisible(0);
			}

			ProcList.setVisible(true);
			ProcList.requestFocus();
			return;
		}
		
		if (source == ComponentBuild)
		{
			int index = ComponentJList.getSelectedIndex();
			if (index == -1) return;

			buildComponent = (component) ComponentList.getElementAt(index);
			System.out.println("\n * Building component "+buildComponent+" ...");

			int p = buildComponent.Slots.getSize();
			if (p == 0)
			{
				System.out.println("   No slots to build for Component "+buildComponent);
				Msg.setText("   No slots to build for Component "+buildComponent);
				return;
			}
			else
			{
				int m = ComponentList.getSize();
				for(int i=0;i<m;i++)
				{
					slot S;
					component C = (component) ComponentList.getElementAt(i);
					int n = C.Slots.getSize();
					for(int j=0;j<n;j++)
					{
						S = (slot) C.Slots.getElementAt(j);
						S.search(ComponentList);
					}
				}

				setEnabled(false);

				Build.setTitle("Build Component  - "+buildComponent);

				if (buildComponent.Model) Build.ModelMsg.setText("Model Name:");
				else Build.ModelMsg.setText("Sub-Model Name:");

				Build.ModelName.setText(buildComponent.Name+"_");
				Build.setTitle("Build Component  - "+buildComponent);
				build_node BuildNode = new build_node(buildComponent);
				DefaultTreeModel model = (DefaultTreeModel) Build.Members.getModel();
				DefaultMutableTreeNode R = new DefaultMutableTreeNode(BuildNode);
				DefaultMutableTreeNode r;
				model.setRoot(R);
				Build.Members.setSelectionRow(0);
				Build.SelectedNode = R;

				Build.ButtonMsg.setText("Component "+buildComponent+" is being build");

				slot S;
				int n = buildComponent.Slots.getSize();
				for(int i=0;i<n;i++)
				{
					S = (slot) buildComponent.Slots.getElementAt(i);
					build_node b = new build_node(buildComponent,S,BuildNode);
					r = new DefaultMutableTreeNode(b);
					model.insertNodeInto(r,R,i);
				}
				TreePath Path = Build.Members.getSelectionPath();
				Build.Members.expandPath(Path);
				Build.Members.setSelectionPath(Path);
				Build.setVisible(true);
				Build.requestFocus();
			}
			return;
		}
	}

	private boolean LoadFile(File F)
	{
		int c=0,d=0;

		String x;
		component tmp = new component();

		DefaultListModel trial1 = new DefaultListModel();
		DefaultListModel trial2 = new DefaultListModel();

		try
		{

			BufferedReader In = new BufferedReader(new FileReader(F));
			x=In.readLine();

			int p = x.indexOf(' ');

			Integer C = Integer.valueOf(x.substring(0,p));
			c=C.intValue();

			Integer D = Integer.valueOf(x.substring(p+1));
			d=D.intValue();

			boolean ans = true;

			if (c>0)
				for(int i=0;i<c;i++)
				{
					x=In.readLine();
					x.trim();
					trial1.addElement(x);
				}

			if (d>0)
				for(int i=0;i<d;i++)
				{
					tmp = new component();
					ans = tmp.readComponent(In,trial1,Lib_Dir);
					if (!(ans)) return false;
					trial2.addElement(tmp);
				}

			In.close();
		}
		catch(Exception e) { return false; }

		ClassList.clear();
		ComponentList.clear();

		if (c>0)
			for(int i=0;i<c;i++)
			{
				x = (String) trial1.getElementAt(i);
				ClassList.addElement(x);
			}

		if (d>0)
			for(int i=0;i<d;i++)
			{
				tmp = (component) trial2.getElementAt(i);
				ComponentList.addElement(tmp);
			}

		ControlPad(0,0);
		return true;
	}

	private boolean MergeFile(File F)
	{
		int c=0,d=0,cc=0,dd=0;

		cc = ClassList.getSize();
		dd = ComponentList.getSize();

		String x;
		component tmp = new component();

		DefaultListModel trial1 = new DefaultListModel();
		DefaultListModel trial2 = new DefaultListModel();

		try
		{
			BufferedReader In = new BufferedReader(new FileReader(F));
			x=In.readLine();

			int p = x.indexOf(' ');

			Integer C = Integer.valueOf(x.substring(0,p));
			c=C.intValue();

			Integer D = Integer.valueOf(x.substring(p+1));
			d=D.intValue();

			boolean ans = true;

			if (c>0)
				for(int i=0;i<c;i++)
				{
					x=In.readLine();
					x.trim();
					trial1.addElement(x);
				}

			if (d>0)
				for(int i=0;i<d;i++)
				{
					tmp = new component();
					ans = tmp.readComponent(In,trial1,Lib_Dir);
					if (!(ans)) return false;
					trial2.addElement(tmp);
				}

			In.close();
		}
		catch(Exception e) { return false; }

		if (c>0)
			for(int i=0;i<c;i++)
			{
				x = (String) trial1.getElementAt(i);

				if (!ClassList.contains(x))
					if (dd>0)
						for(int j=0;j<dd;j++)
						{
							tmp = (component) ComponentList.getElementAt(j);
							tmp.addClass(x);
						}
			}

		if (cc>0)
			for(int i=0;i<cc;i++)
			{
				x = (String) ClassList.getElementAt(i);

				if (!trial1.contains(x))
					if (d>0)
						for(int j=0;j<d;j++)
						{
							tmp = (component) trial2.getElementAt(j);
							tmp.addClass(x);
						}
			}

		if (c>0)
			for(int i=0;i<c;i++)
			{
				x = (String) trial1.getElementAt(i);
				ClassList.addElement(x);
			}

		if (d>0)
			for(int i=0;i<d;i++)
			{
				tmp = (component) trial2.getElementAt(i);
				ComponentList.addElement(tmp);
			}

		ControlPad(0,0);
		return true;
	}

	private boolean SaveFile(File F)
	{
		try
		{
			PrintWriter Out = new PrintWriter(new FileWriter(F));

			boolean ans = true;
			String x;
			component tmp;

			int c = ClassList.getSize();
			int d = ComponentList.getSize();

			Out.println(c + " " + d);

			if (c>0)
				for(int i=0;i<c;i++)
				{
					x = (String) ClassList.getElementAt(i);
					System.out.println("   Writing group "+x+" ...");
					Out.println(x);
				}

			if (d>0)
				for(int i=0;i<d;i++)
				{
					tmp = (component) ComponentList.getElementAt(i);
					System.out.println("   Writing group "+tmp+" ...");
					ans = tmp.writeComponent(Out);
					if (!ans) return false;
			}

			Out.close();
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}

	// --------------- Classification commands -----------------------------

	public void startClassify()
	{
		int i;
		String tmp;

		Classify.setVisible(false);

		classifyComponent.Members.clear();
		classifyComponent.NonMembers.clear();

		int c = Classify.Members.getSize();
		int d = Classify.NonMembers.getSize();

		if (c>0)
			for(i=0;i<c;i++)
			{
				tmp = (String) Classify.Members.getElementAt(i);
				classifyComponent.Members.addElement(tmp);
			}

		if (d>0)
			for(i=0;i<d;i++)
			{
				tmp = (String) Classify.NonMembers.getElementAt(i);
				classifyComponent.NonMembers.addElement(tmp);
			}


		setEnabled(true);
		requestFocus();

		System.out.println("   Group settings updated for Component: "+classifyComponent);
		Msg.setText("Group settings updated for Component "+classifyComponent);

		return;
	}

	public void endClassify()
	{

		Classify.setVisible(false);
		setEnabled(true);
		requestFocus();

		System.out.println("   Group settings cancelled for Component: "+classifyComponent);
		Msg.setText("Group settings cancelled for Component "+classifyComponent);

		return;
	}

	// --------------- Slot List commands -----------------------------

	public void selectSlotList()
	{
		int n;
		String x;

		int index = SlotList.SlotJList.getSelectedIndex();
		if (index == -1) return;

		slotSlotEdit = (slot) slotlistComponent.Slots.getElementAt(index);
		SslotSlotEdit = new String(slotlistComponent.Name+"."+slotSlotEdit.Name+" ["+slotlistComponent.FileName+"]");

		SlotEdit.Members.clear();
		SlotEdit.NonMembers.clear();

		n = slotSlotEdit.Members.getSize();
		if (n>0)
		{
			for(int i=0;i<n;i++)
			{
				x = (String) slotSlotEdit.Members.getElementAt(i);
				SlotEdit.Members.addElement(x);
			}
			SlotEdit.Bremove.setEnabled(true);
			SlotEdit.MembersJList.setSelectedIndex(0);
			SlotEdit.MembersJList.ensureIndexIsVisible(0);
		}
		else SlotEdit.Bremove.setEnabled(false);

		n = slotSlotEdit.NonMembers.getSize();
		if(n>0)
		{
			for(int i=0;i<n;i++)
			{
				x = (String) slotSlotEdit.NonMembers.getElementAt(i);
				SlotEdit.NonMembers.addElement(x);
			}
			SlotEdit.Badd.setEnabled(true);
			SlotEdit.MembersJList.setSelectedIndex(0);
			SlotEdit.MembersJList.ensureIndexIsVisible(0);
		}
		else SlotEdit.Badd.setEnabled(false);

		System.out.println("   Slot "+SslotSlotEdit+" selected for editing");

		SlotEdit.setTitle("Edit Slot - "+SslotSlotEdit);
		SlotList.setEnabled(false);
		SlotEdit.setVisible(true);
		SlotEdit.requestFocus();

		return;
	}

	public void endSlotList()
	{
		SlotList.setVisible(false);
		setEnabled(true);
		requestFocus();

		System.out.println("   Slot Group settings session done for Component "+slotlistComponent);
		Msg.setText("Slot Group settings session done for Component "+slotlistComponent);

		return;
	}

	// --------------- Slot Edit commands -----------------------------

	public void startSlotEdit()
	{
		String tmp;

		slotSlotEdit.Members.clear();
		slotSlotEdit.NonMembers.clear();

		int m = SlotEdit.Members.getSize();
		int n = SlotEdit.NonMembers.getSize();

		for(int i=0;i<m;i++)
		{
			tmp = (String) SlotEdit.Members.getElementAt(i);
			slotSlotEdit.Members.addElement(tmp);
		}

		for(int i=0;i<n;i++)
		{
			tmp = (String) SlotEdit.NonMembers.getElementAt(i);
			slotSlotEdit.NonMembers.addElement(tmp);
		}

		System.out.println("   Slot groups status updated for slot "+SslotSlotEdit);

		SlotEdit.setVisible(false);
		SlotList.setEnabled(true);
		SlotList.requestFocus();

		return;
	}

	public void endSlotEdit()
	{

		SlotEdit.setVisible(false);
		SlotList.setEnabled(true);
		SlotList.requestFocus();

		System.out.println("   Slot groups status cancelled for slot "+SslotSlotEdit);

		return;
	}

	// --------------- Port List commands -----------------------------

	public void selectPortList() // Should actually be selectPort
	{
		String tmp;

		int index = PortList.PortJList.getSelectedIndex();
		if (index == -1) return;

		Integer M = (Integer) PortList.SlotIndexList.getElementAt(index);
		Integer N = (Integer) PortList.PortIndexList.getElementAt(index);

		int m = M.intValue();
		int n = N.intValue();

		if (m==-1)
		{
			slotPortEdit = portlistComponent.Interface;
			portPortEdit = (port) slotPortEdit.Ports.getElementAt(n);
			if (portPortEdit.Type) SportPortEdit = new String("out "+portlistComponent.Name+"."+portPortEdit.Name+"["+portlistComponent.FileName+"]");
			else SportPortEdit = new String("in "+portlistComponent.Name+"."+portPortEdit.Name+" ["+portlistComponent.FileName+"]");
		}
		else
		{
			slotPortEdit = (slot) portlistComponent.Slots.getElementAt(m);
			portPortEdit = (port) slotPortEdit.Ports.getElementAt(n);
			if (portPortEdit.Type) SportPortEdit = new String("out "+portlistComponent.Name+"."+slotPortEdit.Name+"."+portPortEdit.Name+"["+portlistComponent.FileName+"]");
			else SportPortEdit = new String("in "+portlistComponent.Name+"."+slotPortEdit.Name+"."+portPortEdit.Name+" ["+portlistComponent.FileName+"]");
		}

		PortEdit.ServiceList.clear();

		int l = portPortEdit.Services.getSize();
		if (l>0)
		{
			for(int i=0;i<l;i++)
			{
				tmp = (String) portPortEdit.Services.getElementAt(i);
				PortEdit.ServiceList.addElement(tmp);
			}
			PortEdit.ServiceJList.setSelectedIndex(0);
			PortEdit.ServiceJList.ensureIndexIsVisible(0);
		}

		System.out.println("   Editing Port services for port "+SportPortEdit);
		PortEdit.setTitle("Edit Port services - "+SportPortEdit);
		PortList.setEnabled(false);
		PortEdit.setVisible(true);
		PortEdit.requestFocus();

		return;
	}

	public void endPortList()
	{
		PortList.setVisible(false);
		setEnabled(true);
		requestFocus();
		System.out.println("   Port settings editing session done for Component "+portlistComponent);
		Msg.setText("Port settings editing session done for Component "+portlistComponent);

		return;
	}

	// --------------- Port Edit commands -----------------------------

	public void startPortEdit()
	{
		String tmp;

		portPortEdit.Services.clear();

		int n = PortEdit.ServiceList.getSize();
		for(int i=0;i<n;i++)
		{
			tmp = (String) PortEdit.ServiceList.getElementAt(i);
			tmp.trim();
			portPortEdit.Services.addElement(tmp);
		}

		PortEdit.ServiceName.setText("");
		PortEdit.setVisible(false);

		int index = PortList.PortJList.getSelectedIndex();
		PortList.PortList.clear();

		int c,d,i,j;
		slot s = new slot();
		port p = new port();

		s = portlistComponent.Interface;
		c = s.Ports.getSize();
		for(i=0;i<c;i++)
		{
			p = (port) s.Ports.getElementAt(i);
			if (p.Type) tmp = new String("out "+portlistComponent.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" providing services "+p.Services);
			else tmp = new String("in "+portlistComponent.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" requiring services "+p.Services);

			PortList.PortList.addElement(tmp);
			PortList.SlotIndexList.addElement(new Integer(-1));
			PortList.PortIndexList.addElement(new Integer(i));
		}

		c = portlistComponent.Slots.getSize();
		for(i=0;i<c;i++)
		{
			s = (slot) portlistComponent.Slots.getElementAt(i);
			d = s.Ports.getSize();
			for(j=0;j<d;j++)
			{
				p = (port) s.Ports.getElementAt(j);

				if (p.Type) tmp = new String("out "+portlistComponent.Name+"."+s.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" providing services "+p.Services);
				else tmp = new String("in "+portlistComponent.Name+"."+s.Name+"."+p.Name+" ["+portlistComponent.FileName+"]"+" requiring services "+p.Services);

				PortList.PortList.addElement(tmp);
				PortList.SlotIndexList.addElement(new Integer(i));
				PortList.PortIndexList.addElement(new Integer(j));
			}
		}

		PortList.PortJList.setSelectedIndex(index);
		PortList.PortJList.ensureIndexIsVisible(index);
		PortList.setEnabled(true);
		PortList.requestFocus();

		return;
	}

	public void endPortEdit()
	{
		PortEdit.ServiceName.setText("");
		PortEdit.setVisible(false);
		PortList.setEnabled(true);
		PortList.requestFocus();
		return;
	}

	// --------------- Processor Edit commands -----------------------------

	public void startProcEdit()
	{
		String tmp,tmp2;
		slot S;
		int c,d;
		
		tmp = (String) procEdit.get(0);
		procEdit.clear();
		procEdit.add(tmp);
		
		int n = ProcEdit.ServiceList.getSize();
		for(int i=0;i<n;i++)
		{
			tmp = (String) ProcEdit.ServiceList.getElementAt(i);
			tmp.trim();
			procEdit.add(tmp);
		}

		ProcEdit.ServiceName.setText("");
		ProcEdit.setVisible(false);
		
		int index = ProcList.ProcJList.getSelectedIndex();
		ProcList.ProcList.clear();
		ProcList.SlotIndexList.clear();
		ProcList.ProcIndexList.clear();
		
		ArrayList s = new ArrayList();
		c = proclistComponent.ProcServ.getSize();
		for(int i=0;i<c;i++)
		{
			s = new ArrayList((ArrayList) proclistComponent.ProcServ.getElementAt(i));
			tmp2 = (String) s.get(0);
			s.remove(0);
			tmp = new String(tmp2 + " requiring services "+s);
			ProcList.ProcList.addElement(tmp);
			ProcList.SlotIndexList.addElement(new Integer(-1));
			ProcList.ProcIndexList.addElement(new Integer(i));
		}

		c = proclistComponent.Slots.size();
		for(int i=0;i<c;i++)
		{
			
			S = (slot) proclistComponent.Slots.getElementAt(i);
			d = S.ProcServ.size();
			for(int j=0;j<d;j++)
			{
				s = new ArrayList((ArrayList) S.ProcServ.getElementAt(j));
				tmp2 = new String(S.Name + "." + (String) s.get(0));
				s.remove(0);
				tmp = new String(tmp2 + " providing services "+s);
				ProcList.ProcList.addElement(tmp);
				ProcList.SlotIndexList.addElement(new Integer(i));
				ProcList.ProcIndexList.addElement(new Integer(j));
			}
		}
		//System.out.println("1 "+ProcList.ProcList+" "+ProcList.SlotIndexList+" "+ProcList.ProcIndexList);
		
		ProcList.ProcJList.setSelectedIndex(index);
		ProcList.ProcJList.ensureIndexIsVisible(index);
		ProcList.setEnabled(true);
		ProcList.requestFocus();
		return;
	}

	public void endProcEdit()
	{
		ProcEdit.ServiceName.setText("");
		ProcEdit.setVisible(false);
		ProcList.setEnabled(true);
		ProcList.requestFocus();
		return;
	}

	// --------------- Processor List commands -----------------------------

	public void selectProcList()
	{
		String tmp;

		int index = ProcList.ProcJList.getSelectedIndex();
		if (index == -1) return;

		ProcEdit.ServiceList.clear();

		
		int n = ((Integer)ProcList.SlotIndexList.get(index)).intValue();
		int m = ((Integer)ProcList.ProcIndexList.get(index)).intValue();;
		
		if (n==-1)
		{
			procEdit =  (ArrayList) proclistComponent.ProcServ.get(m);
		}
		else
		{
			slot S = (slot) proclistComponent.Slots.get(n);
			procEdit =  (ArrayList) S.ProcServ.get(m);
		}
		
		tmp = (String) procEdit.get(0);
		String s = new String(proclistComponent.Name+"."+tmp+" ["+proclistComponent.FileName+"]");
		
		int l = procEdit.size();
		if (l>1)
			for(int i=1;i<l;i++)
			{
				tmp = (String) procEdit.get(i);
				ProcEdit.ServiceList.addElement(tmp);
			}
		
		if (ProcEdit.ServiceList.getSize()>0)
		{
			ProcEdit.ServiceJList.setSelectedIndex(0);
			ProcEdit.ServiceJList.ensureIndexIsVisible(0);
		}
		

		System.out.println("   Editing Processor services for processor "+s);
		ProcEdit.setTitle("Editing Processor services for processor - "+s);
		ProcList.setEnabled(false);
		ProcEdit.setVisible(true);
		ProcEdit.requestFocus();

		return;
	}

	public void endProcList()
	{
		ProcList.setVisible(false);
		setEnabled(true);
		requestFocus();
		System.out.println("   Processor settings editing session done for Component "+proclistComponent);
		Msg.setText("Processor settings editing session done for Component "+proclistComponent);

		return;
	}
	
// ------------------ Search commands ----------------------------------

	public void startSearch()
	{
		String tmp = new String();
		DefaultListModel tlist = new DefaultListModel();

		component c;
		slot s;
		port p;

		int l,m,n;

		DefaultListModel X = Search.ClassMembers;
		DefaultListModel Y = Search.ServiceMembers;

		l = ComponentList.getSize();
		for(int i=0;i<l;i++)
		{
			c = (component) ComponentList.getElementAt(i);
			if ((c.isMember(X))||(ClassList.isEmpty()))
			{
					n = c.Interface.Ports.getSize();
					for(int k=0;k<n;k++)
					{
						p = (port) c.Interface.Ports.getElementAt(k);
						tlist = p.providesService(Y);
						if (!tlist.isEmpty())
						{
							if (p.Type) tmp = new String("out "+c.Name+"."+p.Name+" ["+c.FileName+"] provides "+tlist);
							else tmp = new String("in "+c.Name+"."+p.Name+" ["+c.FileName+"] requires "+tlist);
							SearchResult.Results.addElement(tmp);
						}
					}

				m = c.Slots.getSize();
				for(int j=0;j<m;j++)
				{
					s = (slot) c.Slots.getElementAt(j);
					n = s.Ports.getSize();
					for(int k=0;k<n;k++)
					{
						p = (port) s.Ports.getElementAt(k);
						tlist = p.providesService(Y);
						if (!tlist.isEmpty())
						{
							if (p.Type) tmp = new String("out "+c.Name+"."+s.Name+"."+p.Name+" ["+c.FileName+"] provides "+tlist);
							else tmp = new String("in "+c.Name+"."+s.Name+"."+p.Name+" ["+c.FileName+"] requires "+tlist);
							SearchResult.Results.addElement(tmp);
						}
					}
				}
			}
		}

		int i = SearchResult.Results.size();

		if (i>0)
		{
			SearchResult.ResultsList.setSelectedIndex(0);
			SearchResult.ResultsList.ensureIndexIsVisible(0);
			SearchResult.setTitle("Search - "+i+" results found!");
			System.out.println("   Search had "+i+" results found");
		}
		else
		{
			System.out.println("   Search had no results found!");
			SearchResult.setTitle("Search - No results found!");
		}

		Search.setEnabled(false);
		SearchResult.setVisible(true);
		SearchResult.requestFocus();

		return;
	}

	public void endSearch()
	{
		Search.setVisible(false);
		setEnabled(true);
		requestFocus();

		System.out.println("   Search session is completed");
		Msg.setText("Search session completed");

		return;
	}


// ------------------ Search Results commands ---------------------------

	public void SearchResult()
	{
		SearchResult.setVisible(false);
		Search.setEnabled(true);
		Search.requestFocus();

		return;
	}

// ----------------------- Build Commands --------------------------------

	public Document IterBuild(DefaultMutableTreeNode NN,DefaultTreeModel M) throws Exception
	{
		int n = M.getChildCount(NN);

		build_node N = (build_node) NN.getUserObject();

		Document A = N.Component.XMLDocument;

		for(int i=0;i<n;i++)
		{
			DefaultMutableTreeNode PP = (DefaultMutableTreeNode) M.getChild(NN,i);
			build_node P = (build_node) PP.getUserObject();

			if (P.Binding)
			{
				xiuping_slot X = P.BindInfo;
				Document B = IterBuild(PP,M);
				A=XIUPING.process(A,X,B);
				System.out.println("   Binding: "+N.Component.Name+"."+P.Slot.Name+"["+N.Component.Name+"]  ->  "+P.Component);
			}
		}
		return A;
	}

	public Document IterBuild1(build_node N) throws Exception
	{
		int n = N.BindList.getSize();
		Document A = N.getXML(Lib_Dir);

		for(int i=0;i<n;i++)
		{
			build_node P = (build_node) N.BindList.getElementAt(i);

			if (P.Binding)
			{
				xiuping_slot X = P.BindInfo;
				Document B = IterBuild1(P);
				A=XIUPING.process(A,X,B);
			}
		}
		return A;
	}

	public void IterBuild2(DefaultMutableTreeNode NN,DefaultTreeModel M) throws Exception
	{
		int n = M.getChildCount(NN);

		build_node N = (build_node) NN.getUserObject();

		for(int i=0;i<n;i++)
		{
			DefaultMutableTreeNode PP = (DefaultMutableTreeNode) M.getChild(NN,i);
			build_node P = (build_node) PP.getUserObject();
			if (P.Binding)
			{
				N.BindList.setElementAt(P,i);
				IterBuild2(PP,M);
			}
		}

		return;
	}

	public void IterBuild3(build_node N) throws Exception
	{
		int n = N.BindList.getSize();

		int i = 0;
		build_node B = new build_node();


		while(i<n)
		{
			B = (build_node) N.BindList.getElementAt(i);
			if (B.Binding) i++; else break;
		}

		if (i==n)
		{
			if (N.Root)
			{
				Document  D = IterBuild1(N);
				ResultsList.addElement(D);
			}
			else IterBuild3(N.Parent);
		}
		else
		{
			int j = B.Slot.Bindings.getSize();
			for(int k=0;k<j;k++)
			{
				xiuping_slot X = (xiuping_slot) B.Slot.Bindings.getElementAt(k);
				component C = (component) ComponentList.getElementAt(X.bindTarget);
				if (B.Check(C)) continue;
				B.bind(C,X);
				IterBuild3(B);
				B.unbind();
			}
		}

		return;
	}

	public void startSave()
	{
		try
		{
			DefaultTreeModel model = (DefaultTreeModel) Build.Members.getModel();

			DefaultMutableTreeNode NN = (DefaultMutableTreeNode) model.getRoot();

			String name = Build.ModelName.getText();

			Document FinalVersion = IterBuild(NN,model);
			Element MainNode = FinalVersion.getDocumentElement();
			MainNode.setAttribute("name",name);

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Save File ...");
			fc.setApproveButtonText("Save");

			int r = fc.showOpenDialog(this);

			if (r == JFileChooser.APPROVE_OPTION)
			{
				File FL = fc.getSelectedFile();
				String F = FL.getName();
				DOMSource domSource = new DOMSource(FinalVersion);
				StreamResult streamResult =new StreamResult(FL);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT,"yes");
				transformer.transform(domSource,streamResult);
				System.out.println("   Saved in the file "+F+" ...");
				Build.ButtonMsg.setText(" Saved the built model in file "+F);
			}
		}
		catch(Exception e)
		{
			System.out.println("   Failed to build component "+buildComponent);
			Build.ButtonMsg.setText(" Failed to build component "+buildComponent);
		}

		return;
	}

	public void startBuild()
	{
		try
		{
			String name = Build.ModelName.getText();

			DefaultTreeModel model = (DefaultTreeModel) Build.Members.getModel();
			DefaultMutableTreeNode NN = (DefaultMutableTreeNode) model.getRoot();
			build_node N = (build_node) NN.getUserObject();
			IterBuild2(NN,model);
			IterBuild3(N);

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(Lib_Dir);
			fc.setDialogTitle("Save File ...");
			fc.setApproveButtonText("Save");

			int r = fc.showOpenDialog(this);
			if (r == JFileChooser.APPROVE_OPTION)
			{
				File F_Old = fc.getSelectedFile();
				String F_OldName = F_Old.getPath();
				String F_Prefix;
				String F_Suffix;

				int d = F_OldName.lastIndexOf('.');
				if (d==-1)
				{
					F_Prefix = new String(F_OldName);
					F_Suffix = new String(".xml");
				}
				else
				{
					F_Prefix = F_OldName.substring(0,d);
					F_Suffix = F_OldName.substring(d);
				}

				int n = ResultsList.getSize();
				System.out.println("   Number of configurations generated: "+n);
				for(int i=0;i<n;i++)
				{
					int j = i + 1;
					Document FinalVersion = (Document) ResultsList.getElementAt(i);
					Element MainNode = FinalVersion.getDocumentElement();
					MainNode.setAttribute("name",name+"_"+i);

					String F = new String(F_Prefix+"_"+j+F_Suffix);
					File FL = new File(F);

					DOMSource domSource = new DOMSource(FinalVersion);

					StreamResult streamResult =new StreamResult(FL);

					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT,"yes");
					transformer.transform(domSource,streamResult);
					System.out.println("   Saved in the file "+F+" ...");
					Build.ButtonMsg.setText(" Saved the built model in file "+F);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("   Failed to build component "+buildComponent);
			Build.ButtonMsg.setText(" Failed to build component "+buildComponent);
		}

		return;
	}

	public void optionsBuild(build_node N)
	{
		component C = (component) N.ParentComponent;
		slot S = (slot) N.Slot;

		BuildNode = N;
		BuildOption.Members.clear();
		int n = S.Bindings.getSize();
		if (n == 0) BuildOption.setTitle("No Binding components for "+C.Name+"."+S.Name+" ["+C.FileName+"]");
		else
		{
			for(int i=0;i<n;i++)
			{
				xiuping_slot X = (xiuping_slot) S.Bindings.getElementAt(i);
				int p = X.bindTarget;
				component G = (component) ComponentList.getElementAt(p);
				BuildOption.Members.addElement(G.toString());
			}
			BuildOption.setTitle("Bind components for "+C.Name+"."+S.Name+" ["+C.FileName+"]");
		}
		Build.setEnabled(false);
		BuildOption.setVisible(true);
		BuildOption.requestFocus();
		return;
	}

	public void endBuild()
	{
		Build.setVisible(false);
		setEnabled(true);
		requestFocus();
		System.out.println("   Build session done for component "+buildComponent);
		Msg.setText("Build session done for component "+buildComponent);

		return;
	}

// ----------------------- Build Option Commands --------------------------------

	public void startBuildOption()
	{
		int index = BuildOption.MembersPane.getSelectedIndex();
		if (index == -1) return;
		slot T= BuildNode.Slot;
		xiuping_slot X = (xiuping_slot) T.Bindings.getElementAt(index);

		int c = X.bindTarget;
		component C = (component) ComponentList.getElementAt(c);

		BuildNode.bind(C,X);

		slot S;

		build_node tmp;
		DefaultMutableTreeNode r;
		DefaultTreeModel model = (DefaultTreeModel) Build.Members.getModel();
		TreePath Path = Build.Members.getSelectionPath();
		build_node P = (build_node) Build.SelectedNode.getUserObject();
		int n = C.Slots.getSize();
		for(int i=0;i<n;i++)
		{
			S = (slot) C.Slots.getElementAt(i);
			tmp = new build_node(C,S,P);
			r = new DefaultMutableTreeNode(tmp);
			model.insertNodeInto(r,Build.SelectedNode,i);
		}
		model.reload();
		Build.Members.expandPath(Path);
		Build.Members.setSelectionPath(Path);

		Build.B1.setText("Unbind");
		BuildOption.setVisible(false);
		Build.setEnabled(true);
		Build.requestFocus();

		return;
	}

	public void endBuildOption()
	{
		BuildOption.setVisible(false);
		Build.setEnabled(true);
		Build.requestFocus();
		return;
	}

// -----------------------------------------------------------------------

	public void insertUpdate(DocumentEvent e)
	{
    	if (e.getDocument().getLength() <= 0)
    		ClassAdd.setEnabled(false);
    	else
    	{
			ClassAdd.setEnabled(true);
			if (ClassJList.getSelectedIndex() == -1)
			{
				ClassEdit.setEnabled(false);
				ClassDelete.setEnabled(false);

			}
			else
			{
				ClassEdit.setEnabled(true);
				ClassDelete.setEnabled(true);
			}
		}
		return;
    }

    public void removeUpdate(DocumentEvent e)
    {
    	if (e.getDocument().getLength() <= 0)
	   		ClassAdd.setEnabled(false);
	   	else
	   	{
			ClassAdd.setEnabled(true);
			if (ClassJList.getSelectedIndex() == -1)
			{
				ClassEdit.setEnabled(false);
				ClassDelete.setEnabled(false);
			}
			else
			{
				ClassEdit.setEnabled(true);
				ClassDelete.setEnabled(true);
			}
		}
		return;
    }

    public void changedUpdate(DocumentEvent e)
    {
    	if (e.getDocument().getLength() <= 0)
	   		ClassAdd.setEnabled(false);
	   	else
	   	{
			ClassAdd.setEnabled(true);
			if (ClassJList.getSelectedIndex() == -1)
			{
				ClassEdit.setEnabled(false);
				ClassDelete.setEnabled(false);

			}
			else
			{
				ClassEdit.setEnabled(true);
				ClassDelete.setEnabled(true);
			}
		}
		return;
    }

	public void valueChanged(ListSelectionEvent e)
	{
    	ControlPad(-1,-1);
		return;
	}

	void ControlPad(int i,int j)
	{
		if (ClassList.getSize() == 0)
		{
			String newname = ClassName.getText();

			if (newname.equals("")) ClassAdd.setEnabled(false);
			else ClassAdd.setEnabled(true);

			ClassEdit.setEnabled(false);
			ClassDelete.setEnabled(false);
		}
		else
		{
			ClassDelete.setEnabled(true);
			String newname = ClassName.getText();
			if (newname.equals(""))
			{
				ClassEdit.setEnabled(false);
				ClassAdd.setEnabled(false);
			}
			else
			{
				ClassAdd.setEnabled(false);
				ClassEdit.setEnabled(true);
			}
		}

		if (ComponentList.getSize() == 0)
		{
			ComponentDelete.setEnabled(false);
			ComponentClassification.setEnabled(false);
			ComponentSlots.setEnabled(false);
			ComponentPorts.setEnabled(false);
			ComponentProcessors.setEnabled(false);
			ComponentBuild.setEnabled(false);
		}
		else
		{
			ComponentDelete.setEnabled(true);
			ComponentClassification.setEnabled(true);
			ComponentSlots.setEnabled(true);
			ComponentPorts.setEnabled(true);
			ComponentProcessors.setEnabled(true);
			ComponentBuild.setEnabled(true);
		}

		if ((i!=-1)&&(ClassList.getSize()>i)) ClassJList.setSelectedIndex(i);
		if ((j!=-1)&&(ComponentList.getSize()>j)) ComponentJList.setSelectedIndex(j);

		return;
	}

	public static void main(String[] args)
	{
		System.out.println("--------------------------------------------------------");
		System.out.println("   C o m p o n e n t   L i b r a r y   M a n a g e r    ");
		System.out.println("--------------------------------------------------------");
		System.out.println("\n Real-Time and Distributed System (RADS) group");
		System.out.println(" Department of Systems & Computer Engineering");
		System.out.println(" Carleton University, Ottawa CANADA");
		
 		try
		{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		

			if (args.length == 0)
			{
				System.out.println("Usage: java library <Library directory>");
				System.out.println("  or   java library <Library directory>  <Library file name>\n");
			}
			else
			{
				try
				{
					File LibDir  = new File(args[0]);
					if (LibDir == null) throw new Exception(" ");
					if (args.length >1)
					{
						try
						{		
							File LibFile = new File(args[1]);
							if (LibFile == null) throw new Exception(" ");
							new library(LibDir,LibFile);
						}
						catch(Exception e)
						{
							System.out.println("\nERROR: Unable to locate library file"+args[1]);
						}
					}
					else new library(LibDir,null);
				}
				catch(Exception e)
				{
					System.out.println("\nERROR: Unable to locate directory "+args[0]);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("\nUnable to start\n");
		}

		return;
	}
}
