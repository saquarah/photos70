package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class PhotoDisplayController extends Controller{
	
	@FXML
	Button prevPhotoB, nextPhotoB, backToAlbumB, logoutB, quitB;
	
	@FXML
	Label albumLabel, dateLabel;
	
	@FXML
	TextArea tagTxtArea;
	
	@FXML
	ImageView imageView;
	
	@FXML
	public void prevPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void nextPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void backToAlbum(ActionEvent e) {
		primaryStage.hide();
		primaryStage.setScene(albumHomeScene);
		albumController.start();
		primaryStage.show();
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		logout();
		
	}
	
	@FXML
	public void quit(ActionEvent e) {
		Platform.exit();
	}
}
