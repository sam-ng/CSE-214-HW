/**
 * The course class is the blueprint to all the course objects
 * that will be added into the planner.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class Course implements Cloneable{
	private String courseName;
	private String department;
	private int code;
	private byte section;
	private String instructor;
/**
 * 
 * @param courseName
 * 		The name of the course.
 * @param department
 * 		The name of the department.
 * @param code
 * 		The code number of the course.
 * @param section
 * 		The section number of the course.
 * @param instructor
 * 		The name of the instructor in the course.
 */
	public Course(String courseName, String department, int code, byte section, String instructor){
		this.courseName = courseName;
		this.department = department;
		this.code = code;
		this.section = section;
		this.instructor = instructor;
	}
/**
 * Returns a copy of the course.
 * Changes to the copy do not affect the original.
 */
	public Object clone() throws CloneNotSupportedException {
		Course clo;
		clo = (Course)super.clone();
		return clo;
	}
/**
 * Returns true if object parameters are equal to the course's parameters.
 */
	public boolean equals(Object obj){
		if (obj instanceof Course) {
			Course compare = (Course) obj;
			return (compare.courseName.equals(courseName)) && 
					(compare.department.equals(department)) && 
					(compare.code == code) && (compare.section == section) && 
					(compare.instructor.equals(instructor));
		}
		else
			return false;
		
	}
/**
 * Accessor methods for the course parameters
 */
	public String getCourseName() {
		return courseName;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public int getCode() {
		return code;
	}
	
	public byte getSection() {
		return section;
	}
	
	public String getInstructor() {
		return instructor;
	}
/**
 * Mutator methods for the course parameters	
 * @param cn
 */
	public void setCourseName(String cn) {
		courseName = cn;
	}
	
	public void setDepartment(String dep) {
		department = dep;
	}
	
	public void setCode(int c) {
		if (c < 0) {
			System.out.println("Error! Invalid input.");
			return;
		}
		code = c;
	}
	
	public void setSection(byte sec) {
		if (sec < 0) {
			System.out.println("Error! Invalid input.");
			return;
		}
		section = sec;
	}
	
	public void setInstructor(String ins) {
		instructor = ins;
	}
}
