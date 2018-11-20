package model;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
public class Photo implements Serializable{
	
	private String caption;
	private Album rootAlbum;
	private Image image;
	private Image thumbnail;
	private File file;
	private ObservableList<Tag> tags = FXCollections.observableArrayList();
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
		caption = "";
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
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public ObservableList<Tag> getTags() {
		return tags;
	}
	
	public void addTag(String type, String value) {
		Tag newTag = new Tag(type, value, this);
		tags.add(newTag);
	}
}
