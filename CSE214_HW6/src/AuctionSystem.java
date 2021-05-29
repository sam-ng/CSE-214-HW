import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Set;

/**
 * The AuctionSystem class allows the user to interact with the database by listing open auctions, make bids on open auctions, and create new auctions for different items.
 * 
 * @author Samuel Ng
 * 	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class AuctionSystem implements Serializable{
	
	private static AuctionTable auctionTable;
	private static String username;
	
	private static boolean run;
	/**
	 * The method should first prompt the user for a username. This should be stored in <code>username</code>. The rest of the program will be executed on behalf of this user.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		run = true;
		Scanner sc = new Scanner(System.in);
		String auctionID = "";
		Auction currentAuction;
		System.out.println("Starting...");
		
		File auctionObj = new File("auctionTable.obj");
		if (!auctionObj.exists()) {
			System.out.println("No previous auction table detected.");
			System.out.println("Creating new table...");
			auctionTable = new AuctionTable();
		}
		else {
			System.out.println("Loading previous Auction Table...");
			FileInputStream fileIn = new FileInputStream("auctionTable.obj");
			ObjectInputStream inStream = new ObjectInputStream(fileIn);
			while (fileIn.available() > 0) {
				auctionTable = (AuctionTable) inStream.readObject();
			}
			inStream.close();
			fileIn.close();
		}	
		System.out.println();
		System.out.print("Please select a username: ");
		username = sc.nextLine();
		System.out.println();
		while (run) {
			System.out.println("Menu:");
			System.out.println("\t(D) - Import Data from URL");
			System.out.println("\t(A) - Create a New Auction");
			System.out.println("\t(B) - Bid on an Item");
			System.out.println("\t(I) - Get Info on Auction");
			System.out.println("\t(P) - Print All Auctions");
			System.out.println("\t(R) - Remove Expired Auctions");
			System.out.println("\t(T) - Let Time Pass");
			System.out.println("\t(Q) - Quit");
			System.out.println();
			System.out.print("Please select an option: ");
			String option = sc.nextLine().toUpperCase();
			switch (option) {
				case "D":
					System.out.print("Please enter a URL: ");
					String URL = sc.nextLine();
					System.out.println();
					auctionTable = AuctionTable.buildFromURL(URL);
					if (auctionTable != null) {
						System.out.println("Loading...");
						System.out.println("Auction data loaded successfully!");
					}
					break;
				case "A":
					System.out.println();
					System.out.println("Creating new Auction as "+username);
					System.out.print("Please enter an Auction ID: ");
					auctionID = sc.nextLine();
					System.out.print("Please enter an Auction time (hours): ");
					int timeRemaining = sc.nextInt();
					sc.nextLine();
					System.out.print("Please enter some Item Info: ");
					String itemInfo = sc.nextLine();
					System.out.println();
					System.out.println("Auction "+auctionID+" inserted into table.");
					Auction newAuction = new Auction(timeRemaining, 0.00, auctionID, username, "", itemInfo);
					auctionTable.put(auctionID, newAuction);
					break;
				case "B":
					System.out.print("Please enter an Auction ID: ");
					auctionID = sc.nextLine();
					System.out.println();
					currentAuction = auctionTable.getAuction(auctionID);
					if (currentAuction.getTimeRemaining() == 0) {
						System.out.println("Auction"+auctionID+" is CLOSED");
						if (currentAuction.getCurrentBid() > 0)
							System.out.println("\tCurrentBid: $"+currentAuction.getCurrentBid());
						else
							System.out.println("\tCurrentBid: None");
						System.out.println();
						System.out.println("You can no longer bid on this item.");
					}
					else {
						System.out.println("Auction"+auctionID+" is OPEN");
						if (currentAuction.getCurrentBid() > 0)
							System.out.println("\tCurrentBid: $"+currentAuction.getCurrentBid());
						else
							System.out.println("\tCurrentBid: None");
						System.out.println();
						System.out.print("What would you like to bid?: ");
						double newBid = sc.nextDouble();
						sc.nextLine();
						if (newBid > currentAuction.getCurrentBid()) {
							try {
								currentAuction.newBid(username, newBid);
							} catch (ClosedAuctionException e) {
								System.out.println("Auction is closed.");
							}
							System.out.println("Bid accepted.");
						}
						else {
							System.out.println("Bid was not greater than the current highest bid.");
						}
					}
					break;
				case "I":
					System.out.print("Please enter an Auction ID: ");
					auctionID = sc.nextLine();
					System.out.println();
					currentAuction = auctionTable.getAuction(auctionID);
					System.out.println("Auction "+auctionID+":");
					System.out.println("\tSeller: "+currentAuction.getSellerName());
					System.out.println("\tBuyer: "+currentAuction.getBuyerName());
					System.out.println("\tTime: "+currentAuction.getTimeRemaining());
					System.out.println("\tInfo: "+currentAuction.getItemInfo());
					break;
				case "P":
					auctionTable.printTable();
					break;
				case "R":
					System.out.println("Removing expired auctions...");
					System.out.println("ALL expired auctions removed.");
					auctionTable.removeExpiredAuctions();
					break;
				case "T":
					System.out.print("How many hours should pass: ");
					int numHours = sc.nextInt();
					sc.nextLine();
					System.out.println();
					System.out.println("Time passing...");
					auctionTable.letTimePass(numHours);
					System.out.println("Auction times updated.");
					break;
				case "Q":
					System.out.println("Writing Auction Table to file...");
					FileOutputStream fileOut = new FileOutputStream("auctionTable.obj");
					ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
					outStream.writeObject(auctionTable);
					outStream.close();
					fileOut.close();
					System.out.println("Done");
					System.out.println();
					System.out.println("Goodbye.");
					run = false;
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option!");
			}
		}
	}
}
