import java.util.ArrayList;
/**
 * The Router class represents a router in the network, which is ultimately a queue.
 * 
 * @author Samuel Ng
 * 	samuel.ng@stonybrook.edu
 * 	112330868
 */
public class Router extends ArrayList {
	private static int bufferSize; //number of packets that a router can hold
	/**
	 * Returns an instance of <code>Router</code>
	 * 
	 * @param bufferSize
	 * 	number of packets that a router can hold
	 */
	public Router(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	/**
	 * Adds a new Packet to the end of the router buffer.
	 * 
	 * @param p
	 * 	packet being added to the router.
	 * 
	 * @exception
	 * 	throws IllegalArgumentException if packet is null.
	 */
	public void enqueue(Packet p) {
		if (p == null)
			throw new IllegalArgumentException("Packet is null!");
		super.add(p);
	}
	/**
	 * Removes the first Packet in the router buffer.
	 * 
	 * @return
	 * 	removed packet.
	 * 
	 * @exception
	 * 	throws IndexOutOfBoundsException if queue is empty.
	 */
	public Packet dequeue() {
		if (isEmpty())
			throw new IndexOutOfBoundsException("Queue is Empty!");
		return (Packet)super.remove(0);
	}
	/**
	 * Returns, but does not remove the first Packet in the router buffer.
	 * 
	 * @return
	 * 	the first Packet in the router buffer.
	 * 
	 * @exception
	 * 	throws IndexOutOfBoundsException if queue is empty.
	 */
	public Packet peek() {
		if (isEmpty())
			throw new IndexOutOfBoundsException("Queue is Empty!");
		return (Packet)super.get(0);
	}
	/**
	 * Returns the number of Packets that are in the router buffer.
	 * 
	 * @return
	 * 	number of Packets in the router buffer.
	 */
	public int size() {
		return super.size();
	}
	/**
	 * Returns whether the router buffer is empty or not.
	 * 
	 * @return
	 * 	true if the router buffer is empty.
	 */
	public boolean isEmpty() {
		return super.isEmpty();
	}
	/**
	 * Returns a String representation of the router buffer in the following format: {[packet1], [packet2], ... , [packetN]}
	 * 
	 * @return
	 * 	string representation of the router buffer.
	 */
	public String toString() {
		String buffer = "{";
		for (int i = 0; i < size(); i++) {
			buffer += get(i);
			if (i < size()-1)
				buffer += ", ";
		}
		buffer += "}";
		return buffer;
	}
	/**
	 * This method should loop through the list Intermediate routers. Find the router with the most free buffer space (contains least Packets), and return the index of the router.
	 * 
	 * @param routers
	 * 	list of routers
	 * @return
	 * 	index of free router packet is sent to.
	 */
	public static int sendPacketTo(ArrayList routers) {
		for (int a = 0; a < routers.size(); a++) {
			Router test = (Router)routers.get(a);
			if (test.size() < bufferSize) {
				break;
			}
			else {
				return -1;
			}
		}
		Router minRouter = (Router)routers.get(0);
		int minIndex = 0;
		for (int i = 0; i < routers.size(); i++) {
			Router router = (Router)routers.get(i);
			if (router.size() < minRouter.size()) {
				minRouter = router;
				minIndex = i;
			}
		}
		return minIndex+1;
	}
}
