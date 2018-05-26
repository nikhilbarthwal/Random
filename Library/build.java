// ******************************************************
//
//   C o m p o n e n t   L i b r a r y   M a n a g e r
//
//          Nikhil Barthwal          Oct 17,2003
//
// ******************************************************
//
//    Build manager  - build.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class build  extends JFrame implements ActionListener, TreeSelectionListener, MouseListener
{
	public library L;

	public JButton B0 = new JButton("Build");
	public JButton B1 = new JButton("Bind");
	public JButton B2 = new JButton("Auto-Build");
	public JButton B3 = new JButton("Done!");

	JPanel ButtonPanel = new JPanel(new GridLayout(2,1,0,0));
	JPanel ButtonPlate = new JPanel(new GridLayout(1,4,5,5));
	//JPanel MemberPanel = new JPanel(new BorderLayout(0,10));

	public JLabel ButtonMsg  = new JLabel("");
	public JLabel ModelMsg  = new JLabel("Model Name:");
	public JTextField ModelName = new JTextField(20);

	JPanel ModelPanel = new JPanel(new BorderLayout(5,5));

	public JTree Members           = new JTree();
	public JScrollPane MembersPane = new JScrollPane(Members);

	public boolean isLeaf = false;
	public DefaultMutableTreeNode SelectedNode;

	public build()
	{
		ButtonPlate.add(B0);
		ButtonPlate.add(B1);
		ButtonPlate.add(B2);
		ButtonPlate.add(B3);

		ButtonPanel.add(ButtonMsg);
		ButtonPanel.add(ButtonPlate);

		B0.addActionListener(this);
		B1.addActionListener(this);
		B2.addActionListener(this);
		B3.addActionListener(this);

		B1.setEnabled(false);

		ButtonPlate.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
		ButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

		ModelPanel.add(ModelMsg,BorderLayout.WEST);
		ModelPanel.add(ModelName,BorderLayout.CENTER);
		ModelPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		MembersPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		Container content = getContentPane();
		content.setLayout(new BorderLayout(0,0));
		content.add(ModelPanel,BorderLayout.NORTH);
		content.add(MembersPane,BorderLayout.CENTER);
		content.add(ButtonPanel,BorderLayout.SOUTH);

		Members.addTreeSelectionListener(this);
		Members.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		Members.addMouseListener(this);

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					L.endBuild();
				}
			});

		setSize(553,420);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source=e.getSource();

		if (source == B0) L.startSave();
		if (source == B1)
		{
			SelectedNode = (DefaultMutableTreeNode) Members.getLastSelectedPathComponent();
			build_node N = (build_node) SelectedNode.getUserObject();

			if (SelectedNode.isRoot()) return;

			if (N.Binding)
			{
				N.unbind();
				TreePath tpath = Members.getSelectionPath();
				Members.collapsePath(tpath);
				SelectedNode.removeAllChildren();
				DefaultTreeModel model = (DefaultTreeModel) Members.getModel();
				model.reload();
				B1.setText("Bind");
				Members.setSelectionPath(tpath);
			}
			else L.optionsBuild(N);
		}
		if (source == B2) L.startBuild();
		if (source == B3) L.endBuild();

		return;
	}

	public void SetChange()
	{
	    SelectedNode = (DefaultMutableTreeNode) Members.getLastSelectedPathComponent();

    	if (SelectedNode == null) return;

    	build_node N = (build_node) SelectedNode.getUserObject();

    	if (SelectedNode.isRoot())
    	{
			String tmp = N.Component.toString();
			ButtonMsg.setText("Component "+tmp+" is being build");
			B1.setEnabled(false);
		}
    	else
    	{
			B1.setEnabled(true);
			String tmp = new String(N.ParentComponent.Name+"."+N.Slot.Name+" ["+N.ParentComponent.FileName+"]: ");
			if (N.Binding)
			{
				B1.setText("Unbind");
				ButtonMsg.setText(tmp+" Binded to component "+N.Component);
			}
			else
			{
				int i = N.Slot.Bindings.getSize();
				if (i==0) ButtonMsg.setText(tmp+"No bindings possible for this slot");
				else ButtonMsg.setText(tmp+i+" Bindings possible for this slot");
				B1.setText("Bind");
			}
		}
		return;

	}

	public void valueChanged(TreeSelectionEvent e)
	{
		SetChange();
		return;
	}

	public void mouseClicked(MouseEvent e)
	{
		int n = e.getClickCount();

		if (n==1) SetChange();
		else
		{
			SelectedNode = (DefaultMutableTreeNode) Members.getLastSelectedPathComponent();
			build_node N = (build_node) SelectedNode.getUserObject();

			if (SelectedNode.isRoot()) return;

			if (N.Binding)
			{
				N.unbind();
				TreePath tpath = Members.getSelectionPath();
				Members.collapsePath(tpath);
				SelectedNode.removeAllChildren();
				DefaultTreeModel model = (DefaultTreeModel) Members.getModel();
				model.reload();
				B1.setText("Bind");
				Members.setSelectionPath(tpath);
			}
			else L.optionsBuild(N);
		}

		return;
	}

	public void mouseEntered(MouseEvent e)  { return; }
	public void mouseExited(MouseEvent e)   { return; }
	public void mousePressed(MouseEvent e)  { return; }
	public void mouseReleased(MouseEvent e) { return; }
}

