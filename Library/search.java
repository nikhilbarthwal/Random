// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Search manager  - search.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class search  extends JFrame implements ActionListener, ListSelectionListener
{
	public library L;

	JButton Bok      = new JButton("Search");
	JButton Bcancel  = new JButton("Done!");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	JTabbedPane Pane = new JTabbedPane();

	JPanel ClassMainPanel = new JPanel(new GridLayout(1,2,5,5));

	JButton BClassAdd     = new JButton("Add >>");
	JButton BClassRemove  = new JButton("<< Remove");

	JPanel ClassPanel = new JPanel(new BorderLayout(5,5));

	JPanel ClassMemberPanel    = new JPanel(new BorderLayout(0,10));
	JPanel ClassNonMemberPanel = new JPanel(new BorderLayout(0,10));

	JLabel ClassMembersMsg =    new JLabel("               Groups to be searched  ");
	JLabel ClassNonMembersMsg = new JLabel("             Groups not to be searched");

	public DefaultListModel ClassMembers     = new DefaultListModel();
	public DefaultListModel ClassNonMembers  = new DefaultListModel();

	JList ClassMembersJList    = new JList(ClassMembers);
	JList ClassNonMembersJList = new JList(ClassNonMembers);

	JScrollPane ClassMembersScroll   = new JScrollPane(ClassMembersJList);
	JScrollPane ClassNonMembersScroll = new JScrollPane(ClassNonMembersJList);

	JPanel ClassButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	JPanel ServiceMainPanel = new JPanel(new GridLayout(1,2,5,5));

	JButton BServiceAdd     = new JButton("Add >>");
	JButton BServiceRemove  = new JButton("<< Remove");

	JPanel ServicePanel = new JPanel(new BorderLayout(5,5));

	JPanel ServiceMemberPanel    = new JPanel(new BorderLayout(0,10));
	JPanel ServiceNonMemberPanel = new JPanel(new BorderLayout(0,10));

	JLabel ServiceMembersMsg    = new JLabel("            Services to be searched   ");
	JLabel ServiceNonMembersMsg = new JLabel("           Services not to be searched");


	public DefaultListModel ServiceMembers     = new DefaultListModel();
	public DefaultListModel ServiceNonMembers  = new DefaultListModel();

	JList ServiceMembersJList    = new JList(ServiceMembers);
	JList ServiceNonMembersJList = new JList(ServiceNonMembers);

	JScrollPane ServiceMembersScroll   = new JScrollPane(ServiceMembersJList);
	JScrollPane ServiceNonMembersScroll = new JScrollPane(ServiceNonMembersJList);

	JPanel ServiceButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	public search()
	{
		ButtonPanel.add(Bok);
		ButtonPanel.add(Bcancel);

		Bok.addActionListener(this);
		Bcancel.addActionListener(this);

		Pane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		ClassMemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ClassNonMemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ClassPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ClassMainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ClassMembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		ClassNonMembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		ServiceMemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ServiceNonMemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ServicePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ServiceMainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ServiceMembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		ServiceNonMembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		ClassButtonPanel.add(BClassAdd);
		ClassButtonPanel.add(BClassRemove);
		ClassMemberPanel.add(ClassMembersMsg,BorderLayout.NORTH);
		ClassMemberPanel.add(ClassMembersScroll,BorderLayout.CENTER);
		ClassNonMemberPanel.add(ClassNonMembersMsg,BorderLayout.NORTH);
		ClassNonMemberPanel.add(ClassNonMembersScroll,BorderLayout.CENTER);
		ClassMainPanel.add(ClassNonMemberPanel);
		ClassMainPanel.add(ClassMemberPanel);
		ClassPanel.add(ClassMainPanel,BorderLayout.CENTER);
		ClassPanel.add(ClassButtonPanel,BorderLayout.SOUTH);

		BClassAdd.addActionListener(this);
		BClassRemove.addActionListener(this);

		ServiceButtonPanel.add(BServiceAdd);
		ServiceButtonPanel.add(BServiceRemove);
		ServiceMemberPanel.add(ServiceMembersMsg,BorderLayout.NORTH);
		ServiceMemberPanel.add(ServiceMembersScroll,BorderLayout.CENTER);
		ServiceNonMemberPanel.add(ServiceNonMembersMsg,BorderLayout.NORTH);
		ServiceNonMemberPanel.add(ServiceNonMembersScroll,BorderLayout.CENTER);
		ServiceMainPanel.add(ServiceNonMemberPanel);
		ServiceMainPanel.add(ServiceMemberPanel);
		ServicePanel.add(ServiceMainPanel,BorderLayout.CENTER);
		ServicePanel.add(ServiceButtonPanel,BorderLayout.SOUTH);

		BServiceAdd.addActionListener(this);
		BServiceRemove.addActionListener(this);

		Pane.add("  Groups Selection  ",ClassPanel);
		Pane.add(" Services Selection ",ServicePanel);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(Pane,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		ClassMembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ClassMembersJList.setVisibleRowCount(10);
		ClassMembersJList.addListSelectionListener(this);

		ClassNonMembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ClassNonMembersJList.setVisibleRowCount(10);
		ClassNonMembersJList.addListSelectionListener(this);

		ServiceMembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ServiceMembersJList.setVisibleRowCount(10);
		ServiceMembersJList.addListSelectionListener(this);

		ServiceNonMembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ServiceNonMembersJList.setVisibleRowCount(10);
		ServiceNonMembersJList.addListSelectionListener(this);

		if (ClassMembers.getSize() == 0) BClassRemove.setEnabled(false);
		else
		{
			ClassMembersJList.setSelectedIndex(0);
			BClassRemove.setEnabled(true);
		}

		if (ClassNonMembers.getSize() == 0) BClassAdd.setEnabled(false);
		else
		{
			ClassNonMembersJList.setSelectedIndex(0);
			BClassAdd.setEnabled(true);
		}

		if (ServiceMembers.getSize() == 0) BServiceRemove.setEnabled(false);
		else
		{
			ServiceMembersJList.setSelectedIndex(0);
			BServiceRemove.setEnabled(true);
		}

		if (ServiceNonMembers.getSize() == 0) BServiceAdd.setEnabled(false);
		else
		{
			ServiceNonMembersJList.setSelectedIndex(0);
			BServiceAdd.setEnabled(true);
		}

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endSearch();
				}
			});

		setSize(603,420);

		setTitle("Search for Components");

		return;
	}

	/*public void addMemberService(String x)
	{
		String tmp;
		boolean b = true;

		int n = ServiceMembers.getSize();
		for(int i=0;i<n;i++)
		{
			tmp = ServiceMembers.getElementAt(i);
			if (tmp.equals(x)) {b = false;break;}
		}

		if (b) ServiceMembers.addElement(tmp);
		return;
	}*/

	public void addService(String x)
	{
		String tmp;
		boolean b = true;

		int n = ServiceNonMembers.getSize();
		for(int i=0;i<n;i++)
		{
			tmp = (String) ServiceNonMembers.getElementAt(i);
			if (tmp.equals(x)) {b = false;break;}
		}

		if (b) ServiceNonMembers.addElement(x);
		return;
	}


	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == Bok)     L.startSearch();
		if (source == Bcancel) L.endSearch();

		if (source == BClassAdd)
		{
			int index = ClassNonMembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) ClassNonMembersJList.getSelectedValue();

			ClassMembers.addElement(name);
			ClassNonMembers.remove(index);

			int size = ClassNonMembers.getSize();
			if (size == 0)
			{
				BClassAdd.setEnabled(false);
			}
			else
			{
				ClassNonMembersJList.setSelectedIndex(0);
			    ClassNonMembersJList.ensureIndexIsVisible(0);
            }

		    size = ClassMembers.size();size--;
			ClassMembersJList.setSelectedIndex(size);
		    ClassMembersJList.ensureIndexIsVisible(size);

			if (size == 0)	BClassRemove.setEnabled(true);
		}

		if (source == BClassRemove)
		{
			int index = ClassMembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) ClassMembersJList.getSelectedValue();

			ClassMembers.remove(index);
			ClassNonMembers.addElement(name);

			int size = ClassMembers.getSize();
			if (size == 0)
			{
				BClassRemove.setEnabled(false);
			}
			else
			{
				ClassMembersJList.setSelectedIndex(0);
			    ClassMembersJList.ensureIndexIsVisible(0);
            }

		    size = ClassNonMembers.size();size--;
			ClassNonMembersJList.setSelectedIndex(size);
		    ClassNonMembersJList.ensureIndexIsVisible(size);

			if (size == 0)	BClassAdd.setEnabled(true);
		}

		if (source == BServiceAdd)
		{
			int index = ServiceNonMembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) ServiceNonMembersJList.getSelectedValue();

			ServiceMembers.addElement(name);
			ServiceNonMembers.remove(index);

			int size = ServiceNonMembers.getSize();
			if (size == 0)
			{
				BServiceAdd.setEnabled(false);
			}
			else
			{
				ServiceNonMembersJList.setSelectedIndex(0);
			    ServiceNonMembersJList.ensureIndexIsVisible(0);
            }

		    size = ServiceMembers.size();size--;
			ServiceMembersJList.setSelectedIndex(size);
		    ServiceMembersJList.ensureIndexIsVisible(size);

			if (size == 0)	BServiceRemove.setEnabled(true);
		}

		if (source == BServiceRemove)
		{
			int index = ServiceMembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) ServiceMembersJList.getSelectedValue();

			ServiceMembers.remove(index);
			ServiceNonMembers.addElement(name);

			int size = ServiceMembers.getSize();
			if (size == 0)
			{
				BServiceRemove.setEnabled(false);
			}
			else
			{
				ServiceMembersJList.setSelectedIndex(0);
			    ServiceMembersJList.ensureIndexIsVisible(0);
            }

		    size = ServiceNonMembers.size();size--;
			ServiceNonMembersJList.setSelectedIndex(size);
		    ServiceNonMembersJList.ensureIndexIsVisible(size);

			if (size == 0)	BServiceAdd.setEnabled(true);
		}

		return;
	}

	public void valueChanged(ListSelectionEvent e)
	{
    	Object source=e.getSource();

    	if (source == ClassMembers)
		{
		    if (e.getValueIsAdjusting() == false)
	    	{
		        if (ClassMembersJList.getSelectedIndex() == -1)
				{
					if (BClassRemove.isEnabled()) BClassRemove.setEnabled(false);
				}
				else
				{
					if (!BClassRemove.isEnabled()) BClassRemove.setEnabled(true);

				}
			}
		}

    	if (source == ClassNonMembers)
		{
		    if (e.getValueIsAdjusting() == false)
	    	{
		        if (ClassNonMembersJList.getSelectedIndex() == -1)
				{
					if (BClassAdd.isEnabled()) BClassAdd.setEnabled(false);
				}
				else
				{
					if (!BClassAdd.isEnabled()) BClassAdd.setEnabled(true);

				}
			}
		}

		if (source == ServiceMembers)
		{
			if (e.getValueIsAdjusting() == false)
			{
				if (ServiceMembersJList.getSelectedIndex() == -1)
				{
					if (BServiceRemove.isEnabled()) BServiceRemove.setEnabled(false);
				}
				else
				{
					if (!BServiceRemove.isEnabled()) BServiceRemove.setEnabled(true);
				}
			}
		}

		if (source == ServiceNonMembers)
		{
			if (e.getValueIsAdjusting() == false)
			{
				if (ServiceNonMembersJList.getSelectedIndex() == -1)
				{
					if (BServiceAdd.isEnabled()) BServiceAdd.setEnabled(false);
				}
				else
				{
					if (!BServiceAdd.isEnabled()) BServiceAdd.setEnabled(true);
				}
			}
		}

		return;
	}
}

