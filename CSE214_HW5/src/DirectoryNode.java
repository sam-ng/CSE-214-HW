/**
 * The DirectoryNode class represents a node in the file tree.
 * The DirectoryNode class should contain 3 DirectoryNode references, left, middle, and right.
 * In addition, the class should contain a String member variable name, which indicates the name of the node in the tree.
 * 
 * @author Samuel Ng
 *	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class DirectoryNode {
	private DirectoryNode left;
	private DirectoryNode middle;
	private DirectoryNode right;
	private DirectoryNode parent;
	private String name;
	private boolean isFile;
	
	/**
	 * 
	 * @param name
	 * 	the name of the node.
	 * @param isFile
	 * 	isFile checks if the node is a file or a directory.
	 */
	public DirectoryNode(String name, boolean isFile) {
		left = null;
		middle = null;
		right = null;
		parent = null;
		this.name = name;
		this.isFile = isFile;
	}
	
	//Accessor methods:
	/**
	 * 
	 * @return
	 * 	the left child of the current DirectoryNode
	 */
	public DirectoryNode getLeft() {
		return left;
	}
	
	/**
	 * 
	 * @return
	 * 	the middle child of the current DirectoryNode
	 */
	public DirectoryNode getMiddle() {
		return middle;
	}
	
	/**
	 * 
	 * @return
	 * 	the right child of the current DirectoryNode
	 */
	public DirectoryNode getRight() {
		return right;
	}
	
	/**
	 * 
	 * @return
	 * 	the parent of the current DirectoryNode
	 */
	public DirectoryNode getParent() {
		return parent;
	}
	
	/**
	 * 
	 * @return
	 * 	the name of the current DirectoryNode
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 * 	checks if the current DirectoryNode is a file or directory
	 */
	public boolean isFile() {
		return isFile;
	}
	
	//Mutator methods:
	/**
	 * 
	 * @param left
	 * 	the new left child of the current DirectoryNode
	 */
	public void setLeft(DirectoryNode left) {
		this.left = left;
	}
	
	/**
	 * 
	 * @param middle
	 * 	the new middle child of the current DirectoryNode
	 */
	public void setMiddle(DirectoryNode middle) {
		this.middle = middle;
	}
	
	/**
	 * 
	 * @param right
	 * 	the new right child of the current DirectoryNode
	 */
	public void setRight(DirectoryNode right) {
		this.right = right;
	}
	
	/**
	 * 
	 * @param parent
	 * 	the new parent of the current DirectoryNode
	 */
	public void setParent(DirectoryNode parent) {
		this.parent = parent;
	}
	
	/**
	 * 
	 * @param name
	 * 	the new name of the current DirectoryNode
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Adds newChild to any of the open child positions of this node (left, middle, or right).
	 * 
	 * <dt>Preconditions:
	 * 	<dd>This node is not a file.
	 * 	<dd>THere is at least one empty position in the children of this node (left, middle, or right).
	 * 
	 * <dt>Postconditions:
	 * 	<dd>newChild has been added as a child of this node. If there is no room for a new node, throw a FullDirectoryException.
	 *
	 * @param newChild
	 * 	the new child DirectoryNode being added to the current DirectoryNode
	 * 
	 * @throws FullDirectoryException
	 * 	Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 * 	Thrown if the current node is a file, as files cannot contain DirectoryNode references (i.e. all files are leaves).
	 */
	public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException {
		if (isFile)
			throw new NotADirectoryException("The node is a file!");
		if (left == null)
			left = newChild;
		else if (middle == null)
			middle = newChild;
		else if (right == null)
			right = newChild;
		else {
			throw new FullDirectoryException("ERROR: Present directory is full.");
		}
	}
}
