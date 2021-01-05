package genDataNOapplication.view;

import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.model.ConfigurationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Class that controlls the behaviour of the Communities Settings Page
public class CommunitiesSettingsController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	//Buttons
	@FXML
	Button inputGeneratorButtonTab;
	@FXML
	Button InputFilesButtonTab;
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
	Button runButtonTab;
	@FXML
	Button statisticsButton;
	@FXML
	Button resetButton;
	@FXML
	Button communityAssignmentButton;
	@FXML
	Button profileFrequencyButton;
	
	//Spinners
	@FXML
	Spinner<Integer> numCommunitiesSpinner;

	
	//Class constructor
	public CommunitiesSettingsController() { }
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
	@FXML
	private void initialize() {
		numCommunitiesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10));
		numCommunitiesSpinner.valueProperty().addListener((ob, oldValue, newValue) ->
		{
			if(this.configuration != null)
				this.configuration.setNumCommunities(newValue);
		});
	}	
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		numCommunitiesSpinner.getValueFactory().setValue(configuration.getNumCommunities());

	}
	
	@FXML
	public void handleCommunityAssignmentButton() {
		main.showCommunityAssaignmentDialog();
	}
		
	//Restores the page values to the default ones
	@FXML
	public void handleResetButton() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Default");
		alert.setHeaderText("Reset parameters to default");
		alert.setContentText("Are you sure you want to reset all settings parameters to the default configuration?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		Image icon = new Image("file:./resources/icons/confirmation_icon.png");
		stage.getIcons().add(icon);		
		alert.setGraphic(new ImageView(icon));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			configuration.setNumCommunities(10);
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
	public void handleProfilesButtonTab() {
		main.showProfilesPage();
	}

	@FXML
	public void handleOutputFilesButtonTab() {
		main.showOutputFileSettingsPage();
	}

	@FXML
	public void handleRunButtonTab() {
		main.showRunPage();
	}
	
	@FXML
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}

}
