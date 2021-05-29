import java.io.Serializable;

/**
 * The Auction class represents an active auction currently in the database.
 * The Auction class should contain member variables for the seller's name, the current bid, the time remaining (in hours), current bidder's name, information about the item, and the unique ID for the auction.
 * 
 * @author Samuel Ng
 * 	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class Auction implements Serializable{
	
	private int timeRemaining;
	private double currentBid;
	private String auctionID;
	private String sellerName;
	private String buyerName;
	private String itemInfo;
	/**
	 * 
	 * @param timeRemaining
	 * 	the remaining time the auction is active
	 * @param currentBid
	 * 	the current highest bid of the auction
	 * @param auctionID
	 * 	the unique ID of the auction
	 * @param sellerName
	 * 	the name of the seller of the auction item
	 * @param buyerName
	 * 	the name of the buyer of the auction item
	 * @param itemInfo
	 * 	the item information
	 */
	public Auction(int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName, String itemInfo) {
		this.timeRemaining = timeRemaining;
		this.currentBid = currentBid * 1.00;
		this.auctionID = auctionID;
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		this.itemInfo = itemInfo;
	}
	/**
	 * Decreases the time remaining for this auction by the specified amount. If time is greater than the current remaining time for the auction, then the time remaining is set to 0.
	 * 
	 * <dt>Postconditions:
	 * 	<dd>timeRemaining has been decremented by the indicated amount and is greater than or equal to 0.
	 * 
	 * @param time
	 * 	time the auction is decremented by
	 */
	public void decrementTimeRemaining(int time) {
		if (time > timeRemaining) {
			timeRemaining = 0;
			return;
		}
		timeRemaining -= time;
	}
	/**
	 * Makes a new bid on this auction. If <code>bidAmt</code> is larger than <code>currentBid</code>, then the value of <code>currentBid</code> is replaced by <code>bidAmt</code> and <code>buyerName</code> is replaced by <code>bidderName</code>
	 * 
	 * <dt>Preconditions:
	 * 	<dd>The auction is not closed
	 * <dt>Postconditions:
	 * 	<dd><code>currentBid</code> reflects the largest bid placed on this object. If the auction is closed, throw a ClosedAuctionException
	 * @param bidderName
	 * 	the name of the current bidder
	 * @param bidAmt
	 * 	the amount the current bidder is bidding
	 * @throws ClosedAuctionException
	 * 	thrown if the auction is closed and no more bids can be placed
	 */
	public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException {
		if (timeRemaining == 0) {
			throw new ClosedAuctionException("Auction" + auctionID + " is CLOSED\n\tCurrent Bid: $ " + currentBid);
		}
		if (bidAmt > currentBid) {
			currentBid = bidAmt;
			buyerName = bidderName;
		}
	}
	/**
	 * @return
	 * 	string of data members in tabular form
	 */
	public String toString() {
		String table = "";
		String[] data = {auctionID, currentBid+"", sellerName, buyerName, timeRemaining+ " hours", itemInfo};
		if (currentBid == 0)
			data[1] = "";
		table += String.format("%-11s|$%-11s|%-23s|%-25s|%-11s|%-100s", data);
		return table;
	}
	//Accessor methods:
	/**
	 * @return
	 * 	time remaining in the auction
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}
	/**
	 * @return
	 * 	current highest bid
	 */
	public double getCurrentBid() {
		return currentBid;
	}
	/**
	 * @return
	 * 	unique auction ID
	 */
	public String getAuctionID() {
		return auctionID;
	}
	/**
	 * @return
	 * 	name of seller
	 */
	public String getSellerName() {
		return sellerName;
	}
	/**
	 * @return
	 * 	name of buyer
	 */
	public String getBuyerName() {
		return buyerName;
	}
	/**
	 * @return
	 * 	item information
	 */
	public String getItemInfo() {
		return itemInfo;
	}
}
