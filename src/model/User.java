package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class User implements  Serializable{
	private String name;
	List<Album> myAlbums;
	
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
	
	public String toString() {
			return name;
		}
	
   
}
