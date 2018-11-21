package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class models an Album
 * @author Sarah Squillace, Nikita Zala
 *
 */
public class Album implements Serializable{
	private String name;
	private List<Photo> photos;
	private Calendar earliestDate = Calendar.getInstance();
	private Calendar latestDate = Calendar.getInstance();
	
	/**
	 * Initializes album
	 * @param name the album's name
	 */
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		earliestDate = null;
		latestDate = null;
	}
	
	/**
	 * 
	 * @return the album's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Shows string representation of album, showing the name, number of photos, and
	 * the earliest and latest dates
	 */
	public String toString() {
		String photoPlur = "photos";
		if(photos.size() == 1) {
			photoPlur = "photo";
		}
		if(photos.size() == 0) {
			return name + " | 0 photos |";
		}
		String strEarlyDate = earliestDate.getTime().toString();
		String strLateDate = latestDate.getTime().toString();
		return name + "|" + photos.size() + " " + photoPlur + " | From " + strEarlyDate + " to " + strLateDate;
	}
	/**
	 * Changes album name
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Adds the photo to the album, also updating earliest and latest dates
	 * @param photo
	 */
	public void addToAlbum(Photo photo) {
		photos.add(photo);
		updateEarliestDate();
		updateLatestDate();
	}
	/**
	 * Removes the photo from the album, also updating earliest and latest dates
	 * @param photo
	 */
	public void removeFromAlbum(Photo photo) {
		photos.remove(photo);
		updateEarliestDate();
		updateLatestDate();
	}
	/**
	 * 
	 * @return the list of photos that the album contains
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	private void updateEarliestDate() {
		if(photos.size() == 0) { // if there are no photos, there is no earliest date
			earliestDate = null;
			return;
		}
		earliestDate = photos.get(0).getDate(); // set earliest date to first element
		for(int i = 1; i < photos.size(); i++) {
			if(photos.get(i).getDate().compareTo(earliestDate) < 0) {
				earliestDate = photos.get(i).getDate();
			}
		}
		
	}
	private void updateLatestDate() {
		if(photos.size() == 0) { // if there are no photos, there is no latest date
			latestDate = null;
			return;
		}
		latestDate = photos.get(0).getDate(); // set latest date to first element
		for(int i = 1; i < photos.size(); i++) {
			if(photos.get(i).getDate().compareTo(latestDate) > 0) {
				latestDate = photos.get(i).getDate();
			}
		}
	}
}
