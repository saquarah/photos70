package model;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable{
	private String name;
	List<Photo> photos;
	
	public Album(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
