public class TreeNode
{
	public int data;
	public TreeNode left;
	public TreeNode right;
	
	TreeNode(int z)
	{
		data = z;
		left = null;
		right = null;
	};
	
	public void insert(int z)
	{
		TreeNode t = new TreeNode(z);
		
		if (data>z)
		{
			if (right==null)
				right=t;
			else
				right.insert(z);
		}
		else
		{
			if (left==null)
				left=t;
			else
				left.insert(z);
		}
		
		return;
	}
	
	public TreeNode find(int z)
	{
		TreeNode t = this;
		
		if (data==z) return this;
		
		if (data>z)
			if (right==null)
				t=null;
			else
				t=right.find(z);
		else
			if (left==null)
				t=null;
			else
				t=left.find(z);
		
		return t;
	}
}

