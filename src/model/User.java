package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This calss models a user
 * @author Sarah Squillace, Nikita Zala
 *
 */
public class User implements Serializable{
	private String name;
	private List<Album> myAlbums;
	
	/**
	 * Initializes user, making an album list too
	 * @param name the user name
	 */
	public User(String name) {
		this.name = name;
		myAlbums = new ArrayList<Album>();
	}
	
	/*
	 * return user name
	 */
	public String getUserName() {
		return name;
	}
	/**
	 * returns user name
	 */
	public String toString() {
			return name;
		}
	/**
	 * 
	 * @return the user's list of albums
	 */
	public List<Album> getAlbumList() {
		return myAlbums;
	}
   
}
