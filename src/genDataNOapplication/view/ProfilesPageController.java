package genDataNOapplication.view;

import java.util.List;
import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ProfilesPageController {
	
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
	
	//Gridpane
	//@FXML
	//GridPane profilesGridPane;
	
	@FXML
	AnchorPane profilesAnchorPane;
	
	
	//Class constructor
	public ProfilesPageController() {
		
	}
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
	@FXML
	private void initialize() {
		
	}
	@FXML
	public void loadProfileCards() {
		
		GridPane profilesGridPane = new GridPane();
		profilesGridPane.setHgap(20);
		profilesGridPane.setVgap(25);
		profilesGridPane.setPrefWidth(970);
		profilesGridPane.setPadding(new Insets(15, 0, 20, 20));
			
		List<AttributeModel> userAttrributesList = configuration.getUserAttrributesList();
		
		for(int i = 0; i < configuration.getProfileList().size(); i++)
		{
			List<Integer> profile = configuration.getProfileList().get(i);
			String title = "Profile " + i;
			GridPane profileAttr = new GridPane();
			profileAttr.setVgap(10);
			profileAttr.setHgap(10);
			
			int profileId = i;
			
			for(int j = 0; j < userAttrributesList.size(); j++)
			{
				ChoiceBox<String> attributeSelection = new ChoiceBox<>();
				for(Pair<String, Double> parameter : userAttrributesList.get(j).getParameterList())
					attributeSelection.getItems().add(parameter.getKey());
				
				attributeSelection.getSelectionModel().select(profile.get(j));
				
				int attributeId = j;
				// If the item of the list is changed 
		        attributeSelection.getSelectionModel().selectedItemProperty().
		        	addListener((observable, oldValue, newValue) -> {
		            	System.out.println(String.format("Changed \"%s\" to \"%s\" for profile %d", oldValue, newValue, profileId));
		            	int parameterId = attributeSelection.getSelectionModel().getSelectedIndex();
		            	profile.set(attributeId, parameterId);
		        });
		        
		        final int nRows = 3;
				profileAttr.add(attributeSelection, j/nRows, j%nRows);
			}
			
			TitledPane profileCard = new TitledPane();
			profileCard.setText(title);
			profileCard.setCollapsible(false);
			profileCard.setContent(profileAttr);
			profileCard.getStyleClass().add("info");
			
			final int nColumns = 2;
			profilesGridPane.add(profileCard, i%nColumns, i/nColumns);			
		}
		profilesAnchorPane.getChildren().add(profilesGridPane);
	}
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		loadProfileCards();
	}
	
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
		if (result.get() == ButtonType.OK){

		}
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
	public void handleCommunitiesButtonTab() {
		main.showCommunitiesSettingsPage();
	}
	
	@FXML
	public void handleUserParametersButtonTab() {
		main.showUserAttributesPage();
	}
	
	@FXML
	public void handleOutputFilesButtonTab() {
		main.showOutputFileSettingsPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		main.showAdvancedSettingsPage();
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

