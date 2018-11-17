package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	Scene loginScene;
	Scene adminScene;
	Scene albumHomeScene;
	Scene photosScene;
	Scene photoDisplayScene;
	
	Controller loginController;
	Controller adminController;
	Controller albumController;
	Controller photosController;
	Controller photoDisplayController;
	
	Stage primaryStage;
	
	@FXML
	AnchorPane basePane;
	


	
	public void initializeFXML(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		FXMLLoader logLoader = new FXMLLoader();
		logLoader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane loginRoot = (AnchorPane)logLoader.load();
		loginScene = new Scene(loginRoot);
		loginController = (Controller) logLoader.getController();
		
		FXMLLoader adminLoader = new FXMLLoader();
		adminLoader.setLocation(getClass().getResource("/view/Admin.fxml"));
		AnchorPane adminRoot = (AnchorPane)adminLoader.load();
		adminScene = new Scene(adminRoot);
		adminController = (Controller) adminLoader.getController();
		
		FXMLLoader albumLoader = new FXMLLoader();
		albumLoader.setLocation(getClass().getResource("/view/AlbumHome.fxml"));
		AnchorPane albumRoot = (AnchorPane)albumLoader.load();
		albumHomeScene = new Scene(albumRoot);
		albumController = (Controller) albumLoader.getController();
		
		FXMLLoader photosLoader = new FXMLLoader();
		photosLoader.setLocation(getClass().getResource("/view/Photos.fxml"));
		AnchorPane photosRoot = (AnchorPane)photosLoader.load();
		photosScene = new Scene(photosRoot);
		photosController = (Controller) photosLoader.getController();
		
		FXMLLoader photoDispLoader = new FXMLLoader();
		photoDispLoader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
		AnchorPane photoDispRoot = (AnchorPane)photoDispLoader.load();
		photoDisplayScene = new Scene(photoDispRoot);
		photoDisplayController = (Controller) photoDispLoader.getController();
		
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
}
