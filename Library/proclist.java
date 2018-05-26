// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//            List all Slots   - slotlist.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class proclist extends JFrame implements ActionListener, ListSelectionListener, MouseListener
{
	public library L;

	public JButton BSelect    = new JButton("Select Processor");
	public JButton BCancel    = new JButton("Done!");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	JLabel Msg = new JLabel("                       List of Processors");

	public DefaultListModel ProcList = new DefaultListModel();       // List of Processors
	public DefaultListModel SlotIndexList = new DefaultListModel();  // Slot Index List
	public DefaultListModel ProcIndexList = new DefaultListModel();  // Processor Index List
	
	public JList ProcJList = new JList(ProcList);

	JScrollPane ProcScroll = new JScrollPane(ProcJList);

	public proclist()
	{
		ButtonPanel.add(BSelect);
		ButtonPanel.add(BCancel);

		BSelect.addActionListener(this);
		BCancel.addActionListener(this);

		Msg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ProcScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		ProcJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ProcJList.setVisibleRowCount(10);
		ProcJList.addListSelectionListener(this);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(Msg,BorderLayout.NORTH);
		content.add(ProcScroll,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		ProcJList.addMouseListener(this);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endProcList();
				}
			});

		setSize(500,320);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == BSelect) L.selectProcList();
		if (source == BCancel) L.endProcList();

		return;
	}

	public void mouseClicked(MouseEvent e)
	{
		int n = e.getClickCount();
		if (n>1) L.selectProcList();
		return;
	}

	public void mouseEntered(MouseEvent e)         { return; }
	public void mouseExited(MouseEvent e)          { return; }
	public void mousePressed(MouseEvent e)         { return; }
	public void mouseReleased(MouseEvent e)        { return; }
	public void valueChanged(ListSelectionEvent e) { return; }
}

