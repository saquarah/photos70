package model;

import java.io.Serializable;

public class Tag implements Serializable{
	private String type, value;
	public Tag(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}
	public String getTag() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public String toString() {
		return type + "=" + value;
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Tag)) {
			return false;
		}
		Tag tag = (Tag) o;
		if(this.type.toLowerCase().equals(tag.type.toLowerCase())  &&
				this.value.toLowerCase().equals(tag.value.toLowerCase())) {
			return true;
		}
		return false;
	}
	
}
