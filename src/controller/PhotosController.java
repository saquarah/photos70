package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import model.User;

public class PhotosController extends Controller{
	ObservableList<Photo> photoList = FXCollections.observableArrayList();
	ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
	ImageView selectedImageView;
	Photo selectedPhoto;
	Album currentAlbum; // this is an album from the user.
	Tag selectedTag;
	Album selectedAlbum;
	
	@FXML
	TextArea captTxtArea;
	
	@FXML
	Label albumNameLbl;
	
	@FXML
	TilePane tilePane;
	
	@FXML
	ScrollPane scrollpane;
	
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
		if( !isPhoto(file) ) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The file you chose was not a .png, .jpeg, or .jpg");
            alert.showAndWait();
            return;
		}
		Photo newPhoto = fileToPhoto(file);
		photoList.add(newPhoto);
		addToTilePane(newPhoto);
		currentAlbum.addToAlbum(newPhoto);
		albumsListView.refresh();
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
		deleteSelectedPhoto();
		albumsListView.refresh();
		captTxtArea.clear();
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
		captionTxt.setText(selectedPhoto.getCaption());
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
		captTxtArea.setText(selectedPhoto.getCaption());
	}
	
	@FXML
	public void openPhoto(ActionEvent e) {
		photoDisplayController.start(selectedPhoto, currentAlbum);
		openPhoto();
	}
	
	@FXML
	public void backToAlbum(ActionEvent e) {
		closeAlbum();
		toAlbum();
	}
	
	@FXML
	public void addTag(ActionEvent e) {
		if(selectedPhoto == null || selectedImageView == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		saveTagB.setVisible(true);
		tagNameTxt.setVisible(true);
		tagValueTxt.setVisible(true);
	}
	
	@FXML
	public void deleteTag(ActionEvent e) {
		if(selectedPhoto == null || selectedImageView == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		if(selectedTag == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No tag selected.");
            alert.showAndWait();
            return;
		}
		tagsListView.getItems().remove(selectedTag);
		tagsListView.refresh();
	}
	
	@FXML
	public void saveTag(ActionEvent e) {
		String tagType = tagNameTxt.getText().trim();
		String tagValue = tagValueTxt.getText().trim();
		
		if(tagType.isEmpty() || tagValue.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("One or more of the tag text fields are blank.");
            alert.showAndWait();
            return;
		}
		
		for(Tag tag: selectedPhoto.getTags()) {
			if(tag.equals(new Tag(tagType, tagValue))) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("Cannot add two of the same tag (with same name and value) to the same photo");
	            alert.showAndWait();
	            return;
			}
		}
		
		selectedPhoto.addTag(tagType, tagValue);
		tagsListView.setItems(selectedPhoto.getTags());
		
		tagNameTxt.clear();
		tagValueTxt.clear();
		tagNameTxt.setVisible(false);
		tagValueTxt.setVisible(false);
		saveTagB.setVisible(false);
	}
	
	@FXML
	public void moveToOtherAlbum(ActionEvent e) {
		if(selectedAlbum == currentAlbum) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("This photo is already in this album.");
            alert.showAndWait();
            return;
		}
		if(selectedPhoto == null || selectedImageView == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		if(selectedAlbum == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No album selected.");
            alert.showAndWait();
            return;
		}
		if(selectedAlbum.getPhotos().contains(selectedPhoto)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("This photo is already in this album.");
            alert.showAndWait();
            return;
		}
		Photo movingPhoto = selectedPhoto;
		deleteSelectedPhoto();
		
		selectedAlbum.addToAlbum(movingPhoto);
		albumsListView.refresh();
		captTxtArea.clear();
		
	}
	
	
	@FXML
	public void copyToOtherAlbum(ActionEvent e) {
		if(selectedPhoto == null || selectedImageView == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No photo selected.");
            alert.showAndWait();
            return;
		}
		if(selectedAlbum == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No album selected.");
            alert.showAndWait();
            return;
		}
		if(selectedAlbum.getPhotos().contains(selectedPhoto)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("This photo is already in this album.");
            alert.showAndWait();
            return;
		}
		// copy the selected photo's image, date, file, caption, and tags
		Photo copiedPhoto = selectedPhoto;
//		copiedPhoto.setCaption(selectedPhoto.getCaption());
//		for(Tag tag: selectedPhoto.getTags()) {
//			copiedPhoto.getTags().add(tag);
//		}
		selectedAlbum.addToAlbum(copiedPhoto);
		
		if(selectedAlbum == currentAlbum) {
			photoList.add(copiedPhoto);
			addToTilePane(copiedPhoto);
		}
		albumsListView.refresh();
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
	
	@FXML
	public void logout(ActionEvent e) {
		closeAlbum();
		logout();
	}
	
	public void initialize() {
		scrollpane.setFitToWidth(true);
		scrollpane.setContent(tilePane);
	}
	
	/**
	 * Populates photoList and imageViewList with photos from this album, and then making imageviews
	 * with those photos and adding them to imageViewList. Also links the album to this controller.
	 */
	public void start(Album thisAlbum) {
		captTxtArea.setWrapText(true);
		albumNameLbl.setText(thisAlbum.getName());
		
		tagsListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectTag());
		tagNameTxt.setVisible(false);
		tagValueTxt.setVisible(false);
		saveTagB.setVisible(false);
		
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
		albumsListView.setItems(albumList);
		albumsListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectAlbum());
	}
	
	private void selectAlbum() {
		selectedAlbum = albumsListView.getSelectionModel().getSelectedItem();
	}
	private void deleteSelectedPhoto() {
		tilePane.getChildren().remove(imageViewList.indexOf(selectedImageView)); // remove from tilePane
		photoList.remove(selectedPhoto);
		currentAlbum.removeFromAlbum(selectedPhoto);
		imageViewList.remove(selectedImageView);
		selectedPhoto = null;
		selectedImageView = null;
		tagsListView.setItems(null);
	}
	
	private void selectTag() {
		selectedTag = tagsListView.getSelectionModel().getSelectedItem();
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
	public void closeAlbum() {
		tagsListView.setItems(null);
		photoList = FXCollections.observableArrayList(); // set photoList to empty
		imageViewList.clear();
		currentAlbum = null;
		tilePane.getChildren().clear();
		selectedPhoto = null;
		selectedImageView = null;
		// BOOKMARK 
		// PLEASE DONT IGNORE ME
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
					// every time an image is clicked,
					// the tagListView must show all of that images tags.
					tagsListView.setItems(selectedPhoto.getTags());
					selectedTag = null;
					captTxtArea.setText(selectedPhoto.getCaption());
				}
			}
		});
	}
	
	private boolean isPhoto(File file) {
		String[] acceptedFileExtension = {"jpg", "jpeg", "png"};
		for(String extension: acceptedFileExtension) {
			if(file.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}
