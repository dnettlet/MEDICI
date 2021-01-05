package genDataNOapplication.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import genDataNOapplication.Main;
import genDataNOapplication.RV.RV;
import genDataNOapplication.User.User;
import genDataNOapplication.Utils.Utils;
import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;


public class StatisticsPageController {
	//Reference to the main application
	private Main main;
		
	//Configuration
	protected ConfigurationModel configuration;
	double total;
	
	//@FXML
	//ButtonBar buttonsBar;	
	
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
	Button statisticsButton;
	@FXML
	Button outFileButton;
	@FXML 
	Button outgFileButton;
	@FXML
	Button out1FileButton;
	@FXML
	Button out2FileButton;
	
	//GridPane
	@FXML
	GridPane chartsSection;
	
	//ChoiceBox
	@FXML
	ChoiceBox<String> whatToDisplay;
	
	//ChoiceBox profile id
	@FXML
	Label whatToDisplayProfileID;
	
	public StatisticsPageController() {
		
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
		total = 0;
		setup();
	}
	
	private void setup() {
		
		outFileButton.setText("out");	
		outgFileButton.setText("outg");
		out1FileButton.setText("out1");
		out2FileButton.setText("out2");
		
		whatToDisplay.getItems().add("Community 0");
		whatToDisplay.getItems().add("Community 1");
		whatToDisplay.getItems().add("Community 2");
		whatToDisplay.getItems().add("Community 3");
		whatToDisplay.getItems().add("Community 4");
		whatToDisplay.getItems().add("Community 5");
		whatToDisplay.getItems().add("Community 6");
		whatToDisplay.getItems().add("Community 7");
		whatToDisplay.getItems().add("Community 8");
		whatToDisplay.getItems().add("Community 9");
		whatToDisplay.getItems().add("Show All");
		whatToDisplay.getSelectionModel().select(10);
        whatToDisplay.getSelectionModel().selectedIndexProperty().addListener((ob, oldValue, newValue) -> 
        { 
            loadStatistics(whatToDisplay.getSelectionModel().selectedIndexProperty().intValue());
        });
        
		loadStatistics(whatToDisplay.getSelectionModel().selectedIndexProperty().intValue());
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadStatistics(int comNum) {
		System.out.println("Displaying community " + comNum);
		
		chartsSection.getChildren().clear();
		
		// Charts indices
		int row = 0;
		int col = 0;
		
		// Chart for Profiles
		{
			int profilesFrequencies[] = this.configuration.getCurrentProfilesFrequencies();
			
			ObservableList<PieChart.Data> profileChartData = FXCollections.observableArrayList();
	        for(int i = 0; i < profilesFrequencies.length; i++)
	           	profileChartData.add(new PieChart.Data("Profile " + i, profilesFrequencies[i]));
	        
	        
	        PieChart profileChart = new PieChart(profileChartData);
	           
	        profileChart.getData().stream().forEach(data -> {
	            Tooltip tooltip = new Tooltip();
	            tooltip.setText(100*data.getPieValue()/1000.0 + " %");
	            Tooltip.install(data.getNode(), tooltip);
	            data.pieValueProperty().addListener((observable, oldValue, newValue) -> 
	                tooltip.setText(newValue + "%"));
	        });
	
	        profileChart.setTitle("Profile Frequency");
			chartsSection.add(profileChart, col, row);
			
			col++;
            if(col > 2) {
            	col = 0;
            	row++;
            }
		}
		
		// Chart attributes of set community
		int countAttr = 0;
		for(AttributeModel currentAttr : configuration.getUserAttrributesList())
		{	        
            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Profile");
            for(Pair<String, Double> currentParam : getExpectedData(comNum, countAttr))
            	series1.getData().add(new XYChart.Data<String, Double>(currentParam.getKey(), currentParam.getValue()));
            
            XYChart.Series series2 = new XYChart.Series();
            series2.setName("Community");
            
            for(Pair<String, Double> currentParam : getRealData(comNum, countAttr))
            	series2.getData().add(new XYChart.Data<String, Double>(currentParam.getKey(), currentParam.getValue()));
            
			final CategoryAxis xAxis = new CategoryAxis();
	        xAxis.setLabel("Parameters");
	        final NumberAxis yAxis = new NumberAxis();
	        yAxis.setLabel("Frequency");
	        final BarChart<String,Number> attributesChart = new BarChart<String,Number>(xAxis,yAxis);
            attributesChart.setTitle(currentAttr.getName());
            
            chartsSection.add(attributesChart, col, row);
            attributesChart.getData().addAll(series1, series2);
            col++;
            if(col > 2) {
            	col = 0;
            	row++;
            }
	       countAttr++;
		}
				
		// Table with all the attributes data if a particular community is selected (not all)
		if(comNum == 10)
		{
			whatToDisplayProfileID.setText("Profile: all");			
		}
		else
		{
			int communityN = comNum;
			int profileID = configuration.getProfileCommunityAssaignment()[communityN];
			
			whatToDisplayProfileID.setText("Profile: " + profileID);			
			
			TableView tableView = new TableView();
			{
				{
					String[][] columns= {
						{"Atribute","atribute",},
						{"Profile " + profileID,"profile"},
					};			
					for(String[] column : columns)
					{
						TableColumn<TableRowData,String> tableColumn = new TableColumn<>(column[0]);
						tableColumn.setCellValueFactory(new PropertyValueFactory<>(column[1]));
						tableView.getColumns().add(tableColumn);
					}
				}
				
				TableColumn<TableRowData,String> communityColumn = new TableColumn<>("Community " + communityN);
				
				String[][] columns= {
						{"Exact","exact"},
						{"Modal 1","modal_1"},
						{"Modal 2","modal_2"}
				};			
				for(String[] column : columns)
				{
					TableColumn<TableRowData,String> tableColumn = new TableColumn<>(column[0]);
					tableColumn.setCellValueFactory(new PropertyValueFactory<>(column[1]));
					communityColumn.getColumns().add(tableColumn);
				}
				
				tableView.getColumns().add(communityColumn);
			}
			{			
				List<AttributeModel> attrributesList = configuration.getUserAttrributesList();
				
				for(int attributeN = 0; attributeN < attrributesList.size(); attributeN++)
				{
					String attribute = attrributesList.get(attributeN).getName();
					
					List<Pair<String, Double>> expectedParamList = configuration.getUserAttrributesList().get(attributeN).getParameterList();
					List<Integer> profile = configuration.getProfileList().get(profileID);
					int paramID = profile.get(attributeN);
					Pair<String, Double> param = expectedParamList.get(paramID);
					String profileAttributeParamater = param.getKey();
					
					List<Pair<String,Double>> realParameterList = getRealData(communityN, attributeN);
					Double exact = realParameterList.get(paramID).getValue();
					
					realParameterList.sort((pair1,pair2) ->  pair2.getValue().compareTo(pair1.getValue()));

					String modal_1 = realParameterList.get(0).getKey();
					String modal_2 = realParameterList.get(1).getKey();
					
					String realStr = String.format("%.2f %%",exact*100); // Truncate to two decimals 
					tableView.getItems().add(new TableRowData(attribute,profileAttributeParamater,realStr,modal_1,modal_2));
				}		
			}
		    col++;
            if(col > 2) {
            	col = 0;
            	row++;
            }
            col = 0;
			chartsSection.add(tableView, col, row,3,1);
		}
	}
	
	//Data class that holds data of an attribute parameter 
	public class TableRowData
	{
		private String atribute;
		private String profile;
		private String exact;
		private String modal_1;
		private String modal_2;
		
		public TableRowData(String atribute, String profile, String exact, String modal_1, String modal_2) {
			super();
			this.atribute = atribute;
			this.profile = profile;
			this.exact = exact; 
			this.modal_1 = modal_1;
			this.modal_2 = modal_2;
		}
		public String getAtribute() {	return atribute;	}
		public String getProfile() {	return profile;	}
		public String getExact() {	return exact;	}
		public String getModal_1() {	return modal_1;	}
		public String getModal_2() {	return modal_2;	}
		public void setAtribute(String atribute) {	this.atribute = atribute;	}
		public void setProfile(String profile) {	this.profile = profile;	}
		public void setExact(String exact) {	this.exact = exact;	}
		public void setModal_1(String modal_1) {	this.modal_1 = modal_1;	}
		public void setModal_2(String modal_2) {	this.modal_2 = modal_2;	}
	}	
	
	private List<Pair<String, Double>> getExpectedData(int comNum, int countAttr){
		List<Pair<String, Double>> expectedParamList = configuration.getUserAttrributesList().get(countAttr).getParameterList();

		if(comNum != 10) {
			int[] profComAssaign = configuration.getProfileCommunityAssaignment();
			int profileID = profComAssaign[comNum];
			List<Integer> profile = configuration.getProfileList().get(profileID);
			int paramID = profile.get(countAttr);
			Pair<String, Double> param = expectedParamList.get(paramID);
			List<Pair<String, Double>> newParamList = new ArrayList<Pair<String, Double>>();
			
			for(Pair<String, Double> currentParam : expectedParamList) {
				if(currentParam.getKey().equals(param.getKey())) {
					Pair<String, Double> newParam = new Pair<String, Double>(currentParam.getKey(), 1.0);
					newParamList.add(newParam);
				}else {
					Pair<String, Double> newParam = new Pair<String, Double>(currentParam.getKey(), 0.0);
					newParamList.add(newParam);
				}
					
			}
			expectedParamList = newParamList;
			
		}
		
		return expectedParamList;
	}
	@SuppressWarnings("unchecked")
	private List<Pair<String, Double>> getRealData(int comNum, int attributeId){
		List<Pair<String, Double>> realParamList = new ArrayList<Pair<String, Double>>();
		List<Integer> paramFreqList = new ArrayList<Integer>();
		List<String> paramNameList = new ArrayList<String>();
		for(Pair<String, Double> currentParam : configuration.getUserAttrributesList().get(attributeId).getParameterList()) {
			paramNameList.add(currentParam.getKey());
			paramFreqList.add(0);
		}
		Enumeration<?> en1 = RV.Users.keys();
		
		while (en1.hasMoreElements()){
			User nw = (User)RV.Users.get((Integer)en1.nextElement());
			if(comNum != 10) {
			Vector<Integer> communities =  nw.communities;
			if(!communities.contains(comNum))
				continue;
			}
			
			String attribute = new String();
			switch(attributeId) {
			case 0:
				attribute = nw.getAge();
				break;
			case 1:
				attribute = nw.getGender();
				break;
			case 2:
				attribute = nw.getResidence();
				break;
			case 3:
				attribute = nw.getReligion();
				break;
			case 4:
				attribute = nw.getMaritalStatus();
				break;
			case 5:
				attribute = nw.getProfession();
				break;
			case 6:
				attribute = nw.getPoliticalOrientation();
				break;
			case 7:
				attribute = nw.getSexualOrientation();
				break;
			case 8:
				attribute = nw.getLike(1);
				break;
			case 9:
				attribute = nw.getLike(2);
				break;
			case 10:
				attribute = nw.getLike(3);
				break;
			}
				
			int index = 0;
			for(String currentName : paramNameList) {
				if(attribute.equals(currentName)) {
					index = paramNameList.indexOf(currentName);
					int freq = paramFreqList.get(index);
					freq++;
					paramFreqList.set(index, freq);
					break;
				}
			}

		}
		int sum = 0;
		for(String currentName : paramNameList) {
			sum += paramFreqList.get(paramNameList.indexOf(currentName));
		}
		
		for(int value : paramFreqList) {
			double freq = (double) value / sum;
			String name = paramNameList.get(paramFreqList.indexOf(value));
			Pair<String, Double> param = new Pair<String, Double>(name, freq);
			realParamList.add(param);
		}
		return realParamList;
	}
	
	private void openFile(String fileName) {
		File file = new File(fileName);
		if(!Utils.openFile(file)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error opening the file " + file.getPath());
			alert.showAndWait();
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
	public void handleStatisticsButton() {
		main.showStatisticsPage();
	}	
	
	@FXML
	public void handleRunButton() {
		main.showRunPage();
	}
	
	@FXML
	public void handleOutFileButton() {
		this.openFile(configuration.getOutFile());
	}
	
	@FXML
	public void handleOutgFileButton() {
		this.openFile(configuration.getOutgFile());
	}
	
	@FXML
	public void handleOut1FileButton() {
		this.openFile(configuration.getOut1File());
	}
	
	@FXML
	public void handleOut2FileButton() {
		this.openFile(configuration.getOut2File());
	}
	
	@FXML
	public void handleBackButton() {
		main.showRunPage();
	}
}
