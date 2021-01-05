package genDataNOapplication.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import genDataNOapplication.model.AttributeModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
//Class that controlls the Attribute Edit dialog used to add or edit a user attribute
public class AttributeEditDialogController {
	
	AttributeModel attribute;
	List<Pair<String, Double>> parameterList;
	List<String> attributeNames;
	private Stage dialogStage;
	private boolean okClicked = false;
	private int paramCount = 0;
	
	//Textfiields
	@FXML
	TextField nameTextField;
	@FXML
	TextArea descriptionTextArea;
	//Buttons
	@FXML
	Button addButton;
	@FXML
	Button resetButton;
	@FXML
	Button cancelButton;
	@FXML
	Button saveButton;
	//Gridpane containing the different fields
	@FXML
	GridPane parametersSection;
	//Class constctor
	public AttributeEditDialogController() {

	}
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
    @FXML
    private void initialize() {
    	nameTextField.setText("Prova Name");
    	nameTextField.setEditable(false);
    	descriptionTextArea.setText("Parameter Description. Nulla vitae elit libero, a pharetra augue. Aenean lacinia bibendum nulla sed consectetur.");
    	descriptionTextArea.setEditable(false);
    	parameterList = new ArrayList<Pair<String, Double>>();
    	attributeNames = new ArrayList<String>();
    	
    }
    
    //Setters
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
 
    public void setAttributeNames(List<String> attributeNames) {
    	this.attributeNames = attributeNames;
    }
    
    public void setAttribute(AttributeModel attribute) {
        this.attribute = attribute;

    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public AttributeModel isOkClicked() {
        if(okClicked) {
        	return attribute;
        }
        else {
        	return null;
        }
    }
    //When the add button is pressed, a new Parameter is created in the corresponding cell of the gridpane
    @FXML
    private void handleAddButton() {
    	TextField paramName = new TextField();
    	paramName.setText("Introduce Parameter Name");
    	parametersSection.add(paramName, 0, paramCount + 2);
        Spinner<Double> paramValue = new Spinner<Double>(); 
        DoubleSpinnerValueFactory factory = new DoubleSpinnerValueFactory(0, 1, 0.5);
        factory.setAmountToStepBy(0.1);
        paramValue.setValueFactory(factory);
        paramValue.setEditable(true);
		parametersSection.add(paramValue, 1, paramCount + 2);
		Button deleteParamButton = new Button();
		deleteParamButton.setText("Delete");
		deleteParamButton.getStyleClass().add("danger");
		String deleteButtonID = "deleteParamButton" + String.valueOf(paramCount);
		deleteParamButton.setId(deleteButtonID);
		parametersSection.add(deleteParamButton, 2, paramCount + 2);
		paramCount++;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @SuppressWarnings("unchecked")
	@FXML
    private void handleOk() {
    	attribute.setName(nameTextField.getText());
    	attribute.setDescription(descriptionTextArea.getText());
    	List<Node> childrens = parametersSection.getChildren();
		TextField paramName = null;
		Spinner<Double> paramValue = null;
		parameterList.clear();
    	for(Node currentNode : childrens) {
    		if(currentNode.getId() != null) {
    			continue;
    		}
    		if(currentNode.getClass().toString().equals("class javafx.scene.control.TextField")) {
    			paramName = (TextField) currentNode;
    			continue;
    		}
    		if(currentNode.getClass().toString().equals("class javafx.scene.control.Spinner")) {
    			paramValue = (Spinner<Double>) currentNode;
    		}
    		else {    			
    			continue;
    		}
    		
    		Pair<String, Double> parameter = new Pair<String, Double>(paramName.getText(), paramValue.getValue());
    		parameterList.add(parameter);
    	}
    	
        if (isInputValid())
        {
        	attribute.setParameterList(parameterList);
            okClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    /**
     * Called when the user clicks Reset.
     */
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
			nameTextField.setText("");
			descriptionTextArea.setText("");
			parameterList.clear();
			for(int i = (paramCount*3) + 4; i > 4; i--) {

				parametersSection.getChildren().remove(i-1);		
				

			}
			paramCount = 0;

		}
	}
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
    	boolean error = false;
    	String errorHeader = "";
    	String errorMessage = "";
    	if(nameTextField.getText().equals("")) {
    		errorHeader = "Error with the Attribute Name";
    		errorMessage = "The Attribute must have a name!";
    		error = true;
    	}
    	for(String attributeName : attributeNames) {
    		if(nameTextField.getText().equals(attributeName)) {
    			errorHeader = "Error with the Attribute Name";
    			errorMessage = "There is already an attribute with this name.";
    			error = true;
    		}
    	}
    	if(paramCount > 0) {
        	double sum = 0;
        	for(Pair<String, Double> parameter : parameterList) {
        		sum += parameter.getValue();
        	}
        	if(sum != 1.00) {
        		errorHeader = "Error with parameter Values";
        		errorMessage = "Please make sure the sum of the values of the different parameters is 1."
        				+ "\n Remember that the parameter values are the % of assignation, so it should sum 1.";
        		error = true;
        	}
    	}else {
    		errorHeader = "There are no Attribute Parameters!";
    		errorMessage = "You can not create a User attribute without parameters."
    				+ "\n Please create some parameters for this attribute by clicking the Add Parameter button";
    		error = true;
    	}
    	
    	if(error) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error with attribute");
    		alert.setHeaderText(errorHeader);
    		alert.setContentText(errorMessage);
    		alert.showAndWait();
    		return false;
    	}
    	return true;
    }
    
    //When from the User Attributes page a user clicks "edit" in one attribute this method is called
    ///in order to load that attribute here in the Attribute Edit Screen
    public void openAttribute(AttributeModel attribute) {
    	this.attribute = attribute;
    	this.parameterList = attribute.getParameterList();
    	paramCount = 0;
    	nameTextField.setText(attribute.getName());
    	descriptionTextArea.setText(attribute.getDescription());
    	for(Pair<String, Double> parameter : parameterList) {
        	TextField paramName = new TextField();
        	paramName.setText(parameter.getKey());
        	paramName.setEditable(false);
        	parametersSection.add(paramName, 0, paramCount + 2);
        	Spinner<Double> paramValue = new Spinner<Double>();
    		//paramValue.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100));
    		//paramValue.getValueFactory().setValue(parameter.getValue());
            DoubleSpinnerValueFactory factory = new DoubleSpinnerValueFactory(0, 1, parameter.getValue());
            factory.setAmountToStepBy(0.1);
            paramValue.setValueFactory(factory);
            paramValue.setEditable(true);
    		parametersSection.add(paramValue, 1, paramCount + 2);
    		Button deleteParamButton = new Button();
    		deleteParamButton.setText("Delete");
    		deleteParamButton.getStyleClass().add("danger");
    		String deleteButtonID = "deleteParamButton" + String.valueOf(paramCount);
    		deleteParamButton.setId(deleteButtonID);
    		deleteParamButton.setOnAction(new EventHandler<ActionEvent>() {
    	
				@Override public void handle(ActionEvent e) {
    		       parameterList.remove(parameter);
    		       attribute.setParameterList(parameterList);
    		       int size = parametersSection.getChildren().size();
    		       for(int i = size - 1; i > 3; i--) {
    		    	   parametersSection.getChildren().remove(i);
    		       }
    		       openAttribute(attribute);
    		    }
    		});
    		parametersSection.add(deleteParamButton, 2, paramCount + 2);
    		deleteParamButton.setVisible(false);
    		paramCount++;
    	}
    	
    }
	
    
	
	
	
}
