package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
public class Photo implements Serializable{
	
	private static final long serialVersionUID = -6324409348554636338L;
	private String caption;
	private transient Image image;
	private transient Image thumbnail;
	private File file;
	private transient ObservableList<Tag> tags = FXCollections.observableArrayList();
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
		//Image thumbnail = image;
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

	public File getFile() {
		return file;
	}
	public Calendar getDate() {
		return date;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}
	public void setTags(ObservableList<Tag> tags) {
		this.tags = tags;
	}
	public void addTag(String type, String value) {
		Tag newTag = new Tag(type, value);
		tags.add(newTag);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		
		out.writeObject(new ArrayList<Tag> (tags));
         
       
    }
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //System.out.println("reading");
		in.defaultReadObject();
		
		image = new Image(file.toURI().toURL().toString());
		thumbnail = createThumbnail();
		
		
		
		
		
		tags = FXCollections.observableArrayList();
		List<Tag> list = (List<Tag>)in.readObject();
		if(!list.isEmpty()) {
		tags.addAll(list);
		}
		else {
			tags = FXCollections.observableArrayList();
		}
		

    }
}
