import java.io.*;
import java.util.Scanner;
/**
 * TrainManager runs the main method that runs a menu drive application for the train list.
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 *
 */
public class TrainManager {
	public static boolean restart;
	public static int numDangerous = 0;
	
	public static void menu(TrainLinkedList list) {
		ProductLoad load = new ProductLoad("Empty", 0.0, 0.00, false);
		String productName = "";
		Scanner sc = new Scanner(System.in);
		System.out.println("(F) Cursor Forward");
		System.out.println("(B) Cursor Backward");
		System.out.println("(I) Insert Car After Cursor");
		System.out.println("(R) Remove Car at Cursor");
		System.out.println("(L) Set Product Load");
		System.out.println("(S) Search For Product");
		System.out.println("(T) Display Train");
		System.out.println("(M) Display Manifest");
		System.out.println("(D) Remove Dangerous Cars");
		System.out.println("(Q) Quit");
		System.out.println();
		System.out.print("Enter a selection: ");
		String selection = sc.nextLine().toUpperCase();
		
		switch (selection) {
			case "F":
				list.cursorForward();
				break;
			case "B":
				list.cursorBackward();
				break;
			case "I":
				load = new ProductLoad("Empty", 0.0, 0.00, false);
				System.out.print("Enter car length in meters: ");
				double carLength = sc.nextDouble();
				System.out.print("Enter car weight in tons: ");
				double carWeight = sc.nextDouble();
				list.insertAfterCursor(new TrainCar(carLength, carWeight, load));
				System.out.println();
				System.out.println("New train car " + carLength + " meters " + carWeight + " tons inserted into train.");
				break;
			case "R":
				String[] heading = {"Name", "Weight (t)", "Value ($)", "Dangerous"};
				String line = "========================================================";
				TrainCar removed = list.removeCursor();
				String[] arr = {removed.getLoad().getProductName(), removed.getLoad().getWeight()+"", removed.getLoad().getValue()+"", ""};
				if (removed.getLoad().getIsDangerous()) {
					arr[3] = "YES";
					numDangerous--;
				}
				else {
					arr[3] = "NO";
				}
				String table = String.format("%10s%15s%13s%16s", heading) + "\n";
				table += line + "\n";
				table += String.format("%10s%15s%13s%16s", arr);
				System.out.println();
				System.out.println("Car successfully unlinked. The following load has been removed from the train: ");
				System.out.println(table);
				if (numDangerous == 0) {
					list.setIsDangerous(false);
				}
				break;
			case "L":
				System.out.print("Enter produce name: ");
				productName = sc.nextLine();
				System.out.print("Enter product weight in tons: ");
				double productWeight = sc.nextDouble();
				list.loadWeight(productWeight);
				System.out.print("Enter product value in dollars: ");
				double productValue = sc.nextDouble();
				sc.nextLine();
				System.out.print("Enter is product dangerous? (y/n): ");
				String yn = sc.nextLine().toUpperCase();
				boolean isDangerous;
				if (yn.equals("Y")) {
					isDangerous = true;
					list.setIsDangerous(true);
					if (list.getCursorData().isEmpty()) {
						numDangerous++;
					}
				}
				else {
					isDangerous = false;
				}
				load = new ProductLoad(productName, productWeight, productValue, isDangerous);
				list.getCursorData().setLoad(load);
				System.out.println();
				System.out.println(productWeight + " tons of " + productName + " added to the current car.");
				break;
			case "S":
				System.out.print("Enter product name: ");
				productName = sc.nextLine();
				System.out.println();
				list.findProduct(productName);
				break;
			case "T":
				int size = list.size();
				if (size == 0) {
					System.out.println("No train cars available.");
					break;
				}
				double length = list.getCursorData().getLength();
				double weight = list.getCursorData().getWeight();
				double value = list.getCursorData().getLoad().getValue();
				String listDanger;
				if (list.isDangerous()) {
					listDanger = "DANGEROUS";
				}
				else {
					listDanger = "SAFE";
				}
				System.out.println();
				System.out.println("Train: " + size + " cars, " + length + " meters, " + weight + " tons, " + value + " value, " + listDanger + ".");		
				break;
			case "M":
				list.printManifest();
				break;
			case "D":
				list.removeDangerousCars();
				System.out.println();
				System.out.println("Dangerous cars successfully removed from the train.");
				break;
			case "Q":
				System.out.println("Program terminating successfully...");
				restart = false;
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Selection!");
		}
	}
/**
 *	main method that runs the program
 * @param args
 * @param args
 */
	public static void main(String[] args) {
		restart = true;
		TrainLinkedList list = new TrainLinkedList();
		while (restart) {
			menu(list);
		}
	}
}
