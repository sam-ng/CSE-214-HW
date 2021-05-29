/**
 * The Complexity class contains the n_power and log_power complexities and can be printed in the form of Big-Oh notation.
 * It represents the Big-Oh complexity of some block of code.
 * 
 * @author Samuel Ng
 * 		samuel.ng@stonybrook.edu
 * 		112330868
 */
public class Complexity {
	private int n_power;
	private int log_power;
	
/**
 * 
 * @param n_power is the exponent of n
 * @param log_power is the exponent of log
 */
	public Complexity(int n_power, int log_power) {
		this.n_power = n_power;
		this.log_power = log_power;
	}
/**
 * Accessor methods:
 * @return n_power
 */
	public int getN_Power() {
		return n_power;
	}
/**
 * 
 * @return log_power
 */
	public int getLog_Power() {
		return log_power;
	}
	
/**
 * Mutator methods:
 * @param n_power
 */
	public void setN_Power(int n_power) {
		this.n_power = n_power;
	}

/**
 * 
 * @param log_power
 */
	public void setLog_Power(int log_power) {
		this.log_power = log_power;
	}

/**
 * toString() returns a string that represents the complexity in Big-Oh notation.
 */
	public String toString() {
		String n_powerTxt = "";
		String log_powerTxt = "";
		String operator = "";
		if (n_power == 0 && log_power == 0) {
			return "O(1)";
		}
		if (n_power == 1) {
			n_powerTxt = "n";
		}
		else if (log_power == 1) {
			log_powerTxt = "log(n)";
		}
		else if (n_power > 1) {
			n_powerTxt = "n^" + n_power;
		}
		else if (log_power > 1) {
			log_powerTxt = "log(n)^" + log_power;
		}
		else if (n_power > 0 && log_power > 0) {
			operator = " * ";
		}
		return "O(" + n_powerTxt + operator + log_powerTxt + ")";
	}
}
