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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController extends Controller{
	
	/**
	 * The textfield where the user enters their username
	 */
	@FXML private TextField loginTxt;
	
	/**
	 * The button that logs the user in
	 */
	@FXML private Button loginButton;
	/**
	 * The method that checks to see if the user exists and then logs them in
	 * @param e
	 */
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
					
					toAlbum();
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
	
	/**
	 * Quits the program safely, loading data to UserList.dat
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
	
	private User getUserFromName(String name) {
		for(User u : userList) {
			if(u.getUserName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	}

