package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
/**
 * This class mainly deals with loading the FXML and data and changing screens
 * It is the parent to all controllers.
 * @author  Sarah Squillace, Nikita Zala
 *
 */
public class Controller {
	/**
	 * The Scene for the login screen
	 */
	static Scene loginScene;
	/**
	 * The Scene for the admin screen
	 */
	static Scene adminScene;
	/**
	 * The Scene for the albums screen
	 */
	static Scene albumHomeScene;
	/**
	 * The Scene for the photos(inside album) screen
	 */
	static Scene photosScene;
	/**
	 * The Scene for the photo display screen
	 */
	static Scene photoDisplayScene;
	/**
	 * The Scene for the search results screen
	 */
	static Scene searchResultsScene;
	
	/**
	 * The Controller for the login screen
	 */
	static LoginController loginController;
	/**
	 * The Controller for the admin screen
	 */
	static AdminController adminController;
	/**
	 * The Controller for the albums screen
	 */
	static AlbumHomeController albumController;
	/**
	 * The Controller for the inside album screen
	 */
	static PhotosController photosController;
	/**
	 * The Controller for the photo results screen
	 */
	static PhotoDisplayController photoDisplayController;
	/**
	 * The Controller for the search results screen
	 */
	static SearchResultsController searchResultsController;
	
	/**
	 * The Stage for the main application
	 */
	static Stage primaryStage;
	/**
	 * The Stage for the search results.
	 */
	static Stage secondaryStage;
	
	private static User user;
	
	/**
	 * The observable list of users
	 */
	static ObservableList<User> userList = FXCollections.observableArrayList();
	/**
	 * The observable list of the user's albums
	 */
	static ObservableList<Album> albumList = FXCollections.observableArrayList();
	/**
	 * Sets primary stage
	 * @param primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage) {
		Controller.primaryStage = primaryStage;
	}

	/**
	 * Loads FXML and also loads data from UserList.dat
	 * @param primaryStage
	 * @throws IOException
	 */
	public static void initializeFXML(Stage primaryStage) throws IOException {
		Controller.primaryStage = primaryStage;
		secondaryStage = new Stage();
		secondaryStage.setHeight(400.0);
		secondaryStage.setWidth(600.0);
		
		FXMLLoader searchResultsLoader = new FXMLLoader();
		searchResultsLoader.setLocation(Controller.class.getResource("/view/SearchResults.fxml"));
		AnchorPane searchResultsRoot = (AnchorPane) searchResultsLoader.load();
		searchResultsScene = new Scene(searchResultsRoot);
		searchResultsController = searchResultsLoader.getController();
		searchResultsController.setPrimaryStage(secondaryStage);
		secondaryStage.setScene(searchResultsScene);
		
		try {
			
			File f = new File("./UserList.dat");
			if(f.exists()) {
				
			
			FileInputStream fis = new FileInputStream("UserList.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<User> list = (List<User>)ois.readObject();
			userList.addAll(list);
			}
			else {
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeUserList();
		
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
		
		
		
		login();
		/*
		 * NOTE
		 * We may have to eventually change this so that we do not load the
		 * controllers all at once, but rather, we load the login controller,
		 * then once the user is logged in we load the album home controller,
		 * then once we enter an album we load the photoscontroller, etc.
		 * This is because each controller must load all the data they need upon 
		 * creation. So if we load them all at once, we are loading the Photos
		 * controller before we even know what user is using the application.
		 * 
		 */
	}
	
	/**
	 * Initializes userlist with admin and stock users unless they already
	 * exist
	 */
	public static void initializeUserList() {
		
		if(!userInList("admin")) {
		userList.add(new User("admin"));
		}
		if(!userInList("stock")) {
			User u = new User("stock");
			userList.add(u);
			Album a = new Album("stock");
			u.getAlbumList().add(a);
			File f = new File("./stockPhotos/images.jpeg");
			a.addToAlbum(fileToPhoto(f));
			File f1 = new File("./stockPhotos/images-2.jpeg");
			a.addToAlbum(fileToPhoto(f1));
			File f2 = new File("./stockPhotos/images-3.jpeg");
			a.addToAlbum(fileToPhoto(f2));
			File f3 = new File("./stockPhotos/images-4.jpeg");
			a.addToAlbum(fileToPhoto(f3));
			File f4 = new File("./stockPhotos/images-5.jpeg");
			a.addToAlbum(fileToPhoto(f4));
			
			
		}
			
	}
	
	private static Photo fileToPhoto(File file) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(file.lastModified());
		Image image = new Image(file.toURI().toString());
		Photo photo = new Photo(image, date, file);
		return photo;
		
	}
	/**
	 * Shows login screen
	 */
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
	/**
	 * Goes to album screen
	 */
	public static void toAlbum() {
		primaryStage.hide();
		primaryStage.setScene(albumHomeScene);
		albumController.start();
		primaryStage.show();
	}
	
//	public static void backToAlbum() {
//		primaryStage.hide();
//		primaryStage.setScene(albumHomeScene);
//		
//		primaryStage.show();
//	}
	/**
	 * Logs user out and goes to logout screen
	 */
	public static void logout() {
		primaryStage.hide();
		primaryStage.setScene(loginScene);
		user = null;
		albumList = FXCollections.observableArrayList(); // reset albumList
		primaryStage.show();
	}
	/**
	 * Goes to photos screen
	 */
	public static void toPhotos() {
		primaryStage.hide();
		primaryStage.setScene(photosScene);
		photosController.start();
		primaryStage.show();
	}
	/**
	 * Opens an album to go into photos screen
	 * @param thisAlbum
	 */
	public static void openThisAlbum(Album thisAlbum) {
		primaryStage.hide();
		primaryStage.setScene(photosScene);
		photosController.start(thisAlbum);
		primaryStage.show();
	}
	
	public void initialize() {
		//userList.add(new User("admin")); // this is for testing
		// we will delete later when we load the users from the files
	}
	
	public void start() {
		
	}
	
	public void start(Album album) { // i think this and the method above can be deleted now
		
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
	 * Opens a photo to go into the photo display screen
	 */
	public static void openPhoto() {
		primaryStage.hide();
		primaryStage.setScene(photoDisplayScene);
		
		primaryStage.show();
	}
	
	/**
	 * 
	 * @param usr
	 * @return boolean value as true if user found
	 */
	public static boolean userInList(String usr) {
		boolean found=false;
		//System.out.println("isUser called"+userList.size());
		for (int i=0;i<userList.size();i++) {
			//System.out.println(userList.get(i).toString());
			if(userList.get(i).getUserName().equals(usr)) {
				found = true;
			}
		}	
		return found;
	}
	
	/**
	 * @param albumName
	 * @return boolean value as true if album found
	 */
	public static boolean albumInList(String albumName) {
		boolean found=false;
		//System.out.println("isUser called"+userList.size());
		for (int i=0;i<albumList.size();i++) {
			//System.out.println(userList.get(i).toString());
			if(albumList.get(i).getName().equals(albumName)) {
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
	
	/**
	 * setting current user
	 * @param usr
	 */
	public static void setUser(User usr) {
		user = usr;
	}
	
	/**
	 * @return the current user
	 */
	public static User currentUser() {
		return user;
	}
	
	
	
	
}
