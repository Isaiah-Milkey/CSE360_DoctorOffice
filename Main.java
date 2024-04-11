package application;
	

// convert logBox to an HBox

import javafx.scene.Node;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.*;

public class Main extends Application {
	private Stage stage = new Stage();
	private Rectangle rect = new Rectangle(0, 0, 0, 141);
	private Rectangle rect2 = new Rectangle(0, 0, 0, 141);
	private Group rectRoot;
	private BorderPane sroot;
	HBox imageBox, blankBox;
	VBox homeBox;
	Pane logBox;
	Image medStar, user, userInv;
	ImageView starView, userView, userInvView;
	Label 	 docOff, docOff2, 
			 patientLN, patientFN, patientDOB, 
			 heightL, weightL, sexL, blankL,
			 currAddL, currPharmaL, currMedsL, createPasswordL;
	TextField userName, password, lastName, 
			 firstName, weight, heightFt, heightIn, 
			 address, currPharma, currMeds, createPassword,
			 vWeightTF, vHeightTF, vBTempTF, vBPressTF, msgField;
	TextArea medsL, medsTA, doctorNotesTA, prevHealthIssuesTA,
			 prevMedsTA, immunizationsTA, chatArea;
	Button   login, signUp, confirmLogin, confirmSignUp, createVisit;
	CheckBox twelveCB;
	DatePicker pDOB = new DatePicker(); 
	ComboBox<String> sexBox = new ComboBox<String>();
	ComboBox<String> userBox = new ComboBox<String>();
	String fileName;
	File chatHistoryFile;
	String userType; //If patient is logged in or Doctor/Nurse
	String patientID = "Vinnie_Freeze";
	String doctorNurseID = "Doctor_Caggiula";
	
	public void chatPopup() {
		//create basic chat history file and check that it was successfully created
		chatHistoryFile = new File(getChatHistoryFilePath());
		if(chatHistoryFile.exists()) {
			System.out.println("Chat history file exists.");
		} else {
			System.out.println("Chat history file does not exist.");
		}
		
		//Simple UI layout just for functionality
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		
		chatArea = new TextArea();
		chatArea.setEditable(false);
		chatArea.setWrapText(true);
		chatArea.setStyle("-fx-font: bold italic 16px \"Arial\"; ");
		root.setCenter(chatArea);
		
		msgField = new TextField();
		msgField.setPromptText("Type your message...");
		msgField.setStyle("-fx-font: bold italic 16px \"Arial\"; ");
		root.setBottom(msgField);
		
		Button sendButton = new Button("Send");
		sendButton.setStyle("-fx-font: 16px \"Arial\"; -fx-min-width: 80; -fx-max-width: 80;");
		sendButton.setOnAction(e-> sendMessage());
		root.setRight(sendButton);
		
		loadChatHistory();
		
		Scene scene = new Scene(root, 400, 300);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage chatStage = new Stage();
		chatStage.setScene(scene);
		chatStage.setTitle("Filesystem Based Chat");
		chatStage.initModality(Modality.APPLICATION_MODAL);
		chatStage.showAndWait();
	}
	
