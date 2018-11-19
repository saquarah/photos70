package controller;

import java.io.File;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import model.Album;
import model.Photo;
import model.Tag;

public class PhotosController extends Controller{
	ObservableList<Photo> photoList = FXCollections.observableArrayList();
	
	Album currentAlbum;
	
	@FXML
	TilePane tilePane;
	
	@FXML
	Button addPhotoB, deletePhotoB,  backToAlbumB, quitB, logoutB, addTagB, deleteTagB, saveTagB, copyToOtherAlbumB;
	@FXML
	Button addCaptB, openB, captionB, moveToOtherAlbumB;
	
	@FXML
	TextField captionTxt, tagNameTxt, tagValueTxt;
	
	@FXML
	ListView<Album> albumsListView;
	
	@FXML
	ListView<Tag> tagsListView;
	
	@FXML
	public void addPhoto(ActionEvent e) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select image");
		File file = fc.showOpenDialog(primaryStage);
		Photo newPhoto = fileToPhoto(file);
		photoList.add(newPhoto);
	}
	
	@FXML
	public void deletePhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void caption(ActionEvent e) {
		
	}
	
	@FXML
	public void addCaption(ActionEvent e) {
		
	}
	
	@FXML
	public void openPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void backToAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void addTag(ActionEvent e) {
		
	}
	
	@FXML
	public void deleteTag(ActionEvent e) {
		
	}
	
	@FXML
	public void saveTag(ActionEvent e) {
		
	}
	
	@FXML
	public void moveToOtherAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void copyToOtherAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void quit(ActionEvent e) {
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		
	}
	
	public void initialize() {
		
	}
	
	private void fillPhotoList() {
		
	}
	
	private Photo fileToPhoto(File file) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(file.lastModified());
		Image image = new Image(file.toURI().toString());
		Photo photo = new Photo(image, date, file);
		return photo;
		
	}
}
