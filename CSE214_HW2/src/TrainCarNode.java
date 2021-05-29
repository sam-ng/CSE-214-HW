/**
 * The TrainCarNode class contains the next and previous node and a TrainCar. This class acts as a node wrapper.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class TrainCarNode {
	private TrainCarNode next;
	private TrainCarNode prev;
	private TrainCar car;
/**
 * 
 * @param next
 * @param prev
 * @param car
 */
	public TrainCarNode(TrainCarNode next, TrainCarNode prev, TrainCar car) {
		this.next = next;
		this.prev = prev;
		this.car = car;
	}
/**
 * Accessor methods:
 * @return next
 */
	public TrainCarNode getNext() {
		return next;
	}
	
	public TrainCarNode getPrev() {
		return prev;
	}
	
	public TrainCar getCar() {
		return car;
	}
/**
 * Mutator methods:
 * @param next
 */
	public void setNext(TrainCarNode next) {
		this.next = next;
	}
/**
 * 
 * @param prev
 */
	public void setPrev(TrainCarNode prev) {
		this.prev = prev;
	}
/**
 * 
 * @param car
 */
	public void setCar(TrainCar car) {
		this.car = car;
	}
/**
 * prints the node information
 */
	public String toString() {
		return car.toString();
	}
}
