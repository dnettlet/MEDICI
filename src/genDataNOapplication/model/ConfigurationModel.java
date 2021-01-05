package genDataNOapplication.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import genDataNOapplication.model.AttributeModel;
import javafx.util.Pair;

//Class that stores all the customizable settings for the program execution
public class ConfigurationModel {
	
	//Input and output file paths
	private String inputFile1;
	private String inputFile2;
	private String outFile;
	private String outgFile;
	private String outs1File;
	private String out1File;
	private String out2File;
	
	//Number of communities
	private int numCommunities;
	
	//Proportion of nodes that are seeds from the total node count of the graph 
	private double seedPercentage;
	
	private int randomness;
	
	//List of User Attributes
	private List<AttributeModel> userAttrributesList;
	
	//List of Profiles
	private List<List<Integer>> profileList;
	
	//Each Profile Assigned to one community
	int profileCommunityAssaign[] = new int[10];	
	
	/**
	 * List of communities frequencies (relative to the count of number of nodes 
	 * of each community from the communities file). The total sum must be 1000.
	 * 
	 * This data is generated from the nodes communities file and doesn't 
	 * need to be exported or imported.
	 */
	private int communitiesFrequencies[] = new int[10];

	
	//Class constructor
	public ConfigurationModel() { }
	
	//Setters and getters
	public String getInputFile1() { return inputFile1; }
	public void setInputFile1(String inputFile1) { this.inputFile1 = inputFile1; }
	
	public String getInputFile2() { return inputFile2; }
	public void setInputFile2(String inputFile2) { this.inputFile2 = inputFile2; }
	
	public String getOutFile() { return outFile; }
	public void setOutFile(String outFile) { this.outFile = outFile; }
	
	public String getOutgFile() { return outgFile; }
	public void setOutgFile(String outgFile) { this.outgFile = outgFile; }
	
	public String getOuts1File() { return outs1File; }
	public void setOuts1File(String outs1File) { this.outs1File = outs1File; }
	
	public String getOut1File() { return out1File; }
	public void setOut1File(String out1File) { this.out1File = out1File; }
	
	public String getOut2File() { return out2File; }
	public void setOut2File(String out2File) { this.out2File = out2File; }
	
	public int getNumCommunities() { return numCommunities; }
	public void setNumCommunities(int numCommunities) { this.numCommunities = numCommunities; }
	
	public double getSeedPercentage() { return seedPercentage; }
	public void setSeedPercentage(double seedPercentage) { this.seedPercentage = seedPercentage; }
	
	public int getRandomness() { return randomness; }
	public void setRandomness(int randomness) { this.randomness = randomness; }
	
	public List<AttributeModel> getUserAttrributesList() { return userAttrributesList; }
	
	public List<List<Integer>> getProfileList() { return profileList; }
	public void setProfileList(List<List<Integer>> profileList) { this.profileList = profileList; }
	
	public int[] getProfileCommunityAssaignment() { return profileCommunityAssaign; }
	public void setProfileCommunityAssaignment(int[] profileCommunityAssaign) { this.profileCommunityAssaign = profileCommunityAssaign; }
	
	//Add user attributes
	public void setAttributeList(List<AttributeModel> attributeList) {this.userAttrributesList = attributeList; }
	
	
	// Calculates number of nodes from the current communities file.
	public int getNumberOfNodes()
	{	
		int count = 0;
		try 
		{
	    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.inputFile2)), "UTF-8"));
	    	while (br.readLine() != null)
	    		count += 1;
	    	br.close();
		}
		catch (Exception e)
		{
            System.out.println("Issue with communities file: " + this.inputFile2);  
            e.printStackTrace();
        }
		
		return count;
	}
	
	// Calculates the this.communitiesFrequency from the current communities file
	public void loadCommunitiesFrequencies()
	{
		Arrays.fill(this.communitiesFrequencies, 0);		
		try 
		{
			int ncount=0;
	    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.inputFile2)), "UTF-8"));
	    	for(String line = br.readLine(); line != null; line = br.readLine())
	    	{
	    		String linedata[] = line.split(";");
	    		int communityId = Integer.parseInt(linedata[1]);
	    		this.communitiesFrequencies[communityId] += 1;
	    		++ncount;
	        }
	    	br.close();
	        
	        int sum = Arrays.stream(this.communitiesFrequencies).sum();        
	        double ratio = ncount/sum;
	        
	        sum = 0;
	        for(int i = 0; i < this.communitiesFrequencies.length - 1; i++) {
	        	this.communitiesFrequencies[i] *= ratio;
	        	sum += this.communitiesFrequencies[i];
	        }
	        
	        // Last one is the difference to make sure sum reaches ncount exactly
	        this.communitiesFrequencies[this.communitiesFrequencies.length-1] = ncount - sum;
		}
		catch (Exception e)
		{
            System.out.println("Issue with communities file: " + this.inputFile2);  
            e.printStackTrace();
        }  
	}

	// This can change if the communities file changes
	public int[] getCommunitiesFrequencies() {  return communitiesFrequencies;  }
	
	// This can change if the communities file changes or the profile assignment changes
	public int[] getCurrentProfilesFrequencies()
	{
		int profileFrequencies[] = new int[this.profileCommunityAssaign.length];
		for(int i=0; i < this.profileCommunityAssaign.length ; i++)
			profileFrequencies[profileCommunityAssaign[i]] = communitiesFrequencies[i]; 
		
		return profileFrequencies;		
	}
}
