package genDataNOapplication.view;

import java.util.ArrayList;
import genDataNOapplication.model.ConfigurationModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ComAssaignDialogController {
	
	private Stage dialogStage;
		
	private ObservableList<ProfileEntry> choiceList = FXCollections.observableArrayList();
	
	private ArrayList<Entry> entries = new ArrayList<Entry>();
	
	private int nEntries = 0;
	
	//Configuration
	protected ConfigurationModel configuration;
	
	@FXML
	GridPane gridPane;
	
	//Buttons
	@FXML
	Button cancelButton;
	@FXML
	Button saveButton;
	
	//Class constructor
	public ComAssaignDialogController() { }
	
	//Initializes the controller class. This method is automatically called
    // after the fxml file has been loaded.
    @FXML
    private void initialize() {  }
    
    // Basically a list of each row
    class Entry
    {
		public final ChoiceBox<ProfileEntry> choiceBox;
    	public final int communityId;
    	public final int communityFrequency;
    	
    	public Entry(ChoiceBox<ProfileEntry> choiceBox, int communityId, int communityFrequency) {
			this.choiceBox = choiceBox;
			this.communityId = communityId;
			this.communityFrequency = communityFrequency;
		}
    }
    
    class ProfileEntry
    {
    	public final int id;
    	ProfileEntry(int id) { this.id = id; }
    	@Override
    	public String toString() { return "Profile " + this.id; }    	
    }
    
    private void addEntry(int communityId , int frequency, int profileId)
    {
    	ChoiceBox<ProfileEntry> choiceBox = new ChoiceBox<>(choiceList);
    	choiceBox.setPrefWidth(180);
    	
    	ProfileEntry choice = choiceList.stream().
    			filter( o -> o.id == profileId).findFirst().orElse(null);
    	
    	choiceBox.getSelectionModel().select(choice);
    	
    	// Switch profiles (as it must be a bijective relation)
    	choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldProfileEntry, newProfileEntry) -> {
			for(Entry entry : entries)
			{
				if(entry.choiceBox == choiceBox)
					continue;
				if(entry.choiceBox.getSelectionModel().getSelectedItem() == newProfileEntry)
				{
					entry.choiceBox.getSelectionModel().select(oldProfileEntry);
					break;
				}    					
			}
    	});    	
 
    	double ncount = configuration.getNumberOfNodes();
		System.out.println("\n***NCOUNT:"+ncount+" frequency: "+frequency);
    	gridPane.addRow(nEntries++,
			new Label("Community " + communityId),
			//new Label(String.format("%.1f %%", (double)frequency/ncount * 1000.0 * 100.0)), // was 1k * 100
			new Label(String.format("%.1f %%", (double)frequency/ncount * 100.0)), // was 1k * 100
			choiceBox
    	);
    	
    	entries.add(new Entry(choiceBox,communityId,frequency));
    }
    
    public void setup(ConfigurationModel configuration) {
		this.configuration = configuration;
		this.configuration.loadCommunitiesFrequencies();
			
		choiceList.clear();
    	for(int i = 0; i < this.configuration.getProfileList().size(); i++)
    		choiceList.add(new ProfileEntry(i));
    	
    	entries.clear();
		for(int i = 0; i < this.configuration.getNumCommunities(); i++)
		{
			int frequency = this.configuration.getCommunitiesFrequencies()[i];
			int profileId = this.configuration.getProfileCommunityAssaignment()[i];
			addEntry(i, frequency, profileId);
		}
    }
       
    //Setters
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
         
    /**
     * Called when the user clicks ok.
     */
	@FXML
    private void handleOk() {
		for(Entry entry : this.entries)
		{
			int profileId = entry.choiceBox.getSelectionModel().getSelectedItem().id;
			this.configuration.getProfileCommunityAssaignment()[entry.communityId] = profileId;
		}
		
		dialogStage.close();
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
