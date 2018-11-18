package model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
	private String name;
	List<Album> myAlbums;
}
