import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;
/**
 * PythonTracer contains the main method and will run the trace program.
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 *
 */
public class PythonTracer {

	public static final int SPACE_COUNT = 4;
/**
 * Opens the indicated file and traces through the code of the Python function contained within the file, returning the Big-Oh order of complexity of the function.
 * During operation, the stack trace should be printed to the console as code blocks are pushed to/popped from the stack.
 * 
 * Preconditions: filename is not null and the file it names contains a single Python function with valid syntax (Reminder: you do NOT have to check for invalid syntax).
 *  
 * @param filename
 * @return A Complexity object representing the total order of complexity of the Python code contained within the file.
 * @throws IOException
 */
	public static Complexity traceFile(String filename) throws IOException {
		if (filename == null) {
			throw new NullPointerException("Error! filename is null.");
		}
		Stack<CodeBlock> stack = new Stack<CodeBlock>();
		int stackSize = 0;
		//ArrayList<double> blockNameArr = new ArrayList<double>();
		String loopVariable = ""; 
		
		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader inStream = new InputStreamReader(fis);
		BufferedReader reader = new BufferedReader(inStream);
		String line = reader.readLine();
		while (line != null) {
			int numSpace = 0;
			for (int i = 0; i < line.length()-1; i++) {
				if (!line.substring(i, i+1).equals(" "))
					break;
				numSpace++;
			}
			if (line.length() > 0 && !line.contains("#")) {
				int indents = numSpace / SPACE_COUNT;
				//System.out.println(indents);
				//System.out.println(stackSize);
				while (indents < stackSize) {
					if (indents == 0) {
						reader.close();
						return stack.peek().getBlockComplexity();
					}
					else {
						CodeBlock oldTop = stack.pop();
						stackSize--;
						//System.out.println(oldTop.getBlockComplexity());
						//System.out.println(oldTop.getHighestSubComplexity());
						int oldTopComplexityN_Power = oldTop.getBlockComplexity().getN_Power() + oldTop.getHighestSubComplexity().getN_Power();
						int oldTopComplexityLog_Power = oldTop.getBlockComplexity().getLog_Power() + oldTop.getHighestSubComplexity().getLog_Power();
						CodeBlock currentTop = stack.pop();
						int currentTopHighestComplexityN_Power = currentTop.getHighestSubComplexity().getN_Power();
						//System.out.println(currentTop.getHighestSubComplexity().getN_Power());
						int currentTopHighestComplexityLog_Power = currentTop.getHighestSubComplexity().getLog_Power();
						//System.out.println(currentTop.getHighestSubComplexity().getLog_Power());
						//System.out.println(oldTopComplexityN_Power);
						//System.out.println(currentTopHighestComplexityN_Power);
						boolean update = false;
						if (oldTopComplexityN_Power > currentTopHighestComplexityN_Power) {
							currentTopHighestComplexityN_Power = oldTopComplexityN_Power;
							currentTopHighestComplexityLog_Power = oldTopComplexityLog_Power;
							update = true;
						}
						else if (oldTopComplexityN_Power == currentTopHighestComplexityN_Power) {
							if (oldTopComplexityLog_Power >= currentTopHighestComplexityLog_Power) {
								currentTopHighestComplexityLog_Power = oldTopComplexityLog_Power;
								update = true;
							}
						}
						Complexity newHighestComplexity = new Complexity(currentTopHighestComplexityN_Power, currentTopHighestComplexityLog_Power);
						currentTop.setHighestSubComplexity(newHighestComplexity);
						stack.push(currentTop);
						if (update) {
							System.out.println("Leaving block , updating block:");
							System.out.println("Block: " + " block complexity = " + currentTop.getBlockComplexity() + " highest sub-complexity = " + currentTop.getHighestSubComplexity());
						}
						else {
							System.out.println("Leaving block , nothing to update.");
							System.out.println("Block: " + " block complexity = " + currentTop.getBlockComplexity() + " highest sub-complexity = " + currentTop.getHighestSubComplexity());
						}
					}
				}
				CodeBlock codeBlock = new CodeBlock();
				for (int i = 0; i < codeBlock.BLOCK_TYPES.length; i++) {
					if (line.contains(codeBlock.BLOCK_TYPES[i])) {
						String keyword = codeBlock.BLOCK_TYPES[i];
						if (keyword.equals(" for ")) {
							if (line.contains(" N:")) {
								Complexity complexity = new Complexity(1, 0);
								codeBlock.setBlockComplexity(complexity);
								//System.out.println(codeBlock.getBlockComplexity());
								//System.out.println(codeBlock.getHighestSubComplexity());
								stack.push(codeBlock);
								stackSize++;
							}
							else if (line.contains(" log_N:")) {
								Complexity complexity = new Complexity(0, 1);
								codeBlock.setBlockComplexity(complexity);
								stack.push(codeBlock);
								stackSize++;
							}
							System.out.println("Entering block 'for':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
						else if (keyword.equals(" while ")) {
							loopVariable = line.substring(line.indexOf("while")+6, line.indexOf(" ", line.indexOf("while ")+6));
							Complexity complexity = new Complexity(0, 0);
							codeBlock.setLoopVariable(loopVariable);
							codeBlock.setBlockComplexity(complexity);
							stack.push(codeBlock);
							stackSize++;
							System.out.println("Entering block 'while':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
						else if (keyword.equals("def ")) {
							Complexity complexity = new Complexity(0, 0);
							codeBlock.setBlockComplexity(complexity);
							stack.push(codeBlock);
							stackSize++;
							System.out.println("Entering block 'def':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
						else if (keyword.equals(" if ")) {
							Complexity complexity = new Complexity(0, 0);
							codeBlock.setBlockComplexity(complexity);
							stack.push(codeBlock);
							stackSize++;
							System.out.println("Entering block 'if':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
						else if (keyword.equals(" else if ")) {
							Complexity complexity = new Complexity(0, 0);
							codeBlock.setBlockComplexity(complexity);
							stack.push(codeBlock);
							stackSize++;
							System.out.println("Entering block 'else if':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
						else if (keyword.equals(" else  ")) {
							Complexity complexity = new Complexity(0, 0);
							codeBlock.setBlockComplexity(complexity);
							stack.push(codeBlock);
							stackSize++;
							System.out.println("Entering block 'else':");
							System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
						}
					}
				}
				if(line.contains(loopVariable + " -=")) {
					stack.pop();
					Complexity complexity = new Complexity(1, 0);
					codeBlock.setBlockComplexity(complexity);
					stack.push(codeBlock);
					System.out.println("Found update statement, updating block ");
					System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());
				}
				if(line.contains(loopVariable + " /= ")) {
					stack.pop();
					Complexity complexity = new Complexity(0, 1);
					codeBlock.setBlockComplexity(complexity);
					stack.push(codeBlock);
					System.out.println("Found update statement, updating block ");
					System.out.println("Block: " + " block complexity = " + codeBlock.getBlockComplexity() + " highest sub-complexity = " + codeBlock.getHighestSubComplexity());	
				}
			}
			line = reader.readLine();
		}
		while (stackSize > 1) {
			CodeBlock oldTop = stack.pop();
			stackSize--;
			int oldTopComplexityN_Power = oldTop.getBlockComplexity().getN_Power();
			int oldTopComplexityLog_Power = oldTop.getBlockComplexity().getLog_Power();
			CodeBlock currentTop = stack.pop();
			int currentTopHighestComplexityN_Power = currentTop.getHighestSubComplexity().getN_Power();
			int currentTopHighestComplexityLog_Power = currentTop.getHighestSubComplexity().getLog_Power();
			if (oldTopComplexityN_Power > currentTopHighestComplexityN_Power) {
				currentTopHighestComplexityN_Power = oldTopComplexityN_Power;
			}
			if (oldTopComplexityLog_Power > currentTopHighestComplexityLog_Power) {
				currentTopHighestComplexityLog_Power = oldTopComplexityLog_Power;
			}
			Complexity newHighestComplexity = new Complexity(currentTopHighestComplexityN_Power, currentTopHighestComplexityLog_Power);
			currentTop.setHighestSubComplexity(newHighestComplexity);
			stack.push(currentTop);
		}
		return stack.pop().getHighestSubComplexity();
	}
/**
 * Prompts the user for the name of a file containing a single Python function, determines its order of complexity, and prints the result to the console.	
 * @param args
 * @throws IOException
 */
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of a file containing a single Python function or Press (Q) to quit: ");
		String filename = sc.nextLine();
		if (filename.equalsIgnoreCase("Q")) {
			System.out.println("Program terminating successfully...");
			System.exit(0);
		}
		Complexity highestComplexity = traceFile(filename);
		System.out.println("Overall complexity of " + filename + ": " + highestComplexity);
	}
}
