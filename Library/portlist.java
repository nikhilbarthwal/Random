// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//            List all ports   - portlist.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class portlist  extends JFrame implements ActionListener, ListSelectionListener, MouseListener
{
	public library L;

	public JButton BSelect    = new JButton("Select Port");
	public JButton BCancel    = new JButton("Done!");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	JLabel Msg = new JLabel("                          List of Ports");

	public DefaultListModel SlotIndexList = new DefaultListModel();  // List of Slots index
	public DefaultListModel PortIndexList = new DefaultListModel();  // List of Ports index

	public DefaultListModel PortList = new DefaultListModel();  // List of Ports
	public JList PortJList = new JList(PortList);

	JScrollPane PortScroll = new JScrollPane(PortJList);

	public portlist()
	{
		ButtonPanel.add(BSelect);
		ButtonPanel.add(BCancel);

		BSelect.addActionListener(this);
		BCancel.addActionListener(this);

		Msg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		PortScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		PortJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PortJList.setVisibleRowCount(10);
		PortJList.addListSelectionListener(this);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(Msg,BorderLayout.NORTH);
		content.add(PortScroll,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		PortJList.addMouseListener(this);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endPortList();
				}
			});

		setSize(603,320);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == BSelect) L.selectPortList();
		if (source == BCancel) L.endPortList();

		return;
	}

	public void mouseClicked(MouseEvent e)
	{
		int n = e.getClickCount();
		if (n>1) L.selectPortList();
		return;
	}

	public void mouseEntered(MouseEvent e)         { return; }
	public void mouseExited(MouseEvent e)          { return; }
	public void mousePressed(MouseEvent e)         { return; }
	public void mouseReleased(MouseEvent e)        { return; }
	public void valueChanged(ListSelectionEvent e) { return; }
}

