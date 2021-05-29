import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
/**
 * The simulator class contains the main method that tests the simulation.
 *
 * @author Samuel Ng
 * 	samuel.ng@stonybrook.edu
 * 	112330868
 */
public class Simulator {
	public static final int MAX_PACKETS = 3; //Maximum number of packets that can arrive at the dispatcher.
	private static boolean trySim = true; //Checks if the simulation should continue running.
	
	/**
	 * Runs the simulator as described in the specs. Calculate and return the average time each packet spends within the network.
	 * 
	 * @return
	 * 	average time each packet spends within the network.
	 */
	public static double simulate() {
		Scanner sc = new Scanner(System.in);
		ArrayList<Router> list = new ArrayList<Router>();
		System.out.println("Starting simulator...");
		System.out.print("Enter the number of Intermediate routers: ");
		int numRouter = sc.nextInt();
		System.out.print("Enter the arrival probability of a packet: ");
		double prob = sc.nextDouble();
		System.out.print("Enter the maximum buffer size of a router: ");
		int bufferSize = sc.nextInt();
		for (int i = 0; i < numRouter; i++) {
			list.add(new Router(bufferSize));
		}
		System.out.print("Enter the minimum size of a packet: ");
		int minVal = sc.nextInt();
		System.out.print("Enter the maximum size of a packet: ");
		int maxVal = sc.nextInt();
		System.out.print("Enter the bandwidth size: ");
		int bandwidth = sc.nextInt();
		System.out.print("Enter the simulation duration: ");
		int duration = sc.nextInt();
		
		Router dispatcher = new Router(bufferSize);
		int time = 0;
		int totalServiceTime = 0;
		int totalPacketsArrived = 0;
		int packetsDropped = 0;
		ArrayList<Integer> priority = new ArrayList<Integer>();
		do {
			time++;
			Packet currentPack;
			Packet removedPack;
			int timeTaken;
			int bandwidthLeft = bandwidth;
		//ADDING PACKETS
		//ARRIVING AT DISPATCHER
			boolean packetAdded = false;
			System.out.println("Time: "+time);
			for (int i = 0; i < MAX_PACKETS; i++) {
				if (Math.random() < prob) {
					Packet newPacket = new Packet(randInt(minVal, maxVal), time);
					dispatcher.enqueue(newPacket);
					packetAdded = true;
					System.out.println("Packet "+newPacket.getId() + " arrives at dispatcher with size "+newPacket.getPacketSize()+".");
				}
			}
			if (!packetAdded)
				System.out.println("No packets arrived.");
		//DISPATCHING TO INTERMEDIATE ROUTERS
			int dispatcherSize = dispatcher.size();
			for (int j = 0; j < dispatcherSize; j++) {
				Packet dispPack = dispatcher.dequeue();
				int routerNum = Router.sendPacketTo(list);
				if (routerNum == -1) {
					System.out.println("Network is congested. Packet "+dispPack.getId()+" is dropped.");
					packetsDropped++;
					break;
				}
				list.get(routerNum-1).enqueue(dispPack);
				System.out.println("Packet "+dispPack.getId()+" sent to Router "+routerNum+".");
			}
			//REMOVING PACKETS AT DESTINATION FROM QUEUE
			if (!priority.isEmpty()) {
				for (int y = 0; y < priority.size(); y++) {
					if (bandwidthLeft > 0) {
						removedPack = list.get(priority.remove(y)).dequeue();
						timeTaken = time - removedPack.getTimeArrive();
						totalServiceTime += timeTaken;
						totalPacketsArrived++;
						bandwidthLeft--;
						y--;
						System.out.println("Packet "+removedPack.getId()+" has successfully reached its destination: +"+timeTaken);
					}
				}
			}
			for (int x = 0; x < list.size(); x++) {
				if (!list.get(x).isEmpty()) {
					currentPack = list.get(x).peek();
					if (currentPack.getTimeToDest() == 0) {
						if (bandwidthLeft > 0) {
							removedPack = list.get(x).dequeue();
							timeTaken = time - removedPack.getTimeArrive();
							totalServiceTime += timeTaken;
							totalPacketsArrived++;
							bandwidthLeft--;
							System.out.println("Packet "+removedPack.getId()+" has successfully reached its destination: +"+timeTaken);
						}
						else {
							if (!priority.contains(x))
								priority.add(x);
						}
					}
				}				
			}
		//PRINT ROUTERS
			for (int l = 0; l < list.size(); l++) {
				int temp = 0;
				if (!list.get(l).isEmpty())
					temp = list.get(l).peek().getTimeToDest();
				if (temp < 0) {
					list.get(l).peek().setTimeToDest(0);
				}
				System.out.println("R"+(l+1)+" :"+list.get(l));
				if (!list.get(l).isEmpty())
					list.get(l).peek().setTimeToDest(temp);
			}
		//DECREMENT TIME TO DEST
			for (int m = 0; m < list.size(); m++) {
				if (!list.get(m).isEmpty()) {
					currentPack = list.get(m).peek();
					currentPack.setTimeToDest(currentPack.getTimeToDest()-1);
				}
			}
		}
		while (time < duration); 
		double averageServiceTime = (double)totalServiceTime/totalPacketsArrived;
		System.out.println("Simulation ending...");
		System.out.println("Total service time: "+totalServiceTime);
		System.out.println("Total packets served: "+totalPacketsArrived);
		System.out.println("Average service time per packet: "+averageServiceTime);
		System.out.println("Total packets dropped: "+packetsDropped);
		return averageServiceTime;
	}
	/**
	 * Generate a random number between minVal and maxVal, inclusively.
	 * 
	 * @param minVal
	 * 	minimum bound for the random number generator
	 * @param maxVal
	 * 	maximum bound for the random number generator
	 * 
	 * @return
	 * 	randomly generated number
	 */
	private static int randInt(int minVal, int maxVal) {
		if (minVal > maxVal)
			throw new IllegalArgumentException("minVal is greater than maxVal!");
		return (int)(Math.random()*((maxVal - minVal) + 1)) + minVal;
	}
	/**
	 * The main() method will prompt the user for inputs to the simulator. It will then run the simulator, and outputs the result. Prompt the user whether he or she wants to run another simulation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (trySim) {
			double averageServiceTime = simulate();
			System.out.print("Do you want to try another simulation? (y/n): ");
			String response = sc.nextLine();
			if (response.equalsIgnoreCase("n")) {
				System.out.println("Program terminating successfully...");
				System.exit(0);
			}
		}
	}
}
