package model;

import java.io.Serializable;

public class Tag implements Serializable{
	private String type, value;
	private Photo photo;
	public Tag(String type, String value, Photo photo) {
		super();
		this.type = type;
		this.value = value;
		this.photo = photo;
	}
	public String getTag() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public Photo getPhoto() {
		return photo;
	}
	public String toString() {
		return type + "=" + value;
	}
	
	
}
