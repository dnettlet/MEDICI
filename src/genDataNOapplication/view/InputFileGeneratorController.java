package genDataNOapplication.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import genDataNOapplication.Main;
import genDataNOapplication.model.ConfigurationModel;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

//Controller class for the Settings Page
public class InputFileGeneratorController {
	
	//Reference to the main application
	private Main main;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	//Buttons
	@FXML
	Button 
	inputGeneratorButtonTab,
	inputFilesButtonTab,
	userParametersButtonTab,
	profilesButtonTab,
	communitiesButtonTab,
	advancedButtonTab,
	runButtonTab,
	outputFilesButtonTab,
	statisticsButton;
	
	// RMAT
	@FXML
	Button RMAT_generate_button;
	@FXML
	TextField
	RMAT_parameter_a,
	RMAT_parameter_b,
	RMAT_parameter_c,
	RMAT_parameter_d,
	RMAT_parameter_nNodes,
	RMAT_parameter_nEdges;
	
	// Louvain
	@FXML
	Button 
	Louvain_inputFile_browser_button,
	Louvain_generate_button;
	@FXML
	TextField Louvain_inputFile_path;
	
	//Class constructor
	public InputFileGeneratorController() {
		
	}
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
	@FXML
	private void initialize() {
		inputGeneratorButtonTab.setDisable(true);
	}
	
	//Is called by the main application to give a reference back to itself.
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//Is called to set a specific configuration
	public void setConfiguration(ConfigurationModel configuration) {
		this.configuration = configuration;
		//Louvain_inputFile_path.setText(configuration.getInputFile2());
	}
	
	@FXML
	public void handleRMATGeneratorButton() {
		int nNodes;
		long nEdges;
		double a,b,c,d;
		
		// Check input is parseable
		try {
	        nNodes = Integer.parseInt(RMAT_parameter_nNodes.getText());
	        nEdges= Long.parseLong(RMAT_parameter_nEdges.getText());
	        a = Double.parseDouble(RMAT_parameter_a.getText());
	        b = Double.parseDouble(RMAT_parameter_b.getText());
	        c = Double.parseDouble(RMAT_parameter_c.getText());
	        d = Double.parseDouble(RMAT_parameter_d.getText());
		}catch (Exception e) {
			Alert alarm = new Alert(Alert.AlertType.ERROR);
			alarm.setTitle("Error");
			alarm.setHeaderText("Invalid input");
			alarm.setContentText("Some input value for the RMAT is invalid");
			alarm.showAndWait();
			e.printStackTrace();
			return;
		}
		
		// Check conditions are met
		String conditonsAlertText = "";
		if(nNodes < 0)	conditonsAlertText += "Number of nodes must be > 0" + "\n";
		if(nEdges < 0)	conditonsAlertText += "Number of edges must be > 0" + "\n";
		if(Math.abs(a+b+c+d-1) > 0.1)	conditonsAlertText += "Paramaters total sum must be 1" + "\n";
		if(b > a) conditonsAlertText += "Parameter a must be bigger than b" + "\n";
		if(c > a) conditonsAlertText += "Parameter a must be bigger than c" + "\n";
		if(d > a) conditonsAlertText += "Parameter a must be bigger than d" + "\n";
		if(conditonsAlertText != "")
		{
			Alert alarm = new Alert(Alert.AlertType.ERROR);
			alarm.setTitle("Error");
			alarm.setHeaderText("Invalid input");
			alarm.setContentText(conditonsAlertText);
			alarm.showAndWait();
			return;
		}
		
		//Generate the file
		String outputFile = "./resources/Input_files/graph_edges.csv";
		
		Alert generatingAlert = new Alert(Alert.AlertType.INFORMATION);
		generatingAlert.setTitle("Generating");
		generatingAlert.setHeaderText("Generating graph nodes file.\nPlease wait.");
		generatingAlert.show();
		
        // Don't change the number of threads from 1 unless your rMAT.GraphOutput implementation allows it
		rMAT.RMATGraphGenerator generator = new rMAT.RMATGraphGenerator(new RMAT_oneThreadedOutput(outputFile), a, b, c, d, nNodes, nEdges,1);
				
		Thread thread = new Thread(new Task<Void>(){
			@Override
			public Void call() {
		        long time = System.currentTimeMillis();
				generator.execute();
				System.out.println("Generating RMAT graph edges took " + (System.currentTimeMillis() - time)/1000.0 + " seconds");
				return null;
			}
			
			@Override
			public void failed(){
				generatingAlert.hide();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Generation error");
				alert.setContentText("An error occured while generating the graph edges file");
				this.getException().printStackTrace();
				alert.showAndWait();
			}
			
			@Override			
			public void succeeded() {
				generatingAlert.hide();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText("Graph edges generated");
				alert.setContentText("File saved in: "+ outputFile);
				alert.showAndWait();
			};
		});
		
		thread.start();
	}
	
