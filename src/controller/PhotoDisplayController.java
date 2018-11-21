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
	private static final double FIT_HEIGHT = 393.0;
	private static final double FIT_WIDTH = 878.0;
	
	private static final double ANCHOR_WIDTH = 1028.0;
	private static final double LAYOUT_X = 75.0;
	
	/**
	 * The album that is currently being viewed
	 */
	Album currentAlbum;
	/**
	 * The photo the user is selecting
	 */
	Photo currentPhoto;
	
	/**
	 * The button that shows the previous photo in the album
	 */
	@FXML private Button prevPhotoB;
	/**
	 * The button that shows the next photo in the album
	 */
	@FXML private Button nextPhotoB;
	/**
	 * The button that shows goes back to viewing the album
	 */
	@FXML private Button backToAlbumB;
	/**
	 * The button that logs the user out
	 */
	@FXML private Button logoutB;
	/**
	 * The button that quits the program
	 */
	@FXML private Button quitB;
	
	/**
	 * Label that shows the album name
	 */
	@FXML private Label albumLabel;
	/**
	 * Label that shows the date of the photo
	 */
	@FXML private Label dateLabel;
	/**
	 * Text area where tags are shown
	 */
	@FXML private TextArea tagTxtArea;
	/**
	 * Text area where captions are shown
	 */
	@FXML private TextArea captionTxtArea;
	
	/**
	 * ImageView used to display the photo
	 */
	@FXML private ImageView imageView;
	
	/**
	 * Initializes the photo displayer
	 * @param firstViewed The photo the program will begin with viewing
	 * @param currentAlbum the current album being viewed
	 */
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
	
	/**
	 * Goes to the previous photo, if there is one
	 * @param e
	 */
	@FXML public void prevPhoto(ActionEvent e) {
		
		int currentIndx = currentAlbum.getPhotos().indexOf(currentPhoto);
		currentPhoto = currentAlbum.getPhotos().get(currentIndx - 1);
		
		updateWidgets();
		checkForPrev();
		checkForNext();
	}
	
	/**
	 * Goes to the next photo, if there is one
	 * @param e
	 */
	@FXML public void nextPhoto(ActionEvent e) {
		
		int currentIndx = currentAlbum.getPhotos().indexOf(currentPhoto);
		currentPhoto = currentAlbum.getPhotos().get(currentIndx + 1);
		
		updateWidgets();
		checkForNext();
		checkForPrev();
	}
	
	/**
	 * Goes back to the view of the entire album
	 * @param e
	 */
	@FXML public void backToAlbum(ActionEvent e) {
		toPhotos();
		
	}
	
	/**
	 * Logs the user out and goes to login screen
	 * @param e
	 */
	@FXML public void logout(ActionEvent e) {
		photosController.closeAlbum();
		logout();
		
	}
	
	/**
	 * Quits the program, loading data to UserList.dat
	 * @param e
	 */
	@FXML public void quit(ActionEvent e) {
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
