package application;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class AppointmentController {
	
	private ObservableList<Building> buildings; 
	private Appointment appointment = new Appointment(); 
	private MainApp mainApp;
	private boolean isnew = true;
	
	//Setting spinner properties
	SpinnerValueFactory.IntegerSpinnerValueFactory minutt1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,15);
	SpinnerValueFactory.IntegerSpinnerValueFactory minutt2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,15);	
	SpinnerValueFactory.IntegerSpinnerValueFactory time1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,24,12,1);
	SpinnerValueFactory.IntegerSpinnerValueFactory time2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,24,12,1);

	//FXML Variables
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
	
	//setting the appointment we will be working on, or creating a new one
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
		
		//If it is going to repeat we are making the menu visible
		if(appointment.getRepetisjon()!=0){
			cbRepeat.setSelected(true);
			repeat(new ActionEvent());
			tbrepeat.setText(appointment.getRepetisjon().toString());
			dpEndDate.setValue(appointment.getSlutt());
		}
		//setting new to false, now that it has values
		this.isnew = false;
		}
		
		this.appointment = appointment; 
		
		
		 
	}
	/*
	 * This method sets the mainApp, in addition it also initializes the controller
	 */
	public void start(MainApp mainApp){
		this.mainApp = mainApp;
		
		//Setting the ComboBox
		buildings = mainApp.getBuildings();
		cbbygg.setItems(buildings);
		cbbygg.setValue(cbbygg.getItems().get(0)); 
		setRomnrs(new ActionEvent()); 
		
		//Setting the Spinner
		sp2.setValueFactory(minutt1);
		sp4.setValueFactory(minutt2);
		sp1.setValueFactory(time2);
		sp3.setValueFactory(time1);
		
		
		//Disables dates before today
	    final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                            if (item.isBefore(LocalDate.now())) {
		                            setDisable(true);
		                            setStyle("-fx-background-color: #ffc0cb;"); //red
	                            }
	                        }
	                    }; 
			        }
			     };
					//Disables dates before startDate
				    final Callback<DatePicker, DateCell> endDateFactory = 
				            new Callback<DatePicker, DateCell>() {
				                @Override
				                public DateCell call(final DatePicker datePicker) {
				                    return new DateCell() {
				                        @Override
				                        public void updateItem(LocalDate item, boolean empty) {
				                            super.updateItem(item, empty);
				                            if (item.isBefore(fromDate.getValue().plusDays(1))) {
					                            setDisable(true);
					                            setStyle("-fx-background-color: #ffc0cb;"); //red
				                            }
				                        }
				                    }; 
						        }
						     };
			     
			     
		dpEndDate.setDayCellFactory(endDateFactory);
		fromDate.setDayCellFactory(dayCellFactory);
		
		fromDate.setValue(LocalDate.now());
		//Only numbers in textfield
	    tbrepeat.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                tbrepeat.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	  }
	/*
	 * Makes the repeat section visible/not visible
	 */
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
	
	/*
	 * Listener for the CheckBox.
	 */
	@FXML
	private void repeat(ActionEvent event) {
	     if(cbRepeat.isSelected()) {
	    	 makeVisible(true);
	     }
	     else {
	    	 makeVisible(false);
	     }
	 } 
	
	/*
	 * Updates romnr when "bygg" is changed"
	 */
	@FXML
	private void setRomnrs(ActionEvent event){
		ObservableList<String> rooms = FXCollections.observableArrayList(cbbygg.getValue().getRooms());
		cbRomNr.setItems(rooms);
		cbRomNr.setValue(cbRomNr.getItems().get(0)); 
	}
	
	/*
	 * Returns the Appointment object, with changes made
	 */
	@FXML
	private void make(ActionEvent event){
		
		//Checks to see to see if it has the necessarily info 
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
			 
			 //get the info from the control elements
			 
			 appointment.setFormal(formal.getText());
			 appointment.setRoom(new Room(cbbygg.getValue(),cbRomNr.getValue()));
			 appointment.setDato(fromDate.getValue());
			 
			 LocalTime endTime = LocalTime.of(time1.getValue(), minutt2.getValue());
			 LocalTime startTime = LocalTime.of(time2.getValue(), minutt1.getValue());
			 appointment.setFra(startTime);
			 appointment.setTil(endTime);
			 

			 //Checks if we want to repeat, if so adds the values
			 if(!cbRepeat.isSelected() || tbrepeat.getText()!=""){
				 appointment.setRepetisjon(0);
				 appointment.setSlutt(null);
			 }
			 else{
				 appointment.setRepetisjon(Integer.parseInt(tbrepeat.getText()));
				 appointment.setSlutt(dpEndDate.getValue());
			 }
			 
			 //if it is a new appointment it adds it to the list
			 if(isnew){
			 mainApp.setAppointment(appointment);
			 }
			 
			 //Updates the table
			 mainApp.setTable();
			 
			 //Closes the window
			 Stage stage = (Stage) btMake.getScene().getWindow();
			 stage.close();
		 }
	 }
}
