// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Classification manager  - classify.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class classify  extends JFrame implements ActionListener, ListSelectionListener
{
	public library L;

	public JButton Bok      = new JButton("OK");
	public JButton Bcancel  = new JButton("Cancel");
	public JButton Badd     = new JButton("Add >>");
	public JButton Bremove  = new JButton("<< Remove");

	JPanel MainPanel = new JPanel(new GridLayout(1,2,5,5));
	JPanel ButtonPanel = new JPanel(new GridLayout(1,4,5,5));
	JPanel MemberPanel    = new JPanel(new BorderLayout(0,10));
	JPanel NonMemberPanel = new JPanel(new BorderLayout(0,10));

	JLabel MembersMsg    = new JLabel("               Member Groups for component  ");
	JLabel NonMembersMsg = new JLabel("             Non-Member Groups for component");

	public DefaultListModel Members     = new DefaultListModel();
	public DefaultListModel NonMembers  = new DefaultListModel();

	public JList MembersJList    = new JList(Members);
	public JList NonMembersJList = new JList(NonMembers);

	JScrollPane MembersScroll   = new JScrollPane(MembersJList);
	JScrollPane NonMembersScroll = new JScrollPane(NonMembersJList);


	public classify()
	{
		ButtonPanel.add(Badd);
		ButtonPanel.add(Bok);
		ButtonPanel.add(Bcancel);
		ButtonPanel.add(Bremove);

		Badd.addActionListener(this);
		Bok.addActionListener(this);
		Bcancel.addActionListener(this);
		Bremove.addActionListener(this);

		MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		MemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		NonMemberPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		MembersMsg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		NonMembersMsg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

		MembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		NonMembersScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		MemberPanel.add(MembersMsg,BorderLayout.NORTH);
		MemberPanel.add(MembersScroll,BorderLayout.CENTER);

		NonMemberPanel.add(NonMembersMsg,BorderLayout.NORTH);
		NonMemberPanel.add(NonMembersScroll,BorderLayout.CENTER);

		MainPanel.add(NonMemberPanel);
		MainPanel.add(MemberPanel);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(MainPanel,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);


		MembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		MembersJList.setVisibleRowCount(10);
		MembersJList.addListSelectionListener(this);

		NonMembersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		NonMembersJList.setVisibleRowCount(10);
		NonMembersJList.addListSelectionListener(this);

		if (Members.getSize() == 0) Bremove.setEnabled(false);
		else
		{
			MembersJList.setSelectedIndex(0);
			Bremove.setEnabled(true);
		}

		if (NonMembers.getSize() == 0) Badd.setEnabled(false);
		else
		{
			NonMembersJList.setSelectedIndex(0);
			Badd.setEnabled(true);
		}

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endClassify();
				}
			});

		setSize(603,420);
		return;
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == Bok)     L.startClassify();
		if (source == Bcancel) L.endClassify();

		if (source == Badd)
		{
			int index = NonMembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) NonMembersJList.getSelectedValue();

			Members.addElement(name);
			NonMembers.remove(index);

			int size = NonMembers.getSize();
			if (size == 0)
			{
				Badd.setEnabled(false);
			}
			else
			{
				NonMembersJList.setSelectedIndex(0);
			    NonMembersJList.ensureIndexIsVisible(0);
            }

		    size = Members.size();size--;
			MembersJList.setSelectedIndex(size);
		    MembersJList.ensureIndexIsVisible(size);

			if (size == 0)	Bremove.setEnabled(true);
		}

		if (source == Bremove)
		{
			int index = MembersJList.getSelectedIndex();
			if (index == -1) return;

			String name = (String) MembersJList.getSelectedValue();

			Members.remove(index);
			NonMembers.addElement(name);

			int size = Members.getSize();
			if (size == 0)
			{
				Bremove.setEnabled(false);
			}
			else
			{
				MembersJList.setSelectedIndex(0);
			    MembersJList.ensureIndexIsVisible(0);
            }

		    size = NonMembers.size();size--;
			NonMembersJList.setSelectedIndex(size);
		    NonMembersJList.ensureIndexIsVisible(size);

			if (size == 0)	Badd.setEnabled(true);
		}

		return;
	}

	public void valueChanged(ListSelectionEvent e)
	{
    	Object source=e.getSource();

    	if (source == Members)
		{
		    if (e.getValueIsAdjusting() == false)
	    	{
		        if (MembersJList.getSelectedIndex() == -1)
				{
					if (Bremove.isEnabled()) Bremove.setEnabled(false);
				}
				else
				{
					if (!Bremove.isEnabled()) Bremove.setEnabled(true);

				}
			}
		}

    	if (source == NonMembers)
		{
		    if (e.getValueIsAdjusting() == false)
	    	{
		        if (NonMembersJList.getSelectedIndex() == -1)
				{
					if (Badd.isEnabled()) Badd.setEnabled(false);
				}
				else
				{
					if (!Badd.isEnabled()) Badd.setEnabled(true);

				}
			}
		}
	}
}

