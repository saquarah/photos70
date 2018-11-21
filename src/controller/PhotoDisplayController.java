package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.Album;
import model.Photo;
import model.User;

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
		tagTxtArea.setWrapText(true);
		captionTxtArea.setWrapText(true);
		
		currentPhoto = firstViewed;
		this.currentAlbum = currentAlbum;
		
		albumLabel.setText("Album: " + currentAlbum.getName());
		
		updateWidgets();
		checkForPrev();
		checkForNext();
	}
	
	@FXML
	public void prevPhoto(ActionEvent e) {
		
		int currentIndx = currentAlbum.getPhotos().indexOf(currentPhoto);
		currentPhoto = currentAlbum.getPhotos().get(currentIndx - 1);
		
		updateWidgets();
		checkForPrev();
		checkForNext();
	}
	
	@FXML
	public void nextPhoto(ActionEvent e) {
		
		int currentIndx = currentAlbum.getPhotos().indexOf(currentPhoto);
		currentPhoto = currentAlbum.getPhotos().get(currentIndx + 1);
		
		updateWidgets();
		checkForNext();
		checkForPrev();
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
		try {
			FileOutputStream fos = new FileOutputStream("UserList.dat");
			ObjectOutputStream os = new ObjectOutputStream (fos);
			os.writeObject(new ArrayList<User> (userList));
			os.close();
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Platform.exit();
	}
	
	private void prepareImageView() {
		double photoHeight = currentPhoto.getImage().getHeight();
		double photoWidth = currentPhoto.getImage().getWidth();
		
		if(photoHeight < FIT_HEIGHT) {
			imageView.setFitHeight(photoHeight);
		} else {
			imageView.setFitHeight(FIT_HEIGHT);
		}
		
		if(photoWidth < FIT_WIDTH) {
			imageView.setFitWidth(photoWidth);
			
//			imageView.setLayoutX(FIT_WIDTH/2.0 - photoWidth / 2.0 - imageView.getLayoutBounds().getMinX());
			imageView.setLayoutX(FIT_WIDTH/2.0 - photoWidth / 2.0);
		} else {
			imageView.setFitWidth(FIT_WIDTH);
		}
		
		imageView.setImage(currentPhoto.getImage());
	}
	
	private void checkForPrev() {
		if(currentAlbum.getPhotos().indexOf(currentPhoto) == 0) {
			prevPhotoB.setDisable(true);
		} else {
			prevPhotoB.setDisable(false);
		}
	}
	private void checkForNext() {

		if(currentAlbum.getPhotos().indexOf(currentPhoto) == currentAlbum.getPhotos().size() - 1) {
			nextPhotoB.setDisable(true);
		} else {
			nextPhotoB.setDisable(false);
		}
	}
	
	private void updateWidgets() {
		prepareImageView();
		
		dateLabel.setText("Date: " + currentPhoto.getDate().getTime().toString());
		String tagsText = "";
		
		if(!currentPhoto.getTags().isEmpty()) {
			StringBuilder sb = new StringBuilder(tagsText);
			for(int i = 0; i < currentPhoto.getTags().size(); i++) {
				String tag = currentPhoto.getTags().get(i).getTag() + "=" + currentPhoto.getTags().get(i).getValue();
				sb.append(tag).append("\n");
			}
			tagsText = sb.toString();
		} else {
			tagsText = "No tags";
		}
		
		tagTxtArea.setText(tagsText);
		
		if(!currentPhoto.getCaption().isEmpty()) {
			captionTxtArea.setText(currentPhoto.getCaption());
		} else {
			captionTxtArea.setText("No caption");
		}
	}
}
