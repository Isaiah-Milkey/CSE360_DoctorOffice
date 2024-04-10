package Application;
	

// convert logBox to an HBox

import javafx.scene.Node;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

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
	Label 	docOff, docOff2, 
			patientLN, patientFN, patientDOB, 
			heightL, weightL, sexL, blankL,
			currAddL, currPharmaL, currMedsL, createPasswordL;
	TextField userName, password, lastName, 
			 firstName, weight, heightFt, heightIn, 
			 address, currPharma, currMeds, createPassword;
	Button login, signUp, confirmLogin, confirmSignUp;
	DatePicker pDOB = new DatePicker(); // SAVANNAH EDITED
	ComboBox<String> sexBox = new ComboBox<String>();
	String fileName;
	
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
		//confirmLogin.setOnAction(e -> switchScenes(patientView()));
		confirmLogin.setOnAction(new FieldHandler()); // 	 SAVANNAH EDITED
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
		docOff2 = new Label("Patient Signup");
		docOff2.setStyle("-fx-text-fill: #E7F8FE;");
		docOff2.setPrefWidth(10000);
		docOff2.setAlignment(Pos.CENTER_RIGHT);
		docOff2.setPadding(new Insets(20, 20, 20, 20));
		imageBox.getChildren().addAll(userView, docOff2, starView);
		
		// info boxes
		GridPane left = new GridPane(),
				 right = new GridPane();
		
		for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 6; col++) {
                // Create a placeholder for the cell
                Pane leftPane = new Pane();
                leftPane.setMinSize(50, 50); // Set minimum size for visibility
                
                Pane rightPane = new Pane();
                rightPane.setMinSize(50, 50); // Set minimum size for visibility

                // Add the placeholder to the corresponding cell
                GridPane.setRowIndex(leftPane, row);
                GridPane.setColumnIndex(leftPane, col);
                left.getChildren().add(leftPane);

                GridPane.setRowIndex(rightPane, row);
                GridPane.setColumnIndex(rightPane, col);
                right.getChildren().add(rightPane);
            }
        }

        // Layout the GridPanes in the scene
        /*GridPane mainLayout = new GridPane();
        mainLayout.add(left, 0, 0); // Add left GridPane to main layout
        mainLayout.add(right, 1, 0); // Add right GridPane to main layout*/
		
		
		logBox = new HBox();
		logBox.setPadding(new Insets(50, 20, 50, 20));
		
		// left info
		patientLN = createLabel("Patient Last Name");
		lastName = createtf("Last Name");
		patientFN = createLabel("Patient First Name");
		firstName = createtf("First Name");
		patientDOB = createLabel("Patient DOB");
		// DataPicker options
		//DatePicker pDOB = new DatePicker(); SAVANNAH EDITED
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
		// Sex Drop-Down SAVANNAH EDITED 
		//ComboBox<String> sexBox = new ComboBox<String>(); SAVANNAH EDITED
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
		Label b1 = createLabel(" ");
		Label b2 = createLabel(" ");
		confirmSignUp = new Button("Confirm");
		confirmSignUp.setAlignment(Pos.CENTER);
		//confirmSignUp.setOnAction(e -> switchScenes(patientView())); SAVANNAH EDITEDSAVANNAH EDITEDSAVANNAH EDITED
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
		
		right.add(address, 2, 0);
		right.add(currPharma, 2, 1);
		right.add(currMeds, 2, 2);
		right.add(createPassword, 2, 3);
		right.add(b1, 0, 3);
		right.add(b2, 0, 4);
		//right.add(confirmSignUp, 2, 5);
		
		//userName.setMinWidth(150);
		//password.setMinWidth(150);
		//userName.setMaxWidth(Control.USE_PREF_SIZE - 400);
		//password.setMaxWidth(Control.USE_PREF_SIZE - 400);
		//userName.getStyleClass().add("textFields");
		//password.getStyleClass().add("textFields");
		left.setVgap(10);
		left.setHgap(10);
		right.setVgap(10);
		right.setHgap(10);
		((HBox) logBox).setAlignment(Pos.BASELINE_CENTER);
		left.setPadding(new Insets(0,50,0,0));
		right.setPadding(new Insets(0,0,0,50));
		logBox.getChildren().addAll(left, right);
		
		//logBox.getChildren().addAll(userName, password, confirmLogin);
		
		
		// spacer box
		blankBox = new HBox();
		//imageBox.setStyle("-fx-background-color: #37718E;");
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
	
	public Scene patientView() {
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
		docOff2 = new Label("Pediatric Doctors Office");
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
		bulkInfo.setWrapText(true);
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
		bulkInfo.setWrapText(true);
		vLabelBox.getChildren().add(vInfoLabel);
		vInfoBox.getChildren().addAll(vLabelBox, vBulkInfo);
		
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
	
	//SAVANNAH EDITEDSAVANNAH EDITEDSAVANNAH EDITEDSAVANNAH EDITED from here on down
	String getPatientInfo() {
		// first name, last name, birthday, see and change contact info, summary of visit, send messages to nursedoctor
		//String line;
		try {
        	BufferedReader infoReader = new BufferedReader(new FileReader(fileName));
            String passwordFromFile = infoReader.readLine() + "\n";
            String firstNameFromFile = "Name: \t\t\t" + infoReader.readLine() + " ";
            String lastNameFromFile = infoReader.readLine() + "\n";
            String DOBFromFile = "Date of Birth: \t\t" + infoReader.readLine() + "\n";
            String heightFromFile = "Height: \t\t\t" + infoReader.readLine() + "\n";
            String weightFromFile = "Weight: \t\t\t" + infoReader.readLine() + "\n";
            String sexFromFile = "Sex: \t\t\t" + infoReader.readLine() + "\n";
            String phoneNumberFromFile = "Phone Number: \t" + infoReader.readLine() + "\n";
            String pharmacyFromFile = "Pharmacy: \t\t" + infoReader.readLine() + "\n";
            String medicationFromFile = "Medication(s): \t\t" + infoReader.readLine() + "\n";
            String allOfThem = firstNameFromFile + lastNameFromFile + DOBFromFile + heightFromFile + weightFromFile + sexFromFile + phoneNumberFromFile + pharmacyFromFile + medicationFromFile;
            infoReader.close();
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
                currentAddress.isEmpty() || currentPharmacy.isEmpty() || currentMedications.isEmpty() || patientPassword.isEmpty()) {
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
        	BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        	writer.write(patientPassword + "\n");
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
	
	public boolean loginCredentials() {
		String userNameText = userName.getText();
        String passwordText = password.getText();
        fileName = userNameText + ".txt";

        try {
        	BufferedReader infoReader = new BufferedReader(new FileReader(fileName));
            String passwordFromFile = infoReader.readLine(); // Read password from the file
            if (passwordFromFile.equals(passwordText)) {
            	infoReader.close();
            	return true;
            }
            
            infoReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
	        if (e.getSource() == confirmLogin) {
	        	if (fieldChecker() == true && loginCredentials() == true) {
	        		switchScenes(patientView());
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
	        		switchScenes(patientView());
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
	}

