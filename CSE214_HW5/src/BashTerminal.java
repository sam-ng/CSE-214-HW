import java.util.Scanner;
/**
 * The BashTerminal should contain a single main method which allows a user to interact with a file system implemented by an instance of DirectoryTree.
 * 
 * @author Samuel Ng
 *	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class BashTerminal {
	public static boolean run = true;
	public static DirectoryTree tree = new DirectoryTree();
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * terminal method runs the file system
	 */
	public static void terminal() {
		String userHost = "[samung@little_bytes]: $ ";
		System.out.print(userHost);
		String command = sc.nextLine();
		if (command.equals("pwd"))
			System.out.println(tree.presentWorkingDirectory());
		else if (command.equals("ls"))
			System.out.println(tree.listDirectory());
		else if (command.equals("ls -R"))
			tree.printDirectoryTree();
		else if (command.contains("cd ")) {
			if (command.equals("cd /"))
				tree.resetCursor();
			else if (command.contains(" .."))
				tree.moveToParent();
			else if (command.substring("cd ".length()+1).contains("/")) {
				if (command.contains("//")) {
					System.out.println("Typo!");
				}
				else {
					tree.moveByPath(command.substring("cd ".length()));
				}
			}
			else {
				try {
					tree.changeDirectory(command.substring("cd ".length()));
				} catch (NotADirectoryException e) {
					System.out.println("ERROR: Cannot change directory into a file.");
				}
			}
		}
		else if (command.contains("mkdir ")) {
			try {
				tree.makeDirectory(command.substring("mkdir ".length()));
			} catch (IllegalArgumentException e) {
				System.out.println("Illegal name.");
			} catch (FullDirectoryException e) {
				System.out.println("ERROR: Present directory is full.");
			}
		}
		else if (command.contains("touch ")) {
			try {
				tree.makeFile(command.substring("touch ".length()));
			} catch (IllegalArgumentException e) {
				System.out.println("Illegal name.");
			} catch (FullDirectoryException e) {
				System.out.println("ERROR: Present directory is full!");
			}
		}
		else if (command.equals("exit")) {
			run = false;
			System.out.println("Program terminating normally");
			System.exit(0);
		}
		//EXTRA CREDIT FUNCTIONS:
		else if (command.contains("find ")) {
			tree.find(command.substring("find ".length()));
		}
		//command "cd .." case is grouped with the other "cd cases
		else if (command.contains("mv ")) {
			String arr[] = command.split(" ");
			String src = arr[1];
			String dst = arr[2];
			try {
				tree.moveSubtree(src, dst);
			} catch (FullDirectoryException e) {
				System.out.println("ERROR: Present directory is full!");
			}
		}
		else {
			System.out.println("Invalid command.");
		}
	}
	
	/**
	 * Runs a program which takes user input and builds a DirectoryTree using the commands indicated above.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting bash terminal.");
		while(run)
			terminal();
	}
}
