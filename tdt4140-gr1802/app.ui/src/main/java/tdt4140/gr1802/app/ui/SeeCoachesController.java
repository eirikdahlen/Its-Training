package tdt4140.gr1802.app.ui;



import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	

}
