/**
 * The Planner class holds a list of all of the courses. 
 * Contains methods to access, add, remove, and display courses
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class Planner {
	private static final int MAX_COURSES = 50; //Constant representing the maximum number of courses the planner can hold
	private Course[] list; //The array that will hold all the courses
	
	public Planner () {
		list = new Course[MAX_COURSES]; //Instantiates the list
	}
/**
 * Preconditions: This Planner has been instantiated.
 * @return the number of courses currently in the list
 */
	public int size() {
		int size = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] == null) {
				size = i;
				break;
			}
		}
		return size;
	}
/**
 * 	
 * @param newCourse is the course that will be added into the list
 * Preconditions: This Course object has been instantiated and 1 <= position <= items_currently_in_list+1.
 * The number of Course objects in this Planner is less than MAX-COURSES.
 * @param position representing the number position in the list
 * Postconditions: The new Course is now listed in the correct preference on the list. All Courses that were originally
 * greater than or equal to position are moved back one position.
 * @throws FullPlannerException, IllegalArgumentException
 * 
 */
	public void addCourse(Course newCourse, int position) throws FullPlannerException{
		int size = size();
		if (position < 1 || position > size+1) {
			throw new IllegalArgumentException("Invalid Position!");
		}
		for (int i = size; i >= position; i--) {
			list[i] = list[i-1];
		}
		list[position-1] = newCourse;
	}
/**
 * Preconditions: The Planner object has been instantiated and 1 <= position <= items_currently_in_list.
 * @param newCourse is the course that will be added into the list
 */
	public void addCourse(Course newCourse) {
		list[size()] = newCourse;
	}
/**
 * Preconditions: This Planner has been instantiated and 1 <= position <= items_currently_in_list.
 * @param position representing the number position in the list
 * Postconditions: The Course at the desired position has been removed. All Courses that were originally greater than
 * or equal to position are moved backward one position.
 * @throws IllegalArgumentException
 */
	public void removeCourse(int position) {
		int size = size();
		if (position < 1 || position > size) {
			throw new IllegalArgumentException("Invalid Position!");
		}
		for (int i = position-1; i < size; i++) {
			list[i] = list[i+1];
		}
		list[size-1] = null;
	}
/**
 * Preconditions: The Planner object has been instantiated and 1 <= position <= items_currently_in_list.
 * @param position representing the number position in the list
 * @return course at position
 * @throws IllegalArgumentException
 */
	public Course getCourse(int position) {
		int size = size();
		if (position < 1 || position > size) {
			throw new IllegalArgumentException("Invalid Position!");
		}
		return list[position-1];
	}
/**
 * Prints all Courses that are within the specified department.
 * Preconditions: This Planner object has been instantiated.
 * @param planner is the planner object that holds all of the courses
 * @param department is the specified department 
 * Postconditions: Displays a neatly formatted table of each course filtered from the Planner. Keep the preference numbers the same.
 */
	public static void filter(Planner planner, String department) {
		Course[] depList = new Course[50];
		int index = 0;
		for (int i = 0; i < planner.size(); i++) {
			if (planner.list[i].getDepartment().equals(department)) {
				depList[index] = planner.list[i];
				index++;
			}
		}
		String[] heading = {"No.", "Course Name", "Department", "Code", "Section", "Instructor"};
		String line = "--------------------------------------------------------------------------\n";
		String table = String.format("%-4s%-26s%11s%5s%8s%11s", heading) + "\n" + line;
		String[] arr = new String[6];
		for (int j = 0; j < depList.length; j++) {
			Course element = depList[j];
			arr[0] = j + 1 + "";
			arr[1] = element.getCourseName();
			arr[2] = element.getDepartment();
			arr[3] = element.getCode() + "";
			arr[4] = element.getSection() + "";
			arr[5] = element.getInstructor();
			table += String.format("%-4s%-26s%11s%5s%8s%11s", arr) + "\n";
		}
		System.out.println(table);
	}
/**
 * Checks whether a certain Course is already in the list.
 * Preconditions: This Planner and Course has both been instantiated.
 * @param course is the course being checked for
 * @return true if course is in planner
 */
	public boolean exists(Course course) {
		int size = size();
		for (int i = 0; i < size; i++) {
			if (list[i].equals(course)) {
				return true;
			}
		}
		return false;
	}
/**
 * Creates a copy of this Planner. Subsequent changes to the copy will not affect the original.
 * Preconditions: This Planner object has been instantiated.
 * @return a copy of the Planner object
 */
	public Object clone() throws CloneNotSupportedException {
		Planner plan;
		plan = (Planner)super.clone();
		return plan;
	}
/**
 * Prints a neatly formatted table of each item in the list with its position number.
 * Preconditions: This Planner has been instantiated.
 * Postconditions: Displays a neatly formatted table of each course from the Planner.
 */
	public void printAllCourses() {
		System.out.println(toString());
	}
/**
 * Gets the String representation of this Planner object, which is a neatly formatted table of each Course in the
 * Planner on its own line with its position number as shown in the sample output.
 * @return the String representation of this Planner object.
 */
	public String toString() {
		String[] heading = {"No.", "Course Name", "Department", "Code", "Section", "Instructor"};
		String line = "--------------------------------------------------------------------------\n";
		String table = String.format("%-4s%-26s%11s%5s%8s%11s", heading) + "\n" + line;
		String[] arr = new String[6];
		for (int i = 0; i < size(); i++) {
			Course element = list[i];
			arr[0] = i + 1 + "";
			arr[1] = element.getCourseName();
			arr[2] = element.getDepartment();
			arr[3] = element.getCode() + "";
			arr[4] = element.getSection() + "";
			arr[5] = element.getInstructor();
			table += String.format("%-4s%-26s%11s%5s%8s%11s", arr) + "\n";
		}
		return table;
	}
}
