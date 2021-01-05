// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.RV;

//import java.math.*;
import java.util.Vector;
import java.lang.Object;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import genDataNOapplication.Dijkstra.AssignSeeds2;
import genDataNOapplication.Dijkstra.FindMedoid;
import genDataNOapplication.User.User;
import genDataNOapplication.community.Community;
import genDataNOapplication.dataFile.dataFile;
import genDataNOapplication.doCalcs.doCalcs;
import genDataNOapplication.model.ConfigurationModel;

public class RV{
	
	public static ConfigurationModel configuration;
	
static RV RVp;
final static int  NUMVARS = 4;
int user2;
public static Hashtable Users = new Hashtable();
public static Vector vecKeys = new Vector();

public static Hashtable Communities = new Hashtable();

//public static Vector Bridges  = new Vector (); // Top bridges in current graph
//public static Vector Hubs     = new Vector (); // Top hubs in current graph

//public static Vector Communities     = new Vector (); // Communities in current graph

public static Vector seeds        = new Vector (); // used in 'rassigndatageneralizedsensattr4 and dijkstra
public static Vector seeds3       = new Vector (); // used in 'rassigndatageneralizedsensattr4 and dijkstra
public static Vector seedsbesttonow = new Vector (); // saves the best result obtained
public static Vector invalids = new Vector (); // list of nodes which do not comply with the distance requirement

static Vector vgroupsf    = new Vector (); 
static Vector vgroups     = new Vector ();

public static Vector medoidids     = new Vector ();
public static Vector medoidgids     = new Vector ();
public static Vector medoiddists     = new Vector ();

public static double max_avgL=0, min_avgL=999999, max_stdevL=0, min_stdevL=999999, max_degreeL=0, min_degreeL=0;
public static double max_edgesL=0, min_edgesL=999999, max_ccL=0, min_ccL=999999; 

/* used as parameters to be estimated for distance function */

//weights derived from whole karate
//private static double alpha=1.123773125, beta=-0.23265777, gamma=1.4047586635, delta=0.264251769, epsilon=0.628574154;

//calibrated weights hep-th 19 10
//private static double alpha=2.036198827, beta=2.899113849, gamma=-0.512900449, delta=0.363877493, epsilon=0.863483746;

//calibrated weights enron 19 10
private static double alpha=2.818975514, beta=0.735939979, gamma=1.957545111, delta=0.003374712, epsilon=-0.288028499;

//calibrated weights wikivote 19 10
//private static double alpha=3.067287079, beta=-0.255742136, gamma=1.332133616, delta=0.013077081, epsilon=-0.116390063;


// IMPORTANT !!! Static data must be reset before every RV.RVp run given is modified by other classes
public static void resetStaticValues()
{
	configuration = null;
		
	RVp = null;
	Users = new Hashtable();
	vecKeys = new Vector();
	
	Communities = new Hashtable();
		
	seeds = new Vector (); // used in 'rassigndatageneralizedsensattr4 and dijkstra
	seeds3 = new Vector (); // used in 'rassigndatageneralizedsensattr4 and dijkstra
	seedsbesttonow = new Vector (); // saves the best result obtained
	invalids = new Vector (); // list of nodes which do not comply with the distance requirement
	
	vgroupsf = new Vector (); 
	vgroups = new Vector ();
	
	medoidids = new Vector ();
	medoidgids = new Vector ();
	medoiddists = new Vector ();
	
	max_avgL=0; min_avgL=999999; max_stdevL=0; min_stdevL=999999; max_degreeL=0; min_degreeL=0;
	max_edgesL=0; min_edgesL=999999; max_ccL=0; min_ccL=999999; 
	
	/* used as parameters to be estimated for distance function */
	
	//calibrated weights enron 19 10
	alpha=2.818975514; beta=0.735939979; gamma=1.957545111; delta=0.003374712; epsilon=-0.288028499;
}

public int RVp(ConfigurationModel configuration) 
{
	this.configuration = configuration;
	
    int NUMVARS = 4;
    
    user2=0;
    
	int i=0,j=0,k=0, yaesta=0;
	
	int ret=0;
	
	int vec[];
	double corr = 0;
	double[] v1 = {1,2,3,4};
	double[] v2 = {1,2,3,4};
	
	//***********************************TEST DATASETS************************************
 	
	//String nomFixterIn1    = "./resources/files/amazongraph.csv";  
	//String nomFixterIn2    = "./resources/files/amazoncommunities.csv";  
	//String nomFixterOut    = "./resources/files/amazon_out.csv"; 
	//String nomFixterOutg   = "./resources/files/amazon_outg.csv"; 
	//String nomFixterOut1   = "./resources/files/amazon_out1.csv"; 
	//String nomFixterOut2   = "./resources/files/amazon_out2.csv";
	
	//String nomFixterIn1    = "./resources/files/youtubegraph.csv";  
	//String nomFixterIn2    = "./resources/files/youtubecommunities.csv";  
	//String nomFixterOut    = "./resources/files/youtube_out.csv"; 
	//String nomFixterOutg   = "./resources/files/youtube_outg.csv"; 
	//String nomFixterOut1   = "./resources/files/youtube_out1.csv"; 
	//String nomFixterOut2   = "./resources/files/youtube_out2.csv"; 
	
	//String nomFixterIn1    = "./resources/files/livejournalgraph.csv";  
	//String nomFixterIn2    = "./resources/files/livejournalcommunities.csv";  
	//String nomFixterOut    = "./resources/files/livejournal_out.csv"; 
	//String nomFixterOutg   = "./resources/files/livejournal_outg.csv"; 
	//String nomFixterOut1   = "./resources/files/livejournal_out1.csv"; 
	//String nomFixterOut2   = "./resources/files/livejournal_out2.csv";
	
	//String nomFixterIn1    = "./resources/files/1kby30k.csv";  
	//String nomFixterIn2    = "./resources/files/1kby30kmodauthb.csv";  
	//String nomFixterOut    = "./resources/files/1kby30k_out.csv"; 
	//String nomFixterOutg   = "./resources/files/1kby30k_outg.csv";
	//String nomFixterOut1   = "./resources/files/1kby30k_out1.csv"; 
	//String nomFixterOut2   = "./resources/files/1kby30k_out2.csv";
	
	//String nomFixterIn1    = "./resources/files/rmatgraph.csv";  
	//String nomFixterIn2    = "./resources/files/rmatcommunities.csv";  
	//String nomFixterOut    = "./resources/files/rmat_out.csv"; 
	//String nomFixterOutg   = "./resources/files/rmat_outg.csv";
	//String nomFixterOut1   = "./resources/files/rmat_out1.csv"; 
	//String nomFixterOut2   = "./resources/files/rmat_out2.csv";
	
	String nomFixterIn1    = configuration.getInputFile1(); 
	String nomFixterIn2    = configuration.getInputFile2();
	String nomFixterOut    = configuration.getOutFile();
	String nomFixterOutg   = configuration.getOutgFile();
	String nomFixterOuts1   = configuration.getOuts1File();
	String nomFixterOut1   = configuration.getOut1File();
	String nomFixterOut2   = configuration.getOut2File();
     
  	//  *** Obtener los datos del fichero de input ****/
    
  	ret = leer_datos_de_casos(nomFixterIn1, nomFixterIn2, nomFixterOut, nomFixterOutg, nomFixterOuts1, nomFixterOut1, nomFixterOut2, user2, NUMVARS);
     
    //corr = Correlation.Correlationp(v1, v2);
    //System.out.println("corr= "+corr);
  	
  	return ret;
} //fin de RVp


/********************** Output Calculations in File ***********************/
public static int writeData(BufferedWriter output, BufferedWriter outputg, BufferedWriter outputs1){

	int THRESHOLD=9999999;
	String str="";
	User nw,nw2; double written=0;
	int user1=0,user2=0,i=0, friends_ok=0;

	
	String age       = "";
	Double weight    = 0.0;
	String gender    = "";
	String residence = "";
	
	String religion = "";
	String maritalstatus = "";
	String profession = "";
	String politicalorientation = "";
	String sexualorientation = "";
	
	
	String like1 = "", like2 = "",like3 = "";
	String numfriends = "";
	
	int mod=0, modf=0, COM=5;

	//The following are the frequency counters for the statistics
	int religion_Buddhist = 0, religion_Christian = 0, religion_Jewish = 0,	religion_Muslim = 0, religion_Sikh = 0,
	    religion_TraditionalSpirituality = 0, religion_OtherReligion = 0, religion_Noreligiousaffiliation = 0,
	    religion_Hindu = 0;
	
	int marital_Single = 0,	 marital_Married = 0,	 marital_Divorced = 0,	 marital_Widowed = 0;
	
	int profession_Manager = 0,	profession_Professional = 0, profession_Service = 0, profession_Salesandoffice = 0,
			profession_Naturalresourcesconstructionandmaintenance = 0, profession_Productiontransportationandmaterialmoving = 0,
	    profession_Student = 0;
	
	int political_FarLeft = 0, political_Left = 0, political_CenterLeft = 0, political_Center = 0,
	    political_CenterRight = 0, political_Right = 0, political_FarRight = 0;
	
	int sexual_Asexual = 0,	 sexual_Bisexual = 0,	 sexual_Heterosexual = 0,	 sexual_Homosexual = 0;
	
	int residence_PaloAlto = 0, residence_SantaBarbara = 0, residence_Winthrop = 0, residence_Boston = 0,	
	    residence_Cambridge = 0, residence_SanJose = 0;	
	
	int gender_male = 0, gender_female = 0;
	
	int age_18_25 = 0, age_26_35 = 0, age_36_45 = 0, age_46_55 = 0,	age_56_65 = 0, age_66_75 = 0, age_76_85 = 0;
	
	int like1_entertainment = 0, like1_musicartist = 0,	like1_drinkbrand = 0, like1_tvshow = 0;
	int like2_entertainment = 0, like2_musicartist = 0,	like2_drinkbrand = 0, like2_tvshow = 0;
	int like3_entertainment = 0, like3_musicartist = 0,	like3_drinkbrand = 0, like3_soccerclub = 0;
	// End of frequency counters
	
	int usercommunity = 0;
	

	try{
		output.write("user;age;gender;residence;religion;maritalstatus;profession;politicalorientation;sexualorientation;numfriends;like1;like2;like3;classvalue;auth;community");
		output.newLine();
		outputg.write("user;userf;linkweight");
		outputg.newLine();
		outputs1.write("community;attribute;value;frequency");
		outputs1.newLine();
		
		Enumeration en1 = RV.Users.keys();
		
	while (en1.hasMoreElements()){
		nw = (User)RV.Users.get((Integer)en1.nextElement());
		user1 = nw.getUser();
		//System.out.println("write out a line. user1 = "+user1);
		
		age       = nw.getAge();
		gender    = nw.getGender();
		residence = nw.getResidence();
		
		religion = nw.getReligion();
		maritalstatus = nw.getMaritalStatus();
		profession = nw.getProfession();
		politicalorientation = nw.getPoliticalOrientation();
		sexualorientation = nw.getSexualOrientation();
		
		//float auth = nw.getAUTH(); 
		//mod  = nw.getMOD();
		
		double ndeg = nw.getNormDegree();
		String ndegs = String.format("%f", ndeg);
		String ndegs2 = ndegs.replaceAll(",", ".");

		usercommunity  = nw.getCommunity(0); // do this for the moment so as not to change the structure
		
		
		like1 = nw.getLike(1);
		like2 = nw.getLike(2);
		like3 = nw.getLike(3);

		
		//***********Now do the frequency counts*******************
		if (religion.equals("Buddhist")) 		religion_Buddhist = religion_Buddhist + 1;
		else if (religion.equals("Christian")) 	religion_Christian = religion_Christian + 1;
		else if (religion.equals("Jewish"))		religion_Jewish = religion_Jewish + 1;
		else if (religion.equals("Muslim"))		religion_Muslim = religion_Muslim + 1;
		else if (religion.equals("Hindu"))		religion_Hindu = religion_Hindu + 1;
		else if (religion.equals("Sikh"))		religion_Sikh = religion_Sikh + 1;
		else if (religion.equals("Traditional Spirituality")) religion_TraditionalSpirituality = religion_TraditionalSpirituality + 1;
		else if (religion.equals("Other Religion"))			  religion_OtherReligion = religion_OtherReligion + 1;
		else if (religion.equals("No religious affiliation")) religion_Noreligiousaffiliation = religion_Noreligiousaffiliation + 1;

		if (maritalstatus.equals("Single"))			marital_Single = marital_Single + 1;
		else if (maritalstatus.equals("Married"))	marital_Married = marital_Married + 1;
		else if (maritalstatus.equals("Divorced"))	marital_Divorced = marital_Divorced + 1;
		else if (maritalstatus.equals("Widowed"))	marital_Widowed = marital_Widowed + 1;		
		
		if (profession.equals("Manager")) profession_Manager = profession_Manager + 1;
		else if (profession.equals("Professional")) profession_Professional = profession_Professional + 1;
		else if (profession.equals("Service")) profession_Service = profession_Service + 1;
		else if (profession.equals("Sales and office")) profession_Salesandoffice = profession_Salesandoffice + 1;
		else if (profession.equals("Natural resources construction and maintenance"))
			profession_Naturalresourcesconstructionandmaintenance = profession_Naturalresourcesconstructionandmaintenance + 1;
		else if (profession.equals("Production transportation and material moving"))
			profession_Productiontransportationandmaterialmoving = profession_Productiontransportationandmaterialmoving + 1;
		else if (profession.equals("Student")) profession_Student = profession_Student + 1;		

		if (politicalorientation.equals("Far Left"))			political_FarLeft = political_FarLeft + 1;
		else if (politicalorientation.equals("Left"))			political_Left = political_Left + 1;
		else if (politicalorientation.equals("Center Left"))	political_CenterLeft = political_CenterLeft + 1;
		else if (politicalorientation.equals("Center"))			political_Center = political_Center + 1;
		else if (politicalorientation.equals("Center Right"))	political_CenterRight = political_CenterRight + 1;
		else if (politicalorientation.equals("Right"))			political_Right = political_Right + 1;
		else if (politicalorientation.equals("Far Right"))		political_FarRight = political_FarRight + 1;

		if (sexualorientation.equals("Asexual"))			sexual_Asexual = sexual_Asexual + 1;
		else if (sexualorientation.equals("Bisexual"))		sexual_Bisexual = sexual_Bisexual + 1;
		else if (sexualorientation.equals("Heterosexual"))	sexual_Heterosexual = sexual_Heterosexual + 1;
		else if (sexualorientation.equals("Homosexual"))	sexual_Homosexual = sexual_Homosexual + 1;

		if (residence.equals("Palo Alto"))			    residence_PaloAlto = residence_PaloAlto + 1;
		else if (residence.equals("Santa Barbara"))		residence_SantaBarbara = residence_SantaBarbara + 1;
		else if (residence.equals("Winthrop"))			residence_Winthrop = residence_Winthrop + 1;
		else if (residence.equals("Boston"))			residence_Boston = residence_Boston + 1;
		else if (residence.equals("Cambridge"))			residence_Cambridge = residence_Cambridge + 1;
		else if (residence.equals("San Jose"))			residence_SanJose = residence_SanJose + 1;
		
		if (gender.equals("male"))			gender_male = gender_male + 1;
		else if (gender.equals("female"))	gender_female = gender_female + 1;	
		
		if (age.equals("18-25"))			age_18_25 = age_18_25 + 1;
		else if (age.equals("26-35"))		age_26_35 = age_26_35 + 1;
		else if (age.equals("36-45"))		age_36_45 = age_36_45 + 1;
		else if (age.equals("46-55"))		age_46_55 = age_46_55 + 1;
		else if (age.equals("56-65"))		age_56_65 = age_56_65 + 1;
		else if (age.equals("66-75"))		age_66_75 = age_66_75 + 1;
		else if (age.equals("76-85"))		age_76_85 = age_76_85 + 1;
		
		if (like1.equals("entertainment")) like1_entertainment = like1_entertainment + 1;
		else if (like1.equals("music artist")) like1_musicartist = like1_musicartist + 1;
		else if (like1.equals("drink brand")) like1_drinkbrand = like1_drinkbrand + 1;
		else if (like1.equals("tv show")) like1_tvshow = like1_tvshow + 1;;

		if (like2.equals("entertainment")) like2_entertainment = like2_entertainment + 1;
		else if (like2.equals("music artist")) like2_musicartist = like2_musicartist + 1;
		else if (like2.equals("tv show")) like2_tvshow = like2_tvshow + 1;
		else if (like2.equals("drink brand")) like2_drinkbrand = like2_drinkbrand + 1;;

		if (like3.equals("music artist")) like3_musicartist = like3_musicartist + 1;
		else if (like3.equals("entertainment")) like3_entertainment = like3_entertainment + 1;
		else if (like3.equals("drink brand")) like3_drinkbrand = like3_drinkbrand + 1;
		else if (like3.equals("soccer club")) like3_soccerclub = like3_soccerclub + 1;

		
		String classvalue = "";
		
		// change this to whatever you want, its just an example
		if (age.equals("26-35") && gender.equals("female") && residence.equals("Cambridge"))
			classvalue= "YES";
		else
			classvalue= "NO";
		
		// now calculate the % of neighbors who live in the same residence as the current user
		double numFriendsR=0;
		for (i = 0; i < nw.friends.size(); i++)
		{
			user2=nw.friends.get(i);
			User nf = (User)RV.Users.get(user2);
	    	String residencef = nf.getResidence();
	    	if (religion.equals(residencef))
	    		++numFriendsR;
		}
		numFriendsR = (double)((double)numFriendsR / (double)nw.friends.size());
		
		if (numFriendsR < 0.35)
		    numfriends = "LOW";
		else if ((0.35 < numFriendsR) && (numFriendsR < 0.60))
			numfriends = "MEDIUM";
		else
			numfriends = "HIGH";
	
		
		if (age != null)
		{
			if (!age.equals(""))
			{
			output.write(user1+";"+age+";"+gender+";"+residence+";"+religion+";"+maritalstatus+";"+profession+";"+politicalorientation+";"+sexualorientation+";"+numfriends+";"+like1+";"+like2+";"+like3+";"+classvalue+";"+ndegs2+";"+usercommunity);
			output.newLine();output.flush();
			}
		}

		if (nw.friends.size() < THRESHOLD)  // ***degree threshold
		{
		if (nw.friends.size() > 0)
		{
			for (i = 0, friends_ok=0; i < nw.friends.size(); i++){
				user2=nw.friends.get(i);

				nw2 = (User)RV.Users.get(user2);
				if (nw2.friends.size() < THRESHOLD) // ***degree threshold
					++friends_ok;
			} //efor
			
			if (friends_ok > 0)
			for (i = 0; i < nw.friends.size(); i++){
				user2=nw.friends.get(i);
				nw2 = (User)RV.Users.get(user2);
				weight = nw.getWeight(i);
				/*if (weight != null)
				{
					outputg.write(user1+","+user2+","+weight);
					System.out.println("user1:"+user1+" user2:"+user2+" weight:"+weight);
				}*/

				if (nw2.friends.size() < THRESHOLD) // ***degree threshold
				{
					modf  = nw2.getMOD();
					//if ((modf==COM) && (mod==COM))
					if (1==1)
					{
						outputg.write(user1+";"+user2+";"+weight);
						outputg.newLine();outputg.flush();
					}
				}
			} //efor
		}
		else if (nw.friends.size() == 1)
		{
			try{
			user2=nw.friends.get(0);
			nw2 = (User)RV.Users.get(user2);
			weight = nw.getWeight(0);
			
			if (nw2 != null)
				if (nw2.friends.size() > 1 && nw2.friends.size() < THRESHOLD);
				{
					modf  = nw2.getMOD();
					//if ((modf==COM) && (mod==COM)) 
					if (1==1)
					{
						outputg.write(user1+";"+user2+";"+weight);
						outputg.newLine();outputg.flush();
					}
				}
			}
			catch(RuntimeException rte){
				rte.printStackTrace();
				System.out.println("\nRun time exception has been caught");
				//return 1;
			}
		}
		else if (nw.friends.size() < 1)
		{
			++written;
			System.out.println("this node's got 0 neighbors: "+user1+" total of these: "+written);
		}
		} //efi <= THRESHOLD
		
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	try{
	// write out the frequency stats in the stats1 file for entire graph
	outputs1.write("ALL;AGE;18-25;"+age_18_25); outputs1.newLine();
	outputs1.write("ALL;AGE;26-35;"+age_26_35);	 outputs1.newLine();
	outputs1.write("ALL;AGE;36-45;"+age_36_45); outputs1.newLine();
	outputs1.write("ALL;AGE;46-55;"+age_46_55); outputs1.newLine();
	outputs1.write("ALL;AGE;56-65;"+age_56_65); outputs1.newLine();
	outputs1.write("ALL;AGE;66-75;"+age_66_75); outputs1.newLine();
	outputs1.write("ALL;AGE;76-85;"+age_76_85); outputs1.newLine();
	
	outputs1.write("ALL;GENDER;Male;"+gender_male); outputs1.newLine();
	outputs1.write("ALL;GENDER;Female;"+gender_female); outputs1.newLine();
	
	outputs1.write("ALL;RESIDENCE;PaloAlto;"+residence_PaloAlto); outputs1.newLine();
	outputs1.write("ALL;RESIDENCE;SantaBarbara;"+residence_SantaBarbara); outputs1.newLine();
	outputs1.write("ALL;RESIDENCE;Winthrop;"+residence_Winthrop); outputs1.newLine();
	outputs1.write("ALL;RESIDENCE;Boston;"+residence_Boston); outputs1.newLine();
	outputs1.write("ALL;RESIDENCE;Cambridge;"+residence_Cambridge); outputs1.newLine();
	outputs1.write("ALL;RESIDENCE;SanJose;"+residence_SanJose); outputs1.newLine();
	
	outputs1.write("ALL;RELIGION;Buddhist;"+religion_Buddhist); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Christian;"+religion_Christian); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Hindu;"+religion_Hindu); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Jewish;"+religion_Jewish); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Muslim;"+religion_Muslim); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Sikh;"+religion_Sikh); outputs1.newLine();
	outputs1.write("ALL;RELIGION;TraditionalSpirituality;"+religion_TraditionalSpirituality); outputs1.newLine();
	outputs1.write("ALL;RELIGION;OtherReligion;"+religion_OtherReligion); outputs1.newLine();
	outputs1.write("ALL;RELIGION;Noreligiousaffiliation;"+religion_Noreligiousaffiliation); outputs1.newLine();
	
	outputs1.write("ALL;MARITAL;Single;"+marital_Single); outputs1.newLine();
	outputs1.write("ALL;MARITAL;Married;"+marital_Married); outputs1.newLine();
	outputs1.write("ALL;MARITAL;Divorced;"+marital_Divorced); outputs1.newLine();
	outputs1.write("ALL;MARITAL;Widowed;"+marital_Widowed); outputs1.newLine();
	
	outputs1.write("ALL;PROFESSION;Manager;"+profession_Manager); outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Professional;"+profession_Professional); outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Service;"+profession_Service); outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Salesandoffice;"+profession_Salesandoffice);	 outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Naturalresourcesconstructionandmaintenance;"+profession_Naturalresourcesconstructionandmaintenance);	 outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Productiontransportationandmaterialmoving;"+profession_Productiontransportationandmaterialmoving);	 outputs1.newLine();
	outputs1.write("ALL;PROFESSION;Student;"+profession_Student);	 outputs1.newLine();

