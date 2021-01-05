package genDataNOapplication.view;

import java.io.File;

import genDataNOapplication.Main;
import genDataNOapplication.Utils.FileUtils;
import genDataNOapplication.model.ConfigurationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;

//Controller class for the Home Page
public class HomePageController {
	
	
	//Reference to the main application
	private Main main;
	
	//Buttons
	@FXML
	Button startButton;
	@FXML
	Button cancelButton;
	@FXML
	Button loginButton;
//	@FXML
//	Button loadFromFileButton;
	
	
	//Class constructor
	public HomePageController() {
		
	}
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
	@FXML
	private void initialize() {
		
	}
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	
	//Goes to the settings screen
	@FXML
	private void handleloginButton() {
		main.showInputFileGeneratorPage();
	}
}