	public class RMAT_oneThreadedOutput implements rMAT.GraphOutput {

	    private String fileName;
	    private BufferedWriter buffer;

	    public RMAT_oneThreadedOutput(String fileName) {
	        this.fileName = fileName;
	        try {
				this.buffer = new BufferedWriter(new FileWriter(this.fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @Override
	    public void addEdges(int[] from, int[] to)  {
            for(int i=0; i<from.length; i++) {
        		try {
					this.buffer.write(from[i] + ";" + to[i]);
					this.buffer.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
	    }

	    public void finishUp() {
	    	try {
				this.buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	//When browse button pressed, a file chooser is opened and when a file is selected its path is written in the textfield
	public void handleBrowseButton() {
		
        FileChooser fileChooser = new FileChooser();
        
        File initialDirectory = new File("./resources/Input_files");
        
        fileChooser.setInitialDirectory(initialDirectory);

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivo de valores separados por comas de Microsoft Excel (.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());        
        if (file == null)
        	return;
        
        System.out.println(file.getName());
        Louvain_inputFile_path.setText(file.getPath());
	}
	
	//Generates communities file from file with graph edges  
	@FXML
	private void handleComunitiesFileGeneratorButton() {
		
		String inputFile = Louvain_inputFile_path.getText();
		
		// Check the inputFile exist first
		File file = new File(inputFile);
		if(!file.exists())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error generating file");
			alert.setContentText("Input file " + inputFile + " doesn't exist.");
			alert.show();
			return;
		}
		
		int extensionIndex = inputFile.lastIndexOf(".");
    	if(extensionIndex == -1)
    		extensionIndex = inputFile.length(); 
    	String outputFile = inputFile.substring(0,extensionIndex) + "_communities.csv";
		
		Alert generatingAlert = new Alert(AlertType.INFORMATION);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		generatingAlert.getButtonTypes().setAll(buttonTypeCancel);
		generatingAlert.setTitle("Generating");
		generatingAlert.setHeaderText(null);
		generatingAlert.setContentText("Generating communities file.\nPlease wait.");
		generatingAlert.show();
		
		louvain.Main louvain = new louvain.Main(inputFile,outputFile);
		
		Task<Void> task = new Task<Void>(){
			@Override
			public Void call() throws IOException {
		        long time = System.currentTimeMillis();
		        if(louvain.generateCommunitiesFile())
		        	System.out.println("Generating Louvain communities file took " + (System.currentTimeMillis() - time)/1000.0 + " seconds");
				return null;
			}
						
			@Override
			public void failed(){
				generatingAlert.hide();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Generation error");
				alert.setContentText("An error occured while generating the communities file");
				this.getException().printStackTrace();
				alert.showAndWait();
			}
			
			@Override			
			public void succeeded() {
				generatingAlert.close();				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText("File communities generated");
				alert.setContentText(
					"Output file: " + louvain.getOutputFileName() + "\n"+
					"Number of communities generated: " + louvain.getTarget_communities() + "\n" +
					"Control communities (generation quality): " + louvain.getControl_communities()
				);
				alert.showAndWait();
			};
		};
		
		generatingAlert.setOnCloseRequest(e->{ task.cancel(true); });
		new Thread(task).start();
	}

	@FXML
	
	private void handleRMAT_helpButton(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("RMAT help");
		alert.setHeaderText(null);
		alert.setContentText("RMAT is an algorithm that generates a list of edges given some parameters.\r\n\n" + 
				"Recommendations to generate good graphs:\r\n" + 
				"· The number of edges should be much bigger than the number of nodes\r\n" + 
				"· Parameter d should be bigger than b and c");
		alert.showAndWait();
	}

	@FXML
	private void handleLouvain_helpButton(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Louvain help");
		alert.setHeaderText(null);
		alert.setContentText("Louvain is an algorithm that groups the graph nodes based on the graph edges.\r\n" + 
				"This Louvain generator will try to group the nodes into 10 communities.\r\n" + 
				"If, given the case, the generator never finishes (can't generate 10 communities) you can cancel the operation and try generating another graph.");
		alert.showAndWait();
	}
		
	//Handles back button. If something has been modified asks for user confirmation.
	@FXML
	public void handleBackButton() {
		main.showHomePage();
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
	public void handleProfilesButtonTab() {
		main.showProfilesPage();
	}
	
	@FXML
	public void handleAdvancedButtonTab() {
		main.showAdvancedSettingsPage();
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