	outputs1.write("ALL;POLITICAL;FarLeft;"+political_FarLeft); outputs1.newLine();
	outputs1.write("ALL;POLITICAL;Left;"+political_Left); outputs1.newLine();
	outputs1.write("ALL;POLITICAL;CenterLeft;"+political_CenterLeft); outputs1.newLine();
	outputs1.write("ALL;POLITICAL;Center;"+political_Center);	 outputs1.newLine();
	outputs1.write("ALL;POLITICAL;CenterRight;"+political_CenterRight);	 outputs1.newLine();
	outputs1.write("ALL;POLITICAL;Right;"+political_Right);	 outputs1.newLine();
	outputs1.write("ALL;POLITICAL;FarRight;"+political_FarRight);	 outputs1.newLine();
	
	outputs1.write("ALL;SEXUALITY;Asexual;"+sexual_Asexual); outputs1.newLine();
	outputs1.write("ALL;SEXUALITY;Bisexual;"+sexual_Bisexual); outputs1.newLine();
	outputs1.write("ALL;SEXUALITY;Heterosexual;"+sexual_Heterosexual); outputs1.newLine();
	outputs1.write("ALL;SEXUALITY;Homosexual;"+sexual_Homosexual);	 outputs1.newLine();
	
	outputs1.write("ALL;LIKE1;entertainment;"+like1_entertainment); outputs1.newLine();
	outputs1.write("ALL;LIKE1;music artist;"+like1_musicartist); outputs1.newLine();
	outputs1.write("ALL;LIKE1;drink brand;"+like1_drinkbrand); outputs1.newLine();
	outputs1.write("ALL;LIKE1;tv show;"+like1_tvshow); outputs1.newLine();
	
