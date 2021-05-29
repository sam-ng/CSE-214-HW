/**
 * The TrainLinkedList class contains the head, tail, cursor, position (int), size (int), length (double), value (double), weight (double), isDangerous (boolean). The list contains
 * methods that structures the nodes into a doubly linked list and allows the user to access and modify the cursor and node information.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class TrainLinkedList {
	private TrainCarNode head;
	private TrainCarNode tail;
	private TrainCarNode cursor;
	private int position;
	private int size;
	private double length;
	private double value;
	private double weight;
	private boolean isDangerous;
	private TrainCarNode temp;
/**
 * Constructs an instance of the TrainLinkedList with no TrainCar objects in it.
 * Postconditions:
 * 		This TrainLinkedList has been initialized to an empty linked list.
 * 		head, tail, and cursor are all set to null.
 */
	public TrainLinkedList() {
		head = null;
		tail = null;
		cursor = null;
		position = 1;
		isDangerous = false;
	}
/**
 * Returns a reference to the TrainCar at the node currently referenced by the cursor.
 * Preconditions:
 * 		The list is not empty (cursor is not null).
 * @return The reference to the TrainCar at the node currently referenced by the cursor.
 */
	public TrainCar getCursorData() {
		return cursor.getCar();
	}
/**
 * Places car in the node currently referenced by the cursor.
 * Preconditions:
 * 		The list is not empty (cursor is not null).
 * Postconditions:
 * 		The cursor node now contains a reference to car as its data.
 * @param car
 */
	public void setCursorData(TrainCar car) {
		cursor.setCar(car);
	}
/**
 * Moves the cursor to point at the next TrainCarNode.
 * Preconditions:
 * 		The list is not empty (cursor is not null) and cursor does not currently reference the tail of the list.
 * Postconditions:
 * 		The cursor has been advanced to the next TrainCarNode, or has remained at the tail of the list.
 */
	public void cursorForward() {
		if (cursor != null && cursor != tail) {
			cursor = cursor.getNext();
			System.out.println("Cursor has moved to the next car successfully.");
			position++;
			return;
		}
		System.out.println("No next car, cursor cannot move forward.");
	}
/**
 * Moves the cursor to point at the previous TrainCarNode.
 * Preconditions:
 * 		The list is not empty (cursor is not null) and the cursor does not currently reference the head of the list.
 * Postconditions:
 * 		The cursor has been moved back to the previous TrainCarNode, or has remained at the head of the list.
 */
	public void cursorBackward() {
		if (cursor != null && cursor != head) {
			cursor = cursor.getPrev();
			System.out.println("Cursor has moved to the previous car successfully.");
			position--;
			return;
		}
		System.out.println("No previous car, cursor cannot move backward.");
	}
/**
 * Inserts a car into the train after the cursor position.
 * @param newCar - the new TrainCar to be inserted into the train.
 * Preconditions:
 * 		This TrainCar object has been instantiated
 * Postconditions:
 * 		The new TrainCar has been inserted into the train after the position of the cursor.
 * 		All TrainCar objects previously on the train are still on the train, and their order has been preserved.
 * 		The cursor now points to the inserted car.
 * @throws IllegalArgumentException - Indicates that newCar is null
 */
	public void insertAfterCursor(TrainCar newCar) {
		if (newCar == null) {
			throw new IllegalArgumentException("newCar is null.");
		}
		TrainCarNode newNode = new TrainCarNode(null, null, newCar);
		if (cursor == null) {
			head = newNode;
			tail = newNode;
			cursor = newNode;
		}
		else {
			newNode.setNext(cursor.getNext());
			cursor.setNext(newNode);
			newNode.setPrev(cursor);
			cursor = newNode;
			if (cursor.getNext() == null) {
				tail = cursor;
			}
		}
		size++;
		length += newCar.getLength();
		value += newCar.getLoad().getValue();
		weight += newCar.getWeight();
	}
/**
 * Removes the TrainCarNode referenced by the cursor and returns the TrainCar contained within the node.
 * Preconditions:
 * 		The cursor is not null.
 * Postconditions:
 * 		The TrainCarNode referenced by the cursor has been removed from the train.
 * 		The cursor now references the next node, or the previous node if no next node exists.
 * @return removed TrainCar
 */
	public TrainCar removeCursor() {
		if (cursor == null) {
			throw new NullPointerException("Cursor is null.");
		}
		TrainCar removed = cursor.getCar();
		if (cursor.getNext() == null && cursor.getPrev() == null) {
			cursor = null;
			head = null;
			tail = null;
		}
		else if (cursor == head) {
			cursor = cursor.getNext();
			head = head.getNext();
			cursor.setPrev(null);;
			head.setPrev(null);
		}
		else if (cursor == tail) {
			cursor = cursor.getPrev();
			tail = tail.getPrev();
			cursor.setNext(null);
			tail.setNext(null);
			
		}
		else {
			cursor = cursor.getNext();
			cursor.setNext(cursor.getNext().getNext());
			cursor.getNext().getNext().setPrev(cursor);
		}
		size--;
		length -= removed.getLength();
		value -= removed.getLoad().getValue();
		weight -= removed.getWeight() + removed.getLoad().getWeight();
		return removed;
	}
/**
 * Determines the number of TrainCar objects currently on the train.
 * @return The number of TrainCar objects on this train.
 */
	public int size() {
		return size;
	}
/**
 * Returns the total length of the train in meters.
 * @return The sum of the lengths of each TrainCar in the train.
 */
	public double getLength() {
		return length;
	}
