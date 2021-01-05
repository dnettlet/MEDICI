package genDataNOapplication.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import genDataNOapplication.Controller;
import genDataNOapplication.Main;
import genDataNOapplication.model.ConfigurationModel;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RunPageController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	@FXML
	ButtonBar buttonsBar;
	
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
		Button outputFilesButtonTab;
		@FXML
		Button advancedButtonTab;
		@FXML
		Button runButton;
		@FXML
		Button cancelButton;
		@FXML
		Button homePageButton;
		@FXML
		Button statisticsButton;
		@FXML
		Button openButton;
		
	//Progress bar
		@FXML
		ProgressBar progressIndicator;
		
	private Controller programExecution;
	
	
	public RunPageController() {
		
	}
	
	@FXML
	public void initialize() {
		
	}
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;

	}
	
	//Stops the execution and re enables the buttons
	@FXML
	private void handleCancelButton() {
		programExecution.cancel(true);
		progressIndicator.setVisible(false);
		
		runButton.setDisable(false);
		buttonsBar.setDisable(false);;
    	cancelButton.setVisible(false);
    	statisticsButton.setDisable(false);
    	homePageButton.setVisible(true);
    	openButton.setVisible(true);
	}
	
	@FXML
	public void handleStartApplicationButton() {
		main.setConfiguration(configuration);
		startApplication();
	}
	
	@FXML
	public void handleOpenButton() {
		try {
		File file = new File(configuration.getOutFile());
		String parent = file.getParent();
		File outDirectory = new File(parent);
		Desktop.getDesktop().open((outDirectory));
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Given a ConfigurationModel, starts the program with this configuration.
	@FXML
	public void startApplication() {
		try{
			runButton.setDisable(true);
			buttonsBar.setDisable(true);
			cancelButton.setVisible(true);
			homePageButton.setVisible(false);
			statisticsButton.setDisable(true);
			openButton.setVisible(false);
			progressIndicator.setVisible(true);
			progressIndicator.setProgress(-1);
			progressIndicator.progressProperty().unbind();
			
	
			
			programExecution = new Controller();
			programExecution.setConfiguration(configuration);
			
			// When completed tasks
						programExecution.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, 
				                new EventHandler<WorkerStateEvent>() {
				
				                    @Override
				                    public void handle(WorkerStateEvent t) {				     
				                    	
				                    	
				                    	int nAssignedSuccessfully = programExecution.getValue();
				                    	int nTotal = configuration.getNumberOfNodes();
				                    	if(!programExecution.isCancelled()) {
				                    	progressIndicator.setVisible(false);
				                		Alert alert = new Alert(AlertType.INFORMATION);
				                    	alert.setTitle("Run Completed");
				                    	alert.setHeaderText("Assigned nodes from total: " + nAssignedSuccessfully + "/" + nTotal);
				                    	alert.setContentText("Execution complete. To see the results check the output files located in the directory ./resources/Output_files/ "
				                    			+ "\n You can run it again by pressing the \"Generate Data\" Button.");
				                		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				                		Image icon = new Image("file:./resources/icons/info_icon.png");
				                		stage.getIcons().add(icon);		
				                		alert.setGraphic(new ImageView(icon));
				                    	alert.showAndWait();
				                    	
				                    	runButton.setDisable(false);
				                    	buttonsBar.setDisable(false);
				                    	cancelButton.setVisible(false);
				                    	statisticsButton.setDisable(false);
				                    	homePageButton.setVisible(false);
				                    	openButton.setVisible(false);
				                    	}
				                        
				                    }
				                });
						
						programExecution.addEventHandler(WorkerStateEvent.WORKER_STATE_CANCELLED, 
				                new EventHandler<WorkerStateEvent>() {
				
				                    @Override
				                    public void handle(WorkerStateEvent t) {
				                    	System.out.println("An error occurred");
				                        
				                    }
				                });

				        // Start the Task.
				        new Thread(programExecution).start();
		}catch(Throwable t) {
			System.out.println("Un error");
		}
	}
	
	@FXML
	public void handleMainPageButton() {
		main.showHomePage();
	}
	
	@FXML
	public void handleInputGeneratorButtonTab() {
		main.showInputFileGeneratorPage();
	}

	@FXML
	public void handleInputFilesButtonTab() {
		main.showSettingsPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		main.showAdvancedSettingsPage();
	}
	
	@FXML
	public void handleUserParametersButtonTab() {
		main.showUserAttributesPage();
	}
	
	@FXML
	public void handleCommunitiesButtonTab() {
		main.showCommunitiesSettingsPage();
	}
	
	@FXML
	public void handleProfilesButtonTab() {
		main.showProfilesPage();
	}
	
	@FXML
	public void handleOutputFilesButtonTab() {
		main.showOutputFileSettingsPage();
	}
	
	@FXML
	public void handleRunButton() {
		main.showRunPage();
	}
	
	@FXML
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}
	

}
