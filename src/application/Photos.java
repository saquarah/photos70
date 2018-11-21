package application;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Photos extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Controller controller = new Controller();
			Controller.initializeFXML(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * The main method.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
