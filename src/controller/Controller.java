package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	Stage primaryStage;
	
	@FXML
	AnchorPane basePane;
	
	public Controller (Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
