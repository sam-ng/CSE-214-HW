/**
 * The TrainCar class contains the length (double), weight (double), and load (ProductLoad).
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class TrainCar {
	private double length;
	private double weight;
	private ProductLoad load;
/**
 * 
 * @param length is the length of a train car
 * @param weight is the weight of a train car
 * @param load is the product load of the train car
 */
	public TrainCar(double length, double weight, ProductLoad load) {
		this.length = length;
		this.weight = weight;
		this.load = load;
	}
/**
 * Accessor methods:
 * @return length
 */
	public double getLength() {
		return length;
	}
/**
 * 
 * @return weight
 */
	public double getWeight() {
		return weight;
	}
/**
 * 	
 * @return load
 */
	public ProductLoad getLoad() {
		return load;
	}
/**
 * Mutator methods:
 * @param load
 */
	public void setLoad(ProductLoad load) {
		this.load = load;
	}
/**
 * isEmpty() checks if there is a load in the car
 * @return true or false
 */
	public boolean isEmpty() {
		if (load == null) {
			return true;
		}
		return false;
	}
}
