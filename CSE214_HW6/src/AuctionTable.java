import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import big.data.*;
/**
 * The AuctionTable class stores the database of open auctions to provide constant time insertion and deletion.
 * 
 * @author Samuel Ng
 * 	e-mail: samuel.ng@stonybrook.edu
 *	Stony Brook ID: 112330868
 */
public class AuctionTable extends Hashtable implements Serializable{
	/**
	 * Uses the <code>BigData</code> library to construct an <code>AuctionTable</code> from a remote data source.
	 * 
	 * @param URL
	 * 	String representing the URL for the remote data source.
	 * 
	 * <dt>Preconditions:
	 * 	<dd>URL represents a data source which can be connected to using the <code>BigData</code> library.
	 * 	<dd>The data source has proper syntax.
	 * 
	 * @return
	 * 	The <code>AuctionTable</code> constructed from the remote data source.
	 * @throws IllegalArgumentException
	 * 	thrown if the URL does not represent a valid datasource (can't connect or invalid syntax)
	 */
	public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
		DataSource ds = DataSource.connect(URL).load();
		if (ds == null)
			throw new IllegalArgumentException("URL is invalid!");
		String[] auctionID = ds.fetchStringArray("listing/auction_info/id_num");
		String[] currentBid = ds.fetchStringArray("listing/auction_info/current_bid");
		String[] sellerName = ds.fetchStringArray("listing/seller_info/seller_name");
		String[] buyerName = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
		String[] timeRemaining = ds.fetchStringArray("listing/auction_info/time_left");
		String[] itemInfoMemory = ds.fetchStringArray("listing/item_info/memory");
		String[] itemInfoHardDrive = ds.fetchStringArray("listing/item_info/hard_drive");
		String[] itemInfoCPU = ds.fetchStringArray("listing/item_info/cpu");
		
		AuctionTable URLTable = new AuctionTable();
		
		for (int i = 0; i < auctionID.length; i++) {
			String numDays = timeRemaining[i].substring(0, timeRemaining[i].indexOf("days")-1).trim();
			String numHours = timeRemaining[i].substring(timeRemaining[i].indexOf(",")+1, timeRemaining[i].indexOf("hours")-1).trim();
			int timeLeft = (Integer.parseInt(numDays)*24) + Integer.parseInt(numHours);
			double currentBidNum = Double.parseDouble(currentBid[i].replaceAll("[$,]",""));
			Auction newAuction = new Auction(timeLeft, currentBidNum, auctionID[i], sellerName[i], buyerName[i], itemInfoCPU[i]+" - "+itemInfoHardDrive[i]+" - "+itemInfoMemory[i]);
			URLTable.put(auctionID[i], newAuction);
		}
		return URLTable;
	}
	/**
	 * Manually posts an auction, and add it into the table.
	 * 
	 * @param auctionID
	 * 	the unique key for this object
	 * @param auction
	 * 	the auction to insert into the table with the corresponding auctionID
	 * 
	 * <dt>Postconditions:
	 * 	<dd>The item will be added to the table if all given parameters are correct
	 * 
	 * @throws IllegalArgumentException
	 * 	thrown if the given auction ID is already stored in the table
	 */
	public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
		if (super.containsKey(auctionID))
			throw new IllegalArgumentException("Given AuctionID is already stored in the table.");
		super.put(auctionID, auction);
	}
	/**
	 * Get the information of an Auction that contains the given ID as key
	 * 
	 * @param auctionID
	 * 	the unique key for this object
	 * @return
	 * 	an auction object with the given key, null otherwise
	 */
	public Auction getAuction(String auctionID) {
		return (Auction)super.get(auctionID);
	}
	/**
	 * Simulates the passing of time. Decrease the timeRemaining of all Auction objects by the amount specified. The value cannot go below 0.
	 * 
	 * @param numHours
	 * 	the number of hours to decrease the timeRemaining value by
	 * 
	 * <dt>Postconditions:
	 * 	<dd>All Auctions in the table have their timeRemaining timer decreased. If the original value is less than the decreased value, set the value to 0.
	 * 
	 * @throws IllegalArgumentException
	 * 	thrown if the given numHours is non postiive
	 */
	public void letTimePass(int numHours) throws IllegalArgumentException {
		if (numHours <= 0)
			throw new IllegalArgumentException("numHours is non positive.");
		Set<String> keys = super.keySet();
		for (String auctionID: keys) {
			getAuction(auctionID).decrementTimeRemaining(numHours);
		}
	}
	/**
	 * Iterates over all Auction objects in the table and removes them if they are expired
	 * 
	 * <dt>Postconditions:
	 * 	<dd>Only open Auction remain in the table
	 */
	public void removeExpiredAuctions() {
		Set<String> keys = super.keySet();
		for (String auctionID: keys) {
			Auction currentAuction = getAuction(auctionID);
			if (currentAuction.getTimeRemaining() == 0) {
				super.remove(auctionID);
				break;
			}
		}
	}
	/**
	 * Prints the AuctionTable in tabular form.
	 */
	public void printTable() {
		String table = "";
		String[] heading = {"Auction ID", "Bid", "Seller", "Buyer", "Time", "Item Info"};
		table += String.format("%-11s|%-11s|%-23s|%-25s|%-11s|%-100s", heading);
		table += "\n";
		String separator = "";
		Set<String> keys = super.keySet();
		ArrayList<String> list = new ArrayList<String>();
		for (String auctionID: keys) {
			list.add(auctionID);
			for (int i = 0; i < getAuction(auctionID).toString().length(); i++) {
				separator += "=";
			}
		}
		table += separator;
		table += "\n";
		//SORT BY TIME
		for (int i = 0; i < list.size(); i++) {
			for (int j = i+1; j < list.size(); j++) {
				if (getAuction(list.get(i)).getTimeRemaining() < getAuction(list.get(j)).getTimeRemaining()) {
					String temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j,  temp);
				}
				if (getAuction(list.get(i)).getTimeRemaining() == getAuction(list.get(j)).getTimeRemaining()) {
					if (Integer.parseInt(list.get(i)) < Integer.parseInt(list.get(j))) {
						String temp = list.get(i);
						list.set(i, list.get(j));
						list.set(j,  temp);
					}
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			table += getAuction(list.get(i)).toString() + "\n";
		}
		System.out.println(table);
	}
}