	outputs1.write("ALL;LIKE2;entertainment;"+like2_entertainment); outputs1.newLine();
	outputs1.write("ALL;LIKE2;music artist;"+like2_musicartist); outputs1.newLine();
	outputs1.write("ALL;LIKE2;tv show;"+like2_tvshow); outputs1.newLine();
	outputs1.write("ALL;LIKE2;drink brand;"+like2_drinkbrand); outputs1.newLine();
	
	outputs1.write("ALL;LIKE3;music artist;"+like3_musicartist); outputs1.newLine();
	outputs1.write("ALL;LIKE3;entertainment;"+like3_entertainment); outputs1.newLine();
	outputs1.write("ALL;LIKE3;drink brand;"+like3_drinkbrand); outputs1.newLine();
	outputs1.write("ALL;LIKE3;soccer club;"+like3_soccerclub); outputs1.newLine();
	
	}
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	
	return 0;
}

public static int writeData2(BufferedWriter outputs1, BufferedWriter output2){

	String str="";
	User nw; Community co;
	int user1=0,com1=0,i=0;
	String age       = "";
	Double weight    = 0.0;
	String gender    = "";
	String residence = "";
	
	String religion = "";
	String maritalstatus = "";
	String profession = "";
	String politicalorientation = "";
	String sexualorientation = "";
	
	
	String like1 = "", like2 = "",like3 = "";
	
	try{
	System.out.println();System.out.println();
	Enumeration en2 = Communities.keys();
	while (en2.hasMoreElements()){    // OUTER LOOP COMMUNITIES
		co = (Community)Communities.get((Integer)en2.nextElement());
		com1 = co.getCommunity();
		int numusersincommunity = co.vertexids.size();
		output2.write(com1+";"+numusersincommunity);
		output2.newLine();output2.flush();
		
		//The following are the frequency counters for the statistics
		int religion_Buddhist = 0, religion_Christian = 0, religion_Jewish = 0,	religion_Muslim = 0, religion_Sikh = 0,
		    religion_TraditionalSpirituality = 0, religion_OtherReligion = 0, religion_Noreligiousaffiliation = 0,
		    religion_Hindu = 0;
		
		int marital_Single = 0,	 marital_Married = 0,	 marital_Divorced = 0,	 marital_Widowed = 0;
		
		int profession_Manager = 0,	profession_Professional = 0, profession_Service = 0, profession_Salesandoffice = 0,
				profession_Naturalresourcesconstructionandmaintenance = 0, profession_Productiontransportationandmaterialmoving = 0,
		    profession_Student = 0;
		
		int political_FarLeft = 0, political_Left = 0, political_CenterLeft = 0, political_Center = 0,
		    political_CenterRight = 0, political_Right = 0, political_FarRight = 0;
		
		int sexual_Asexual = 0,	 sexual_Bisexual = 0,	 sexual_Heterosexual = 0,	 sexual_Homosexual = 0;
		
		int residence_PaloAlto = 0, residence_SantaBarbara = 0, residence_Winthrop = 0, residence_Boston = 0,	
		    residence_Cambridge = 0, residence_SanJose = 0;	
		
		int gender_male = 0, gender_female = 0;
		
		int age_18_25 = 0, age_26_35 = 0, age_36_45 = 0, age_46_55 = 0,	age_56_65 = 0, age_66_75 = 0, age_76_85 = 0;
		
		int like1_entertainment = 0, like1_musicartist = 0,	like1_drinkbrand = 0, like1_tvshow = 0;
		int like2_entertainment = 0, like2_musicartist = 0,	like2_drinkbrand = 0, like2_tvshow = 0;
		int like3_entertainment = 0, like3_musicartist = 0,	like3_drinkbrand = 0, like3_soccerclub = 0;
		// End of frequency counters		
	

		Enumeration en1 = Users.keys();
		while (en1.hasMoreElements()){  // INNER LOOP USERS
			nw = (User)Users.get((Integer)en1.nextElement());
			user1 = nw.getUser();
			age       = nw.getAge();
			gender    = nw.getGender();
			residence = nw.getResidence();
			
			religion = nw.getReligion();
			maritalstatus = nw.getMaritalStatus();
			profession = nw.getProfession();
			politicalorientation = nw.getPoliticalOrientation();
			sexualorientation = nw.getSexualOrientation();
			
			like1 = nw.getLike(1);
			like2 = nw.getLike(2);
			like3 = nw.getLike(3);
			
			//System.out.println("\nPOINT 3");			
			int usercommunity = (Integer)nw.communities.get(0); // changed
			
			if (usercommunity == com1) // IF USER COMMUNITY AND CURRENT COMMUNITY COINCIDE, ACCUMULATE STATS
			{
				//***********Now do the frequency counts*******************
				if (religion.equals("Buddhist")) 		religion_Buddhist = religion_Buddhist + 1;
				else if (religion.equals("Christian")) 	religion_Christian = religion_Christian + 1;
				else if (religion.equals("Jewish"))		religion_Jewish = religion_Jewish + 1;
				else if (religion.equals("Muslim"))		religion_Muslim = religion_Muslim + 1;
				else if (religion.equals("Sikh"))		religion_Sikh = religion_Sikh + 1;
				else if (religion.equals("Hindu"))		religion_Hindu = religion_Hindu + 1;
				else if (religion.equals("Traditional Spirituality")) religion_TraditionalSpirituality = religion_TraditionalSpirituality + 1;
				else if (religion.equals("Other Religion"))			  religion_OtherReligion = religion_OtherReligion + 1;
				else if (religion.equals("No religious affiliation")) religion_Noreligiousaffiliation = religion_Noreligiousaffiliation + 1;

				if (maritalstatus.equals("Single"))			marital_Single = marital_Single + 1;
				else if (maritalstatus.equals("Married"))	marital_Married = marital_Married + 1;
				else if (maritalstatus.equals("Divorced"))	marital_Divorced = marital_Divorced + 1;
				else if (maritalstatus.equals("Widowed"))	marital_Widowed = marital_Widowed + 1;		
				
				if (profession.equals("Manager")) profession_Manager = profession_Manager + 1;
				else if (profession.equals("Professional")) profession_Professional = profession_Professional + 1;
				else if (profession.equals("Service")) profession_Service = profession_Service + 1;
				else if (profession.equals("Sales and office")) profession_Salesandoffice = profession_Salesandoffice + 1;
				else if (profession.equals("Natural resources construction and maintenance"))
					profession_Naturalresourcesconstructionandmaintenance = profession_Naturalresourcesconstructionandmaintenance + 1;
				else if (profession.equals("Production transportation and material moving"))
					profession_Productiontransportationandmaterialmoving = profession_Productiontransportationandmaterialmoving + 1;
				else if (profession.equals("Student")) profession_Student = profession_Student + 1;		

				if (politicalorientation.equals("Far Left"))			political_FarLeft = political_FarLeft + 1;
				else if (politicalorientation.equals("Left"))			political_Left = political_Left + 1;
				else if (politicalorientation.equals("Center Left"))	political_CenterLeft = political_CenterLeft + 1;
				else if (politicalorientation.equals("Center"))			political_Center = political_Center + 1;
				else if (politicalorientation.equals("Center Right"))	political_CenterRight = political_CenterRight + 1;
				else if (politicalorientation.equals("Right"))			political_Right = political_Right + 1;
				else if (politicalorientation.equals("Far Right"))		political_FarRight = political_FarRight + 1;

				if (sexualorientation.equals("Asexual"))			sexual_Asexual = sexual_Asexual + 1;
				else if (sexualorientation.equals("Bisexual"))		sexual_Bisexual = sexual_Bisexual + 1;
				else if (sexualorientation.equals("Heterosexual"))	sexual_Heterosexual = sexual_Heterosexual + 1;
				else if (sexualorientation.equals("Homosexual"))	sexual_Homosexual = sexual_Homosexual + 1;

				if (residence.equals("Palo Alto"))			    residence_PaloAlto = residence_PaloAlto + 1;
				else if (residence.equals("Santa Barbara"))		residence_SantaBarbara = residence_SantaBarbara + 1;
				else if (residence.equals("Winthrop"))			residence_Winthrop = residence_Winthrop + 1;
				else if (residence.equals("Boston"))			residence_Boston = residence_Boston + 1;
				else if (residence.equals("Cambridge"))			residence_Cambridge = residence_Cambridge + 1;
				else if (residence.equals("San Jose"))			residence_SanJose = residence_SanJose + 1;
				
				if (gender.equals("male"))			gender_male = gender_male + 1;
				else if (gender.equals("female"))	gender_female = gender_female + 1;	
				
				if (age.equals("18-25"))			age_18_25 = age_18_25 + 1;
				else if (age.equals("26-35"))		age_26_35 = age_26_35 + 1;
				else if (age.equals("36-45"))		age_36_45 = age_36_45 + 1;
				else if (age.equals("46-55"))		age_46_55 = age_46_55 + 1;
				else if (age.equals("56-65"))		age_56_65 = age_56_65 + 1;
				else if (age.equals("66-75"))		age_66_75 = age_66_75 + 1;
				else if (age.equals("76-85"))		age_76_85 = age_76_85 + 1;	
				
				if (like1.equals("entertainment")) like1_entertainment = like1_entertainment + 1;
				else if (like1.equals("music artist")) like1_musicartist = like1_musicartist + 1;
				else if (like1.equals("drink brand")) like1_drinkbrand = like1_drinkbrand + 1;
				else if (like1.equals("tv show")) like1_tvshow = like1_tvshow + 1;;

				if (like2.equals("entertainment")) like2_entertainment = like2_entertainment + 1;
				else if (like2.equals("music artist")) like2_musicartist = like2_musicartist + 1;
				else if (like2.equals("tv show")) like2_tvshow = like2_tvshow + 1;
				else if (like2.equals("drink brand")) like2_drinkbrand = like2_drinkbrand + 1;;

				if (like3.equals("music artist")) like3_musicartist = like3_musicartist + 1;
				else if (like3.equals("entertainment")) like3_entertainment = like3_entertainment + 1;
				else if (like3.equals("drink brand")) like3_drinkbrand = like3_drinkbrand + 1;
				else if (like3.equals("soccer club")) like3_soccerclub = like3_soccerclub + 1;
				
			} // if user comm and current comm coincide
			
			//System.out.println("\nPOINT 5");			
			

		} //ewhile	each user
		//System.out.println("\nPOINT 6********************");		
		// WRITE OUT STATS IN FILE FOR CURRENT COMMUNITY
		// write out the frequency stats in the stats1 file for community
		outputs1.write(com1+";AGE;18-25;"+age_18_25); outputs1.newLine();
		outputs1.write(com1+";AGE;26-35;"+age_26_35); outputs1.newLine();
		outputs1.write(com1+";AGE;36-45;"+age_36_45); outputs1.newLine();
		outputs1.write(com1+";AGE;46-55;"+age_46_55); outputs1.newLine();
		outputs1.write(com1+";AGE;56-65;"+age_56_65); outputs1.newLine();
		outputs1.write(com1+";AGE;66-75;"+age_66_75); outputs1.newLine();
		outputs1.write(com1+";AGE;76-85;"+age_76_85); outputs1.newLine();
		
		outputs1.write(com1+";GENDER;Male;"+gender_male); outputs1.newLine();
		outputs1.write(com1+";GENDER;Female;"+gender_female); outputs1.newLine();
		
		outputs1.write(com1+";RESIDENCE;PaloAlto;"+residence_PaloAlto); outputs1.newLine();
		outputs1.write(com1+";RESIDENCE;SantaBarbara;"+residence_SantaBarbara); outputs1.newLine();
		outputs1.write(com1+";RESIDENCE;Winthrop;"+residence_Winthrop); outputs1.newLine();
		outputs1.write(com1+";RESIDENCE;Boston;"+residence_Boston); outputs1.newLine();
		outputs1.write(com1+";RESIDENCE;Cambridge;"+residence_Cambridge); outputs1.newLine();
		outputs1.write(com1+";RESIDENCE;SanJose;"+residence_SanJose); outputs1.newLine();

		outputs1.write(com1+";RELIGION;Buddhist;"+religion_Buddhist); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Christian;"+religion_Christian); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Hindu;"+religion_Hindu); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Jewish;"+religion_Jewish); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Muslim;"+religion_Muslim); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Sikh;"+religion_Sikh); outputs1.newLine();
		outputs1.write(com1+";RELIGION;TraditionalSpirituality;"+religion_TraditionalSpirituality); outputs1.newLine();
		outputs1.write(com1+";RELIGION;OtherReligion;"+religion_OtherReligion); outputs1.newLine();
		outputs1.write(com1+";RELIGION;Noreligiousaffiliation;"+religion_Noreligiousaffiliation); outputs1.newLine();
		
		outputs1.write(com1+";MARITAL;Single;"+marital_Single); outputs1.newLine();
		outputs1.write(com1+";MARITAL;Married;"+marital_Married); outputs1.newLine();
		outputs1.write(com1+";MARITAL;Divorced;"+marital_Divorced); outputs1.newLine();
		outputs1.write(com1+";MARITAL;Widowed;"+marital_Widowed); outputs1.newLine();
		
		outputs1.write(com1+";PROFESSION;Manager;"+profession_Manager); outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Professional;"+profession_Professional); outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Service;"+profession_Service); outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Salesandoffice;"+profession_Salesandoffice);	 outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Naturalresourcesconstructionandmaintenance;"+profession_Naturalresourcesconstructionandmaintenance);	 outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Productiontransportationandmaterialmoving;"+profession_Productiontransportationandmaterialmoving);	 outputs1.newLine();
		outputs1.write(com1+";PROFESSION;Student;"+profession_Student);	 outputs1.newLine();


		outputs1.write(com1+";POLITICAL;FarLeft;"+political_FarLeft); outputs1.newLine();
		outputs1.write(com1+";POLITICAL;Left;"+political_Left); outputs1.newLine();
		outputs1.write(com1+";POLITICAL;CenterLeft;"+political_CenterLeft); outputs1.newLine();
		outputs1.write(com1+";POLITICAL;Center;"+political_Center);	 outputs1.newLine();
		outputs1.write(com1+";POLITICAL;CenterRight;"+political_CenterRight);	 outputs1.newLine();
		outputs1.write(com1+";POLITICAL;Right;"+political_Right);	 outputs1.newLine();
		outputs1.write(com1+";POLITICAL;FarRight;"+political_FarRight);	 outputs1.newLine();
		
		outputs1.write(com1+";SEXUALITY;Asexual;"+sexual_Asexual); outputs1.newLine();
		outputs1.write(com1+";SEXUALITY;Bisexual;"+sexual_Bisexual); outputs1.newLine();
		outputs1.write(com1+";SEXUALITY;Heterosexual;"+sexual_Heterosexual); outputs1.newLine();
		outputs1.write(com1+";SEXUALITY;Homosexual;"+sexual_Homosexual);	 outputs1.newLine();
		
		outputs1.write(com1+";LIKE1;entertainment;"+like1_entertainment); outputs1.newLine();
		outputs1.write(com1+";LIKE1;music artist;"+like1_musicartist); outputs1.newLine();
		outputs1.write(com1+";LIKE1;drink brand;"+like1_drinkbrand); outputs1.newLine();
		outputs1.write(com1+";LIKE1;tv show;"+like1_tvshow); outputs1.newLine();
		
		outputs1.write(com1+";LIKE2;entertainment;"+like2_entertainment); outputs1.newLine();
		outputs1.write(com1+";LIKE2;music artist;"+like2_musicartist); outputs1.newLine();
		outputs1.write(com1+";LIKE2;tv show;"+like2_tvshow); outputs1.newLine();
		outputs1.write(com1+";LIKE2;drink brand;"+like2_drinkbrand); outputs1.newLine();
		
		outputs1.write(com1+";LIKE3;music artist;"+like3_musicartist); outputs1.newLine();
		outputs1.write(com1+";LIKE3;entertainment;"+like3_entertainment); outputs1.newLine();
		outputs1.write(com1+";LIKE3;drink brand;"+like3_drinkbrand); outputs1.newLine();
		outputs1.write(com1+";LIKE3;soccer club;"+like3_soccerclub); outputs1.newLine();
		outputs1.flush();
		
		//System.out.println("\nPOINT 7********************");		
		
	} //ewhile
	} //etry
	catch(Exception e){
		e.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}

