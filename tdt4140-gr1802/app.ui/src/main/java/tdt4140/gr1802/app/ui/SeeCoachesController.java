package tdt4140.gr1802.app.ui;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1802.app.core.Coach;

public class SeeCoachesController {
	
	@FXML
	private TableView<Coach> tableView;
	
	@FXML
	private TableColumn<Coach, String> selectColumn;
	
	@FXML
	private TableColumn<Coach, Integer> IDColumn;
	
	@FXML
	private TableColumn<Coach, String> nameColumn;
	
	@FXML
	private TableColumn<Coach, String> emailColumn;
	
	private ObservableList<Coach> list = FXCollections.observableArrayList();
	
	public void init() {
		fillList();
		update();
		
	}
	
	private void fillList() {
		String s = "eirik";
		for (int i = 0; i < 20; i++) {
			Coach c = new Coach(null, s, null, null);
			list.add(c);
			s += "a";
		}
	}
	
	private void update() {
		tableView.setItems(list);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Coach, String>("name"));
		
	}
	
	private void remove() {
		
	}
	
	public void clickAddWorkout (ActionEvent event) throws IOException, LoadException{
		// Open new window 
		Parent root = FXMLLoader.load(getClass().getResource("AddWorkout.fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
		
	}
	
	public void clickSeeWorkouts (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeWorkouts.fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void clickSeeCoaches (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("SeeCoaches.fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	public void clickCoachRequest (ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource(".fxml"));
		Scene scene = new Scene(root,800,600);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(scene);
		window.show();
	}
	
	

}
