/**
 * The Packet class represents a packet that will be sent through the network.
 * 
 * @author Samuel Ng
 * 	samuel.ng@stonybrook.edu
 *	112330868
 */
public class Packet {
	private static int packetCount = 0; //This value is used to assign an id to a newly created packet. It will start with the value 0, and every time a new packet object is created, increment this counter and assign the value as the id of the Packet.
	private int id;	//A unique identifier for the packet. This will be systematically determined by using packetCount.
	private int packetSize; //The size of the packet being sent. This value is randomly determined by the simulator by using the Math.random() method.
	private int timeArrive;	//The time this packet is created should be recorded in this variable.
	private int timeToDest;	//This variable contains the number of simulation units that it takes for a packet to arrive at the destination router. The value will start at one hundredth of the packet size, that is: packetSize/100. At every simulation time unit, this counter will decrease. Once it reaches 0, we can assume that the packet has arrived at the destination.
	
	/**
	 * Returns an instance of <code>Packet</code>.
	 * 
	 * @param packetSize
	 * 	The size of the packet being sent.
	 * @param timeArrive
	 * 	The time this packet is created.
	 */
	public Packet(int packetSize, int timeArrive) {
		packetCount++;
		id = packetCount;
		this.packetSize = packetSize;
		this.timeArrive = timeArrive;
		timeToDest = packetSize / 100;
	}
	//Accessor methods:
	/**
	 * Returns the id of the packet.
	 * 
	 * @return
	 * 	returns the id of the packet.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the size of the packet.
	 * 
	 * @return
	 * 	returns the size of the packet.
	 */
	public int getPacketSize() {
		return packetSize;
	}
	/**
	 * Returns the time the packet was created.
	 * 
	 * @return
	 * 	returns the time the packet was created.
	 */
	public int getTimeArrive() {
		return timeArrive;
	}
	/**
	 * Returns the time left until the packet reaches the destination.
	 * 
	 * @return
	 * 	returns the time left until the packet reaches the destination.
	 */
	public int getTimeToDest() {
		return timeToDest;
	}
	//Mutator methods:
	/**
	 * Changes the packet id to the parameter.
	 * 
	 * @param id
	 * 	new id of the packet.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Changes the packet size to the parameter.
	 * 
	 * @param packetSize
	 * 	new size of the packet.
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}
	/**
	 * Changes the time the packet was created to the parameter.
	 * 
	 * @param timeArrive
	 * 	new time the packet was created.
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}
	/**
	 * Changes the time to destination to the parameter.
	 * 
	 * @param timeToDest
	 * 	new time to destination.
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}
	/**
	 * Returns the text representation of the packet object.
	 * 
	 * @return
	 * 	returns the text in format [id, timeArrive, timeToDest].
	 */
	public String toString() {
		return "["+id+", "+timeArrive+", "+timeToDest+"]";
	}
}
