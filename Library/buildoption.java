// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Build Options  - buildoption.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class buildoption  extends JFrame implements ActionListener, ListSelectionListener, MouseListener
{
	public library L;

	public JButton B1 = new JButton("Bind Component");
	public JButton B2 = new JButton("Cancel");

	JPanel ButtonPanel = new JPanel(new GridLayout(1,2,5,5));
	JPanel MemberPanel    = new JPanel(new BorderLayout(0,10));

	JLabel MembersMsg =  new JLabel("                                            List of Options");

	public DefaultListModel Members = new  DefaultListModel();

	public JList MembersPane = new JList(Members);
	JScrollPane Scroll = new JScrollPane(MembersPane);


	public buildoption()
	{
		ButtonPanel.add(B1);
		ButtonPanel.add(B2);

		B1.addActionListener(this);
		B2.addActionListener(this);

		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		MemberPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

		MembersMsg.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

		Scroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		MemberPanel.add(MembersMsg,BorderLayout.NORTH);
		MemberPanel.add(Scroll,BorderLayout.CENTER);

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(MemberPanel,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		MembersPane.addMouseListener(this);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endBuildOption();
				}
			});

		setSize(403,320);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == B1) L.startBuildOption();
		if (source == B2) L.endBuildOption();

		return;
	}

	public void mouseClicked(MouseEvent e)
	{
		int n = e.getClickCount();
		if (n>1) L.startBuildOption();
		return;
	}

	public void mouseEntered(MouseEvent e)         { return; }
	public void mouseExited(MouseEvent e)          { return; }
	public void mousePressed(MouseEvent e)         { return; }
	public void mouseReleased(MouseEvent e)        { return; }
	public void valueChanged(ListSelectionEvent e) { return; }

}

