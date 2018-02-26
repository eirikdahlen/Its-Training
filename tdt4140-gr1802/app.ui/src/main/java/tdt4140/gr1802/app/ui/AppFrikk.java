package tdt4140.gr1802.app.ui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFrikk extends Application{
	
	Stage window ;
	Scene AddAthlete ;
	
	@Override
	public void start(Stage primaryStage) throws InvocationTargetException, IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AddAthlete.fxml")) ;
		Scene scene = new Scene(root, 800, 600) ;
		primaryStage.setScene(scene) ;
		primaryStage.show() ;
	}
	
	public static void main(String[] args) {
		launch(args) ;
	}

}




