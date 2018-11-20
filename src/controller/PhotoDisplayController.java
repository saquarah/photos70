package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.Photo;

public class PhotoDisplayController extends Controller{
	
	Photo currentPhoto;
	
	@FXML
	Button prevPhotoB, nextPhotoB, backToAlbumB, logoutB, quitB;
	
	@FXML
	Label albumLabel, dateLabel;
	
	@FXML
	TextArea tagTxtArea;
	
	@FXML
	ImageView imageView;
	
	public void start(Photo firstViewed) {
		currentPhoto = firstViewed;
	}
	
	@FXML
	public void prevPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void nextPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void backToAlbum(ActionEvent e) {
		toPhotos();
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		photosController.closeAlbum();
		logout();
		
	}
	
	@FXML
	public void quit(ActionEvent e) {
		Platform.exit();
	}
}
