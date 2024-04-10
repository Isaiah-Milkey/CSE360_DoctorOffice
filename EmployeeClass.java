import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;

public class EmployeeClass{

void refreshPrevVisits() {

}

void refreshPatientList(HBox prevVisitsBox, String doc_selectedPatient, ComboBox<String> patientNames, ComboBox<String> prevVisits, Label prevVisitsL) {
	
	prevVisitsBox.getChildren().clear();
	doc_selectedPatient = patientNames.valueProperty().get();
	prevVisits = getVisitsList(doc_selectedPatient);
	prevVisitsL.setStyle("-fx-font: 20px \"Arial\";");
	prevVisitsL.setAlignment(Pos.CENTER);
	prevVisits = getVisitsList(doc_selectedPatient); //should be updated on every event on patientNames ComboBox
	prevVisits.setPromptText("Visit");
	prevVisits.getItems().add("Temp Visits");
	prevVisits.getStyleClass().add("labelB");
	prevVisits.setMinWidth(100);
	prevVisits.setMaxWidth(200);
	prevVisitsBox.getChildren().addAll(prevVisitsL, prevVisits);
}
	
ComboBox<String> getPatientList(){
	ComboBox<String> cb = new ComboBox<String>();
	File root = new File("Users");
	try {
		File[] patientsFolder = root.listFiles();
		for (int i = 0; i < patientsFolder.length; i++) {
			if (patientsFolder[i].getName().endsWith(".txt")) {
				String patientName = patientsFolder[i].getName();
				patientName = patientName.substring(0, patientName.length() - 4); //shorten name
				cb.getItems().addAll(patientName);
			}
		}
	}
	catch (Exception e) { // Throw error message if file cannot be created
		// TODO Auto-generated catch block
		System.out.println("\nPatient ComboBox unable to be updated");
		e.printStackTrace();
		return cb; // Dont return a pane on error
	}
	
	return cb;
}

ComboBox<String> getVisitsList(String username){
	System.out.println(username);
	ComboBox<String> cb = new ComboBox<String>();
	File root = new File("PatientVisits");

	try {
		File[] visits = root.listFiles();
		for (int i = 0; i < visits.length; i++) {
			//System.out.println("\nEnter for loop\n");
			if (visits[i].getName().startsWith(username) ) {
				//System.out.println("add item");
				cb.getItems().addAll(visits[i].getName());
			}
		}
	} catch (Exception e) { // Throw error message if file cannot be created
		// TODO Auto-generated catch block
		System.out.println("\nVisits ComboBox unable to be updated");
		//e.printStackTrace();
		return cb; // Dont return a pane on error
	}
	return cb;
}

void createNewVisit(String username) {
	System.out.println(username);
	String directory = "PatientVisits";
	
	LocalDate todaysDate = LocalDate.now();
	String filename = username + "_" + todaysDate + ".txt";
	
	try {
		System.out.println(filename);
		System.out.println("Entered try");
		FileWriter fw = new FileWriter(directory + "\\" + filename);
		PrintWriter pw = new PrintWriter(fw);
		pw.print("This is a test");
		pw.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("\nUnable to create new visit");
		//e.printStackTrace();
	}
}
	
	
}