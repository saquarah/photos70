package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class Controller {
	static Stage loginStage;
	static Stage albumStage;
	
	
	static Scene loginScene;
	static Scene adminScene;
	static Scene albumHomeScene;
	static Scene photosScene;
	static Scene photoDisplayScene;
	
	static Controller loginController;
	static Controller adminController;
	static Controller albumController;
	static Controller photosController;
	static Controller photoDisplayController;
	
	static Stage primaryStage;
	
	private User name;
	
	ObservableList<User> userList = FXCollections.observableArrayList();
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	
	public static void initializeFXML(Stage primaryStage) throws IOException {
		Controller.primaryStage = primaryStage;
		
		FXMLLoader logLoader = new FXMLLoader();
		logLoader.setLocation(Controller.class.getResource("/view/Login.fxml"));
		AnchorPane loginRoot = (AnchorPane)logLoader.load();
		loginScene = new Scene(loginRoot);
		loginController = logLoader.getController();
		loginController.setPrimaryStage(primaryStage);
		
		FXMLLoader adminLoader = new FXMLLoader();
		adminLoader.setLocation(Controller.class.getResource("/view/Admin.fxml"));
		AnchorPane adminRoot = (AnchorPane)adminLoader.load();
		adminScene = new Scene(adminRoot);
		adminController = adminLoader.getController();
		adminController.setPrimaryStage(primaryStage);
		
		
		FXMLLoader albumLoader = new FXMLLoader();
		albumLoader.setLocation(Controller.class.getResource("/view/AlbumHome.fxml"));
		AnchorPane albumRoot = (AnchorPane)albumLoader.load();
		albumHomeScene = new Scene(albumRoot);
		albumController = albumLoader.getController();
		
		FXMLLoader photosLoader = new FXMLLoader();
		photosLoader.setLocation(Controller.class.getResource("/view/Photos.fxml"));
		AnchorPane photosRoot = (AnchorPane)photosLoader.load();
		photosScene = new Scene(photosRoot);
		photosController = photosLoader.getController();
		
		FXMLLoader photoDispLoader = new FXMLLoader();
		photoDispLoader.setLocation(Controller.class.getResource("/view/PhotoDisplay.fxml"));
		AnchorPane photoDispRoot = (AnchorPane)photoDispLoader.load();
		photoDisplayScene = new Scene(photoDispRoot);
		photoDisplayController = photoDispLoader.getController();
		
		loginStage = primaryStage;
		albumStage = new Stage();
		login();
	}
	
	public void initializeUserList() {
		
	}
	
	public static void login() {
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	
	/**
	 * Hides current scene and loads admin scene 
	 */
	public static void toAdmin() {
		primaryStage.hide();
		primaryStage.setScene(adminScene);
		//Stage adminStage = new Stage();
		//adminStage.setScene(adminScene);
		//adminStage.show();
		primaryStage.show();
	}
	
	public void initialize() {
		
	}
	/**
	 * Hides current stage and shows login scene. 
	 */
	
	public static void BackfromAdmin() {
		primaryStage.hide();
		primaryStage.setScene(loginScene);
		
		primaryStage.show();
	}
	
	
	
	/**
	 * 
	 * @param usr
	 * @return boolean value as true if user found
	 */
	public boolean isUser(String usr) {
		boolean found=false;
		for (int i=0;i<userList.size();i++) {
			if(userList.get(i).toString().equals(usr)) {
				found = true;
			}
		}
		
		
		
		return found;
	}
	
	/**
	 * Deletes selected user from listView. Cannot remove admin. 
	 * @param i
	 */
	public void deleteUser(int i) {
		if(userList.get(i).toString().equals("admin")) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Cannot remove admin.");
            alert.showAndWait();
		}else {
		userList.remove(i); 
		}
	   
	
	}
	
	
}
