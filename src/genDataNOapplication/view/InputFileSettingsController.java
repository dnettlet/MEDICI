package genDataNOapplication.view;

import java.io.File;
import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.model.ConfigurationModel;
import genDataNOapplication.Utils.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//Controller class for the Settings Page
public class InputFileSettingsController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	//Buttons
	@FXML
	Button inputGeneratorButtonTab;
	@FXML
	Button inputFilesButtonTab;
	@FXML
	Button userParametersButtonTab;
	@FXML
	Button profilesButtonTab;
	@FXML
	Button communitiesButtonTab;
	@FXML
	Button advancedButtonTab;
	@FXML
	Button runButtonTab;
	@FXML
	Button statisticsButton;
	@FXML
	Button outputFilesButtonTab;
	@FXML
	Button browseInFile1Button;
	@FXML
	Button browseInFile2Button;
	@FXML
	Button importConfigurationButton;
	@FXML
	Button helpButton;
	@FXML
	Button resetButton;
	
	//TextFields
	@FXML
	TextField inputFile1Name;
	@FXML
	TextField inputFile2Name;
	
	//Class constructor
	public InputFileSettingsController() {
		
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
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		inputFile1Name.setText(configuration.getInputFile1());
		inputFile2Name.setText(configuration.getInputFile2());
	}
	
	//Browse button Handlers
	public void handleInFile1BrowseButton() { handleBrowseButton("inputFile1"); }
	public void handleInFile2BrowseButton() { handleBrowseButton("inputFile2"); }

	//When browse button pressed, a file chooser is opened and when a file is selected its path is writen in the textfield
	private void handleBrowseButton(String field) {
		
        FileChooser fileChooser = new FileChooser();
        
        File initialDirectory = new File("./resources/Input_files");
        
        fileChooser.setInitialDirectory(initialDirectory);

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivo de valores separados por comas de Microsoft Excel (.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            System.out.println(file.getName());
            String filePath = file.getPath();
            //String[] parts = filePath.split("(?<=\\\\)");
           // String filename = parts[parts.length - 1];
            String filename = filePath;
            
            switch(field) {
            case "inputFile1":
            	inputFile1Name.setText(filename);
            	break;
            case "inputFile2":
            	inputFile2Name.setText(filename);
            	break;
            }
        }
	}
	
	private void save() {	
		if(!inputFile1Name.getText().isEmpty()) { configuration.setInputFile1(inputFile1Name.getText());	}
		if(!inputFile2Name.getText().isEmpty()) { configuration.setInputFile2(inputFile2Name.getText()); }
		main.setConfiguration(configuration);
	}
	
	//Resets the default configuration. Asks user confirmation.
	@FXML
	public void handleResetButton() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Default");
		alert.setHeaderText("Reset parameters to default");
		alert.setContentText("Are you sure you want to reset all settings parameters to the default configuration?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image("file:./resources/icons/confirmation_icon.png");
		stage.getIcons().add(icon);

		// Add a custom icon		
		alert.setGraphic(new ImageView(icon));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			inputFile1Name.clear();
			inputFile2Name.clear();
		}

	}
	
	@FXML
	public void handleHelpButton() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText("Input Files");
		alert.setContentText("Input File 1 contain a graph, defined as edges, one per row, in the format id1 id2, i1 id3,"
				+ " .. etc. where id is a node id and id1 id2 indicates there is an edge (link) between these two nodes. \n \n"
				+ "Input File 2 contains the community labels for each of the users, that is, each user has an associated community "
				+ "label in the format id comm, where id is the user and comm is the community id (0 to 9).");
		// Get the Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image("file:./resources/icons/info_icon.png");
		stage.getIcons().add(icon);

		// Add a custom icon		
		alert.setGraphic(new ImageView(icon));
		alert.showAndWait();
	}
	
	//EImports the current configuration from an xml file
	@FXML
	public void handleImportConfigurationButton() {
		//Let the user choose a file from the file chooser screen
        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File("./config");
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivo de origen XML (.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());
        ConfigurationModel configuration = new ConfigurationModel();
        
        //Executes the import and shows a message depending if the import was successful or not
		if(FileUtils.loadConfig(file, configuration))
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Import Successfull");
			alert.setHeaderText("The configuration has been successfully imported");
			alert.setContentText("You imported the configuration from file " + file.getName() + " . Click Start Application to run with the imported"
					 + "settings or click change settings to edit those settings.");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			Image icon = new Image("file:./resources/icons/info_icon.png");
			stage.getIcons().add(icon);		
			alert.setGraphic(new ImageView(icon));
			alert.showAndWait();
			main.setConfiguration(configuration);
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Importing Configuration");
			alert.setHeaderText("An error occurred while importing the configuration from a file.");
			alert.setContentText("The configuration couldn't be imported. Check that the file " + file.getName()
						+ " exists and is not corrupted. The default configuration will be restored, please try again.");
			alert.showAndWait();
		}
	}
	
	//Handles back button. If something has been modified asks for user confirmation.
	@FXML
	public void handleBackButton() {
		save();
		main.showHomePage();
	}
	
	@FXML
	public void handleCommunitiesButtonTab() {
		save();
		main.showCommunitiesSettingsPage();
	}
	
	@FXML
	public void handleInputGeneratorButtonTab() {
		save();
		main.showInputFileGeneratorPage();
	}
	
	@FXML
	public void handleUserParametersButtonTab() {
		save();
		main.showUserAttributesPage();
	}
	
	@FXML
	public void handleProfilesButtonTab() {
		save();
		main.showProfilesPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		save();
		main.showAdvancedSettingsPage();
	}
	
	@FXML
	public void handleOutputFilesButtonTab() {
		save();
		main.showOutputFileSettingsPage();
	}
	
	@FXML
	public void handleRunButtonTab() {
		save();
		main.showRunPage();
	}
	
	@FXML
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}
	

}
