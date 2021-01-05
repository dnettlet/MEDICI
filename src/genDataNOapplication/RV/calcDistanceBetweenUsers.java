// Copyright (C) 2018  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.RV;

import java.lang.Math;

import genDataNOapplication.User.User;

public class calcDistanceBetweenUsers{
	

	public static double dlikes(String lk1, String lk2, String lk3, String lk21, String lk22, String lk23)
	{
	int i=0, j=0;

	String likes[] = new String[6];

	double plikesd[][] = new double[5][5];

	plikesd[0][0] = 0.00;  // entertainment - entertainment
	plikesd[1][0] = 0.25;  // entertainment - music artist
	plikesd[2][0] = 0.25;  // entertainment - tv show
	plikesd[3][0] = 0.25;  // entertainment - soccer club
	plikesd[4][0] = 0.50;  // entertainment - drink brand

	plikesd[0][1] = 0.25;  // music artist - entertainment
	plikesd[1][1] = 0.00;  // music artist - music artist
	plikesd[2][1] = 0.25;  // music artist - tv show
	plikesd[3][1] = 0.25;  // music artist - soccer club
	plikesd[4][1] = 0.50;  // music artist - drink brand

	plikesd[0][2] = 0.25;  // tv show - entertainment
	plikesd[1][2] = 0.25;  // tv show - music artist
	plikesd[2][2] = 0.00;  // tv show - tv show
	plikesd[3][2] = 0.25;  // tv show - soccer club
	plikesd[4][2] = 0.50;  // tv show - drink brand

	plikesd[0][3] = 0.25;  // soccer club - entertainment
	plikesd[1][3] = 0.25;  // soccer club - music artist
	plikesd[2][3] = 0.00;  // soccer club - tv show
	plikesd[3][3] = 0.00;  // soccer club - soccer club
	plikesd[4][3] = 0.50;  // soccer club - drink brand

	plikesd[0][4] = 0.50;  // drink brand - entertainment
	plikesd[1][4] = 0.50;  // drink brand - music artist
	plikesd[2][4] = 0.50;  // drink brand - tv show
	plikesd[3][4] = 0.25;  // drink brand - soccer club
	plikesd[4][4] = 0.00;  // drink brand - drink brand


	likes[0] = "entertainment"		; //type A
	likes[1] = "music artist"		; //type A
	likes[2] = "tv show"			; //type A
	likes[3] = "soccer club"		; //type C
	likes[4] = "drink brand"		; //type B

	String likesA[]    = new String[3]; 
	String likesB[]    = new String[3]; 

	likesA[0] = lk1;  likesA[1] = lk2;  likesA[2] = lk3;
	likesB[0] = lk21; likesB[1] = lk22; likesB[2] = lk23;

	int pos1=0, pos2=0, k=0, ii=0, jj=0;

	double distlike1 = 0.0, distlike2 = 0.0, distlike3 = 0.0, distlike = 0.0;

	for(i=0;i<3;i++)
		for(j=0;j<3;j++)
		{
			for(k=0;k<5;k++)
			{
				if (likes[k].equals(likesA[i]))
				{
					pos1=k;
				}
				if (likes[k].equals(likesB[j]))
				{
					pos2=k;
				}
			}
			distlike = distlike + plikesd[pos1][pos2];
			//System.out.println("ASSIGN");
			//System.out.println("distance between "+likesA[i]+" and "+likesB[j]+" is "+plikesd[pos1][pos2]+" with pos1= "+pos1+" and pos2= "+pos2+" distlike="+distlike);
		
	}

	//System.out.println("DLIKES1. lk1:"+ lk1+ " lk2:"+ lk2+ " lk3:"+ lk3+ " lk21: "+ lk21+ " lk22:"+ lk22+ " lk23:"+ lk23);


	distlike = distlike / 9.0;

	//System.out.println("DLIKES3. distlike: "+distlike);


	return(distlike);
	}

	
public static double compareRefNodes(String agev1, String residencev1, String genderv1, 
		                             String agev2, String residencev2, String genderv2)
{
	double distref = 0.0;
	int i=0, j=0;
	
	String residence[] = new String[6]; 
	String county[]    = new String[6]; 
	String state[]     = new String[6]; 
	String division[]  = new String[6]; 
	String region[]    = new String[6]; 
	
	String gender[]    = new String[2]; 
	String age[]       = new String[7]; 
	
	String weight[]    = new String[3]; 
	Double weightav[]  = new Double[3]; 
	
	String res="";
	String gen="";
	String ag="";
	String we="";
	
	int genix = 0, resix = 0, agix = 0, weix = 0;
	
	residence[0] = "Palo Alto";
	residence[1] = "Santa Barbara";
	residence[2] = "Winthrop";
	residence[3] = "Boston";
	residence[4] = "Cambridge";
	residence[5] = "San Jose";

	county[0] = "Santa Clara";
	county[1] = "Santa Barbara";
	county[2] = "Suffolk";
	county[3] = "Suffolk";
	county[4] = "Middlesex";
	county[5] = "Santa Clara";

	state[0] = "California";
	state[1] = "California";
	state[2] = "Massachusetts";
	state[3] = "Massachusetts";
	state[4] = "Massachusetts";
	state[5] = "California";

	division[0] = "Pacific";
	division[1] = "Pacific";
	division[2] = "New England";
	division[3] = "New England";
	division[4] = "New England";
	division[5] = "Pacific";

	region[0] = "West";
	region[1] = "West";
	region[2] = "Northeast";
	region[3] = "Northeast";
	region[4] = "Northeast";
	region[5] = "West";
	
	gender[0] = "male";
	gender[1] = "female";
	
	age[0]  = "18-25";
	age[1]  = "26-35";
	age[2]  = "36-45";
	age[3]  = "46-55";
	age[4]  = "56-65";
	age[5]  = "66-75";
	age[6]  = "76-85";

	
	int pos1=0, pos2=0;
	
	for(i=0; i<age.length; i++)
	{
		if (agev1.equals(age[i]) == true ) // found it
		{
			pos1 = i;
		}
		if (agev2.equals(age[i]) == true ) // found it
		{
			pos2 = i;
		}
	}
	double distage = Math.abs(pos1 - pos2) / 6.0;
	
	
	double distgender = 0.0;
	if (genderv1.equals(genderv2) == true ) // just check for equality or not
		distgender=1.0;
	else
		distgender=0.0;
	
	
	pos1=0; pos2=0;
	double distresidence = 0.0;
	
	for(i=0; i<residence.length; i++)
	{
		if (residencev1.equals(residence[i]) == true ) // found it
		{
			pos1 = i;
		}
		if (residencev2.equals(residence[i]) == true ) // found it
		{
			pos2 = i;
		}
	}
    if (residence[pos1].equals(residence[pos2]))
    	distresidence = 0.0;
    else if (county[pos1].equals(county[pos2]))
    	distresidence = 0.25;
    else if (state[pos1].equals(state[pos2]))
    	distresidence = 0.50;
    else if (division[pos1].equals(division[pos2]))
    	distresidence = 0.75;
    else if (region[pos1].equals(region[pos2]))
    	distresidence = 0.90;
    else
    	distresidence = 1.00;
    
    
    distref = (distage + distgender + distresidence) / 3.0;
	
	
return(distref);
}

public static double compareRefNodes2(String religionv1, String maritalv1, String professionv1, String politicalv1, String sexualv1,
		                              String religionv2, String maritalv2, String professionv2, String politicalv2, String sexualv2)
{
double distref = 0.0;
int i=0, j=0;


String religion[]    	 = new String[9]; 
String marital[]       	 = new String[4]; 
String profession[]    = new String[10]; 
String political[]       = new String[7]; 
String sexual[]    		 = new String[4]; 

String rel="";
String mar="";
String pro="";
String pol="";
String sex="";

int relix = 0, marix = 0, proix = 0, polix = 0, sexix=0;

religion[0] = "Buddhist";  religion[1] = "Christian"; 
religion[2] = "";     religion[3] = "Jewish";    
religion[4] = "Muslim";    religion[5] = "Sikh";      
religion[6] = "Traditional Spirituality";  religion[7] = "Other Religions"; religion[8] = "No religious affiliation";

marital[0] = "Single";   marital[1] = "Married";  marital[2] = "Divorced"; marital[3] = "Widowed";

profession[0] = "Manager";     		profession[1] = "Professional";  
profession[2] = "Service";          profession[3] = "Sales and office"; 
profession[4] = "Natural resources construction and maintenance"; 
profession[5] = "Production transportation and material moving"; 
profession[6] = "Student"; 

political[0] = "Far Left";    political[1] = "Left";         political[2] = "Center Left";  
political[3] = "Center";      political[4] = "Center Right"; political[5] = "Right";        
political[6] = "Far Right";

sexual[0] = "Asexual";      sexual[1] = "Bisexual";     sexual[2] = "Heterosexual"; sexual[3] = "Homosexual";

int pos1=0, pos2=0;

for(i=0; i<political.length; i++)
{
if (politicalv1.equals(political[i]) == true ) // found it
{
pos1 = i;
}
if (politicalv2.equals(political[i]) == true ) // found it
{
pos2 = i;
}
}
double distpolitical = Math.abs(pos1 - pos2) / 6.0;

double distsexual = 0.0;

if (sexualv1.equals(sexualv2) == true ) // found it
	distsexual = 0.0;
else if (sexualv1.equals(sexualv2) == false &&  sexualv1.equals(sexual[2]) == false &&
         sexualv2.equals(sexual[2]) == false) 
	distsexual = 0.5;
else
	distsexual = 1.0;


double distmarital = 0.0;

if (maritalv1.equals(maritalv2) == true ) // found it
	distmarital = 0.0;
else if (maritalv1.equals(maritalv2) == false &&  maritalv1.equals(marital[0]) == false &&
maritalv2.equals(marital[0]) == false) 
	distmarital = 0.5;
else
	distmarital = 1.0;


double distreligion = 0.0;

if (religionv1.equals(religionv2) == true ) // found it
	distreligion = 0.0;

// Buddhist, Hindu and Sikh are considered quite similar
else if ( (religionv1.equals(religion[0]) == true || religionv1.equals(religion[2]) == true || religionv1.equals(religion[5]) == true)
&&  (religionv2.equals(religion[0]) == true || religionv2.equals(religion[2]) == true || religionv2.equals(religion[5]) == true)
)
	distreligion = 0.5;
// Christian and Jewish are considered quite similar
else if ( (religionv1.equals(religion[1]) == true || religionv1.equals(religion[3]) == true )
&&  (religionv2.equals(religion[1]) == true || religionv2.equals(religion[3]) == true )
)
	distreligion = 0.5;
else
	distreligion = 1.0;



double distprof = 0.0;

if (professionv1.equals(professionv2) == true ) // found it
	distprof = 0.0;

// Manager and professional are considered quite similar
else if ( (professionv1.equals(profession[0]) == true || professionv1.equals(profession[1]) == true)
&&  (professionv2.equals(profession[0]) == true || professionv2.equals(profession[1]) == true)
)
	distprof = 0.5;
//Service and sales are considered quite similar
else if ( (professionv1.equals(profession[2]) == true || professionv1.equals(profession[3]) == true)
&&  (professionv2.equals(profession[2]) == true || professionv2.equals(profession[3]) == true)
)
	distprof = 0.5;
//natural and prod are considered quite similar
else if ( (professionv1.equals(profession[4]) == true || professionv1.equals(profession[5]) == true)
&&  (professionv2.equals(profession[4]) == true || professionv2.equals(profession[5]) == true)
)
	distprof = 0.5;
else
	distprof = 1.0;


distref = (distpolitical + distsexual + distmarital + distreligion + distprof) / 5.0;


return(distref);
}

public static double calcDistanceBetweenUsers(int user1, int user2)
{
	User nw, nw2;
	int userf1=0, userf2=0, i=0, j=0;
	double distref = 0.0, distref2 = 0.0, distlikesref = 0.0;
	
	String lk1="";	String lk2="";	String lk3="";
	String lk21="";	String lk22="";	String lk23="";
	
	String lkf1="";	String lkf2="";	String lkf3="";
	String lkf21="";	String lkf22="";	String lkf23="";

	try{
		nw  = (User)RV.Users.get(user1);
		nw2 = (User)RV.Users.get(user2);
			
		String agev1       	= nw.getAge();
		String residencev1 	= nw.getResidence();
		String genderv1    	= nw.getGender();
		String religionv1 	= nw.getReligion();
		String maritalv1 	= nw.getMaritalStatus();
		String professionv1 = nw.getProfession();
		String politicalv1 	= nw.getPoliticalOrientation();
		String sexualv1 	= nw.getSexualOrientation();
		lk1 = nw.getLike(1); 		lk2 = nw.getLike(2); 		lk3 = nw.getLike(3);
				
		if (agev1 != null)
		{		
				String agev2       = nw2.getAge();
				String residencev2 = nw2.getResidence();
				String genderv2    = nw2.getGender();
				String religionv2 	= nw2.getReligion();
				String maritalv2 	= nw2.getMaritalStatus();
				String professionv2 = nw2.getProfession();
				String politicalv2 	= nw2.getPoliticalOrientation();
				String sexualv2 	= nw2.getSexualOrientation();
				lk21 = nw2.getLike(1); 		lk22 = nw2.getLike(2); 		lk23 = nw2.getLike(3);
						
				if (agev1 != null && agev2 != null)
				{
					distref = compareRefNodes(agev1, residencev1, genderv1, agev2, residencev2, genderv2);
					
					distref2 = compareRefNodes2(religionv1, maritalv1, professionv1, politicalv1, sexualv1,
                                                religionv2, maritalv2, professionv2, politicalv2, sexualv2);
				
					distlikesref = dlikes(lk1, lk2, lk3, lk21, lk22, lk23);
				
					distref = ((distref * 3.0) + (distref2 * 5.0) + distlikesref) / 9.0;
					
					//System.out.println("\ndistref: "+distref);
				
	
				} // eif age1 != null
		}

	}
	catch(Exception e){
		e.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}

	return distref;
	
} //fin de funcion 'rDataCategoryDistanceValuesNew'

} // end of public class rDataCategoryDistanceValuesNew