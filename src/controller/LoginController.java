package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	TextField loginTxt;
	
	@FXML
	Button loginButton;
	
	public void checkUserName(ActionEvent e) {
		String userString = loginTxt.getText();
//		 TODO implement this
//		if(userString is in list of users ) {
//			check if userString is admin
//				if so, then do whatever special preparations admin needs
//			
//			go to AlbumHome screen
//		} else {
//			show an error message indicating that the username does not exist
//		}
		
		
	}
}
