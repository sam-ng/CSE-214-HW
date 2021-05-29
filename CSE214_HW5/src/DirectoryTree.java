import java.util.Scanner;
/**
 * The DirectoryTree class implements a ternary (3-child) tree of DirectoryNodes.
 * The class should contain a reference to the root of the tree, a cursor for the present working directory, and various methods for insertion and deletion.
 * 
 * @author Samuel Ng
 *	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class DirectoryTree {
	private DirectoryNode root;
	private DirectoryNode cursor;
	private int depth;
	private	Scanner sc = new Scanner(System.in);
	private boolean found = false;

	/**
	 * Initializes a DirectoryTree object with a single DirectoryNode named "root".
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The tree contains a single DirectoryNode named "root", and both cursor and root reference this node.
	 * 	<dd>The DirectoryNode member variable of DirectoryTree named root should reference a DirectoryNode whose name is "root", i.e. root.getName().equals("root") is true.
	 */
	public DirectoryTree() {
		root = new DirectoryNode("root", false);
		cursor = root;
		depth = 0;
	}
	
	/**
	 * 
	 * @return
	 * 	the current depth of the tree
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Moves the cursor to the root node of the tree.
	 * 
	 * <dt>
	 * 	<dd>The cursor now references the root node of the tree.
	 */
	public void resetCursor() {
		cursor = root;
		depth = 0;
	}
	
	/**
	 * Recursive helper method for changeDirectory(String name).
	 * @param name
	 * 	the name of the destination directory
	 */
	public void changeDirectoryHelper(String name) {
		if (cursor.getName().equals(name))
			return;
		if (cursor.getLeft() != null) {
			depth++;
			cursor = cursor.getLeft();
			changeDirectoryHelper(name);
		}
		if (cursor.getMiddle() != null) {
			depth++;
			cursor = cursor.getMiddle();
			changeDirectoryHelper(name);
		}
		if (cursor.getRight() != null) {
			depth++;
			cursor = cursor.getRight();
			changeDirectoryHelper(name);
		}
		if (!cursor.getName().equals(name)) {
			cursor = cursor.getParent();
			depth--;
		}
	}
	
	/**
	 * 
	 * Moves the cursor to the directory with the name indicated by <code>name</code>.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>"name" references a valid directory ("name" cannot reference a file).
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The cursor now references the directory with the name indicated by <code>name</code>. If a child could not be found with that name, then the user is prompted to enter a different directory name.
	 * 
	 * @param name
	 * 	the name of the destination directory
	 * 
	 * @throws NotADirectoryException
	 * 	thrown if the node with the indicated name is a file, as files cannot be selected by the cursor, or cannot be found.
	 */
	public void changeDirectory(String name) throws NotADirectoryException {
		DirectoryNode tempCursor = cursor;
		int tempDepth = depth;
		//resetCursor();
		changeDirectoryHelper(name);
		if (cursor == null || !cursor.getName().equals(name)) {
			cursor = tempCursor;
			depth = tempDepth;
			System.out.println("ERROR: No such directory named '" + name + "'.");
			System.out.print("Enter another directory name: ");
			String newName = sc.nextLine();
			changeDirectory(newName);
		}
		else if (cursor.getName().equals(name)) {
			if (cursor.isFile())
				throw new NotADirectoryException(name + " is not a directory!");
		}
	}
	
	/**
	 * Returns a String containing the path of directory names from the root node of the tree to the cursor, with each name separated by a forward slash "/".
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The cursor remains at the same DirectoryNode.
	 * 
	 * @return
	 * 	the String representation of the directory path
	 */
	public String presentWorkingDirectory() {
		DirectoryNode tempCursor = cursor;
		int tempDepth = depth;
		String path = "";
		for (DirectoryNode cursor = tempCursor; cursor != root; cursor = cursor.getParent()) {
			path = "/" + cursor.getName() + path;
			depth--;
		}
		path = root.getName() + path;
		cursor = tempCursor;
		depth = tempDepth;
		return path;
	}
	
	/**
	 * Returns a String containing a space-separated list of names of all the child directories or files of the curosr.
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The cursor remains at the same DirectoryNode.
	 * 
	 * @return
	 * 	the String representation of the list of children nodes of the current DirectoryNode
	 */
	public String listDirectory() {
		String list = "";
		if (cursor.getLeft() != null)
			list += cursor.getLeft().getName() + " ";
		if (cursor.getMiddle() != null)
			list += cursor.getMiddle().getName() + " ";
		if (cursor.getRight() != null)
			list += cursor.getRight().getName();
		return list;
	}
	
	/**
	 * Recursive helper method for printDirectoryTree().
	 */
	public void printDirectoryTreeHelper() {
		String indent = "    ";
		for (int i = 0; i < depth; i++) {
			System.out.print(indent);
		}
		if (!cursor.isFile()) {
			System.out.println(" | - " + cursor.getName());
		}
		else if (cursor.isFile()) {
			System.out.println(" - " + cursor.getName());
		}
		if (cursor.getLeft() != null) {
			cursor = cursor.getLeft();
			depth++;
			printDirectoryTreeHelper();
		}
		if (cursor.getMiddle() != null) {
			cursor = cursor.getMiddle();
			depth++;
			printDirectoryTreeHelper();
		}
		if (cursor.getRight() != null) {
			cursor = cursor.getRight();
			depth++;
			printDirectoryTreeHelper();
		}
		cursor = cursor.getParent();
		depth--;
	}
	
	/**
	 * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The cursor remains at the same DirectoryNode.
	 */
	public void printDirectoryTree() {
		DirectoryNode tempCursor = cursor;
		int tempDepth = depth;
		depth = 0;
		printDirectoryTreeHelper();
		cursor = tempCursor;
		depth = tempDepth;
	}
	
	/**
	 * Creates a directory with the indicated name and adds it to the children of the cursor node.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>"name" is a legal argument (does not contain spaces " " or forward slashes "/").
	 * 
	 * <dt>Postconditions:
	 * 	<dd>A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
	 * 
	 * @param name
	 * 	the name of the directory to add
	 * 
	 * @throws IllegalArgumentException
	 * 	thrown if the "name" argument is invalid.
	 * @throws FullDirectoryException
	 * 	thrown if all child references of this directory are occupied.
	 */
	public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException {
		if (name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException("Name should not contain spaces or forward slashes.");
		if (cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null)
			throw new FullDirectoryException("ERROR: Present directory is full.");
		DirectoryNode newChild = new DirectoryNode(name, false);
		newChild.setParent(cursor);
		try {
			cursor.addChild(newChild);
		} catch (NotADirectoryException e) {
			System.out.println("Node is not a directory.");
		}
	}
	
	/**
	 * Creates a file with the indicated name and adds it to the children of the cursor node.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>"name" is a legal argument (does not contain spaces " " or forward slashes "/").
	 * 
	 * <dt>Postconditions:
	 * 	<dd>A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
	 * 
	 * @param name
	 * 	the name of the file to add
	 * 
	 * @throws IllegalArgumentException
	 * 	thrown if the "name" argument is invalid.
	 * @throws FullDirectoryException
	 * 	thrown if all child references of this directory are occupied.
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException {
		if (name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException("Name should not contain spaces or forward slashes.");
		DirectoryNode newChild = new DirectoryNode(name, true);
		newChild.setParent(cursor);
		if (cursor.getLeft() == null)
			cursor.setLeft(newChild);
		else if (cursor.getMiddle() == null)
			cursor.setMiddle(newChild);
		else if (cursor.getRight() == null)
			cursor.setRight(newChild);
		else {
			throw new FullDirectoryException("ERROR: Present directory is full.");
		}
	}
	
	//EXTRA CREDIT FUNCTIONS:
	public void findHelper(String name) {
		if (cursor.getName().equals(name)) {
			System.out.println(presentWorkingDirectory());
			found = true;
		}
		if (cursor.getLeft() != null) {
			depth++;
			cursor = cursor.getLeft();
			findHelper(name);
		}
		if (cursor.getMiddle() != null) {
			depth++;
			cursor = cursor.getMiddle();
			findHelper(name);
		}
		if (cursor.getRight() != null) {
			depth++;
			cursor = cursor.getRight();
			findHelper(name);
		}
		if (cursor.getParent() != null) {
			cursor = cursor.getParent();
			depth--;
		}
	}
	
	public void find(String name) {
		found = false;
		DirectoryNode tempCursor = cursor;
		int tempDepth = depth;
		resetCursor();
		findHelper(name);
		if (!found)
			System.out.println("ERROR: No such file exists.");
		cursor = tempCursor;
		depth = tempDepth;
	}
	
	public void moveToParent() {
		if (cursor.getName().equals("root")) {
			System.out.println("ERROR: Already at root directory.");
			return;
		}
		cursor = cursor.getParent();
		depth--;
	}
	
	public void moveByPath(String path) {
		String[] arr = path.split("/");
		resetCursor();
		try {
			changeDirectory(arr[0]);
		} catch (NotADirectoryException e) {
			System.out.println("ERROR: Cannot change directory into a file.");
		}
		for (int i = 1; i < arr.length; i++) {
			if (cursor.getLeft().getName().equals(arr[i]))
				cursor = cursor.getLeft();
			else if (cursor.getMiddle().getName().equals(arr[i]))
				cursor = cursor.getMiddle();
			else if (cursor.getRight().getName().equals(arr[i]))
				cursor = cursor.getRight();
		}
	}
	
	public void moveSubtree(String src, String dst) throws FullDirectoryException {
		DirectoryNode tempCursor = cursor;
		int tempDepth = depth;
		DirectoryNode srcCursor = cursor;
		DirectoryNode dstCursor = cursor;
		moveByPath(src);
		srcCursor = cursor;
		moveByPath(dst);
		dstCursor = cursor;
		if (srcCursor.getParent().getLeft().equals(srcCursor))
			srcCursor.getParent().setLeft(null);
		else if (srcCursor.getParent().getMiddle().equals(srcCursor))
			srcCursor.getParent().setMiddle(null);
		else if (srcCursor.getParent().getRight().equals(srcCursor))
			srcCursor.getParent().setRight(null);
		srcCursor.setParent(dstCursor);
		if (dstCursor.getLeft() == null)
			dstCursor.setLeft(srcCursor);
		else if (dstCursor.getMiddle() == null)
			dstCursor.setMiddle(srcCursor);
		else if (dstCursor.getRight() == null)
			dstCursor.setRight(srcCursor);
		else {
			throw new FullDirectoryException("ERROR: Present directory is full.");
		}
		cursor = tempCursor;
		depth = tempDepth;
	}
}
