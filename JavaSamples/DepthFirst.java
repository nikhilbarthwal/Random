import java.util.*;

public class DepthFirst
{
	private static void depth(ArrayList<TreeNode> Q)
	{
		ArrayList<TreeNode> T = new ArrayList<TreeNode>();
		
		for(TreeNode t:Q)
		{
			System.out.println(t.data);
			
			if (t.right!=null) T.add(t.right);
			if (t.left!=null) T.add(t.left);
		}
		
		if (T.size()>0) depth(T);
		
		return;
	}
	
	public static void main(String[] args)
	{
		int[] A = {10, 5, 30, 3, 7, 20, 40, 1, 4, 6, 8, 15, 25, 35, 45};
		
		TreeNode root = new TreeNode(A[0]);
		
		for(int i=1; i<A.length; i++)
			root.insert(A[i]);
		
		ArrayList<TreeNode> R = new ArrayList<TreeNode>(1);
		R.add(root);
		depth(R);
				
		return;
	}
}
