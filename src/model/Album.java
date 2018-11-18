package model;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable{
	private String name;
	List<Photo> photos;
}
