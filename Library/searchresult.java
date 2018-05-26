// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Search result manager  - searchresult.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class searchresult  extends JFrame implements ActionListener, ListSelectionListener
{
	public library L;

	JButton Bok      = new JButton("Done!");
	JPanel MainPanel = new JPanel(new BorderLayout(5,5));

	public DefaultListModel Results     = new DefaultListModel();
	public JList ResultsList = new JList(Results);
	JScrollPane Scroll   = new JScrollPane(ResultsList);

	public searchresult()
	{
		Bok.addActionListener(this);

		MainPanel.add(Scroll,BorderLayout.CENTER);
		MainPanel.add(Bok,BorderLayout.SOUTH);
		MainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		ResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ResultsList.setVisibleRowCount(10);
		ResultsList.addListSelectionListener(this);

		Scroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		Container content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		content.add(MainPanel);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.SearchResult();
				}
			});

		setSize(703,420);
	}

	public void actionPerformed(ActionEvent e)
	{
		L.SearchResult();
		return;
	}

	public void valueChanged(ListSelectionEvent e)
	{
    	return;
	}
}

