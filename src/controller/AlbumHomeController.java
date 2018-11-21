package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;
import model.Photo;
import model.User;
/**
 * This class deals with the logic in the albums screen.
 * @author Sarah Squillace, Nikita Zala
 *
 */
public class AlbumHomeController extends Controller{
	ObservableList<Photo> photosList = FXCollections.observableArrayList();
	private final static String DATE_SEARCH = "Date range search";
	private final static String SINGLE_TAG_SEARCH = "Single tag-value search";
	private final static String CONJUNCTIVE_SEARCH = "Conjuntive tag-value search";
	private final static String DISJUNCTIVE_SEARCH = "Disjunctive tag-value search";
	
	/**
	 * The search option that the user currently has selected
	 */
	String selectedOption;
	
	/**
	 * Labels that displays AND or OR depending on whether the search is
	 * conjunctive or disjunctive
	 */
	@FXML private Label typeSearchLbl;
	/**
	 * Label that says TO for displaying when searching for date range
	 */
	@FXML private Label dateLbl;
	/**
	 * Label that shows the user the date format when searching for date range.
	 */
	@FXML private Label dateFormatLbl;
	
	
	/**
	 * ListView that shows all of the user's albums.
	 */
	@FXML private ListView<Album> albumListView;
	
	/**
	 * Combobox that lists users options of searching.
	 */
	@FXML private ComboBox<String> searchOptionBox;
	
	
	/**
	 * Button that takes the admin to the admin screen
	 */
	@FXML private Button adminB;
	/**
	 * Button that allows the user to safely quit
	 */
	@FXML private Button quitB;
	/**
	 * Button that shows the field and button for saving and naming an album
	 */
	@FXML private Button createB;
	/**
	 * Button that shows the field and button for saving and renaming an album
	 */
	@FXML private Button renameB;
	/**
	 * Button that deletes the currently selected album
	 */
	@FXML private Button deleteB;
	/**
	 * Button that logs the user out
	 */
	@FXML private Button logoutB;
	/**
	 * Button that saves the album
	 */
	@FXML private Button saveB;
	/**
	 * Button that saves the album of search results to the user's albums
	 */
	@FXML private Button saveSearchAlbumBtn;
	/**
	 * Button that begins search
	 */
	@FXML private Button searchB;
	/**
	 * Button that shows field and button for making a new album with search results
	 */
	@FXML private Button newAlbumResultsB;
	/**
	 * Button that opens the selected album
	 */
	@FXML private Button openB;
	/**
	 * Button that cancels the current search
	 */
	@FXML private Button cancelSearchBtn;
	/**
	 * Button that cancels adding or renaming an album
	 */
	@FXML private Button cancelAlbumBtn;
	
	/**
	 * The textfield where the user enters an album's name
	 */
	@FXML private TextField albumNameTxt;
	/**
	 * The textfield where the user enters the first tag name they wish to search for
	 */
	@FXML private TextField firstTagNameTxt;
	/**
	 * The textfield where the user enters the first tag value they wish to search for
	 */
	@FXML private TextField firstTagValueTxt;
	/**
	 * The textfield where the user enters the second tag name they wish to search for
	 */
	@FXML private TextField secondTagNameTxt;
	/**
	 * The textfield where the user enters the second tag value they wish to search for
	 */
	@FXML private TextField secondTagValueTxt;
	
	/**
	 * The textfield where the user enters the start date they wish to search for
	 */
	@FXML private TextField startDateTxt;
	/**
	 * The textfield where the user enters the end date they wish to search for
	 */
	@FXML private TextField endDateTxt;
	/**
	 * The textfield where the user enters the name they want for the album made using the
	 * search results
	 */
	@FXML private TextField searchAlbumNameTxt;
	
	/**
	 * The album the user is currently selecting.
	 */
	Album selectedAlbum;
	
	boolean adding = false;
	/**
	 * Initializes the search option box.
	 */
	public void initialize() {
		searchOptionBox.getItems().add("Date range search");
		searchOptionBox.getItems().add("Single tag-value search");
		searchOptionBox.getItems().add("Conjuntive tag-value search");
		searchOptionBox.getItems().add("Disjunctive tag-value search");
	}
	
