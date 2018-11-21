package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
	
	/**
	 * Gets selected index of users from userList and deletes the user.
	 * @param e
	 */
	@FXML
	public void deleteUser(ActionEvent e) {
		int i = userListView.getSelectionModel().getSelectedIndex();
//		User deletMe = userListView.getSelectionModel().getSelectedItem();
//		userList.remove(deletMe); this would work too just put the admin warning
		deleteUser(i);
	}
	
	@FXML void saveUser(ActionEvent e) {
		String userName = userNameTxt.getText().trim();
		
		if(userName.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("User name cannot be blank.");
            alert.showAndWait();
		}else {
			/*if(userList.contains(user)) {
				//give error
				userNameTxt.clear();
			}
			else {
				userList.add(user);
				System.out.println(userList.toString());
				userNameTxt.clear();
				
			}*/
			
			//userList.add(user);
			
			//checks if user is in list
			if(userInList(userName)) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("User name already exists.");
	            alert.showAndWait();
				userNameTxt.clear();
			}else {
				User user = new User(userName);
				userList.add(user);
				userNameTxt.clear();
				//userListView.refresh();
				userListView.setItems(userList);
			}
			
		}
		
		
		hideAdditionElements();
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
	/**
	 * Allows admin to go to login screen on clicking back button
	 * @param e
	 */
	public void goBackToAlbum(ActionEvent e) {
		toAlbum();
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
	//	initializeUserList();
		// TODO fill list with saved users
		
//		if(userList.size() > 0) {
//			userListView.getSelectionModel().select(0);
//		}
		
		userListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectUser());
		userListView.setItems(userList);
	}
	
	private void selectUser() {
		selectedUser = userListView.getSelectionModel().getSelectedItem();
	}
	
}
