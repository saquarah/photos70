package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;

public class AlbumHomeController extends Controller{
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
		
	}
	
	public void start() {
		setCreationVisibility(false);
		albumListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectAlbum());
		if(!currentUser().getUserName().equals("admin")) {
			adminB.setVisible(false);
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
			Album newAlbum = new Album(albumName);
			albumList.add(newAlbum);
		} else {
			selectedAlbum.setName(albumName);
		}
		albumListView.refresh();
		setCreationVisibility(false);
		albumNameTxt.clear();
	}
	
	@FXML
	public void search(ActionEvent e) {
		
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
		logout();
	}
	
	@FXML
	public void quit(ActionEvent e) {

		Platform.exit();
		
	}
	
	private void loadAlbumList() {
		albumListView.setItems(albumList);
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
		typeSearchLbl.setVisible(false);
		secondTagNameTxt.setVisible(true);
		secondTagValueTxt.setVisible(true);
	}
	
	
}
