import java.io.*;
import java.util.Scanner;
/**
 * PlannerManager runs the main method that runs a menu drive application which first creates an empty Planner object.
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 *
 */
public class PlannerManager {
	public static Planner backup;
	public static boolean restart;
/**
 * @param planner
 * @throws FullPlannerException
 * @throws CloneNotSupportedException
 */
	public static void menu(Planner planner) throws FullPlannerException, CloneNotSupportedException{
		Scanner sc = new Scanner(System.in);
		System.out.println("(A) Add Course");
		System.out.println("(G) Get Course");
		System.out.println("(R) Remove Course");
		System.out.println("(P) Print Courses in Planner");
		System.out.println("(F) Filter by Department Code");
		System.out.println("(L) Look For Course");
		System.out.println("(S) Size");
		System.out.println("(B) Backup");
		System.out.println("(PB) Print Courses in Backup");
		System.out.println("(RB) Revert to Backup");
		System.out.println("(Q) Quit");
		System.out.println();
		System.out.print("Enter a selection: ");
		String selection = sc.nextLine().toUpperCase();
		
		switch (selection) {
			case "A":
				System.out.print("Enter course name: ");
				String courseName = sc.nextLine();
				System.out.print("Enter department: ");
				String department = sc.nextLine();
				System.out.print("Enter course code: ");
				int courseCode = sc.nextInt();
				System.out.print("Enter course section: ");
				byte courseSection = sc.nextByte();
				System.out.print("Enter instructor: ");
				sc.nextLine();
				String instructor = sc.nextLine();
				System.out.print("Enter position: ");
				int position = sc.nextInt();
				Course course = new Course(courseName, department, courseCode, courseSection, instructor);
				planner.addCourse(course, position);
				System.out.println(department + " " + (courseCode + courseSection * .01) + " successfully added to planner.");
				break;
			case "G":
				Planner get = new Planner();
				System.out.print("Enter position: ");
				position = sc.nextInt();
				get.addCourse(planner.getCourse(position));
				get.printAllCourses();
				break;
			case "R":
				System.out.print("Enter position: ");
				position = sc.nextInt();
				Course store = planner.getCourse(position);
				planner.removeCourse(position);
				System.out.println(store.getDepartment() + " " + (store.getCode() + store.getSection() * .01) + " has been succesfully removed from the planner.");
				break;
			case "P":
				planner.printAllCourses();
				break;
			case "F":
				System.out.print("Enter department: ");
				department = sc.nextLine();
				planner.filter(planner, department);
				break;
			case "L":
				System.out.print("Enter course name: ");
				courseName = sc.nextLine();
				System.out.print("Enter department: ");
				department = sc.nextLine();
				System.out.print("Enter course code: ");
				courseCode = sc.nextInt();
				System.out.print("Enter course section: ");
				courseSection = sc.nextByte();
				sc.nextLine();
				System.out.print("Enter instructor: ");
				instructor = sc.nextLine();
				System.out.print("Enter position: ");
				position = sc.nextInt();
				course = new Course(courseName, department, courseCode, courseSection, instructor);
				if (planner.exists(course))
					System.out.println(department + " " + (courseCode + courseSection * .01) + " is found in the planner at position " + position + ".");
				break;
			case "S":
				System.out.println("There are " + planner.size() + " courses in the planner.");
				break;
			case "B":
				backup = (Planner)planner.clone();
				System.out.println("Created a backup of the current planner.");
				break;
			case "PB":
				backup.printAllCourses();
				break;
			case "RB":
				planner = backup;
				System.out.println("Planner successfully reverted to the backup copy.");
				break;
			case "Q":
				System.out.println("Program terminating successfully...");
				restart = false;
				System.exit(0);
			default:
				System.out.println("Invalid Selection!");
		}
	}
/**
 * main method that runs the program
 * @param args
 * @throws FullPlannerException
 * @throws CloneNotSupportedException
 */
	public static void main(String[] args) throws FullPlannerException, CloneNotSupportedException{
		restart = true;
		Planner original = new Planner();
		while (restart)
			menu(original);
	}
}
