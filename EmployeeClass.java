
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;

public class EmployeeClass{
	ComboBox<String> pastV;
	TextField tfWeight = new TextField();
	String weight = "";

void collectFields(TextField tfw) {
	tfw = tfWeight;
	
}
	
String returnFileFromVisitBox(ComboBox<String> cb) {
	System.out.println(cb.getId());
	return cb.getId();
	//return cb.valueProperty().get();
}
	
//Needs 10 inputs vitals->history->doctor data
void clearInputAreas(TextField weight, TextField height, TextField bodyTemp, TextField bloodP, CheckBox over12, TextArea hIssues, TextArea pMedications, TextArea hImmunizations, TextArea dNotes, TextArea prescribeMeds ) {
	weight.setText("#");
	height.setText("#");
	bodyTemp.setText("#");
	bloodP.setText("#");
}

String retChosenFile(ComboBox<String> cb) {
	return cb.valueProperty().get();
}

void updateWeight(TextField wtf) {
	if(!weight.equals(wtf.getText())) {
		wtf.setText(weight);
	}
	
}

void updateBoxes(String filename) {
	filename = "PatientVisits\\" + filename;
	
	String entireFile = "";
	try {		
		entireFile = Files.readString(Paths.get(filename));
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//System.out.println("Unable to grab entire file from pastVisits combobox");
		//e.printStackTrace();
	}
	
	Scanner scanner = new Scanner(filename); //Get File
	//scanner.useDelimiter("<");
	weight = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
	entireFile = entireFile.substring(entireFile.indexOf(">"), entireFile.length());
	
	System.out.println(weight);
	
	
	//String height = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));;
	//entireFile = entireFile.substring(entireFile.indexOf(">"), entireFile.length() - 1);
	//System.out.println(height);
	
	/*
	String bodyTemp = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));;
	entireFile = entireFile.substring(entireFile.indexOf(">"), entireFile.length());
	
	String bloodP = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));;
	entireFile = entireFile.substring(entireFile.indexOf(">"), entireFile.length());
	
	boolean ageOver12 = Boolean.parseBoolean(entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">")));
	*/
	
	//String 
	//System.out.println(weight);
	
	//System.out.println(filename);
	/*
	Scanner scanner = new Scanner(filename); //Get File
	String entireFile = "";
	try {		
		entireFile = Files.readString(Paths.get(filename));
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//System.out.println("Unable to grab entire file from pastVisits combobox");
		//e.printStackTrace();
	}
	
	int weightS = entireFile.indexOf("Weight ");
	int weightE = weightS + 7;
	System.out.println(entireFile.substring(weightS, weightE));
	//vWeightTF.setText(entireFile.substring(weightS, weightE));
	
	scanner.close();
	*/
}

ComboBox<String> refreshPatientList(HBox prevVisitsBox, String doc_selectedPatient, ComboBox<String> patientNames, ComboBox<String> prevVisits, Label prevVisitsL) {	
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

	//String chosenFile = prevVisits.valueProperty().get();
	//System.out.println(chosenFile);
	pastV = prevVisits;
	prevVisits.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg1) {
			updateBoxes(pastV.valueProperty().get());
			//System.out.println(pastV.valueProperty().get()); 
			
			/*
			System.out.println(chosenFile);
			Scanner scanner = new Scanner(chosenFile); //Get Fil)e
			String entireFile = "";
			try {		
				entireFile = new String(Files.readAllBytes(Paths.get(chosenFile)));
				System.out.println(entireFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to grab entire file from pastVisits combobox");
				//e.printStackTrace();
			}
			
			int weightS = entireFile.indexOf("Weight ");
			int weightE = weightS + 7;
			System.out.println(entireFile.substring(weightS, weightE));
			//vWeightTF.setText(entireFile.substring(weightS, weightE));
			
			scanner.close();
			//employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);	
		*/
		}
	});
	return prevVisits;
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

void createNewVisit(String username, String vitals, String history, String docNotes, String presMeds ) {
	System.out.println(username);
	String directory = "PatientVisits";
	
	LocalDate todaysDate = LocalDate.now();
	String filename = username + "_" + todaysDate + ".txt";
	
	try {
		System.out.println(filename);
		//System.out.println("Entered try");
		FileWriter fw = new FileWriter(directory + "\\" + filename);
		PrintWriter pw = new PrintWriter(fw);
		pw.print("Visit for " + username + " on: " + todaysDate + "\n");
		pw.print(vitals);
		pw.print(history);
		pw.print("\nNotes from the Doctor: " + "<" + docNotes + ">" + "\n");
		pw.print("\nMedications to prescribe: " + "<" + presMeds + ">" + "\n");
		
		pw.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("\nUnable to create new visit");
		//e.printStackTrace();
	}
}

String vitalsOuput(String weight, String height, String bodyTemp, String bloodP, boolean over12) {
	String finalOut = "";
	finalOut += "Vitals Information\n";
	finalOut += "Weight: " + "<" + weight + ">" + "\n";
	finalOut += "Height: " + "<" + height + ">" + "\n";
	finalOut += "Body Temperature: " + "<" + bodyTemp + ">" + "\n";
	finalOut += "Blood Pressure: " + "<" + bloodP + ">" + "\n";
	if(over12 == true) {
		finalOut += "Age is over 12?: <true>\n";
	}
	else {
		finalOut += "Age is over 12?: <false>\n";
	}
	
	return finalOut;
}

String medicalHistoryOutput(String hIssues, String pMedications, String hImmunizations) {
	String finalOut = "";
	finalOut += "\nMedical History\n";
	finalOut += "\nPrevious Health Issues:\n" + "<" + hIssues + ">" + "\n";
	finalOut += "\nPrevious Medications:\n" + "<" + pMedications + ">" + "\n";
	finalOut += "\nHistory of Immunizations:\n" + "<" + hImmunizations + ">" + "\n";
	return finalOut;
}
} //end of EmployeeClass