	/**
	 * Initializes fields every time this screen is loaded. Also loads in the albums
	 * from the user.
	 */
	public void start() {
		searchOptionBox.getSelectionModel().clearSelection();
		selectedOption = null;
		
		searchAlbumNameTxt.setVisible(false);
		saveSearchAlbumBtn.setVisible(false);
		cancelSearchBtn.setVisible(false);
		cancelAlbumBtn.setVisible(false);
		searchB.setVisible(false);
		newAlbumResultsB.setVisible(false);
		makeTagStuffGoAway();
		setDateSearchVisibility(false);
		setCreationVisibility(false);
		
		albumListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectAlbum());
		if(!currentUser().getUserName().equals("admin")) {
			adminB.setVisible(false);
		} else {
			adminB.setVisible(true);
		}
		loadAlbumList();
	}
	
	
	/**
	 * Goes to the admin screen
	 * @param e
	 */
	@FXML public void adminPressed(ActionEvent e) {
		toAdmin();
	}
	
	/**
	 * Shows field and button for creating album.
	 * @param e
	 */
	@FXML public void createAlbum(ActionEvent e) {
		adding = true;
		albumNameTxt.setPromptText("Enter name");
		setCreationVisibility(true);
	}
	
	/**
	 * Shows field and button for renaming album
	 * @param e
	 */
	@FXML public void renameAlbum(ActionEvent e) {
		adding = false;
		albumNameTxt.setPromptText("Enter new name");
		setCreationVisibility(true);
	}
	
	/**
	 * Deletes the album that is currently selected.
	 * @param e
	 */
	@FXML public void deleteAlbum(ActionEvent e) {
		if(selectedAlbum != null) {
			currentUser().getAlbumList().remove(selectedAlbum);
			albumList.remove(selectedAlbum);
			
			albumListView.refresh();
			albumListView.getSelectionModel().select(0);
			selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No album selected.");
            alert.showAndWait();
            return;
		}
	}
	
	/**
	 * Saves the album using the name from the album text field, adding it to the user's
	 * albums.
	 * @param e
	 */
	@FXML public void saveAlbum(ActionEvent e) {
		String albumName = albumNameTxt.getText().trim();
		if(albumName.length() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No name entered.");
            alert.showAndWait();
            return;
		}
		if(adding) {
			if (!albumInList(albumName)) {
				Album newAlbum = new Album(albumName);
				albumList.add(newAlbum);
				currentUser().getAlbumList().add(newAlbum);
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("This albmun name already exist. Choose a new name.");
	            alert.showAndWait();
	            albumNameTxt.clear();
	            albumNameTxt.setPromptText("Enter name");
	            
	            return;
				
			}
		} else {
			selectedAlbum.setName(albumName);
		}
		
		albumListView.refresh();
		setCreationVisibility(false);
		albumNameTxt.clear();
	}
	
	
	/**
	 * Activates when the user changes search option, adding necessary fields for the
	 * search
	 * @param e
	 */
	@FXML public void searchOption(ActionEvent e) {
		dateFormatLbl.setVisible(false);
		searchB.setVisible(true);
		makeTagStuffGoAway();
		selectedOption = searchOptionBox.getSelectionModel().getSelectedItem();
		if(selectedOption != null) {
			if(selectedOption.equals(DATE_SEARCH)) {
				dateFormatLbl.setVisible(true);
				setDateSearchVisibility(true);
			} else if (selectedOption.equals(SINGLE_TAG_SEARCH)) {
				showFirstTag();
				
			} else if (selectedOption.equals(CONJUNCTIVE_SEARCH)) {
				showFirstTag();
				showSecondTag();
				typeSearchLbl.setText("AND");
			} else if (selectedOption.equals(DISJUNCTIVE_SEARCH)) {
				showFirstTag();
				showSecondTag();
				typeSearchLbl.setText("OR");
			}
			
		}
	}
	
	/**
	 * Searches for photos that match the tags/dates
	 * @param e
	 */
	@FXML public void search(ActionEvent e) {
		searchResultsController.start(selectedOption, startDateTxt.getText(),
				endDateTxt.getText(), firstTagNameTxt.getText(),
				firstTagValueTxt.getText(), secondTagNameTxt.getText(),
				secondTagValueTxt.getText());
		// a 7 parameter method i am dying
		clearSearchFields();
		ObservableList<Photo> searchResults = searchResultsController.searchResults;
		if(searchResults.isEmpty()) {
			newAlbumResultsB.setVisible(false);
			cancelSearchBtn.setVisible(false);
			secondaryStage.hide();
		} else {
			secondaryStage.show();
			newAlbumResultsB.setVisible(true);
			cancelSearchBtn.setVisible(true);
		}
		
	}
	
	/**
	 * Cancels adding/renaming an album
	 * @param e
	 */
	@FXML public void cancelAlbumOperation(ActionEvent e) {
		albumNameTxt.clear();
		albumNameTxt.setVisible(false);
		saveB.setVisible(false);
	}
	
	/**
	 * Shows fields for creating new album
	 * @param e
	 */
	@FXML public void createNewAlbumWithResults(ActionEvent e) {
		searchAlbumNameTxt.setVisible(true);
		saveSearchAlbumBtn.setVisible(true);
	}
	
	/**
	 * Saves the album containing search results.
	 * @param e
	 */
	@FXML public void saveSearchAlbum(ActionEvent e) {
		String albumName = searchAlbumNameTxt.getText().trim();
		if(albumName.length() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No name entered.");
            alert.showAndWait();
            return;
		}
		if (!albumInList(albumName)) {
			Album newAlbum = new Album(albumName);
			
			for(Photo photo: searchResultsController.searchResults) {
				newAlbum.addToAlbum(photo);
			}
			albumList.add(newAlbum);
			currentUser().getAlbumList().add(newAlbum);
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("This album name already exist. Choose a new name.");
            alert.showAndWait();
            searchAlbumNameTxt.clear();
            searchAlbumNameTxt.setPromptText("Enter name");
            
            return;
			
		}
		albumListView.refresh();
		searchAlbumNameTxt.clear();
		searchAlbumNameTxt.setVisible(false);
		newAlbumResultsB.setVisible(false);
		saveSearchAlbumBtn.setVisible(false);
		cancelSearchBtn.setVisible(false);
		clearSearchFields();
		secondaryStage.hide();
		
	}
	
	/**
	 * Stops search and ability to add an album with search results.
	 * @param e
	 */
	@FXML public void cancelSearch(ActionEvent e) {
		searchAlbumNameTxt.clear();
		searchAlbumNameTxt.setVisible(false);
		newAlbumResultsB.setVisible(false);
		saveSearchAlbumBtn.setVisible(false);
		clearSearchFields();
		secondaryStage.hide();
		cancelSearchBtn.setVisible(false);
	}
	
	/**
	 * Opens the selected album, transitioning to the photos screen
	 * @param e
	 */
	@FXML public void openAlbum(ActionEvent e) {
		if(selectedAlbum == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No album selected.");
            alert.showAndWait();
            return;
		} else {
			openThisAlbum(selectedAlbum);
		}
		
	}
	
	/**
	 * Closes albums and logs the user out
	 * @param e
	 */
	@FXML public void logout(ActionEvent e) {
		closeAlbums();
		logout();
	}
	
	/**
	 * Safely writes data to Userlist.dat and then terminates program.
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
	
	private void closeAlbums() {
		selectedAlbum = null;
		
	}
	
	private void loadAlbumList() {
		albumList.clear();
		if(albumList.isEmpty()) { // if the album is empty then the user has just logged in
			for(Album album: currentUser().getAlbumList()) { // fill the observable album list with the user's albums
				albumList.add(album);
				// even if the user hasn't just logged in, this loop is harmless.
			}
		}
		albumListView.setItems(albumList); // set the list view
		albumListView.refresh();
	}
	
	private void selectAlbum() {
		selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
	}
	
	private void setCreationVisibility(boolean visibility) {
		saveB.setVisible(visibility);
		albumNameTxt.setVisible(visibility);
	}
	
	private void setDateSearchVisibility(boolean visibility) {
		dateLbl.setVisible(visibility);
		startDateTxt.setVisible(visibility);
		endDateTxt.setVisible(visibility);
	}
	
	private void makeTagStuffGoAway() {
		firstTagNameTxt.setVisible(false);
		firstTagValueTxt.setVisible(false);
		secondTagNameTxt.setVisible(false);
		secondTagValueTxt.setVisible(false);
		startDateTxt.setVisible(false);
		endDateTxt.setVisible(false);
		dateLbl.setVisible(false);
		typeSearchLbl.setVisible(false);
		dateFormatLbl.setVisible(false);
	}
	
	private void clearSearchFields() {
		endDateTxt.clear();
		firstTagNameTxt.clear();
		firstTagValueTxt.clear();
		secondTagNameTxt.clear();
		secondTagValueTxt.clear();
		startDateTxt.clear();
	}
	
	private void showDateStuff() {
		startDateTxt.setVisible(true);
		endDateTxt.setVisible(true);
		dateLbl.setVisible(true);
		dateFormatLbl.setVisible(true);
	}
	
	private void conjunctiveLabel() {
		typeSearchLbl.setText("AND");
	}
	
	private void disjunctiveLabel() {
		typeSearchLbl.setText("OR");
	}
	private void showFirstTag() {
		firstTagNameTxt.setVisible(true);
		firstTagValueTxt.setVisible(true);
	}
	private void showSecondTag() {
		typeSearchLbl.setVisible(true);
		secondTagNameTxt.setVisible(true);
		secondTagValueTxt.setVisible(true);
	}
	
	private void makeSecondTagGoAway() {
		typeSearchLbl.setVisible(false);
		secondTagNameTxt.setVisible(false);
		secondTagValueTxt.setVisible(false);
	}
}
