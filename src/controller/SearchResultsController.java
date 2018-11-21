package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import model.Album;
import model.Photo;
import model.Tag;

public class SearchResultsController extends Controller {

	private final static String DATE_SEARCH = "Date range search";
	private final static String SINGLE_TAG_SEARCH = "Single tag-value search";
	private final static String CONJUNCTIVE_SEARCH = "Conjuntive tag-value search";
	private final static String DISJUNCTIVE_SEARCH = "Disjunctive tag-value search";
	
	ObservableList<Photo> searchResults = FXCollections.observableArrayList();
	
	String selectedOption;
	
	@FXML ScrollPane scrollpane;
	
	@FXML TilePane tilePane;
	
	public SearchResultsController() {
		// TODO Auto-generated constructor stub
	}
	
	public void start(String selectedOption, String startDate, String endDate, String tag1Type, String tag1Value, String tag2Type, String tag2Value) {
		startDate = startDate.trim(); endDate = endDate.trim(); tag1Type = tag1Type.trim(); 
		tag1Value = tag1Value.trim(); tag2Type = tag2Type.trim(); tag2Value = tag2Value.trim();
		
		this.selectedOption = selectedOption;
		scrollpane.setContent(tilePane);
		scrollpane.setFitToWidth(true);
		tilePane.getChildren().clear();
		
		searchResults.clear();
		
		if(selectedOption.equals(DATE_SEARCH)) {
			if(matchesFormat(startDate) && matchesFormat(endDate)) {
				Calendar startCal = getCal(startDate);
				Calendar endCal = getCal(endDate);
				dateSearch(startCal, endCal);
			} else {
				// show error message
				// shut down this controller
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("One or more of the entered dates do not match the format: dd/MM/yyyy");
	            alert.showAndWait();
	            close();
	            return;
			}
		} else if (selectedOption.equals(SINGLE_TAG_SEARCH)) {
			if(tag1Value.isEmpty() || tag1Type.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("Some information not entered for search");
	            alert.showAndWait();
	            close();
	            return;
			}
			singleSearch(tag1Type, tag1Value);
		} else if (selectedOption.equals(CONJUNCTIVE_SEARCH)) {
			if(tag1Value.isEmpty() || tag1Type.isEmpty() || tag2Value.isEmpty() || tag2Type.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("Some information not entered for search");
	            alert.showAndWait();
	            close();
	            return;
			}
			conjunctiveSearch(tag1Type, tag2Type, tag1Value, tag2Value);
		} else if (selectedOption.equals(DISJUNCTIVE_SEARCH)) {
			if(tag1Value.isEmpty() || tag1Type.isEmpty() || tag2Value.isEmpty() || tag2Type.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("Some information not entered for search");
	            alert.showAndWait();
	            close();
	            return;
			}
			disjunctiveSearch(tag1Type, tag2Type, tag1Value, tag2Value);
		}
		
		// file tilePane
		for(Photo photo: searchResults) {
			ImageView img = new ImageView();
			img.setImage(photo.getThumbnail());
			img = initializeImageView(img);
			tilePane.getChildren().add(img);
			}
		
		if(searchResults.isEmpty()) {
			// show message saying no results
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Results");
            alert.setContentText("No photos were found.");
            alert.showAndWait();
		}
		close();
	}
	private void dateSearch(Calendar startCal, Calendar endCal) {
		
		SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
		endCal.add(Calendar.DATE, 1);
		endCal.add(Calendar.MILLISECOND, -1);
		System.out.println(startCal.getTime());
		System.out.println(endCal.getTime());
		for(Album album: currentUser().getAlbumList()) {
			for(Photo photo: album.getPhotos()) {
				if(photo.getDate().compareTo(startCal) >= 0 && photo.getDate().compareTo(endCal) <= 0) {
					if(!searchResults.contains(photo)) {
						searchResults.add(photo);
					}
				}
			}
		}
	}
	private void singleSearch(String tagType, String tagValue) {
		for(Album album: currentUser().getAlbumList()) {
			for(Photo photo: album.getPhotos()) {
				for(Tag tag: photo.getTags()) {
					if(tagType.toLowerCase().equals(tag.getTag().toLowerCase()) && tagValue.toLowerCase().equals(tag.getValue().toLowerCase())) {
						if(!searchResults.contains(photo))
							searchResults.add(photo);
					}
				}
			}
		}
	}
	private void conjunctiveSearch(String tag1Type, String tag2Type, String tag1Value, String tag2Value) {
		boolean hasFirstTag;
		boolean hasSecondTag;
		for(Album album: currentUser().getAlbumList()) {
			for(Photo photo: album.getPhotos()) {
				hasFirstTag = false;
				hasSecondTag = false;
				for(Tag tag: photo.getTags()) {
					if(!hasFirstTag)
						hasFirstTag = tag1Type.toLowerCase().equals(tag.getTag().toLowerCase()) && tag1Value.toLowerCase().equals(tag.getValue().toLowerCase());
					if(!hasSecondTag)
						hasSecondTag = tag2Type.toLowerCase().equals(tag.getTag().toLowerCase()) && tag2Value.toLowerCase().equals(tag.getValue().toLowerCase());
					if(hasFirstTag && hasSecondTag) {
						if(!searchResults.contains(photo))
							searchResults.add(photo);
					}
				}
			}
		}
	}
	private void disjunctiveSearch(String tag1Type, String tag2Type, String tag1Value, String tag2Value) {
		boolean hasFirstTag;
		boolean hasSecondTag;
		for(Album album: currentUser().getAlbumList()) {
			for(Photo photo: album.getPhotos()) {
				hasFirstTag = false;
				hasSecondTag = false;
				for(Tag tag: photo.getTags()) {
					if(!hasFirstTag)
						hasFirstTag = tag1Type.toLowerCase().equals(tag.getTag().toLowerCase()) && tag1Value.toLowerCase().equals(tag.getValue().toLowerCase());
					if(!hasSecondTag)
						hasSecondTag = tag2Type.toLowerCase().equals(tag.getTag().toLowerCase()) && tag2Value.toLowerCase().equals(tag.getValue().toLowerCase());
					if(hasFirstTag || hasSecondTag) {
						if(!searchResults.contains(photo))
							searchResults.add(photo);
					}
				}
			}
		}
	}
	
	private boolean matchesFormat(String date) {
		SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
		spf.setLenient(false);
		try {
			spf.parse(date.trim());
		} catch(ParseException pe) {
			return false;
		}
		return true;
	}
	
	private Calendar getCal(String strDate) {
		SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
		spf.setLenient(false);
		Date date = null;
		try {
			date = spf.parse(strDate.trim());
		} catch(ParseException pe) {
			return null;
		}
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(date);
		return newCal;
	}
	
	private ImageView initializeImageView(ImageView img) {
		img.setFitWidth(100.0);
		img.setFitHeight(100.0);
		return img;
	}
	
	void clearTilePane() {
		
	}
	
	private void close() {
		
	}
}
