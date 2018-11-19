package model;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

import javafx.scene.image.Image;
public class Photo implements Serializable{
	
	private String caption;
	private Album rootAlbum;
	private Image image;
	private Image thumbnail;
	private File file;
	Calendar date = Calendar.getInstance();
	
	public Photo(Image image, Calendar date, File file) {
		this.image = image;
		this.date = date;
		this.file = file;
		this.thumbnail = createThumbnail();
	}
	private Image createThumbnail() {
		Image thumbnail = new Image(file.getAbsolutePath(), 100, 100, false, false);
		return thumbnail;
	}
}
