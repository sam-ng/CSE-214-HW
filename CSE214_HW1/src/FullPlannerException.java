/**
 * An exception class indicating whether the user attempts to add a course while
 * the planner is full.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class FullPlannerException extends Exception{
	
	public FullPlannerException() {
		super("No more room in planner.");
	}
	
	public FullPlannerException(String message) {
		super(message);
	}
}