/**
 * Returns the total value of product carried by the train.
 * @return The sum of the values of each TrainCar in the train.
 */
	public double getValue() {
		return value;
	}
/**
 * Returns the total weight in tons of the train.
 * @return the sum of the weight of each TrainCar plus the sum of the ProductLoad carried by that car.
 */
	public double getWeight() {
		return weight;
	}
/**
 * Whether or not there is a dangerous product on one of the TrainCar objects on the train.
 * @return true if the train contains at least one TrainCar carrying a dangerous ProductLoad, false otherwise.
 */
	public boolean isDangerous() {
		return isDangerous;
	}
/**
 * Mutator methods:
 * @param isDangerous
 */
	public void setIsDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
/**
 * 
 * @param weight
 */
	public void loadWeight(double weight) {
		this.weight += weight;
	}
/**
 * Searches the list for all ProductLoad objects with the indicated name and sums together their weight and value (Also keeps track of whether the product
 * is dangerous or not), then prints a single ProductLoad record to the console.
 * @param name - the name of the ProductLoad to find on the train.
 */
	public void findProduct(String name) {
		double valueSum = 0;
		double lengthSumCar = 0;
		double weightSumCar = 0;
		double weightSumLoad = 0;
		boolean isDangerous = false;
		String productName = "Empty";
		temp = cursor;
		for (cursor = head; cursor != null; cursor=cursor.getNext()) {
			if (cursor.getCar().getLoad().getProductName().equals(name)) {
				valueSum += cursor.getCar().getLoad().getValue();
				lengthSumCar += cursor.getCar().getLength();
				weightSumCar += cursor.getCar().getWeight();
				weightSumLoad += cursor.getCar().getLoad().getWeight();
				isDangerous = cursor.getCar().getLoad().getIsDangerous();
				productName = name;
			}
		}
		String[] table1Heading1 = {"", "CAR:", "", "", "", "LOAD:", "", "", ""};
		String[] table1Heading2 = {"", "Num", "Length (m)", "Weight (t)", "|", "Name", "Weight (t)", "Value ($)", "Dangerous"};
		String line = "============================================================================================";
		String table = String.format("%-3s%-7s%-14s%-5s%3s%10s%15s%13s%16s", table1Heading1) + "\n";
		table += String.format("%-3s%-7s%-14s%-5s%3s%10s%15s%13s%16s", table1Heading2) + "\n" + line + "\n";
		String[] arr = new String[9];
		arr[0] = "->";
		arr[1] = 1+"";
		arr[2] = lengthSumCar+"";
		arr[3] = weightSumCar+"";
		arr[4] = "|";
		arr[5] = productName;
		arr[6] = weightSumLoad+"";
		arr[7] = valueSum+"";
		if (isDangerous) {
			arr[8] = "YES";
		}
		else {
			arr[8] = "NO";
		}
		table += String.format("%-3s%-7s%-14s%-10s%3s%10s%15s%13s%16s", arr);
		System.out.println(table);
		cursor = temp;
	}
/**
 * Prints a neatly formatted table of the car number, car length, car weight, load name, load weight,
 * load value, and load dangerousness for all of the car on the train.
 */
	public void printManifest() {
		System.out.println(toString());
	}
/**
 * Removes all dangerous cars from the train, maintaining the order of the cars in the train.
 * Postconditions:
 * 		All dangerous cars have been removed from this train.
 * 		The order of all non-dangerous cars must be maintained upon the completion of this method.
 */
	public void removeDangerousCars() {
		temp = cursor;
		for (cursor = head; cursor != null; cursor=cursor.getNext()) {
			if (cursor.getCar().getLoad().getIsDangerous()) {
				this.removeCursor();
			}
		}
		cursor = temp;
	}
/**
 * Returns a neatly formatted String representation of the train.
 */
	public String toString() {
		String[] table1Heading1 = {"", "CAR:", "", "", "", "LOAD:", "", "", ""};
		String[] table1Heading2 = {"", "Num", "Length (m)", "Weight (t)", "|", "Name", "Weight (t)", "Value ($)", "Dangerous"};
		String line = "============================================================================================";
		String arrow = "->";
		String[] data = new String[9];
		String table = String.format("%-3s%-7s%-14s%-5s%3s%10s%15s%13s%16s", table1Heading1) + "\n";
		table += String.format("%-3s%-7s%-14s%-5s%3s%10s%15s%13s%16s", table1Heading2) + "\n";
		table += line + "\n";
		int count = 1;
		temp = cursor;
		for (cursor = head; cursor!=null; cursor=cursor.getNext()) {
			if (position == count) {
				data[0] = arrow;
			}
			data[1] = count+"";
			data[2] = cursor.getCar().getLength()+"";
			data[3] = cursor.getCar().getWeight()+"";
			data[4] = "|";
			if (cursor.getCar().isEmpty()) {
				data[5] = "Empty";
				data[6] = 0+"";
				data[7] = 0+"";
				data[8] = "NO";
			}
			else {
				data[5] = cursor.getCar().getLoad().getProductName();
				data[6] = cursor.getCar().getLoad().getWeight()+"";
				data[7] = cursor.getCar().getLoad().getValue()+"";
				if (cursor.getCar().getLoad().getIsDangerous()) {
					data[8] = "YES";
				}
				else {
					data[8] = "NO";
				}
			}
			table += String.format("%-3s%-7s%-14s%-10s%3s%10s%15s%13s%16s", data) + "\n";
			count++;
		}
		cursor = temp;
		return table;
	}
}
