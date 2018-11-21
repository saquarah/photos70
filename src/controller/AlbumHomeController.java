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

public class AlbumHomeController extends Controller{
	ObservableList<Photo> photosList = FXCollections.observableArrayList();
	private final static String DATE_SEARCH = "Date range search";
	private final static String SINGLE_TAG_SEARCH = "Single tag-value search";
	private final static String CONJUNCTIVE_SEARCH = "Conjuntive tag-value search";
	private final static String DISJUNCTIVE_SEARCH = "Disjunctive tag-value search";
	
	String selectedOption;
	
	@FXML
	Label typeSearchLbl, dateLbl;
	
	@FXML
	ListView<Album> albumListView;
	
	@FXML
	ComboBox<String> searchOptionBox;
	
	@FXML
	Button adminB, quitB, createB, renameB, deleteB, logoutB, saveB;
	@FXML
	Button searchB, newAlbumResultsB, openB;
	
	@FXML
	TextField albumNameTxt;
	@FXML
	TextField firstTagNameTxt, firstTagValueTxt;
	@FXML
	TextField secondTagNameTxt, secondTagValueTxt;
	@FXML
	TextField startDateTxt, endDateTxt;
	
	Album selectedAlbum;
	
	boolean adding = false;
	public void initialize() {
		searchOptionBox.getItems().add("Date range search");
		searchOptionBox.getItems().add("Single tag-value search");
		searchOptionBox.getItems().add("Conjuntive tag-value search");
		searchOptionBox.getItems().add("Disjunctive tag-value search");
	}
	
	public void start() {
		searchOptionBox.getSelectionModel().clearSelection();
		selectedOption = null;
		
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
	
	@FXML
	public void adminPressed(ActionEvent e) {
		toAdmin();
	}
	
	@FXML
	public void createAlbum(ActionEvent e) {
		adding = true;
		albumNameTxt.setPromptText("Enter name");
		setCreationVisibility(true);
	}
	
	@FXML
	public void renameAlbum(ActionEvent e) {
		adding = false;
		albumNameTxt.setPromptText("Enter new name");
		setCreationVisibility(true);
	}
	
	@FXML
	public void deleteAlbum(ActionEvent e) {
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
	
	@FXML
	public void saveAlbum(ActionEvent e) {
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
	
	@FXML
	public void searchOption(ActionEvent e) {
		searchB.setVisible(true);
		makeTagStuffGoAway();
		selectedOption = searchOptionBox.getSelectionModel().getSelectedItem();
		if(selectedOption != null) {
			if(selectedOption.equals(DATE_SEARCH)) {
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
	
	@FXML
	public void search(ActionEvent e) {
		searchResultsController.start(selectedOption, startDateTxt.getText(),
				endDateTxt.getText(), firstTagNameTxt.getText(),
				firstTagValueTxt.getText(), secondTagNameTxt.getText(),
				secondTagValueTxt.getText());
		// a 7 parameter method i am dying
		clearSearchFields();
		ObservableList<Photo> searchResults = searchResultsController.searchResults;
		if(searchResults.isEmpty()) {
			newAlbumResultsB.setVisible(false);
		} else {
			secondaryStage.show();
		}
		
	}
	
	@FXML
	public void createNewAlbumWithResults(ActionEvent e) {
		
	}
	
	@FXML
	public void openAlbum(ActionEvent e) {
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
	
	@FXML
	public void logout(ActionEvent e) {
		closeAlbums();
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
