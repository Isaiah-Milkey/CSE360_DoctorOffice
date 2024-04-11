import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDateTime;

public class Chat extends Application {
	
	private TextArea chatArea;
	private TextField msgField;
	
	
	private File chatHistoryFile;
	
	private String userType; //If patient is logged in or Doctor/Nurse
	private String patientID;
	private String doctorNurseID;
	@Override
	public void start(Stage primaryStage) {
		//create basic chat history file and check that it was successfully created
		chatHistoryFile = new File("chat_history.txt");
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
		root.setCenter(chatArea);
		
		msgField = new TextField();
		msgField.setPromptText("Type your message...");
		root.setBottom(msgField);
		
		Button sendButton = new Button("Send");
		sendButton.setOnAction(e-> sendMessage());
		root.setRight(sendButton);
		
		loadChatHistory();
		
		Scene scene = new Scene(root, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Filesystem Based Chat");
		primaryStage.show();
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
			
		
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(getChatHistoryFilePath(),true)))) {
			LocalDateTime timestamp = LocalDateTime.now();
			writer.println("[" + timestamp + "]" + userType + ": " + msg);
			chatArea.appendText("[" + timestamp + "] You: " + msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	//else {
		//System.out.println("User information unavailable.");
	//}
	//}
	
//will load chat history but user info needs to be set in log in class first
	private void loadChatHistory() {
		try (BufferedReader reader = new BufferedReader(new FileReader(chatHistoryFile))){
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
		// TODO Auto-generated method stub
		launch(args);
     
	}

}
