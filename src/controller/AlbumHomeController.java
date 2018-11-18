package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class AlbumHomeController extends Controller{
	@FXML
	TilePane tilePane;
	
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
	
	@FXML
	public void adminPressed(ActionEvent e) {
		
	}
	
	@FXML
	public void createAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void renameAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void deleteAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void saveAlbum(ActionEvent e) {
		
	}
	
	@FXML
	public void logout(ActionEvent e) {
		
	}
	
	@FXML
	public void quit(ActionEvent e) {
		
	}
}
