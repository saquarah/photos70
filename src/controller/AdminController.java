package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.User;

public class AdminController extends Controller{
	private User selectedUser;
	
	@FXML
	Button addUserB, deleteUserB, saveB, backB;
	
	@FXML
	TextField userNameTxt;
	
	@FXML
	ListView<User> userListView;
	
	@FXML
	public void addUser(ActionEvent e) {
		userNameTxt.setVisible(true);
		saveB.setVisible(true);
	}
	
	@FXML
	public void deleteUser(ActionEvent e) {
		
	}
	
	@FXML void saveUser(ActionEvent e) {
		String userName = userNameTxt.getText();
		User user = new User(userName);
		if(userList.contains(user)) {
			
		}
		hideAdditionElements();
	}
	
	@FXML
	public void goBackToAlbum(ActionEvent e) {
		
	}
	
	public void hideAdditionElements() {
		userNameTxt.setVisible(false);
		saveB.setVisible(false);
	}
	
	/**
	 * Initializes the admin screen, loading the list with users and adding a listener.
	 */
	@Override
	public void initialize() {
		hideAdditionElements();
		userListView = new ListView<User>();
		// TODO fill list with saved users
		
//		if(userList.size() > 0) {
//			userListView.getSelectionModel().select(0);
//		}
		
		userListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectUser());
	}
	
	private void selectUser() {
		selectedUser = userListView.getSelectionModel().getSelectedItem();
	}
}
