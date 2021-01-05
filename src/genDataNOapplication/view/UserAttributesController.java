package genDataNOapplication.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import genDataNOapplication.Main;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
//Class that controls the behaviour of the User Attributes page
public class UserAttributesController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	private List<AttributeModel> list;
	private ObservableList<AttributeModel> attributeList;
	
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
	Button runButtonTab;
	@FXML
	Button statisticsButton;
	@FXML
	Button addButton;

	
	//Grid pane
	@FXML
	GridPane attributesSection;
	
	//Anchor Pane
	@FXML
	AnchorPane pageAnchorPane;

	//Class Constructor
	public UserAttributesController() {
		
	}
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
	@FXML
	private void initialize() {
		list = new ArrayList<AttributeModel>();		
	}
	

	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		list = configuration.getUserAttrributesList();
		attributeList = FXCollections.observableList(list);
		attributeList.addListener(new ListChangeListener<Object>() {

			@SuppressWarnings("rawtypes")
			@Override
	        public void onChanged(ListChangeListener.Change change) {
	            reloadAttributesSection();
	        }
	    });
		reloadAttributesSection();
	}
	
	//Button to add a new User attribute. It opens the Edit User Attribute dialog
	@FXML
	public void handleAddButton() {
		List<String> attributeNames = new ArrayList<String>();
		for(AttributeModel attribute : attributeList) {
			attributeNames.add(attribute.getName());
		}
	    AttributeModel attribute = new AttributeModel();
	    AttributeModel modifiedAttribute = main.showAttributeEditDialog(attribute, attributeNames, false);
	    if (modifiedAttribute  != null) {
	        attributeList.add(modifiedAttribute);
	    }
	}
	
	//Saves the current state. If there isn't any attribute an error pops
	@FXML
	public void handleNextButton() {
		this.reloadAttributesSection();
		if(attributeList.size() > 0) {
		configuration.setAttributeList(attributeList);
		handleProfilesButtonTab();
		}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("No attributes set");
    		alert.setHeaderText("Error: No attributes set");
    		alert.setContentText("There is any User attribute specified, therefore the application can't run."
    				+ "Please add some User Attributes Using the Add button.");
    		alert.showAndWait();
		}
		

		
	}
	
	
	
	//When called it refreshes the user attributes displayed by taking a look at the attributes list and
	//updating the corresponding graphical elements
	protected void reloadAttributesSection() {
		attributesSection.getChildren().clear();
		int attributeColumn = 0;
		int attributeRow = 0;
		for(AttributeModel attribute : attributeList) {
			
			TitledPane attributeCard = new TitledPane();
			attributeCard.setText(attribute.getName());
			attributeCard.setCollapsible(false);
			attributeCard.resize(300, 363);
			attributeCard.setMinHeight(363);
			attributeCard.setMaxHeight(363);
			attributeCard.setMaxWidth(290);
			attributeCard.getStyleClass().add("info");
			attributesSection.add(attributeCard, attributeColumn, attributeRow);			
			BorderPane cardBorderPane = new BorderPane();
			attributeCard.setContent(cardBorderPane);
			cardBorderPane.resize(attributeCard.getWidth(), attributeCard.getHeight());
			
			HBox hbox = new HBox();
			cardBorderPane.setTop(hbox);
			hbox.setSpacing(30);
			Text description = new Text();
			description.setText(attribute.getDescription());
			description.setWrappingWidth(150);
			hbox.getChildren().add(description);
			VBox vbox = new VBox();
			hbox.getChildren().add(vbox);
			Button editButton = new Button();
			editButton.setText("Edit");
			editButton.getStyleClass().add("success");
			editButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	        		List<String> attributeNames = new ArrayList<String>();
	        		for(AttributeModel currentAttribute : attributeList) {
	        			if(currentAttribute.getName().equals(attribute.getName())) {
	        				continue;
	        			}
	        			attributeNames.add(currentAttribute.getName());
	        		}
	        	    AttributeModel modifiedAttribute = main.showAttributeEditDialog(attribute, attributeNames, true);
	        	    if (modifiedAttribute  != null) {
	        	    	int index = attributeList.indexOf(attribute);
	        	    	attributeList.set(index, modifiedAttribute);
	        	    	//attributeList.remove(attribute);  // was commented out
	        	        //attributeList.add(modifiedAttribute); // idem
	        	       
	        	    }
	            }
	        });
			/*Button deleteButton = new Button();
			deleteButton.setText("Delete");
			deleteButton.getStyleClass().add("danger");
			deleteButton.setCancelButton(true);
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Delete attribute");
					alert.setHeaderText("Do you really want to delete the attribute?");
					alert.setContentText("By clicking okay the selected attribute will be deleted. There will not be the possibility to restore it.");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					Image icon = new Image("file:./resources/icons/confirmation_icon.png");
					stage.getIcons().add(icon);		
					alert.setGraphic(new ImageView(icon));
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						attributeList.remove(attribute);
					}
					
				}
			}); 
			deleteButton.setVisible(false);*/
			//vbox.getChildren().addAll(editButton, deleteButton);
			vbox.getChildren().addAll(editButton);
			
			
			ScrollPane cardScrollPane = new ScrollPane();
			cardScrollPane.resize(cardScrollPane.getWidth(), cardScrollPane.getHeight());
			cardBorderPane.setCenter(cardScrollPane);
			
			GridPane parametersGridPane = new GridPane();
			parametersGridPane.resize(cardBorderPane.getWidth(), cardBorderPane.getHeight());
			parametersGridPane.setHgap(10);
			parametersGridPane.setVgap(10);
			parametersGridPane.setPadding(new Insets(10, 10, 0, 10));
			cardScrollPane.setContent(parametersGridPane);
			
			int paramCount = 0;
			for(Pair<String, Double> parameter : attribute.getParameterList()) {
				Label paramName = new Label();
				paramName.setText(parameter.getKey());
				parametersGridPane.add(paramName, 0, paramCount);
				
				Separator separator = new Separator();
				separator.setOrientation(Orientation.VERTICAL);
				parametersGridPane.add(separator, 1, paramCount);
				
				Label paramValue = new Label();
				paramValue.setText(String.valueOf(parameter.getValue()));
				parametersGridPane.add(paramValue, 2, paramCount);
				
				
				paramCount++;
			}
			attributeColumn++;
			if(attributeColumn > 2) {
				System.out.println(pageAnchorPane.getWidth() + " , " + pageAnchorPane.getHeight());
				pageAnchorPane.resize(pageAnchorPane.getWidth(), pageAnchorPane.getHeight() + 2000);
				
				System.out.println(pageAnchorPane.getWidth() + " , " + pageAnchorPane.getHeight());
				attributeColumn = 0;
				attributeRow++;
			}
		}
		
	}
	
	
	@FXML
	public void handleInputGeneratorButtonTab() {
		main.setConfiguration(configuration);
		main.showInputFileGeneratorPage();
	}
	
	@FXML
	public void handleInputFilesButtonTab() {
		main.setConfiguration(configuration);
		main.showSettingsPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		main.setConfiguration(configuration);
		main.showAdvancedSettingsPage();
	}
	
	@FXML
	public void handleProfilesButtonTab() {
		main.setConfiguration(configuration);
		main.showProfilesPage();
	}
	
	@FXML
	public void handleCommunitiesButtonTab() {
		main.setConfiguration(configuration);
		main.showCommunitiesSettingsPage();
	}
	
	@FXML
	public void handleOutputFilesButtonTab() {
		main.setConfiguration(configuration);
		main.showOutputFileSettingsPage();
	}
	
	@FXML
	public void handleRunButtonTab() {
		main.setConfiguration(configuration);
		main.showRunPage();
	}
	
	@FXML
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}
	
	

}
