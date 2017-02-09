package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application{
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MainApp.fxml")),640,480);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Appointments");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	private void newAppointment(ActionEvent event){
	    try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Appointment.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMaxWidth(500);
            stage.setMaxHeight(650);
            stage.setMinHeight(650);
            stage.setMinWidth(500);
            stage.setTitle("New Appointment");
            stage.setScene(new Scene(root1));  
            stage.show();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
		        }
		}
}
