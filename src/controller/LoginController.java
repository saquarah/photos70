package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController extends Controller{
	
	@FXML
	TextField loginTxt;
	
	@FXML
	Button loginButton;
	public void checkUserName(ActionEvent e) {
		String userString = loginTxt.getText().trim();
//		 TODO implement this
//		if(userString is in list of users ) {
//			check if userString is admin
//				if so, then do whatever special preparations admin needs
//			
//			go to AlbumHome screen
//		} else {
//			show an error message indicating that the username does not exist
//		}
		if(userString.equals("zak")) {
			System.out.print("");
		}
		if(userString.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Error");
			alert.setContentText("User name cannot be blank.");
			alert.showAndWait();
        
		}else {
			if(userInList(userString)) {
				User userLoggingIn = getUserFromName(userString);
				setUser(userLoggingIn);
				if (userString.equals("admin")) {
					
					toAdmin();
					loginTxt.clear(); //clears login text 
				} else {
					toAlbum();
					loginTxt.clear();
					
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("User does not exist.");
	            alert.showAndWait();
				}
			}
		}
	private User getUserFromName(String name) {
		for(User u : userList) {
			if(u.getUserName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	}

