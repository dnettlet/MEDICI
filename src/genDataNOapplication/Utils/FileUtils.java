package genDataNOapplication.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import genDataNOapplication.model.AttributeModel;
import genDataNOapplication.model.ConfigurationModel;
import javafx.util.Pair;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class FileUtils {
	
	//Function to load the configuration from a xml file
   public static boolean loadConfig(File file, ConfigurationModel configuration) {
	   
	   try {
		   //Open the file
		   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(file);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         
	         //Load input files
    		 Element eElement = (Element) doc.getElementsByTagName("inputFiles").item(0);
    		 System.out.println("InputFile1: " + eElement.getElementsByTagName("inputFile1").item(0).getTextContent());
    		 configuration.setInputFile1(eElement.getElementsByTagName("inputFile1").item(0).getTextContent());
    		 System.out.println("InputFile2: " + eElement.getElementsByTagName("inputFile2").item(0).getTextContent());
    		 configuration.setInputFile2(eElement.getElementsByTagName("inputFile2").item(0).getTextContent());
    		 
    		 //Load output files
        	 Element outFilesElem = (Element) doc.getElementsByTagName("outputFiles").item(0);
	         System.out.println("OutFile: " + outFilesElem.getElementsByTagName("outFile").item(0).getTextContent());
	         configuration.setOutFile(outFilesElem.getElementsByTagName("outFile").item(0).getTextContent());
	         System.out.println("OutgFile: " + outFilesElem.getElementsByTagName("outgFile").item(0).getTextContent());
	         configuration.setOutgFile(outFilesElem.getElementsByTagName("outgFile").item(0).getTextContent());
	         System.out.println("Out1File: " + outFilesElem.getElementsByTagName("out1File").item(0).getTextContent());
	         configuration.setOut1File(outFilesElem.getElementsByTagName("out1File").item(0).getTextContent());
	         System.out.println("Out2File: " + outFilesElem.getElementsByTagName("out2File").item(0).getTextContent());
	         configuration.setOut2File(outFilesElem.getElementsByTagName("out2File").item(0).getTextContent());
	         
	         //Load numCommunities, SeedPercentage and randomness
        	 Element varElem = (Element) doc.getElementsByTagName("vars").item(0);
        	 System.out.println("Numcommunities: " + varElem.getElementsByTagName("numCommunities").item(0).getTextContent());
        	 configuration.setNumCommunities(Integer.parseInt(varElem.getElementsByTagName("numCommunities").item(0).getTextContent()));
        	 System.out.println("SeedPercentage: " + varElem.getElementsByTagName("seedPercentage").item(0).getTextContent());
        	 configuration.setSeedPercentage(Double.parseDouble(varElem.getElementsByTagName("seedPercentage").item(0).getTextContent()));
        	 System.out.println("Randomness: " + varElem.getElementsByTagName("randomness").item(0).getTextContent());
        	 configuration.setRandomness(Integer.parseInt(varElem.getElementsByTagName("randomness").item(0).getTextContent()));
	         
        	 //Load User Attributes
        	 List<AttributeModel> attributeList = new ArrayList<AttributeModel>();
        	 NodeList attributeNList = doc.getElementsByTagName("attribute");
        	 for(int i = 0; i < attributeNList.getLength(); i++)
        	 {
        		 Node attributeNode = attributeNList.item(i);
        		 if(attributeNode.getNodeType() == Node.ELEMENT_NODE)
        		 {
            		 Element attributeElem = (Element) attributeNode;
            		 AttributeModel attribute = new AttributeModel();
            		 System.out.println("Attribute Name: " + attributeElem.getAttribute("name"));
            		 attribute.setName(attributeElem.getAttribute("name"));
            		 System.out.println("Attribute Description: " + attributeElem.getElementsByTagName("description").item(0).getTextContent());
            		 attribute.setDescription(attributeElem.getElementsByTagName("description").item(0).getTextContent());
            		 
            		 List<Pair<String, Double>> parameterList = new ArrayList<Pair<String, Double>>();
            		 NodeList paramNList = attributeElem.getElementsByTagName("param");
            		 System.out.println("Number of params: " + paramNList.getLength());
            		 for(int j = 0; j < paramNList.getLength(); j++)
            		 {
            			 Element paramElem = (Element) paramNList.item(j);
            			 System.out.println("Value: " + paramElem.getElementsByTagName("value").item(0).getTextContent());
            			 String value = paramElem.getElementsByTagName("value").item(0).getTextContent();
            			 System.out.println("Frequency: " + paramElem.getElementsByTagName("frequency").item(0).getTextContent());
            			 Double frequency = Double.parseDouble(paramElem.getElementsByTagName("frequency").item(0).getTextContent());
            			 Pair<String, Double> param = new Pair<String, Double>(value, frequency);
            			 parameterList.add(param);
            		 }
            		 attribute.setParameterList(parameterList);
            		 attributeList.add(attribute);
        		 }
        	 }
        	 configuration.setAttributeList(attributeList);
        	 
        	 //Load profiles
        	 List<List<Integer>> profileList = new ArrayList<>();
        	 NodeList profileNList = doc.getElementsByTagName("profile");
        	 for(int i = 0; i < profileNList.getLength(); i++) {
        		 Node profileNode = profileNList.item(i);
        		 if(profileNode.getNodeType() == Node.ELEMENT_NODE) {
        			 Element profileElem = (Element) profileNode;
        			 System.out.println("Profile ID: " + profileElem.getAttribute("id"));
        			 System.out.println("Params: " + profileElem.getElementsByTagName("params").item(0).getTextContent());
        			 String[] paramArray = profileElem.getElementsByTagName("params").item(0).getTextContent().split(",");
        			 
        			 List<Integer> profileParamList = new ArrayList<>();
        			 for(int j = 0; j < paramArray.length; j++)
        				 profileParamList.add(Integer.parseInt(paramArray[j]));
        			 
        			 profileList.add(profileParamList);
        		 }
        	 }
        	 configuration.setProfileList(profileList);
        	 
        	 //Load profile community assaignment        	 
        	 System.out.println("Profile Community Assaignment: " + doc.getElementsByTagName("profileCommunityAssaignment").item(0).getTextContent());
        	 String[] profileCommunityAssaignStr = doc.getElementsByTagName("profileCommunityAssaignment").item(0).getTextContent().split(",");
        	 int[] profileCommunityAssaign = new int[profileCommunityAssaignStr.length];
        	 for(int i = 0; i < profileCommunityAssaignStr.length; i++) {
        		 profileCommunityAssaign[i] = Integer.parseInt(profileCommunityAssaignStr[i]);
        	 }
        	 configuration.setProfileCommunityAssaignment(profileCommunityAssaign);
        	 
        	 return true;
	         
	   } catch (Exception e) {
	         e.printStackTrace();
	         return false;
	      }
   }
   
   //Function to save the custom configuration to a xml file
   public static boolean exportConfig(File file, ConfigurationModel configuration) {
	   try {
		   //Create the document
		   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("config");
			doc.appendChild(rootElement);

			//Export Input files
			Element inputFiles = doc.createElement("inputFiles");
			rootElement.appendChild(inputFiles);
			Element inputFile1 = doc.createElement("inputFile1");
			inputFile1.appendChild(doc.createTextNode(configuration.getInputFile1()));
			inputFiles.appendChild(inputFile1);
			Element inputFile2 = doc.createElement("inputFile2");
			inputFile2.appendChild(doc.createTextNode(configuration.getInputFile2()));
			inputFiles.appendChild(inputFile2);
			
			//Export Output files
			Element outputFiles = doc.createElement("outputFiles");
			rootElement.appendChild(outputFiles);
			Element outFile = doc.createElement("outFile");
			outFile.appendChild(doc.createTextNode(configuration.getOutFile()));
			outputFiles.appendChild(outFile);
			Element outgFile = doc.createElement("outgFile");
			outgFile.appendChild(doc.createTextNode(configuration.getOutgFile()));
			outputFiles.appendChild(outgFile);
			Element out1File = doc.createElement("out1File");
			out1File.appendChild(doc.createTextNode(configuration.getOut1File()));
			outputFiles.appendChild(out1File);
			Element out2File = doc.createElement("out2File");
			out2File.appendChild(doc.createTextNode(configuration.getOut2File()));
			outputFiles.appendChild(out2File);
			
			//Export numCommunities, SeedPercentage and randomness
			Element vars = doc.createElement("vars");
			rootElement.appendChild(vars);
			Element numCommunities = doc.createElement("numCommunities");
			numCommunities.appendChild(doc.createTextNode(String.valueOf(configuration.getNumCommunities())));
			vars.appendChild(numCommunities);
			Element SeedPercentage = doc.createElement("seedPercentage");
			SeedPercentage.appendChild(doc.createTextNode(String.valueOf(configuration.getSeedPercentage())));
			vars.appendChild(SeedPercentage);
			Element randomness = doc.createElement("randomness");
			randomness.appendChild(doc.createTextNode(String.valueOf(configuration.getRandomness())));
			vars.appendChild(randomness);
			
			//Export User Attributes
			for(AttributeModel currentAttribute : configuration.getUserAttrributesList()) {
				Element attribute = doc.createElement("attribute");
				attribute.setAttribute("name", currentAttribute.getName());
				Element description = doc.createElement("description");
				description.appendChild(doc.createTextNode(currentAttribute.getDescription()));
				attribute.appendChild(description);
			
				for(Pair<String, Double> currentParam : currentAttribute.getParameterList()) {
					Element param = doc.createElement("param");
					attribute.appendChild(param);
					Element value = doc.createElement("value");
					value.appendChild(doc.createTextNode(currentParam.getKey()));
					param.appendChild(value);
					Element frequency = doc.createElement("frequency");
					frequency.appendChild(doc.createTextNode(String.valueOf(currentParam.getValue())));
					param.appendChild(frequency);
				}
				rootElement.appendChild(attribute);
			}
			
			
			//Export profiles
			int count = 0;
			for(List<Integer> currentProfile : configuration.getProfileList()) {
				Element profile = doc.createElement("profile");
				profile.setAttribute("id", String.valueOf(count));
				
				String paramsString = new String();
				for(int currentParam : currentProfile) {
					paramsString += String.valueOf(currentParam);
					paramsString +=",";
				}
				paramsString = paramsString.substring(0, paramsString.length() - 1);
				Element params = doc.createElement("params");
				params.appendChild(doc.createTextNode(paramsString));
				profile.appendChild(params);
				
				rootElement.appendChild(profile);
				count++;
			}
			
			//Assaign profiles to communities
			Element profileComAssaign = doc.createElement("profileCommunityAssaignment");
			int[] profileCommunityAssaign = configuration.getProfileCommunityAssaignment();
			String profileComAssaignString = new String();
			for(int i = 0; i < profileCommunityAssaign.length; i++) {
				profileComAssaignString += profileCommunityAssaign[i] + ",";
			}
			profileComAssaignString = profileComAssaignString.substring(0, profileComAssaignString.length() - 1);
			profileComAssaign.appendChild(doc.createTextNode(profileComAssaignString));
			rootElement.appendChild(profileComAssaign);

			// Write to XML file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			System.out.println("File saved!");
			return true;

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		  }
   }
}
