package application;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AppointmentController {
	private ObservableList<Building> buildings; 
	private Appointment appointment = new Appointment(); 
	private MainApp mainApp;
	private boolean isnew = true;
	SpinnerValueFactory.IntegerSpinnerValueFactory minutt1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,-15);
	SpinnerValueFactory.IntegerSpinnerValueFactory minutt2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,-15);
	
	SpinnerValueFactory.IntegerSpinnerValueFactory time1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,24,0,-1);
	SpinnerValueFactory.IntegerSpinnerValueFactory time2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,24,0,-1);
	
	@FXML
	CheckBox cbRepeat;
	@FXML
	Pane repeatPane; 
	@FXML
	GridPane mainGrid; 
	@FXML
	Button btMake; 
	@FXML
	TextArea formal; 
	@FXML
	Label errorFormal; 
	@FXML
	ComboBox<Building> cbbygg; 
	@FXML 
	ComboBox<String> cbRomNr;
	@FXML
	Spinner<Integer> sp1; 
	@FXML
	Spinner<Integer> sp2; 
	@FXML
	Spinner<Integer> sp3; 
	@FXML
	Spinner<Integer> sp4; 
	@FXML
	DatePicker fromDate; 
	@FXML
	Label errorrom; 
	@FXML
	Label errordato; 
	@FXML 
	Label errorkl;
	@FXML
	TextField tbrepeat;
	@FXML
	DatePicker dpEndDate; 
	
	public void setAppointment(Appointment appointment, boolean isnew){
		if(!isnew){
		formal.setText(appointment.getFormal());
		fromDate.setValue(appointment.getDato());
		cbbygg.setValue(appointment.getRoom().getBuilding());
		cbRomNr.setValue(appointment.getRoom().getRoom());
		
		sp1.getValueFactory().setValue(appointment.getFra().getHour());
		sp2.getValueFactory().setValue(appointment.getFra().getMinute());
		sp3.getValueFactory().setValue(appointment.getTil().getHour());
		sp4.getValueFactory().setValue(appointment.getTil().getMinute());
		this.isnew = false;
		}
		this.appointment = appointment; 
		 
	}
	
	public void start(MainApp mainApp){
		this.mainApp = mainApp;
		
		buildings = mainApp.getBuildings();
		cbbygg.setItems(buildings);
		cbbygg.setValue(cbbygg.getItems().get(0)); 
		setRomnrs(new ActionEvent()); 
		
		sp2.setEditable(true);
		sp2.setValueFactory(minutt1);
		sp4.setEditable(true);
		sp4.setValueFactory(minutt2);
		
		
		sp1.setEditable(true);
		sp1.setValueFactory(time2);
		sp3.setEditable(true);
		sp3.setValueFactory(time1);
	}
	
	@FXML
	private void repeat(ActionEvent event) {
	     if(cbRepeat.isSelected()) {
	    	 makeVisible(true);
	     }
	     else {
	    	 makeVisible(false);
	     }
	 } 
	@FXML
	private void setRomnrs(ActionEvent event){
		ObservableList<String> rooms = FXCollections.observableArrayList(cbbygg.getValue().getRooms());
		cbRomNr.setItems(rooms);
		cbRomNr.setValue(cbRomNr.getItems().get(0)); 
	}	
	 @FXML
	private void make(ActionEvent event){
		 if(formal.getText().equals("") || cbbygg.getValue().equals("") || cbRomNr.equals("") || fromDate.getValue()==null || time1.getValue() <= time2.getValue()) {
			 errorFormal.setVisible(false);
			 errorrom.setVisible(false); 
			 errorkl.setVisible(false);
			 errordato.setVisible(false);
			 
			 if(formal.getText().equals("")){
				 errorFormal.setVisible(true);
			 }
			 if(cbbygg.getValue().equals("") || cbRomNr.equals("")){
				 errorrom.setVisible(true); 
			 }
			 if(time1.getValue() < time2.getValue() || (time1.getValue() == time2.getValue() && minutt1.getValue() >= minutt2.getValue())){
				errorkl.setVisible(true);
			 }
			 if(fromDate.getValue()==null){
				 errordato.setVisible(true);
			 }
		 }
		 else {	 
			 appointment.setFormal(formal.getText());
			 appointment.setRoom(new Room(cbbygg.getValue(),cbRomNr.getValue()));
			 appointment.setDato(fromDate.getValue());
			 
			 LocalTime endTime = LocalTime.of(time1.getValue(), minutt2.getValue());
			 LocalTime startTime = LocalTime.of(time2.getValue(), minutt1.getValue());
			 appointment.setFra(startTime);
			 appointment.setTil(endTime);
			 
			 if(!cbRepeat.isSelected()){
				 appointment.setRepetisjon(0);
			 }
			 else{
				 appointment.setRepetisjon(Integer.parseInt(tbrepeat.getText()));
				 appointment.setSlutt(dpEndDate.getValue());
			 }
			 if(isnew){
			 mainApp.setAppointment(appointment);
			 }
			 mainApp.setTable();
			 Stage stage = (Stage) btMake.getScene().getWindow();
			 stage.close();
		 }
	 }
	private void makeVisible(boolean visible){
		if(visible){
			repeatPane.setVisible(true);
			mainGrid.getRowConstraints().get(3).setMaxHeight(100);
			mainGrid.getRowConstraints().get(4).setPrefHeight(0);
			mainGrid.getRowConstraints().get(4).setMaxHeight(0);
			mainGrid.getRowConstraints().get(4).setMinHeight(0);
		}
		else{
			repeatPane.setVisible(false);
			mainGrid.getRowConstraints().get(3).setMaxHeight(0);
			mainGrid.getRowConstraints().get(4).setPrefHeight(160);
			mainGrid.getRowConstraints().get(4).setMaxHeight(160);
			mainGrid.getRowConstraints().get(4).setMinHeight(160);
		}
	}
}
