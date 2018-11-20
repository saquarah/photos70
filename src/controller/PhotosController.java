package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import model.Album;
import model.Photo;
import model.Tag;

public class PhotosController extends Controller{
	ObservableList<Photo> photoList = FXCollections.observableArrayList();
	ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
	ImageView selectedImageView;
	Photo selectedPhoto;
	Album currentAlbum; // this is an album from the user.
	
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
		if(file == null) {
			return;
		}
		Photo newPhoto = fileToPhoto(file);
		photoList.add(newPhoto);
		addToTilePane(newPhoto);
		currentAlbum.getPhotos().add(newPhoto);
	}
	
	@FXML
	public void deletePhoto(ActionEvent e) {
		if(selectedImageView == null || selectedPhoto == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		// we have to delete it from the photoList, the imageViewList
		// and the tilePane itself. Additionally we have to delete it
		// from this album's photo list as well, but we don't have that 
		// in place yet.
		tilePane.getChildren().remove(imageViewList.indexOf(selectedImageView)); // remove from tilePane
		photoList.remove(selectedPhoto);
		currentAlbum.getPhotos().remove(selectedPhoto);
		imageViewList.remove(selectedImageView);
		selectedPhoto = null;
		selectedImageView = null;
		
	}
	
	@FXML
	public void caption(ActionEvent e) {
		if(selectedImageView == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		addCaptB.setVisible(true);
		captionTxt.setVisible(true); // add cancel button to this
	}
	
	@FXML
	public void addCaption(ActionEvent e) {
		if(selectedImageView == null || selectedPhoto == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		String caption = captionTxt.getText().trim();
		selectedPhoto.setCaption(caption);
		captionTxt.setVisible(false);
		addCaptB.setVisible(false);
		captionTxt.clear();
	}
	
	@FXML
	public void openPhoto(ActionEvent e) {
		
	}
	
	@FXML
	public void backToAlbum(ActionEvent e) {
		closeAlbum();
		toAlbum();
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
		Platform.exit();
	}
	
	@FXML
	public void logout(ActionEvent e) {
		closeAlbum();
		logout();
	}
	
	public void initialize() {}
	
	/**
	 * Populates photoList and imageViewList with photos from this album, and then making imageviews
	 * with those photos and adding them to imageViewList. Also links the album to this controller.
	 */
	public void start(Album thisAlbum) {
		addCaptB.setVisible(false);
		captionTxt.setVisible(false);
		currentAlbum = thisAlbum;
		selectedImageView = null;
		selectedPhoto = null;
		for(int i = 0; i < thisAlbum.getPhotos().size(); i++) {
			photoList.add(thisAlbum.getPhotos().get(i));
//			ImageView imvw = new ImageView(thisAlbum.getPhotos().get(i).getThumbnail());
			addToTilePane(thisAlbum.getPhotos().get(i));
		}
	}
	
	
	private Photo fileToPhoto(File file) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(file.lastModified());
		Image image = new Image(file.toURI().toString());
		Photo photo = new Photo(image, date, file);
		return photo;
		
	}
	
	/**
	 * This method is used when we are getting out of the view from inside the album. 
	 * So after a log out, a back to album button press, or a quit.
	 * What this does is it resets the photosList, imageViewList, tilePane, and 
	 * the selections, as well as any other album-specific fields.
	 */
	private void closeAlbum() {
		photoList = FXCollections.observableArrayList(); // set photoList to empty
		imageViewList.clear();
		currentAlbum = null;
		tilePane.getChildren().clear();
		selectedPhoto = null;
		selectedImageView = null;
		// BOOKMARK 
		//PLEASE DONT IGNORE ME
	}
	
	private void addToTilePane(Photo newPhoto) {
		ImageView newImageView = new ImageView();
		newImageView.setImage(newPhoto.getThumbnail());
		initializeImageView(newImageView);
		tilePane.getChildren().add(newImageView);
	}
	
	private void initializeImageView(ImageView imageView) {
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		imageViewList.add(imageView);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getButton().equals(MouseButton.PRIMARY)) {
					if(selectedImageView != null)
						selectedImageView.setOpacity(1.0); // unselect the previous selectedImview
					selectedImageView = imageView; // select new imageView that was clicked on.
					// the photo has already been added to photolist at this point
					selectedPhoto = photoList.get(imageViewList.indexOf(selectedImageView)); // make the selected photo by
					// 1. get the index of the selected imageView in the imageViewList
					// 2. get the photo at that index in the photoList.
					// 3. we can be sure that those indices are mapping each photo to its imageview because 
					//    a. every time a photo is added to the list, it is also added to the imageViewList.
					//    b. every time a photo is removed from the photoList, it is also removed from the imageViewList
					imageView.setOpacity(.5);
					//tilepane can get stuff by index
				}
			}
		});
	}
}
