/**
 * The CodeBlock class describes a nest of code. It contains an array of final keywords, blockComplexity, highestSubComplexity, name, and loopVariable.
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class CodeBlock {
	public static final String[] BLOCK_TYPES = {"def ", " for ", " while ", " if ", " elif " , " else "};
	public static final int DEF_INDEX = 0;
	public static final int FOR_INDEX = 1;
	public static final int WHILE_INDEX = 2;
	public static final int IF_INDEX = 3;
	public static final int ELIF_INDEX = 4;
	public static final int ELSE_INDEX = 5;
	private Complexity blockComplexity;
	private Complexity highestSubComplexity;
	private String name;
	private String loopVariable;
	
	public CodeBlock() {
		blockComplexity = new Complexity(0, 0);
		highestSubComplexity = new Complexity(0, 0);
		name = "";
		loopVariable = null;
	}
/**
 * Accessor methods	
 * @return blockComplexity
 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
/**
 * 	
 * @return highestSubComplexity
 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
/**
 * 	
 * @return name
 */
	public String getName() {
		return name;
	}
/**
 * 	
 * @return loopVariable
 */
	public String loopVariable() {
		return loopVariable;
	}
/**
 * Mutator methods:	
 * @param blockComplexity
 */
	public void setBlockComplexity(Complexity blockComplexity) {
		this.blockComplexity = blockComplexity;
	}
/**
 * 	
 * @param highestSubComplexity
 */
	public void setHighestSubComplexity(Complexity highestSubComplexity) {
		this.highestSubComplexity = highestSubComplexity;
	}
/**
 * 	
 * @param name
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * 	
 * @param loopVariable
 */
	public void setLoopVariable(String loopVariable) {
		this.loopVariable = loopVariable;
	}
}