	//function to send a message
	private void sendMessage() {
		String msg = msgField.getText().trim();
		if(!msg.isEmpty()) {
			logMessage(msg);
			msgField.clear();
		}
	}
	//will save message along with userType and time, userType is set in log in class
	private void logMessage(String msg) {
		
		//if statement can be removed/commented out if you want to test sending messages and if they get saved to file correctly
		//if(userType != null && patientID != null && doctorNurseID != null) {
		
		// *****************************************************************************************************************
		// add functionality to have doctors choose patients and vice versa based on current doctors and patients that exist
		// *****************************************************************************************************************
		
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("ChatLogs\\" + getChatHistoryFilePath(),true)))) {
			LocalDateTime timestamp = LocalDateTime.now();
			String time = timestamp + "";
			time = time.substring(5, 7) + "/" + time.substring(8, 10) + "@" + time.substring(11, 19);
			writer.println("[" + time + "] " + userType + ": " + msg);
			chatArea.appendText("[" + time + "] You: " + msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//will load chat history but user info needs to be set in log in class first
	private void loadChatHistory() {
		try (BufferedReader reader = new BufferedReader(new FileReader("ChatLogs\\" + chatHistoryFile))){
			String line;
			while((line = reader.readLine()) != null) {
				chatArea.appendText(line + "\n");
			}
		} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	//create a unique file for patient and doctor/nurse, log in class needs to set the info first though
	private String getChatHistoryFilePath() {
		return "chat_history_" + patientID + "_" + doctorNurseID + ".txt";
	}
	
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	
	public void setDoctorNurseID(String doctorNurseID) {
		this.doctorNurseID = doctorNurseID;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		stage.setScene(homePage());
		stage.setWidth(1000);
		stage.setHeight(670);
        stage.setTitle("Doctornator");
		stage.show();
	}
	
	public void switchScenes(Scene scene) {
		stage.setScene(scene);
	}
	
	public FadeTransition createFade (Node n, int s, double start, double end) {
		FadeTransition ft = new FadeTransition();
		ft.setNode(n);
		ft.setDuration(Duration.millis(s));
		ft.setFromValue(start);
		ft.setToValue(end);
		return ft;
	}
	
	public void loginTransition(Scene start, Scene end) {
		TranslateTransition tt = new TranslateTransition();
		FadeTransition ftHomeBox = createFade(homeBox, 1000, 1, 0);
		FadeTransition ftUser = createFade(userView, 1200, 0, 1);
		FadeTransition ftText = createFade(docOff2, 1200, 0, 1);
		FadeTransition ftText1 = createFade(docOff, 300, 1, 0);
		FadeTransition ftblank = createFade(rect, 1000, 1, 1);
		FadeTransition ftblank2 = createFade(rect, 200, 1, 1);
		FadeTransition ftUserInv = createFade(userInvView, 300, 1, 0);
		FadeTransition ftLogBox = createFade(logBox, 1200, 0, 1);
		FadeTransition ftStar = createFade (starView, 1200, 0, 1);
		ParallelTransition finaltrn = new ParallelTransition();
		ParallelTransition starttrn = new ParallelTransition();
		SequentialTransition seq = new SequentialTransition();
		
		
		ftUser.setRate(1);
		starttrn.getChildren().addAll(ftText1, ftUserInv);
	    finaltrn.getChildren().addAll(ftText, ftUser, ftStar, ftLogBox);
	    seq.getChildren().addAll(starttrn, ftblank2, finaltrn);
	    ftHomeBox.play();
	    ftblank.play();
		ftblank.setOnFinished(e -> {
			seq.play();
		});
		tt.setDuration(Duration.millis(1550));
		tt.setNode(rect);
		System.out.print(rect.getY());
		tt.setToY(-rect.getY());
		tt.play();
		
		tt.setOnFinished(e -> {
			stage.setScene(end);
		});
	}
	
	public TextArea createta(String innerText) {
		TextArea f = new TextArea();
		GridPane.setColumnSpan(f, 2);
		f.getStyleClass().add("textFields");
		f.setPromptText(innerText);
		f.setMinWidth(100);
		f.setMaxWidth(200);
		return f;
	}
	
	public TextField createtf(String innerText) {
		TextField f = new TextField();
		GridPane.setColumnSpan(f, 2);
		f.getStyleClass().add("textFields");
		f.setPromptText(innerText);
		f.setMinWidth(100);
		f.setMaxWidth(200);
		return f;
	}
	
	public Label createLabel(String labelName) {
		Label l = new Label(labelName);
		l.getStyleClass().add("labelB");
		return l;
	}
	
	String getPatientInfo() {
		// first name, last name, birthday, see and change contact info, summary of visit, send messages to nursedoctor
		//String line;
		try {
        	BufferedReader infoReader = new BufferedReader(new FileReader("Users\\" + fileName));
            String passwordFromFile = infoReader.readLine() + "\n";
            String userTypeFromFile = infoReader.readLine() + "\n";
            String firstNameFromFile = "Name: \t\t\t" + infoReader.readLine();
            String lastNameFromFile =  " " + infoReader.readLine() + "\n";
            String userType = "User Type: \t\t" + userTypeFromFile;
            String DOBFromFile = "Date of Birth: \t\t" + infoReader.readLine() + "\n";
            String heightFromFile = "Height: \t\t\t" + infoReader.readLine() + "\n";
            String weightFromFile = "Weight: \t\t\t" + infoReader.readLine() + "\n";
            String sexFromFile = "Sex: \t\t\t" + infoReader.readLine() + "\n";
            String addressFromFile = "Address: \t\t\t" + infoReader.readLine() + "\n";
            String pharmacyFromFile = "Pharmacy: \t\t" + infoReader.readLine() + "\n";
            String medicationFromFile = "Medication(s): \t\t" + infoReader.readLine() + "\n";
            String allOfThem = firstNameFromFile + lastNameFromFile + userType + DOBFromFile + heightFromFile + weightFromFile + sexFromFile + addressFromFile + pharmacyFromFile + medicationFromFile;
            infoReader.close();
            System.out.println(allOfThem);
            return allOfThem;

        } catch (IOException e) {
            e.printStackTrace();
        }
		return  "NO INFO CURRENTLY\n\n" +
				"THE PATIENT MIGHT BE DEAD";
	}

	public boolean fieldChecker() {
		if (userName.getText().isEmpty()) {
            return false;
        }
		if (password.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean writePatientInfoToFile() {
        // Create the filename using the patient first name and last name
        fileName = firstName.getText() + "_" + lastName.getText() + ".txt";

        String patientLastName = lastName.getText(); 
        String patientFirstName = firstName.getText();
        userType = userBox.getValue();
        LocalDate dob = pDOB.getValue();
        String height = heightFt.getText() + "'" + heightIn.getText() + "\""; 
        String weight = this.weight.getText(); 
        String sex = sexBox.getValue();
        String currentAddress = address.getText();
        String currentPharmacy = currPharma.getText();
        String currentMedications = currMeds.getText();
        String patientPassword = createPassword.getText();

        // Alert if patient leaves any of the fields empty
        if (patientLastName.isEmpty() || patientFirstName.isEmpty() || dob == null ||
                height.isEmpty() || weight.isEmpty() || sex == null || 
                currentAddress.isEmpty() || currentPharmacy.isEmpty() || 
                currentMedications.isEmpty() || patientPassword.isEmpty() || userType.isEmpty()) {
            // If any field is empty, display an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
            return false; // Stop further execution
        }

        // Write content to the file
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter("Users\\" + fileName));
        	writer.write(patientPassword + "\n");
        	writer.write(userType + "\n");
        	writer.write(patientFirstName + "\n");
        	writer.write(patientLastName + "\n");
        	writer.write(dob + "\n");
        	writer.write(height + "\n");
        	writer.write(weight + "\n");
        	writer.write(sex + "\n");
        	writer.write(currentAddress + "\n");
        	writer.write(currentPharmacy + "\n");
        	writer.write(currentMedications + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

	public String loginCredentials() {
		String userNameText = userName.getText();
        String passwordText = password.getText();
        fileName = userNameText + ".txt";

        try {
        	BufferedReader infoReader = new BufferedReader(new FileReader("Users\\" + fileName));
            String passwordFromFile = infoReader.readLine(); // Read password from the file
            String userTypeFromFile = infoReader.readLine();
            if (passwordFromFile.equals(passwordText)) {
            	infoReader.close();
    	    	System.out.println(userTypeFromFile);
            	return userTypeFromFile;
            }

            infoReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    	System.out.println("INVALID");
        return "INVALID";
	}

	// ButtonHandler handles the order, cancel, and confirm functions
	public class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Button newButton = (Button)e.getSource();
			newButton.getScene();
		}
	}

	public class FieldHandler implements EventHandler<ActionEvent> {
	    public void handle(ActionEvent e) {
	    	userType = loginCredentials().trim();
	        if (e.getSource() == confirmLogin) {
	        	if (fieldChecker() == true && userType != "INVALID") {
	        		if (userType.equals("Patient")) {
		        		setPatientID(userName.getText());
		        		switchScenes(patientView());
	        		}
	        		else if (userType.equals("Doctor")) {
	        			setDoctorNurseID(userName.getText());
		        		switchScenes(doctorView());
	        		}
	        		else if (userType.equals("Nurse")) {
	        			setDoctorNurseID(userName.getText());
		        		switchScenes(nurseView());
	        		}
	        	}
	        	else {
	        		Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Input Error");
	                alert.setHeaderText(null);
	                alert.setContentText("Please enter a correct Username and/or Password.");
	                alert.showAndWait();
	        	}
	        }
	    }
	}

	public class SignUpHandler implements EventHandler<ActionEvent> {
	    public void handle(ActionEvent e) {
	        if (e.getSource() == confirmSignUp) {
	        	if (writePatientInfoToFile()) {
	        		if (userType.equals("Patient")) {
	        			setPatientID(firstName.getText() + "_" + lastName.getText());
	        			switchScenes(patientView());
	        		}
	        		else if (userType.equals("Doctor")) {
	        			setDoctorNurseID(firstName.getText() + "_" + lastName.getText());
	        			switchScenes(doctorView());
	        		}
	        		else if (userType.equals("Nurse")) {
	        			setDoctorNurseID(firstName.getText() + "_" + lastName.getText());
		        		switchScenes(nurseView());
	        		}
	        	}
	        }
	        else {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter Username and/or Password.");
                alert.showAndWait();
        	}
        }
    }
	public Scene homePage() {
		rect.setStyle("-fx-fill: #37718E;");
		rectRoot = new Group();
		rectRoot.getChildren().add(rect);
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		userInv = new Image(getClass().getResourceAsStream("iconInv.png"));
		starView = new ImageView();
		userInvView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userInvView.setFitWidth(75);
		userInvView.setFitHeight(75);
		userInvView.setTranslateX(10);
		userInvView.setTranslateY(10);
		userInvView.setImage(userInv);
		docOff = new Label("Pediatric Doctors Office");
		docOff.setPrefWidth(10000);
		docOff.setPadding(new Insets(20, 20, 20, 20));
		docOff.setStyle("-fx-text-fill: #37718E;");

		docOff.setAlignment(Pos.CENTER_RIGHT);
		
		imageBox.getChildren().addAll(userInvView, docOff, starView);
		
		
		homeBox = new VBox();
		login = new Button("Login");
		signUp = new Button("Signup");
		homeBox.setPadding(new Insets(20, 20, 20, 20));
		homeBox.setSpacing(25);
		homeBox.setAlignment(Pos.CENTER);
		homeBox.getChildren().addAll(login, signUp);
		
		
		
		blankBox = new HBox();
		blankBox.setPrefHeight(140);
		rect.widthProperty().bind(sroot.widthProperty());
		
		// finalization
		sroot.setTop(imageBox);
		sroot.setCenter(homeBox);
		sroot.setBottom(blankBox);
		sroot.getChildren().add(rectRoot);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(600);
		//sroot.getChildren().get(3).toBack();
		rect.yProperty().bind(sroot.heightProperty().subtract(140));
		//sroot.getChildren().get(3).setBlendMode(BlendMode.OVERLAY);
		//rect.setY(460);
		Scene scene = new Scene(sroot);			// creates and launches the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		// actions
		signUp.setOnAction(e -> loginTransition(scene, signUpPage()));
		login.setOnAction(e -> loginTransition(scene, loginPage()));
		return scene;
	}
	
	public Scene loginPage() {
		rectRoot = new Group();
		rectRoot.getChildren().add(rect2);
		//bigRoot = new AnchorPane();			// bigger pane to hold most nodes, other than moving ones
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		// top bar header thing
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		user = new Image(getClass().getResourceAsStream("icon.png"));
		starView = new ImageView();
		userView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userView.setFitWidth(75);
		userView.setFitHeight(75);
		userView.setTranslateX(10);
		userView.setTranslateY(10);
		userView.setImage(user);
		docOff2 = new Label("Pediatric Doctors Office");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// center box for all login stuff
		logBox = new VBox();
		logBox.setPadding(new Insets(50, 300, 50, 300));
		userName = createtf("Username");
		userName.setMaxWidth(200);
		password = createtf("Password");
		password.setMaxWidth(200);
		confirmLogin = new Button("Login");
		confirmLogin.setPrefWidth(200);
		confirmLogin.setOnAction(new FieldHandler());
		((VBox) logBox).setSpacing(25);
		((VBox) logBox).setAlignment(Pos.CENTER);
		logBox.getChildren().addAll(userName, password, confirmLogin);
		
		// spacer box
		blankBox = new HBox();
		//imageBox.setStyle("-fx-background-color: #37718E;");
		blankBox.setPrefHeight(140);
		
		// finalization
		sroot.setTop(imageBox);
		sroot.setCenter(logBox);
		sroot.setBottom(blankBox);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(600);
		sroot.getChildren().add(rectRoot);

		sroot.getChildren().get(3).toBack();
		rect2.setStyle("-fx-fill: #37718E;");
		rect2.widthProperty().bind(sroot.widthProperty());

		
		Scene scene = new Scene(sroot);				// creates and launches the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.web("#E7F8FE"));
		return scene;
	}
	
	public Scene signUpPage () {
		rectRoot = new Group();
		rectRoot.getChildren().add(rect2);
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		// top bar header thing
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		user = new Image(getClass().getResourceAsStream("icon.png"));
		starView = new ImageView();
		userView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userView.setFitWidth(75);
		userView.setFitHeight(75);
		userView.setTranslateX(10);
		userView.setTranslateY(10);
		userView.setImage(user);
		docOff2 = new Label("Signup");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// info boxes
		GridPane left = new GridPane(4, 6),
				 right = new GridPane(4, 6);
		
		logBox = new HBox();
		logBox.setPadding(new Insets(50, 20, 50, 20));
		
		// left info
		patientLN = createLabel("Patient Last Name");
		lastName = createtf("Last Name");
		patientFN = createLabel("Patient First Name");
		firstName = createtf("First Name");
		patientDOB = createLabel("Patient DOB");
		// DataPicker options
		GridPane.setColumnSpan(pDOB, 2);
		pDOB.setPromptText("01/01/1980");
		pDOB.getStyleClass().add("labelB");
		pDOB.setMinWidth(100);
		pDOB.setMaxWidth(200);
		heightL = createLabel("Height");
		heightFt = createtf("ft");
		GridPane.setColumnSpan(heightFt, 1);
		heightFt.setMinWidth(47.5);
		heightFt.setMaxWidth(95);
		weightL = createLabel("Weight");
		weight = createtf("Weight");
		heightIn = createtf("in");
		GridPane.setColumnSpan(heightIn, 1);
		heightIn.setMinWidth(37.5);
		heightIn.setMaxWidth(95);
		sexL = createLabel("Sex");
		// Sex Drop-Down
		GridPane.setColumnSpan(sexBox, 2);
		sexBox.getItems().addAll("Male", "Female", "Other");
		sexBox.getStyleClass().add("labelB");
		sexBox.setMinWidth(100);
		sexBox.setMaxWidth(200);
		
		// right info
		currAddL = createLabel("Current Address");
		address = createtf("Address");
		currPharmaL = createLabel("Current Pharmacy");
		currPharma = createtf("Pharmacy");
		currMedsL = createLabel("Current Medications");
		currMeds = createtf("Medication");
		createPasswordL = createLabel("Create Password");
		createPassword = createtf("Password");
		Label userTypeL = createLabel("User Type");
		userBox.getItems().addAll("Patient", "Doctor", "Nurse");
		userBox.getStyleClass().add("labelB");
		userBox.setMinWidth(100);
		userBox.setMaxWidth(200);
		
		
		Label b1 = createLabel(" ");
		Label b2 = createLabel(" ");
		confirmSignUp = new Button("Confirm");
		confirmSignUp.setAlignment(Pos.CENTER);
		confirmSignUp.setOnAction(new SignUpHandler());
		
		left.add(patientLN, 0, 0);
		left.add(patientFN, 0, 1);
		left.add(patientDOB, 0, 2);
		left.add(heightL, 0, 3);
		left.add(weightL, 0, 4);
		left.add(sexL, 0, 5);
		
		left.add(lastName, 2, 0);
		left.add(firstName, 2, 1);
		left.add(pDOB, 2, 2);
		left.add(heightFt, 2, 3);
		left.add(heightIn, 3, 3);
		left.add(weight, 2, 4);
		left.add(sexBox, 2, 5);
		
		//((GridPane) logBox).add(blankL, 4, 0);
		
		right.add(currAddL, 0, 0);
		right.add(currPharmaL, 0, 1);
		right.add(currMedsL, 0, 2);
		right.add(createPasswordL, 0, 3);
		right.add(userTypeL, 0, 4);
		
		right.add(address, 2, 0);
		right.add(currPharma, 2, 1);
		right.add(currMeds, 2, 2);
		right.add(createPassword, 2, 3);
		right.add(userBox, 2, 4);
		right.add(b1, 0, 3);
		right.add(b2, 0, 4);
		
		left.setVgap(10);
		left.setHgap(10);
		right.setVgap(10);
		right.setHgap(10);
		((HBox) logBox).setAlignment(Pos.BASELINE_CENTER);
		left.setPadding(new Insets(0,50,0,0));
		right.setPadding(new Insets(0,0,0,50));
		logBox.getChildren().addAll(left, right);		
		
		// spacer box
		blankBox = new HBox();
		blankBox.setPrefHeight(140);
		blankBox.getChildren().add(confirmSignUp);
		//blankBox.prefWidth(1000);
		blankBox.setPadding(new Insets(0, 50, 50, 50));
		blankBox.setAlignment(Pos.CENTER_RIGHT);
		
		// finalization
		sroot.setTop(imageBox);
		sroot.setCenter(logBox);
		sroot.setBottom(blankBox);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(700);
		sroot.getChildren().add(rectRoot);
		
		rect2.setStyle("-fx-fill: #37718E;");
		rect2.widthProperty().bind(sroot.widthProperty());
		sroot.getChildren().get(3).toBack();
		
		Scene scene = new Scene(sroot);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.web("#E7F8FE"));
		return scene;
	}
	
	public Scene patientView () {
		//rectRoot = new Group();
		//rectRoot.getChildren().add(rect2);
		//bigRoot = new AnchorPane();			// bigger pane to hold most nodes, other than moving ones
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		// top bar header thing
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		user = new Image(getClass().getResourceAsStream("icon.png"));
		starView = new ImageView();
		userView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userView.setFitWidth(75);
		userView.setFitHeight(75);
		userView.setTranslateX(10);
		userView.setTranslateY(10);
		userView.setImage(user);
		docOff2 = new Label("Patient Portal");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// left box for user information
		VBox uInfoBox = new VBox();
		uInfoBox.setPadding(new Insets(20, 20, 20, 20));
		uInfoBox.setSpacing(20);
		Label uInfoLabel = createLabel("Your Information");
		uInfoLabel.setStyle("-fx-font: bold italic 40px \"Arial\"; ");
		Label bulkInfo = createLabel(getPatientInfo());
		bulkInfo.setStyle("-fx-font: 18px \"Arial\";");
		uInfoBox.getChildren().addAll(uInfoLabel, bulkInfo);
		
		// right box for visit information
		VBox vInfoBox = new VBox();
		HBox vLabelBox = new HBox();
		vInfoBox.setPadding(new Insets(20, 20, 20, 20));
		vInfoBox.setSpacing(20);
		Label vInfoLabel = createLabel("Visit Information");
		vInfoLabel.setStyle("-fx-font: bold italic 40px \"Arial\"; ");
		Label vBulkInfo = createLabel(getPatientInfo());
		vBulkInfo.setStyle("-fx-font: 18px \"Arial\";");
		vLabelBox.getChildren().add(vInfoLabel);
		
		Button chatButton = new Button("Chat");
		chatButton.setOnAction(e -> chatPopup());
		
		vInfoBox.getChildren().addAll(vLabelBox, vBulkInfo, chatButton);
		
		// spacer box
		blankBox = new HBox();
		//imageBox.setStyle("-fx-background-color: #37718E;");
		blankBox.setPrefHeight(140);
		
		// finalization
		sroot.setTop(imageBox);
		sroot.setLeft(uInfoBox);
		sroot.setRight(vInfoBox);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(600);
		sroot.getChildren().add(rectRoot);

		sroot.getChildren().get(3).toBack();
		rect2.setStyle("-fx-fill: #37718E;");
		rect2.widthProperty().bind(sroot.widthProperty());

		
		Scene scene = new Scene(sroot);				// creates and launches the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.web("#E7F8FE"));
		return scene;
	}
	
	public Scene doctorView () {
		EmployeeClass employeeClass = new EmployeeClass();
		//rectRoot = new Group();
		//rectRoot.getChildren().add(rect2);
		//bigRoot = new AnchorPane();			// bigger pane to hold most nodes, other than moving ones
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		// top bar header thing
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		user = new Image(getClass().getResourceAsStream("icon.png"));
		starView = new ImageView();
		userView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userView.setFitWidth(75);
		userView.setFitHeight(75);
		userView.setTranslateX(10);
		userView.setTranslateY(10);
		userView.setImage(user);
		docOff2 = new Label("Doctor Portal");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// central VBox
		VBox nurseV = new VBox();
		
		// top line HBox
		HBox topLine = new HBox(50);
		topLine.setPadding(new Insets(20, 20, 20, 20));
		
		// patient name HBox
		HBox patientBox = new HBox(10);
		Label patientL = createLabel("Patient: "); // patient label
		patientL.setStyle("-fx-font: 20px \"Arial\";");
		patientL.setStyle("-fx-min-width: 100;");
		patientL.setAlignment(Pos.CENTER);
		ComboBox<String> patientNames = employeeClass.getPatientList();
		patientNames.setPromptText("Name");
		patientNames.getItems().add("Temp Child");
		patientNames.getStyleClass().add("labelB");
		patientNames.setMinWidth(100);
		patientNames.setMaxWidth(200);
		patientBox.getChildren().addAll(patientL, patientNames);
		
		// prev visits HBox
		HBox prevVisitsBox = new HBox(10);
		Label prevVisitsL = createLabel("Previous Visits: "); // patient label
		prevVisitsL.setStyle("-fx-font: 20px \"Arial\";");
		prevVisitsL.setAlignment(Pos.CENTER);
		ComboBox<String> prevVisits = employeeClass.getVisitsList(doc_selectedPatient); //should be updated on every event on patientNames ComboBox
		prevVisits.setPromptText("Visit");
		prevVisits.getItems().add("Temp Visits");
		prevVisits.getStyleClass().add("labelB");
		prevVisits.setMinWidth(100);
		prevVisits.setMaxWidth(200);
		
		prevVisitsBox.getChildren().addAll(prevVisitsL, prevVisits);
		
		// create new visit button
		createVisit = new Button("New Visit");
		
		// add all to top line
		topLine.getChildren().addAll(patientBox, prevVisitsBox, createVisit);
		topLine.setAlignment(Pos.CENTER);	
		
		// Center HBox
		HBox centralBox = new HBox(70);
		
		// doctor VBox
		VBox doctorBox = new VBox(10);
		Label doctorNotesL = createLabel("Doctor's Notes");
		doctorNotesL.setStyle("-fx-font: bold 26px \"Arial\";");
		doctorNotesL.setUnderline(true);
		doctorNotesTA = createta("Type here");
		doctorNotesTA.setMaxHeight(100);
		Label medsL = createLabel("Prescribe Medications");
		medsL.setStyle("-fx-font: bold 26px \"Arial\";");
		medsL.setUnderline(true);
		medsTA = createta("Type here");
		medsTA.setMaxHeight(100);
		doctorBox.getChildren().addAll(doctorNotesL, doctorNotesTA, medsL, medsTA);
		doctorBox.setAlignment(Pos.CENTER);
		
		// med history
		VBox medHistoryBox = new VBox(10);
		Label medicalHistoryL = createLabel("Medical History");
		medicalHistoryL.setStyle("-fx-font: bold 26px \"Arial\";");
		medicalHistoryL.setUnderline(true);
		Label prevHealthIssuesL = createLabel("Previous Health Issues");
		prevHealthIssuesTA = createta("Type here");
		prevHealthIssuesTA.setMaxHeight(60);
		Label prevMedsL = createLabel("Previous Medications");
		prevMedsTA = createta("Type here");
		prevMedsTA.setMaxHeight(60);
		Label immunizationsTL = createLabel("History of Immunizations");
		immunizationsTA = createta("Type here");
		immunizationsTA.setMaxHeight(60);
		medHistoryBox.getChildren().addAll(medicalHistoryL, prevHealthIssuesL, prevHealthIssuesTA, 
											prevMedsL, prevMedsTA, immunizationsTL, immunizationsTA);
		medHistoryBox.setAlignment(Pos.CENTER);
		
		// vitals info
		VBox vitalsInfoBroad = new VBox(20);
		HBox vitals = new HBox(20);
		
		Label vitalsInfoL = createLabel("Vitals Information");
		vitalsInfoL.setStyle("-fx-font: bold 26px \"Arial\";");
		vitalsInfoL.setUnderline(true);

		
		VBox vitalsLabels = new VBox(27);
		Label vWeightL = createLabel("Weight");
		Label vHeightL = createLabel("Height");
		Label vBTempL = createLabel("Body Temp.");
		Label vBPressL = createLabel("Blood Pressure");
		vitalsLabels.getChildren().addAll(vWeightL, vHeightL, vBTempL, vBPressL);
		
		VBox vitalsTFs = new VBox(15);
		vWeightTF = createtf("#");
		vWeightTF.setMaxWidth(80);
		vHeightTF = createtf("#");
		vHeightTF.setMaxWidth(80);
		vBTempTF = createtf("#");
		vBTempTF.setMaxWidth(80);
		vBPressTF = createtf("#");
		vBPressTF.setMaxWidth(80);
		vitalsTFs.getChildren().addAll(vWeightTF, vHeightTF, vBTempTF, vBPressTF);
		
		vitals.getChildren().addAll(vitalsLabels, vitalsTFs);
		
		CheckBox twelveCB = new CheckBox("Over 12 years old?");
		twelveCB.setSelected(false);
		twelveCB.setStyle("-fx-font: bold 26px \"Arial\";");
		
		Button chatButton = new Button("Chat");
		chatButton.setOnAction(e -> chatPopup());
		
		vitalsInfoBroad.getChildren().addAll(vitalsInfoL, vitals, twelveCB, chatButton);
		vitalsInfoBroad.setAlignment(Pos.CENTER);
		
		// center box
		centralBox.getChildren().addAll(doctorBox, medHistoryBox, vitalsInfoBroad);
		centralBox.setAlignment(Pos.CENTER);
		
		// add all to nurseV
		nurseV.getChildren().addAll(topLine, centralBox);	
		
		// spacer box
		blankBox = new HBox();
		
		//imageBox.setStyle("-fx-background-color: #37718E;");
		blankBox.setPrefHeight(140);
		
		//Refresh PatientVisit box
		patientNames.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//System.out.println("Presesd patient combobox");
				doc_selectedPatient = patientNames.valueProperty().get();
				employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);			
			}
		});
		employeeClass.collectFields(vWeightTF);
		//Refresh Data after choosing Visit
		prevVisits = employeeClass.getVisitsList(doc_selectedPatient);
		
		//Update current items
		doc_selectedPatient = patientNames.valueProperty().get();
		employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);
		
		createVisit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ComboBox<String> cbt = employeeClass.getVisitsList(doc_selectedPatient);
				doc_selectedPatient = patientNames.valueProperty().get();
				vitalsInfo = employeeClass.vitalsOuput(vWeightTF.getText(),vHeightTF.getText(),vBTempTF.getText(),vBPressTF.getText(), twelveCB.isSelected());
				medicalHistory = employeeClass.medicalHistoryOutput(prevHealthIssuesTA.getText(), prevMedsTA.getText() ,immunizationsTA.getText() );
				doctorsNotes = doctorNotesTA.getText();
				prescribeMeds = medsTA.getText();
				
				employeeClass.createNewVisit(doc_selectedPatient, vitalsInfo, medicalHistory, doctorsNotes, prescribeMeds);				//createVisit.setOnAction(e -> employeeClass.createNewVisit(doc_selectedPatient, vitalsInfo, medicalHistory, doctorsNotes, prescribeMeds));
				//createVisit.setOnAction(e -> System.out.println(employeeClass.vitalsOuput(vWeightTF.getText(),vHeightTF.getText(),vBTempTF.getText(),vBPressTF.getText(), twelveCB.isSelected())));
				cbt = employeeClass.getVisitsList(doc_selectedPatient);
				employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);			
				employeeClass.clearInputAreas();
			}
		});
		// end of action listeners 
		
		// finalization
		sroot.setTop(imageBox);
		sroot.setCenter(nurseV);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(600);
		sroot.getChildren().add(rectRoot);

		sroot.getChildren().get(2).toBack();
		rect2.setStyle("-fx-fill: #37718E;");
		rect2.widthProperty().bind(sroot.widthProperty());

		createVisit.setStyle("-fx-font: 20px \"Arial\";");
		Scene scene = new Scene(sroot);				// creates and launches the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.web("#E7F8FE"));
		return scene;
	}
	
	public Scene nurseView() {
		EmployeeClass employeeClass = new EmployeeClass();
		//rectRoot = new Group();
		//rectRoot.getChildren().add(rect2);
		//bigRoot = new AnchorPane();			// bigger pane to hold most nodes, other than moving ones
		sroot = new BorderPane(); 			// established the main pane || everything is built on root
		sroot.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		// top bar header thing
		imageBox = new HBox();
		imageBox.setPadding(new Insets(20, 20, 20, 20));
		medStar = new Image(getClass().getResourceAsStream("star.png"));
		user = new Image(getClass().getResourceAsStream("icon.png"));
		starView = new ImageView();
		userView = new ImageView();
		starView.setFitWidth(100);
		starView.setFitHeight(100);
		starView.setImage(medStar);
		userView.setFitWidth(75);
		userView.setFitHeight(75);
		userView.setTranslateX(10);
		userView.setTranslateY(10);
		userView.setImage(user);
		docOff2 = new Label("Nurse Portal");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// central VBox
		VBox nurseV = new VBox();
		
		// top line HBox
		HBox topLine = new HBox(50);
		topLine.setPadding(new Insets(20, 20, 20, 20));
		
		// patient name HBox
		HBox patientBox = new HBox(10);
		Label patientL = createLabel("Patient: "); // patient label
		patientL.setStyle("-fx-font: 20px \"Arial\";");
		patientL.setStyle("-fx-min-width: 100;");
		patientL.setAlignment(Pos.CENTER);
		ComboBox<String> patientNames = employeeClass.getPatientList();
		patientNames.setPromptText("Name");
		patientNames.getItems().add("Temp Child");
		patientNames.getStyleClass().add("labelB");
		patientNames.setMinWidth(100);
		patientNames.setMaxWidth(200);
		patientBox.getChildren().addAll(patientL, patientNames);
		
		// prev visits HBox
		HBox prevVisitsBox = new HBox(10);
		Label prevVisitsL = createLabel("Previous Visits: "); // patient label
		prevVisitsL.setStyle("-fx-font: 20px \"Arial\";");
		prevVisitsL.setAlignment(Pos.CENTER);
		ComboBox<String> prevVisits = employeeClass.getVisitsList(doc_selectedPatient); 
		prevVisits.setPromptText("Visit");
		prevVisits.getItems().add("Temp Visits");
		prevVisits.getStyleClass().add("labelB");
		prevVisits.setMinWidth(100);
		prevVisits.setMaxWidth(200);
		
		prevVisitsBox.getChildren().addAll(prevVisitsL, prevVisits);
		
		// create new visit button
		createVisit = new Button("New Visit");
		
		// add all to top line
		topLine.getChildren().addAll(patientBox, prevVisitsBox, createVisit);
		topLine.setAlignment(Pos.CENTER);	
		
		// Center HBox
		HBox centralBox = new HBox(70);
		
		// med history
		VBox medHistoryBox = new VBox(10);
		Label medicalHistoryL = createLabel("Medical History");
		medicalHistoryL.setStyle("-fx-font: bold 26px \"Arial\";");
		medicalHistoryL.setUnderline(true);
		Label prevHealthIssuesL = createLabel("Previous Health Issues");
		prevHealthIssuesTA = createta("Type here");
		prevHealthIssuesTA.setMaxHeight(60);
		Label prevMedsL = createLabel("Previous Medications");
		prevMedsTA = createta("Type here");
		prevMedsTA.setMaxHeight(60);
		Label immunizationsTL = createLabel("History of Immunizations");
		immunizationsTA = createta("Type here");
		immunizationsTA.setMaxHeight(60);
		medHistoryBox.getChildren().addAll(medicalHistoryL, prevHealthIssuesL, prevHealthIssuesTA, 
											prevMedsL, prevMedsTA, immunizationsTL, immunizationsTA);
		medHistoryBox.setAlignment(Pos.CENTER);
		
		// vitals info
		VBox vitalsInfoBroad = new VBox(20);
		HBox vitals = new HBox(20);
		
		Label vitalsInfoL = createLabel("Vitals Information");
		vitalsInfoL.setStyle("-fx-font: bold 26px \"Arial\";");
		vitalsInfoL.setUnderline(true);

		
		VBox vitalsLabels = new VBox(27);
		Label vWeightL = createLabel("Weight");
		Label vHeightL = createLabel("Height");
		Label vBTempL = createLabel("Body Temp.");
		Label vBPressL = createLabel("Blood Pressure");
		vitalsLabels.getChildren().addAll(vWeightL, vHeightL, vBTempL, vBPressL);
		
		VBox vitalsTFs = new VBox(15);
		vWeightTF = createtf("#");
		vWeightTF.setMaxWidth(80);
		vHeightTF = createtf("#");
		vHeightTF.setMaxWidth(80);
		vBTempTF = createtf("#");
		vBTempTF.setMaxWidth(80);
		vBPressTF = createtf("#");
		vBPressTF.setMaxWidth(80);
		vitalsTFs.getChildren().addAll(vWeightTF, vHeightTF, vBTempTF, vBPressTF);
		
		vitals.getChildren().addAll(vitalsLabels, vitalsTFs);
		
		twelveCB = new CheckBox("Over 12 years old?");
		twelveCB.setStyle("-fx-font: bold 26px \"Arial\";");
		
		Button chatButton = new Button("Chat");
		chatButton.setOnAction(e -> chatPopup());
		
		vitalsInfoBroad.getChildren().addAll(vitalsInfoL, vitals, twelveCB, chatButton);
		vitalsInfoBroad.setAlignment(Pos.CENTER);
		
		// center box
		centralBox.getChildren().addAll(medHistoryBox, vitalsInfoBroad);
		centralBox.setAlignment(Pos.CENTER);
		
		// add all to nurseV
		nurseV.getChildren().addAll(topLine, centralBox);	
		
		// spacer box
		blankBox = new HBox();
		
		//imageBox.setStyle("-fx-background-color: #37718E;");
		blankBox.setPrefHeight(140);
		
		//Refresh PatientVisit box
				patientNames.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						//System.out.println("Presesd patient combobox");
						doc_selectedPatient = patientNames.valueProperty().get();
						employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);			
					}
				});
				employeeClass.collectFields(vWeightTF);
				//Refresh Data after choosing Visit
				prevVisits = employeeClass.getVisitsList(doc_selectedPatient);
				
				//Update current items
				doc_selectedPatient = patientNames.valueProperty().get();
				employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);
				
				createVisit.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						ComboBox<String> cbt = employeeClass.getVisitsList(doc_selectedPatient);
						doc_selectedPatient = patientNames.valueProperty().get();
						vitalsInfo = employeeClass.vitalsOuput(vWeightTF.getText(),vHeightTF.getText(),vBTempTF.getText(),vBPressTF.getText(), twelveCB.isSelected());
						medicalHistory = employeeClass.medicalHistoryOutput(prevHealthIssuesTA.getText(), prevMedsTA.getText() ,immunizationsTA.getText() );
						doctorsNotes = "";
						prescribeMeds = "";
						employeeClass.createNewVisit(doc_selectedPatient, vitalsInfo, medicalHistory, doctorsNotes, prescribeMeds);				//createVisit.setOnAction(e -> employeeClass.createNewVisit(doc_selectedPatient, vitalsInfo, medicalHistory, doctorsNotes, prescribeMeds));
						//createVisit.setOnAction(e -> System.out.println(employeeClass.vitalsOuput(vWeightTF.getText(),vHeightTF.getText(),vBTempTF.getText(),vBPressTF.getText(), twelveCB.isSelected())));
						cbt = employeeClass.getVisitsList(doc_selectedPatient);
						employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);			
						employeeClass.clearInputAreas();
					}
				});
		
		// finalization
		
		sroot.setTop(imageBox);
		sroot.setCenter(nurseV);
		sroot.setPrefWidth(1000);
		sroot.setPrefHeight(600);
		sroot.getChildren().add(rectRoot);

		sroot.getChildren().get(2).toBack();
		rect2.setStyle("-fx-fill: #37718E;");
		rect2.widthProperty().bind(sroot.widthProperty());

		createVisit.setStyle("-fx-font: 20px \"Arial\";");
		chatButton.setStyle("-fx-font: 20px \"Arial\";");
		Scene scene = new Scene(sroot);				// creates and launches the application
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.web("#E7F8FE"));
		return scene;
	}
	
	// ISAIAH
	String doc_selectedPatient;
	String vitalsInfo = "";
	String medicalHistory = "";
	String immunizationHistory = "";
	String doctorsNotes = "";
	String prescribeMeds = "";
	
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
	void clearInputAreas( ) {
		//Vitals
		vWeightTF.clear();
		vHeightTF.clear();
		vBTempTF.clear();
		vBPressTF.clear();
		
		//Medical History
		prevHealthIssuesTA.clear();
		prevMedsTA.clear();
		immunizationsTA.clear();
		//Doctor Section
		try {
			doctorNotesTA.clear();
			medsTA.clear();
		} catch(Exception e) {
			System.out.println("Must be a nurse!");
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
		if(entireFile.length() > 10) {
		Scanner scanner = new Scanner(filename); //Get File
		//scanner.useDelimiter("<");
		weight = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		vWeightTF.setText(weight);
		//System.out.println(weight);
		
		//System.out.println(entireFile);
		String height = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		vHeightTF.setText(height);
		
		String bodyTemp = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		vBTempTF.setText(bodyTemp);
		
		String bloodP = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		vBPressTF.setText(bloodP);
		
		String over12 = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		//System.out.println(over12);
		//Boolean isOver12 = Boolean.parseBoolean(over12);
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		if(over12.equals("true")) {
			//twelveCB.setSelected(true);
		}
		else {
			//twelveCB.setSelected(false);
		}
		//Fin Vitals
		
		//Start updating Medical History
		String prevHealthIssues = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		prevHealthIssuesTA.setText(prevHealthIssues);
		
		String prevMeds = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		prevMedsTA.setText(prevMeds);
		
		String prevImm = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
		entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
		immunizationsTA.setText(prevImm);
		//end of updating medical history
		
		//Left Side
		try{
			String docNotes = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
			entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
			doctorNotesTA.setText(docNotes);
			
			String prescribeMeds = entireFile.substring(entireFile.indexOf("<") + 1, entireFile.indexOf(">"));
			entireFile = entireFile.substring(entireFile.indexOf(">") + 1, entireFile.length());
			medsTA.setText(docNotes); 
		} catch(Exception e) {
			System.out.println("Must be a nurse!");
		}
		
		//End of medical notes
		}
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

		pastV = prevVisits;
		prevVisits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg1) {
				updateBoxes(pastV.valueProperty().get());
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
			//System.out.println("\nPatient ComboBox unable to be updated");
			e.printStackTrace();
			return cb; // Dont return a pane on error
		}
		
		return cb;
	}

	ComboBox<String> getVisitsList(String username){
		//System.out.println(username);
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
			//System.out.println("\nVisits ComboBox unable to be updated");
			//e.printStackTrace();
			return cb; // Dont return a pane on error
		}
		return cb;
	}

	void createNewVisit(String username, String vitals, String history, String docNotes, String presMeds ) {
		//System.out.println(username);
		String directory = "PatientVisits";
		
		LocalDate todaysDate = LocalDate.now();
		String filename = username + "_" + todaysDate + ".txt";
		
		try {
			//System.out.println(filename);
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
			//System.out.println("\nUnable to create new visit");
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
	
}
