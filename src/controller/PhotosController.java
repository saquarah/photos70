package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;
import model.Tag;

public class PhotosController extends Controller{
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
	
}
