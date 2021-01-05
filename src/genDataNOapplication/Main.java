package genDataNOapplication;
	
import java.io.File;
import java.io.IOException;
import java.util.List;

import genDataNOapplication.Utils.FileUtils;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import genDataNOapplication.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ConfigurationModel configuration;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Synthetic Data Generator");
		this.primaryStage.getIcons().add(new Image("file:./resources/icons/logo.png"));
		
		configuration = new ConfigurationModel();
		File file = new File("./config/DefaultConfig.xml");
		boolean isDefaultConfigLoaded = FileUtils.loadConfig(file, configuration);
		
		initRootLayout();
		showHomePage();
		
		if(!isDefaultConfigLoaded)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error importing default configuration file");
			alert.setHeaderText("An error occurred while importing the default configuration file.");
			alert.setContentText("The configuration couldn't be imported. Check that the file " + file
						+ " exists and is not corrupted.");
			alert.showAndWait();
		}
	}
	
	//Initializes the root layout
	private void initRootLayout() {
		try {
			//Load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//Show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			
	        // Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);
			
			primaryStage.show();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//When called shows the Home Page of the program
	public void showHomePage() {
		try {
			//Load Home Page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/HomePage.fxml"));
			AnchorPane homePage = (AnchorPane) loader.load();
		
			//Set homePage in the center of root layout
			rootLayout.setCenter(homePage);
			
			//Give the controller access to the main app
			HomePageController controller = loader.getController();
			controller.setMainApp(this);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//When called shows the file generator page of the program
	public void showInputFileGeneratorPage() {
		 try {
			 	
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("view/InputFileGenerator.fxml"));
	            AnchorPane settingsPage = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(settingsPage);

				InputFileGeneratorController controller = loader.getController();
				controller.setMainApp(this);
				controller.setConfiguration(configuration);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	//When called shows the settings page of the program
	public void showSettingsPage() {
		 try {
			 	
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("view/InputFileSettings.fxml"));
	            AnchorPane settingsPage = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(settingsPage);

				InputFileSettingsController controller = loader.getController();
				controller.setMainApp(this);
				controller.setConfiguration(configuration);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void showProfilesPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ProfilesPage.fxml"));
			Node ProfilesPage = (Node) loader.load();
			
			rootLayout.setCenter(ProfilesPage);
			
			ProfilesPageController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showCommunitiesSettingsPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CommunitiesProfilesSettings.fxml"));
			AnchorPane communitiesSettings = (AnchorPane) loader.load();
			
			rootLayout.setCenter(communitiesSettings);
			
			CommunitiesSettingsController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showOutputFileSettingsPage() {
		 try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("view/OutputFileSettings.fxml"));
	            AnchorPane settingsPage = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(settingsPage);

				OutputFileSettingsController controller = loader.getController();
				controller.setMainApp(this);
				controller.setConfiguration(configuration);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public void showAdvancedSettingsPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AdvancedSettings.fxml"));
			AnchorPane AdvancedSettings = (AnchorPane) loader.load();
			
			rootLayout.setCenter(AdvancedSettings);
			
			AdvancedSettingsController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showUserAttributesPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/UserAttributes.fxml"));
			Node UserAttributes = (Node) loader.load();
			
			rootLayout.setCenter(UserAttributes);
			
			UserAttributesController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public AttributeModel showAttributeEditDialog(AttributeModel attribute, List<String> attributeNames, boolean openExistentAttr) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/AttributeEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Attribute");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:./resources/icons/edit_icon.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        AttributeEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setAttribute(attribute);
	        controller.setAttributeNames(attributeNames);
	        if(openExistentAttr) {
	        	controller.openAttribute(attribute);
	        }

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	        
	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	//Runs the program with a set of customized settings
	public void runCustomSettings() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RunPage.fxml"));
			AnchorPane runPage = (AnchorPane) loader.load();
		
			rootLayout.setCenter(runPage);
			
			RunPageController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
			controller.startApplication();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showRunPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RunPage.fxml"));
			AnchorPane runPage = (AnchorPane) loader.load();
		
			rootLayout.setCenter(runPage);
			
			RunPageController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showStatisticsPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/StatisticsPage.fxml"));
			Node statisticsPage = (Node) loader.load();
		
			rootLayout.setCenter(statisticsPage);
			
			StatisticsPageController controller = loader.getController();
			controller.setMainApp(this);
			controller.setConfiguration(configuration);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Opens a dialog to edit details for the specified person.
	 * @return true if the user clicked OK, false otherwise.
	 */
	public void showCommunityAssaignmentDialog() {
	    try {
	        // Load the fxml file and create a new stage for the pop-up dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/CommunityAssaignmentDialog.fxml"));

	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Community Assignment");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.getIcons().add(new Image("file:./resources/icons/edit_icon.png"));
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ComAssaignDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setup(this.configuration);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	       
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
		
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
	}
	
	public ConfigurationModel getConfiguration() {
		return configuration;
	}
	
	//Returns the primary stage of the program
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
