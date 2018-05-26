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

public class slotlist  extends JFrame implements ActionListener, ListSelectionListener, MouseListener
{
	public library L;

	public JButton BSelect    = new JButton("Select Slot");
	public JButton BCancel    = new JButton("Done!");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,2,5,5));

	JLabel Msg = new JLabel("                          List of Slots");

	public DefaultListModel SlotList = new DefaultListModel();  // List of Slots
	public JList SlotJList = new JList(SlotList);

	JScrollPane SlotScroll = new JScrollPane(SlotJList);

	public slotlist()
	{
		ButtonPanel.add(BSelect);
		ButtonPanel.add(BCancel);

		BSelect.addActionListener(this);
		BCancel.addActionListener(this);

		Msg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		SlotScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		SlotJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		SlotJList.setVisibleRowCount(10);
		SlotJList.addListSelectionListener(this);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(Msg,BorderLayout.NORTH);
		content.add(SlotScroll,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		SlotJList.addMouseListener(this);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endSlotList();
				}
			});

		setSize(403,320);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == BSelect) L.selectSlotList();
		if (source == BCancel) L.endSlotList();

		return;
	}

	public void mouseClicked(MouseEvent e)
	{
		int n = e.getClickCount();
		if (n>1) L.selectSlotList();
		return;
	}

	public void mouseEntered(MouseEvent e)         { return; }
	public void mouseExited(MouseEvent e)          { return; }
	public void mousePressed(MouseEvent e)         { return; }
	public void mouseReleased(MouseEvent e)        { return; }
	public void valueChanged(ListSelectionEvent e) { return; }
}