/********************** Get Max Degree ***********************/
public static int getMaxDegree(){
	Enumeration en1 = Users.keys();
	
	String str=""; int degree_user2=0;
	int maxDegree=0, degree=0;
	User nw,nw2;
	int user1=0,user2=0,i=0;
	
	while (en1.hasMoreElements()){
		nw = (User)Users.get((Integer)en1.nextElement());
		user1 = nw.getUser();
		degree = nw.friends.size();
		if (degree > maxDegree)
			maxDegree = degree;
	} //ewhile
	return maxDegree;
}

//******************************************************************** */
//Funci�n que lee los datos de casos del fichero "nomFixter" y los     */
//mete en la estructura 'datosD'									   */
//******************************************************************** */
public static int leer_datos_de_casos(String nomFixterIn1, String nomFixterIn2, 
		String nomFixterOut, String nomFixterOutg, String nomFixterOuts1, String nomFixterOut1, String nomFixterOut2, 
		int user2, int num_values)
{
String ubicFixter = "";
String nomUbicFixterIn1 = ubicFixter+nomFixterIn1;
String nomUbicFixterIn2 = ubicFixter+nomFixterIn2;
String nomUbicFixterOut = ubicFixter+nomFixterOut;
String nomUbicFixterOutg = ubicFixter+nomFixterOutg;
String nomUbicFixterOuts1 = ubicFixter+nomFixterOuts1;
String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;
String nomUbicFixterOut2 = ubicFixter+nomFixterOut2;
String delim = ";";
String isNull = "";
String temp;
Object[] data=null;
int i, j, k;
//num_values es el n�mero de variables, num_cases es el n�mero de casos
data  = new Object[4+1];
String valVar;

String[] s1; String s2;

int len=0, kount=0;
char oldChar='.';
char newChar=',';

long ii=0;
int u1; int u2; double t1 ; double t2;
String delimiter=";";

Integer oneUser=0;
Integer userActual=0;

int maxid=0;

	try{
		FileReader inputFile1 = new FileReader(nomUbicFixterIn1);
		BufferedReader input1= new BufferedReader(inputFile1);
		
		FileReader inputFile2 = new FileReader(nomUbicFixterIn2);
		BufferedReader input2= new BufferedReader(inputFile2);

		dataFile df1= new dataFile();
		dataFile df2= new dataFile();
		dataFile df3= new dataFile();
		dataFile df4= new dataFile();
		
		FileWriter outputFile = new FileWriter(nomUbicFixterOut);
		BufferedWriter output = new BufferedWriter(outputFile);
		
		FileWriter outputFileg = new FileWriter(nomUbicFixterOutg);
		BufferedWriter outputg = new BufferedWriter(outputFileg);
		
		FileWriter outputFiles1 = new FileWriter(nomUbicFixterOuts1);
		BufferedWriter outputs1 = new BufferedWriter(outputFiles1);
		
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1 = new BufferedWriter(outputFile1);
		
		FileWriter outputFile2 = new FileWriter(nomUbicFixterOut2);
		BufferedWriter output2 = new BufferedWriter(outputFile2);
		
		if (inputFile1 == null)
		{
			System.out.println("\nCant open the input file!\n");
			return -1;
		}

		int linecount=0, validlines=0;
		//for (i=0;i<num_cases;i++)
		i=0;
		int user1_old, user3_old, user1, user3, user4, ts1, ts2, alreadythere;
		int num_friends_user1=0, amigos_en_comun=0;
		String stemp;
		user1_old = user1 = 1;
		int weight = 0;
		
		//System.out.println("\n****GET ALL neighbors FOR ALL USERS*****\n");
		
	    String oneToken;
		temp= input1.readLine();
		int kk=0;
		int lines=0;
		int linesread=0;
		num_friends_user1=0;
		if (temp==null) // end of the file....
			;
		else
		{
			while (temp !=null && linesread<1500000) // for all edges
			{
				

			// Process linea sencer amb RegEx per a extreure els variables 
			// d'un cas.  
			//df1.processarLinea(temp, delim, data, 2); 
			
			StringTokenizer tokens = new StringTokenizer(temp);

			
			if (tokens.hasMoreTokens())
				stemp = tokens.nextToken(delim);
			else
				stemp = null;
			
			user1_old=user1;
			if (stemp != null)
				user1 = Integer.parseInt(stemp);
				
			//stemp = (String)data[0];

			//user1 = Integer.parseInt(stemp);

			
			while (stemp !=null && linesread<1500000) // for this line read user1 and user2
			{
				++linesread;
				//stemp = (String)data[1];
				//user2 = Integer.parseInt(stemp);
				
				if (tokens.hasMoreTokens())
					stemp = tokens.nextToken(delim);
				else
					stemp = null;
				
				if (stemp != null)
				{
					user2 = Integer.parseInt(stemp);
				
				
					if (user1 > maxid)
						maxid = user1;
					if (user2 > maxid)
						maxid = user2;
					++kk;


					if (kk>1000)
					{
						kk=0;
						System.out.println("lines: "+lines+" linesread: "+linesread);
						System.out.flush();
					}
				
					if ((Users.containsKey(user1)) == false){
						User nw = new User(user1);
						//nw.afegirFriend(user1); // first time, him/herself
						nw.afegirFriend(user2); // his/her friend
						nw.loadWeight(0.0);
						Users.put(user1, nw);
						++lines;
					} else {
						User nw = (User)Users.get(user1);
						// check this friend is not already there
						alreadythere = 0;
						if (nw.friends.contains((Integer)user2))
							alreadythere = 1;
						//for (j = 0; j < nw.friends.size(); j++)
						//if (nw.friends.get(j) == user2)
						//alreadythere = 1;
						if (alreadythere != 1) // if not there, insert it
						{
							nw.afegirFriend(user2);
							nw.loadWeight(0.0);
							Users.remove(nw.getUser());
							Users.put(nw.getUser(),nw);
						}
					} //eelse
					// al reves
					if ((Users.containsKey(user2)) == false){
						User nw = new User(user2);
						//nw.afegirFriend(user2); // first time, him/herself
						nw.afegirFriend(user1); // his/her friend
						nw.loadWeight(0.0);
						Users.put(user2, nw);
						++lines;
					} else {
						User nw = (User)Users.get(user2);
						// check this friend is not already there
						alreadythere = 0;
						if (nw.friends.contains((Integer)user1))
							alreadythere = 1;
						//for (j = 0; j < nw.friends.size(); j++)
						//if (nw.friends.get(j) == user1)
						//alreadythere = 1;
						if (alreadythere != 1) // if not there, insert it
						{
							nw.afegirFriend(user1);
							nw.loadWeight(0.0);
							Users.remove(nw.getUser());
							Users.put(nw.getUser(),nw);
						}
					} // eelse
					// fin al reves
				
					//System.out.print(" friend "+user1+" = "+friends_user1[num_friends_user1]+"\n");
					++num_friends_user1;
					
					temp= input1.readLine();
					
					if(temp!= null)
						tokens = new StringTokenizer(temp);
					
					if (tokens.hasMoreTokens())
						stemp = tokens.nextToken(delim);
					else
						stemp = null;
					
					user1_old=user1;
					if (stemp != null)
						user1 = Integer.parseInt(stemp);

					
					//df1.processarLinea(temp, delim, data, 2); 
					//stemp = (String)data[0];
					//user1_old=user1;
					//user1 = Integer.parseInt(stemp);
				} //eelse
			} // ewhile. for this line
			} // ewhile for all lines
		} //eelse
	
			System.out.println("\n****Number of users inserted into hashtable :"+lines);
			System.out.println("\n****maxid :"+maxid);
			
			//writeCalcs(output,lines);
			//seeCalcs();
			//if (1==1)
				//return 0;
			
			
			int unchosenlimit=0, max_degree=0;
			
			int numnodes = Users.size();

	
			unchosenlimit=0;
			System.out.println("\n****read community file****");
			/***************************************************************************************************/
			/* Now read in the community file with the user id and community id                                */
			/* for each node and update the user and community structures with these values.                                */
			/***************************************************************************************************/
			float auth=0.0f;
			int mod=0, degree=0;
			int maxdegree=0;
			temp= input2.readLine();
			if (temp==null) // end of the file....
				;
			else
			{
				// Process linea sencer amb RegEx per a extreure els variables 
				// d'un cas.  
				df2.processarLinea(temp, delim, data, 2); 
					
				stemp = (String)data[0];
				user1_old=user1;
				user1 = Integer.parseInt(stemp);
				kk=0; num_friends_user1=0; //maxid=0;
				
				while ( kk>=0 ) // for all users
				{
					stemp = (String)data[1];
					mod = Integer.parseInt(stemp);
					if (Users.get(user1)!=null)
					{
						// Write community into Community class data structure with corresponding node
						if ((Communities.containsKey(mod)) == false){
							Community co = new Community(mod);
							co.loadvertexids((Integer)user1);
							co.loadvertexidvalues((Double)0.0);
							Communities.put(mod, co);
						}
						else // Aggregate node to existing community
						{
						   Community co = (Community)Communities.get(mod);
						   co.loadvertexids((Integer)user1);
						   co.loadvertexidvalues((Double)0.0);
						   Communities.remove(co.getCommunity());
						   Communities.put(co.getCommunity(),co);
						}
					}
			
	
					//System.out.println("node: "+user1+" mod: "+stemp);
					
					if ((Users.containsKey(user1)) != false){
						User nw = (User)Users.get(user1);
						
						++kk;
					
						degree = nw.friends.size();
						if (degree > max_degree)
							max_degree = degree;
				
						nw.loadCommunity(mod);

						Users.remove(nw.getUser());
						Users.put(nw.getUser(),nw);
						//System.out.println("node: "+user1+" brce: "+brce+ "mod: "+mod);
					}	
					
					temp= input2.readLine();
					if (temp==null) // end of the file....
						kk=-10;
					else
					{
						df2.processarLinea(temp, delim, data, 2); 
						stemp = (String)data[0];
						user1_old=user1;
						user1 = Integer.parseInt(stemp);
					}
					//} // eif user1 is there
				} // ewhile. ok, got all friends for all users...
			} // eif not end of file at start
			/***** OK, now got communities and community for each node********/	
			
			System.out.println("users: "+Users.size()+" communities: "+Communities.size());
		
			//writeData2(output1, output2);
			
			//if (1==1) return 0.0;
			
			/*******************************************************************************************************/			

			int i2=0,j2=0;
			double resd=0;
			
			double okd = doCalcs.doCalcsq(Users); // CALCULATE stats
			System.out.println("OK, done the stats");

			//writeData2(output1, output2);
			
			//if (1==1) return 0;
			
			// 110 seeds for 1K synth file, 5K seeds for amazon, 12k seeds for youtube and livejournal
			int seedsize = (int)(configuration.getSeedPercentage()*Double.valueOf(configuration.getNumberOfNodes())/100.0);
			
			System.out.println("\nNUMBER OF SEEDS TO BE ASSIGNED:"+seedsize);
			
			//System.out.println("seedsize: "+seedsize+" sumalldegrees: "+sumalldegrees+" numnodes: "+numnodes+" maxid: "+maxid);
			
			//double kanon = 2.0; //
			
			// STEP 1. ASSIGN COMMUNITIES AND MEDOIDS TO COMMUNITIES
			FindMedoid fm = new FindMedoid();
			
			Enumeration enc = RV.Communities.keys();
			Community co; int com=0;
			while ( enc.hasMoreElements() )
			{
				co = (Community)RV.Communities.get((Integer)enc.nextElement());
				com = co.getCommunity();
				fm.FindMedoid(co, com);
			}
			
			
			//writeData2();
			
			//if (1==1) return 0.0;
			
			//STEP 2. ASSIGN THE SEEDS
			System.out.println("\n****STEP 1. ASSIGN THE SEEDS*****\n");
			AssignSeeds2 as2 = new AssignSeeds2();
			//seedsize = 30; 
			as2.AssignSeeds2(Users, maxid, seedsize);
			System.out.println("\nNUMBER OF SEEDS2 ASSIGNED:"+RV.seeds.size());
			
			//if (1==1) return 0.0;
			
	  		enc = RV.Communities.keys();
    		while(enc.hasMoreElements())
    		{
        		co = (Community)RV.Communities.get((Integer)enc.nextElement());
        		int co1 = co.getCommunity();
        		co.numberofseedassigned = 0;
    			RV.Communities.remove(co.getCommunity());
    			RV.Communities.put(co.getCommunity(),co);
    		}
    		
			//if (1==1) return 0.0;
			
			//assign the seeds to the communities
			for(i=0;i<RV.seeds.size();i++) // for each seed
			{
				int sid = (Integer)RV.seeds.get(i);
				User nwc = (User)RV.Users.get(sid);
				//int comseed = nwc.getMOD();
				for(j=0;j<nwc.communities.size();j++) // for each community to which this seed belongs
				{
					int comseed = (Integer) nwc.communities.elementAt((Integer)j);
					Community cwc = (Community)RV.Communities.get(comseed);
					int comcom = cwc.getCommunity();
					//System.out.println("comseed: "+comseed+" comcom: "+comcom+" sid: "+sid);
					if (comseed == comcom)
					{
						//System.out.println("hola..........");
						cwc.seedsc.add(sid);
						++cwc.numberofseedassigned;
						Communities.remove(cwc.getCommunity());
						Communities.put(cwc.getCommunity(),cwc);
					}
				} // efor 
			} // efor
			
    		
			//if (1==1) return 0;	
			
			//STEP 2. ASSIGN THE DATA
			System.out.println("\n****STEP 2. ASSIGN THE DATA*****\n");
			int totalAssigned = rAssignDataGeneralizedSensAttr4.rAssignDataGeneralizedSensAttr4(numnodes, configuration);

			
			// STEP 3. WRITE OUT THE DATA INTO A FILE, ONE ROW PER NODE (1) and ONE ROW PER LINK WITH CORRESPONDING EDGE WEIGHT(2).
			System.out.println("\n****STEP 3. WRITE OUT THE DATA INTO FOUR FILES*****\n");
			writeData(output, outputg, output1);
			writeData2(output1, output2);
	
			System.out.println("\n****END OF PROCESSING*****\n");
			inputFile1.close();
			outputFile.close();
			output.close();outputg.close();
			output1.close();output2.close();
			input1.close();
			input2.close();

			return totalAssigned;

		//} //end of if for first line of input file read

		
	}
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nHa ocurrido un problema al llegir el fixter");
	}
	return -1;
} //fin de funcion 'leer_datos_de_casos'

} // end of public class RVp
