package model;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
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
		try {
			this.thumbnail = createThumbnail();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Image createThumbnail() throws MalformedURLException {
		Image thumbnail = new Image(file.toURI().toURL().toString(), 100, 100, false, false);
		return thumbnail;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getThumbnail() {
		return thumbnail;
	}
}
