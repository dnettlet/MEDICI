package genDataNOapplication.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.Utils.FileUtils;
import genDataNOapplication.model.ConfigurationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public class RootLayoutController {
	
	//Reference to the main application
	private Main main;
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	@FXML
	private void handleImport() {
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
	
	@FXML
	private void handleExport() {
		//Opens a window for the user to choose the save location
        FileChooser fileChooser = new FileChooser();   
        File initialDirectory = new File("./config");
        fileChooser.setInitialDirectory(initialDirectory);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivo de origen XML (.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());
        
        //Executes the export process and shows a message to the user depending on the result
        boolean status = FileUtils.exportConfig(file, main.getConfiguration());
        
        if(status) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Export Successfull");
    		alert.setHeaderText("The configuration has been successfully saved to file");
    		alert.setContentText("The configuration has been saved to  " + file.getPath() + " ."
    				 + "to load this file go to the initial page and click the button Load Config From File.");
    		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		Image icon = new Image("file:./resources/icons/info_icon.png");
    		stage.getIcons().add(icon);		
    		alert.setGraphic(new ImageView(icon));
    		alert.showAndWait();
        }else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Exporting configuration");
			alert.setHeaderText("An error occurred while saving the configuration to a file.");
			alert.setContentText("The configuration couldn't be saved. Check that the directory " + file.getPath()
						+ " is valid and accessible. Try executing the application with Administrator rights or try another location.");
			alert.showAndWait();
        }
	}
    @FXML
    private void handleExit() {
        System.exit(0);
    }
	
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("About the program");
    	alert.setHeaderText("About the Synthetic Data Generator");
    	alert.setContentText("A synthetic data generator for Online Social Networks Graphs");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image("file:./resources/icons/info_icon.png");
		stage.getIcons().add(icon);		
		alert.setGraphic(new ImageView(icon));
    	alert.showAndWait();
    }
    
    @FXML
    private void handleLicense() {
    	/*Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("License");
    	alert.setHeaderText("GNU General Public License v3.0");
    	alert.setContentText("This program is distributed under the GNU General Public License v3.0" + 
    			"\n Permissions of this strong copyleft license are conditioned on making available complete source code of licensed works and modifications," + 
    			"which include larger works using a licensed work, under the same license." +
    			"\n Copyright and license notices must be preserved. Contributors provide an express grant of patent rights.");
    	alert.showAndWait();*/
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("License");
    	alert.setHeaderText("GNU General Public License v3.0");
    	alert.setContentText("This program is distributed under the GNU General Public License v3.0" + 
    			"\n Permissions of this strong copyleft license are conditioned on making available complete source code of licensed works and modifications," + 
    			"which include larger works using a licensed work, under the same license." +
    			"\n Copyright and license notices must be preserved. Contributors provide an express grant of patent rights.");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image("file:./resources/icons/info_icon.png");
		stage.getIcons().add(icon);		
		alert.setGraphic(new ImageView(icon));

    	ButtonType buttonTypeOne = new ButtonType("Open License File");
    	ButtonType buttonTypeCancel = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOne){
    	    File file = new File("./LICENSE");
    	    try {
    	    	Desktop.getDesktop().open(file);
    	    	
    	    } catch (IOException e) {
    	    	e.printStackTrace();
    	    }
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    }
    
    @FXML
    private void handleDocumentation() {
    	File file = new File("./README and USER MANUAL.pdf");
        try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @FXML
    private void handleSwitchHome() {
    	main.showHomePage();
    }
    
    @FXML
    private void handleSwitchGraphGenerator() {
		main.showInputFileGeneratorPage();
    }
 
	@FXML
	public void handleSwitchInputFiles() {
		main.showSettingsPage();
	}
	
	@FXML
	public void handleSwitchUserAttributes() {
		main.showUserAttributesPage();
	}
	
	@FXML
	public void handleSwitchProfileSettings() {
		main.showProfilesPage();
	}
	
    @FXML
    private void handleSwitchCommunities() {
    	main.showCommunitiesSettingsPage();
    }
	
	@FXML
	public void handleSwitchOutputFiles() {
		main.showOutputFileSettingsPage();
	}
	
    @FXML
    private void handleSwitchAdvancedSettings() {
    	main.showAdvancedSettingsPage();
    }
	
	@FXML
	public void handleSwitchRun() {
		main.showRunPage();
	}
	
	@FXML
	public void handleSwitchResults() {
		main.showStatisticsPage();
	}


}
    
    
