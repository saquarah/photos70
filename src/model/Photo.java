package model;

import java.io.Serializable;
import java.util.Calendar;
public class Photo implements Serializable{
	
	private String photoTitle;
	private Album rootAlbum;
	Calendar date = Calendar.getInstance();
	
}
