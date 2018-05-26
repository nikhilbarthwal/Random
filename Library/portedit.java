// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Port information editor  - portedit.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class portedit  extends JFrame implements ActionListener, ListSelectionListener, DocumentListener
{
	public library L;

	public JButton SAdd    = new JButton("Add");
	public JButton SEdit   = new JButton("Edit");
	public JButton SDelete = new JButton("Delete");
	public JButton SDone   = new JButton("Done!");
	public JButton SCancel = new JButton("Cancel");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,5,5,5));

	JPanel     ServicePanel = new JPanel(new BorderLayout(0,10));
	JLabel     ServiceMsg   = new JLabel("Service: ");
	JTextField ServiceName  = new JTextField(20);

	public DefaultListModel ServiceList = new DefaultListModel();
	public JList ServiceJList           = new JList(ServiceList);

	JScrollPane ServiceScroll = new JScrollPane(ServiceJList);
	JLabel Msg                = new JLabel("                                                                                List of Services   ");

	public portedit()
	{
		ButtonPanel.add(SAdd);
		ButtonPanel.add(SEdit);
		ButtonPanel.add(SDelete);
		ButtonPanel.add(SDone);
		ButtonPanel.add(SCancel);

		SAdd.addActionListener(this);
		SEdit.addActionListener(this);
		SDelete.addActionListener(this);
		SDone.addActionListener(this);
		SCancel.addActionListener(this);

		ServicePanel.add(ServiceMsg,BorderLayout.WEST);
		ServicePanel.add(ServiceName,BorderLayout.CENTER);
		ServicePanel.add(Msg,BorderLayout.SOUTH);

		ServicePanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ServiceScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(ServicePanel,BorderLayout.NORTH);
		content.add(ServiceScroll,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		ServiceName.addActionListener(this);
		ServiceName.getDocument().addDocumentListener(this);


		ServiceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ServiceJList.setVisibleRowCount(10);
		ServiceJList.addListSelectionListener(this);

		ServiceName.setText("");
		SAdd.setEnabled(false);
		SEdit.setEnabled(false);

		if (ServiceList.getSize() == 0) SDelete.setEnabled(false);
		else
		{
			ServiceJList.setSelectedIndex(0);
			SDelete.setEnabled(true);
		}

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endPortEdit();
				}
			});

		setSize(603,420);
		//setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == SCancel) L.endPortEdit();

		if (source == SDone)
		{
			String name = ServiceName.getText();
			name.trim();
			if (!((name.equals(""))||(ServiceList.contains(name))))	ServiceList.addElement(name);
			L.startPortEdit();
		}

		if ((source == SAdd)||(source == ServiceName))
		{
			String name = ServiceName.getText();
			name.trim();

			if (!((name.equals(""))||(ServiceList.contains(name))))
			{
				System.out.println("   Service "+name+" added");
				ServiceList.addElement(name);
			}
			else System.out.println("   Service name duplicated or empty");


			ServiceName.setText("");
			SAdd.setEnabled(false);
			SEdit.setEnabled(false);

			int c = ServiceList.getSize();
			if (c == 0) SDelete.setEnabled(false);
			else
			{
				ServiceJList.setSelectedIndex(c-1);
				SDelete.setEnabled(true);
			}

			return;
		}

		if (source == SEdit)
		{
			int index = ServiceJList.getSelectedIndex();
			if (index ==-1) return;

			String name = ServiceName.getText();
			name.trim();

			if (!((name.equals(""))||(ServiceList.contains(name))))
			{
				System.out.println("   Service "+name+" edited");
				ServiceList.removeElementAt(index);
				ServiceList.insertElementAt(name,index);
			}
			else System.out.println("   Service name duplicated or empty");

			ServiceName.setText("");
			SAdd.setEnabled(false);
			SEdit.setEnabled(false);

			int c = ServiceList.getSize();
			if (c == 0) SDelete.setEnabled(false);
			else
			{
				ServiceJList.setSelectedIndex(c-1);
				SDelete.setEnabled(true);
			}

			return;
		}

		if (source == SDelete)
		{
			int index = ServiceJList.getSelectedIndex();
			if (index ==-1) return;

			String name = ServiceName.getText();
			name.trim();

			ServiceList.removeElementAt(index);
			System.out.println("   Service "+name+" removed");

			int c = ServiceList.getSize();
			if (c == 0) SDelete.setEnabled(false);
			else
			{
				ServiceJList.setSelectedIndex(index);
				SDelete.setEnabled(true);
			}

			return;
		}

		return;
	}

	public void insertUpdate(DocumentEvent e)
	{
	   	if (e.getDocument().getLength() <= 0)
		SAdd.setEnabled(false);
	   	else
	   	{
			SAdd.setEnabled(true);
			if (ServiceJList.getSelectedIndex() == -1)
			{
				SEdit.setEnabled(false);
				SDelete.setEnabled(false);
			}
			else
			{
				SEdit.setEnabled(true);
				SDelete.setEnabled(true);
			}
		}

		return;
    }

	public void removeUpdate(DocumentEvent e)
	{
	   	if (e.getDocument().getLength() <= 0)
		SAdd.setEnabled(false);
	   	else
	   	{
			SAdd.setEnabled(true);
			if (ServiceJList.getSelectedIndex() == -1)
			{
				SEdit.setEnabled(false);
				SDelete.setEnabled(false);
			}
			else
			{
				SEdit.setEnabled(true);
				SDelete.setEnabled(true);
			}
		}

		return;
    }

    public void changedUpdate(DocumentEvent e)
	{
	   	if (e.getDocument().getLength() <= 0)
		SAdd.setEnabled(false);
	   	else
	   	{
			SAdd.setEnabled(true);
			if (ServiceJList.getSelectedIndex() == -1)
			{
				SEdit.setEnabled(false);
				SDelete.setEnabled(false);
			}
			else
			{
				SEdit.setEnabled(true);
				SDelete.setEnabled(true);
			}
		}

		return;
    }

	public void valueChanged(ListSelectionEvent e)
	{
    	if (ServiceJList.getSelectedIndex() == -1)
		{
			String newname = ServiceName.getText();

			if (newname.equals(""))	SAdd.setEnabled(false);
			else SAdd.setEnabled(true);

			SEdit.setEnabled(false);
			SDelete.setEnabled(false);
		}
		else
		{
			SDelete.setEnabled(true);
			String newname = ServiceName.getText();
			if (newname.equals(""))
			{
				SEdit.setEnabled(false);
				SAdd.setEnabled(false);
			}
			else
			{
				SAdd.setEnabled(true);
				SEdit.setEnabled(true);
			}
		}
	   	return;
	}
}

