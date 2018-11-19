package controller;

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
	public void initialize() {
		
		// this should load in the user albums nvm dont do this lol
		// and also update the albumList in Controller
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
		setCreationVisibility(true);
	}
	
	@FXML
	public void renameAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void deleteAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void saveAlbum(ActionEvent e) {
		if(albumNameTxt.getText().trim().length() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No name entered.");
            alert.showAndWait();
            return;
		}
		
		
	}
	
	@FXML
	public void search(ActionEvent e) {
		
	}
	
	@FXML
	public void createNewAlbumWithResults(ActionEvent e) {
		
	}
	
	@FXML
	public void openAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		logout();
	}
	
	@FXML
	public void quit(ActionEvent e) {
		
	}
	
	private void loadAlbumList() {
		albumListView.setItems(albumList);
	}
	
	private void selectAlbum() {
		
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
