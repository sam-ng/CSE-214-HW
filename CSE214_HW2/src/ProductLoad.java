/**
 * The ProductLoad class contains the product name (String), weight (double), value (double), and a boolean that shows if the load is dangerous.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class ProductLoad {
	private String productName;
	private double weight;
	private double value;
	private boolean isDangerous;
/**
 * 
 * @param productName is the name of the product load in the car
 * @param weight is the weight of the product load
 * @param value is the dollar value of the product load
 * @param isDangerous checks if the product load is safe or dangerous
 */
	public ProductLoad(String productName, double weight, double value, boolean isDangerous) {
		this.productName = productName;
		this.weight = weight;
		this.value = value;
		this.isDangerous = isDangerous;
	}
/**
 * Accessor methods:
 * @return productName
 */
	public String getProductName() {
		return productName;
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
 * @return value
 */
	public double getValue() {
		return value;
	}
/**
 * 
 * @return isDangerous
 */
	public boolean getIsDangerous() {
		return isDangerous;
	}
/**
 * Mutator methods:
 * @param productName
 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
/**
 * 
 * @param weight
 */
	public void setWeight(double weight) {
		if (weight < 0) {
			throw new IllegalArgumentException("Error! Weight cannot be negative.");
		}
		this.weight = weight;
	}
/**
 * 	
 * @param value
 */
	public void setValue (double value) {
		if (value < 0) {
			throw new IllegalArgumentException("Error! Value cannot be negative.");
		}
		this.value = value;
	}
/**
 * 	
 * @param isDangerous
 */
	public void setIsDangerous (boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
}
