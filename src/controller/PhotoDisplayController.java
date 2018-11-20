package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.Album;
import model.Photo;

public class PhotoDisplayController extends Controller{
	static final double FIT_HEIGHT = 393.0;
	static final double FIT_WIDTH = 878.0;
	
	static final double ANCHOR_WIDTH = 1028.0;
	static final double LAYOUT_X = 75.0;
	
	Album currentAlbum;
	Photo currentPhoto;
	
	@FXML
	Button prevPhotoB, nextPhotoB, backToAlbumB, logoutB, quitB;
	
	@FXML
	Label albumLabel, dateLabel;
	
	@FXML
	TextArea tagTxtArea, captionTxtArea;
	
	@FXML
	ImageView imageView;
	
	public void start(Photo firstViewed, Album currentAlbum) {
		currentPhoto = firstViewed;
		this.currentAlbum = currentAlbum;
		prepareImageView();
		System.out.println(imageView.getLayoutX());
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
	
	private void prepareImageView() {
		double photoHeight = currentPhoto.getImage().getHeight();
		double photoWidth = currentPhoto.getImage().getWidth();
		
		if(photoHeight < imageView.getFitHeight()) {
			imageView.setFitHeight(photoHeight);
		} else {
			imageView.setFitHeight(FIT_HEIGHT);
		}
		
		if(photoWidth < imageView.getFitWidth()) {
			imageView.setFitWidth(photoWidth);
			
			imageView.setLayoutX(ANCHOR_WIDTH/2.0 - photoWidth / 2.0);
		} else {
			imageView.setFitWidth(FIT_WIDTH);
		}
		
		imageView.setImage(currentPhoto.getImage());
	}
}
