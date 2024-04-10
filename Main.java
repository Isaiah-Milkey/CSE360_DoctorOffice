
	

// convert logBox to an HBox

import javafx.scene.Node;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.*;

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
			 currAddL, currPharmaL, currMedsL;
	TextField userName, password, lastName, 
			 firstName, weight, heightFt, heightIn, 
			 address, currPharma, currMeds,
			 vWeightTF, vHeightTF, vBTempTF, vBPressTF;
	TextArea medsL, medsTA, doctorNotesTA, prevHealthIssuesTA,
			 prevMedsTA, immunizationsTA;
	Button   login, signUp, confirmLogin, createVisit;
	CheckBox twelveCB;
	
	// For Doctor Page
	ComboBox<String> prevVisits;
	String doc_selectedPatient;
	
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
		confirmLogin.setOnAction(e -> switchScenes(patientView()));
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
		DatePicker pDOB = new DatePicker();
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
		ComboBox<String> sexBox = new ComboBox<String>();
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
		Label b1 = createLabel(" ");
		Label b2 = createLabel(" ");
		Button confirmSignUp = new Button("Confirm");
		confirmSignUp.setAlignment(Pos.CENTER);
		confirmSignUp.setOnAction(e -> switchScenes(doctorView()));
		
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
		
		right.add(address, 2, 0);
		right.add(currPharma, 2, 1);
		right.add(currMeds, 2, 2);
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
			prevVisits = new ComboBox<String>(); //should be updated on every event on patientNames ComboBox
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
		twelveCB.setStyle("-fx-font: bold 26px \"Arial\";");
		
		vitalsInfoBroad.getChildren().addAll(vitalsInfoL, vitals, twelveCB);
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
		
		// action listener(s)
		
		
		//Middle Section
		patientNames.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				doc_selectedPatient = patientNames.valueProperty().get();
				employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);
				
				// TODO Auto-generated method stub
				/*
				doc_selectedPatient = patientNames.valueProperty().get();
				prevVisits = employeeClass.getVisitsList(doc_selectedPatient);
				prevVisitsL.setStyle("-fx-font: 20px \"Arial\";");
				prevVisitsL.setAlignment(Pos.CENTER);
					//prevVisits = new ComboBox<String>();//employeeClass.getVisitsList("null"); //should be updated on every event on patientNames ComboBox
				prevVisits.setPromptText("Visit");
				prevVisits.getItems().add("Temp Visits");
				prevVisits.getStyleClass().add("labelB");
				prevVisits.setMinWidth(100);
				prevVisits.setMaxWidth(200);
				
				prevVisitsBox.getChildren().addAll(prevVisitsL, prevVisits);
				*/
			}
		});
		
		//Right Side
		doc_selectedPatient = patientNames.valueProperty().get();
		createVisit.setOnAction(e -> employeeClass.createNewVisit(doc_selectedPatient));
		prevVisits = employeeClass.getVisitsList(doc_selectedPatient);
		employeeClass.refreshPatientList(prevVisitsBox, doc_selectedPatient, patientNames, patientNames, prevVisitsL);
		
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
		ComboBox<String> patientNames = new ComboBox<String>();
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
		ComboBox<String> prevVisits = new ComboBox<String>();
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
		
		vitalsInfoBroad.getChildren().addAll(vitalsInfoL, vitals, twelveCB);
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
	
	String getPatientInfo() {
		return  "NO INFO CURRENTLY\n\n" +
				"THE PATIENT MIGHT BE DEAD";
	}
}
