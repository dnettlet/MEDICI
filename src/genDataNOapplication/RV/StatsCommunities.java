// Copyright (C) 2018  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.RV;


import java.util.Random;
import java.util.Enumeration;
import java.util.Vector;

import genDataNOapplication.User.User;
import genDataNOapplication.community.Community;

public class StatsCommunities{
	


public static int StatsCommunities()
{
	Random generator = new Random(System.currentTimeMillis()*1000);
	Random generator2 = new Random(System.currentTimeMillis()*1000);
	Enumeration en1 = RV.Users.keys();
	String str="";
	User nw, nw2;
	int user1=0,user2=0,i=0, j=0, friends_ok=0;

	
	Vector agev        = new Vector ();	Vector agevf        = new Vector ();
	Vector genderv     = new Vector ();	Vector gendervf     = new Vector ();
	Vector residencev  = new Vector ();	Vector residencevf  = new Vector ();
	
	Vector religionv       = new Vector ();	Vector religionvf       = new Vector ();
	Vector maritalstatusv  = new Vector ();	Vector maritalstatusvf  = new Vector ();
	Vector professionv     = new Vector ();	Vector professionvf     = new Vector ();
	Vector politicalorientationv     = new Vector ();	Vector politicalorientationvf     = new Vector ();
	Vector sexualorientationv        = new Vector ();	Vector sexualorientationvf        = new Vector ();
	
	Vector weightv        = new Vector ();	Vector weightvf        = new Vector ();
	
	Vector like1v        = new Vector ();	Vector likevf        = new Vector ();
	Vector like2v        = new Vector ();	
	Vector like3v        = new Vector ();	

	
	String res="";
	String gen="";
	String ag="";
	
	String rel="";
	String mar="";
	String prof="";
	String pol="";
	String seo="";
	
	String we="";
	String lk1="";	String lk2="";	String lk3="";
	String lk21="";	String lk22="";	String lk23="";
	
	int genix = 0, resix = 0, agix = 0, weix = 0, lkix1 = 0, lkix2 = 0, lkix3 = 0;
	
	int relix = 0, marix = 0, profix = 0, polix = 0, seoix = 0;
	
	residencev.add((String)"Palo Alto");   		residencevf.add((Integer)(0)); 
	residencev.add((String)"Santa Barbara");   	residencevf.add((Integer)(0)); 
	residencev.add((String)"Boca Raton");   	residencevf.add((Integer)(0)); 
	residencev.add((String)"Boston");   		residencevf.add((Integer)(0)); 
	residencev.add((String)"Norfolk");   		residencevf.add((Integer)(0)); 
	residencev.add((String)"San Jose");   		residencevf.add((Integer)(0)); 
	
	
	genderv.add((String)"male");   gendervf.add((Integer)(0)); 
	genderv.add((String)"female"); gendervf.add((Integer)(0)); 
	
	agev.add((String)"18-25");	agevf.add((Integer)(0));
	agev.add((String)"26-35");	agevf.add((Integer)(0));
	agev.add((String)"36-45");	agevf.add((Integer)(0));
	agev.add((String)"46-55");	agevf.add((Integer)(0));
	agev.add((String)"56-65");	agevf.add((Integer)(0));
	agev.add((String)"66-75");	agevf.add((Integer)(0));
	agev.add((String)"76-85");	agevf.add((Integer)(0));

	religionv.add((String)"Buddhist");	religionvf.add((Integer)(0));
	religionv.add((String)"Christian");	religionvf.add((Integer)(0));
	religionv.add((String)"Hindu");	religionvf.add((Integer)(0));
	religionv.add((String)"Jewish");	religionvf.add((Integer)(0));
	religionv.add((String)"Muslim");	religionvf.add((Integer)(0));
	religionv.add((String)"Sikh");	religionvf.add((Integer)(0));
	religionv.add((String)"Traditional Spirituality");	religionvf.add((Integer)(0));
	religionv.add((String)"Other Religions");	religionvf.add((Integer)(0));
	religionv.add((String)"No religious affiliation");	religionvf.add((Integer)(0));
	
	maritalstatusv.add((String)"Single");	maritalstatusvf.add((Integer)(0));
	maritalstatusv.add((String)"Married");	maritalstatusvf.add((Integer)(0));
	maritalstatusv.add((String)"Divorced");	maritalstatusvf.add((Integer)(0));
	maritalstatusv.add((String)"Widowed");	maritalstatusvf.add((Integer)(0));
	
	professionv.add((String)"Manager");	professionvf.add((Integer)(0));
	professionv.add((String)"Professional");	professionvf.add((Integer)(0));
	professionv.add((String)"Service");	professionvf.add((Integer)(0));
	professionv.add((String)"Sales and office");	professionvf.add((Integer)(0));
	professionv.add((String)"Natural resources construction and maintenance");	professionvf.add((Integer)(0));
	professionv.add((String)"Production transportation and material moving");	professionvf.add((Integer)(0));
	professionv.add((String)"Student");	professionvf.add((Integer)(0));
 

	politicalorientationv.add((String)"Far Left");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Left");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Center Left");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Center");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Center Right");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Right");	politicalorientationvf.add((Integer)(0));
	politicalorientationv.add((String)"Far Right");	politicalorientationvf.add((Integer)(0));

	sexualorientationv.add((String)"Asexual");	sexualorientationvf.add((Integer)(0));
	sexualorientationv.add((String)"Bisexual");	sexualorientationvf.add((Integer)(0));
	sexualorientationv.add((String)"Heterosexual");	sexualorientationvf.add((Integer)(0));
	sexualorientationv.add((String)"Homosexual");	sexualorientationvf.add((Integer)(0));

	weightv.add((String)"1-2");	weightvf.add((Integer)(0));	
	weightv.add((String)"3-6");	weightvf.add((Integer)(0));	
	weightv.add((String)"7-10");	weightvf.add((Integer)(0));	

	like1v.add((String)"entertainment");	like2v.add((String)"entertainment");	like3v.add((String)"music artist");
	like1v.add((String)"music artist");		like2v.add((String)"music artist");		like3v.add((String)"entertainment");
	like1v.add((String)"drink brand");		like2v.add((String)"drink brand");		like3v.add((String)"entertainment");
	like1v.add((String)"tv show");			like2v.add((String)"drink brand");		like3v.add((String)"soccer club");
	
	likevf.add((Integer)(0));
	likevf.add((Integer)(0));
	likevf.add((Integer)(0));
	likevf.add((Integer)(0));
	
	
    int ranlim = 0;
     
    int mod1 = 0, mod2 = 0;
    
	//for each community and for each userid in each community, accumulate the frequencies of the attribute-values and print them
	Enumeration enc = RV.Communities.keys();
	while(enc.hasMoreElements())  // each community
	{
    	Community co = (Community)RV.Communities.get((Integer)enc.nextElement());
    	int co1 = co.getCommunity();
    	
		for(i=0;i<co.vertexids.size();i++) // each user
		{
			int vid = (Integer)co.vertexids.get(i);
	
			nw = (User)RV.Users.get((Integer)vid);
			
			ag = nw.getAge(); res = nw.getResidence(); gen = nw.getGender();
			rel = nw.getReligion(); mar = nw.getMaritalStatus(); prof = nw.getProfession();
			pol = nw.getPoliticalOrientation(); seo = nw.getSexualOrientation();
			lk1 = nw.getLike(1); lk2 = nw.getLike(2); lk3 = nw.getLike(3);
			
            if (agev.contains((String)ag))
            {
            	int pos = agev.indexOf(ag);
            	int ef = (Integer)agevf.elementAt(pos);
            	++ef;
            	agevf.setElementAt(ef, pos);
            }
            
            if (residencev.contains((String)res))
            {
            	int pos = residencev.indexOf(res);
            	int ef = (Integer)residencevf.elementAt(pos);
            	++ef;
            	residencevf.setElementAt(ef, pos);
            }
            
            if (genderv.contains((String)gen))
            {
            	int pos = genderv.indexOf(gen);
            	int ef = (Integer)gendervf.elementAt(pos);
            	++ef;
            	gendervf.setElementAt(ef, pos);
            }
            
            if (religionv.contains((String)rel))
            {
            	int pos = religionv.indexOf(rel);
            	int ef = (Integer)religionvf.elementAt(pos);
            	++ef;
            	religionvf.setElementAt(ef, pos);
            }
            
            if (maritalstatusv.contains((String)mar))
            {
            	int pos = maritalstatusv.indexOf(mar);
            	int ef = (Integer)maritalstatusvf.elementAt(pos);
            	++ef;
            	maritalstatusvf.setElementAt(ef, pos);
            }
            
            if (professionv.contains((String)prof))
            {
            	int pos = professionv.indexOf(prof);
            	int ef = (Integer)professionvf.elementAt(pos);
            	++ef;
            	professionvf.setElementAt(ef, pos);
            }
            
            if (politicalorientationv.contains((String)pol))
            {
            	int pos = politicalorientationv.indexOf(pol);
            	int ef = (Integer)politicalorientationvf.elementAt(pos);
            	++ef;
            	politicalorientationvf.setElementAt(ef, pos);
            }
            
            if (sexualorientationv.contains((String)seo))
            {
            	int pos = sexualorientationv.indexOf(seo);
            	int ef = (Integer)sexualorientationvf.elementAt(pos);
            	++ef;
            	sexualorientationvf.setElementAt(ef, pos);
            }
            
            if (like1v.elementAt(0).equals(lk1) && like2v.elementAt(0).equals(lk2) && like3v.elementAt(0).equals(lk3))
            {
            	int ef = (Integer)likevf.elementAt(0);
            	++ef;
            	likevf.setElementAt(ef, 0);
            }
            else if (like1v.elementAt(1).equals(lk1) && like2v.elementAt(1).equals(lk2) && like3v.elementAt(1).equals(lk3))
            {
            	int ef = (Integer)likevf.elementAt(1);
            	++ef;
            	likevf.setElementAt(ef, 1);
            }
            else if (like1v.elementAt(2).equals(lk1) && like2v.elementAt(2).equals(lk2) && like3v.elementAt(2).equals(lk3))
            {
            	int ef = (Integer)likevf.elementAt(2);
            	++ef;
            	likevf.setElementAt(ef, 2);
            }
			
		} // efor all users in the community
		
		// Now write out the stats for current community (and reassign freqs to zero ready for next community
		System.out.println("COMMUNITY :"+co1);
		
		for(i=0;i<agev.size();i++)
		{
			System.out.println("AGE: "+agev.get(i)+" FREQ: "+agevf.get(i));
			agevf.setElementAt((Integer)0, i);
		}
		for(i=0;i<genderv.size();i++)
		{
			System.out.println("gender: "+genderv.get(i)+" FREQ: "+gendervf.get(i));
			gendervf.setElementAt((Integer)0, i);
		}
		for(i=0;i<residencev.size();i++)
		{
			System.out.println("residence: "+residencev.get(i)+" FREQ: "+residencevf.get(i));
			residencevf.setElementAt((Integer)0, i);
		}
		for(i=0;i<religionv.size();i++)
		{
			System.out.println("religion: "+religionv.get(i)+" FREQ: "+religionvf.get(i));
			religionvf.setElementAt((Integer)0, i);
		}
		for(i=0;i<maritalstatusv.size();i++)
		{
			System.out.println("maritalstatus: "+maritalstatusv.get(i)+" FREQ: "+maritalstatusvf.get(i));
			maritalstatusvf.setElementAt((Integer)0, i);
		}
		for(i=0;i<professionv.size();i++)
		{
			System.out.println("profession: "+professionv.get(i)+" FREQ: "+professionvf.get(i));
			professionvf.setElementAt((Integer)0, i);
		}
		for(i=0;i<politicalorientationv.size();i++)
		{
			System.out.println("politicalorientation: "+politicalorientationv.get(i)+" FREQ: "+politicalorientationvf.get(i));
			politicalorientationvf.setElementAt((Integer)0, i);
		}
		for(i=0;i<sexualorientationv.size();i++)
		{
			System.out.println("sexualorientation: "+sexualorientationv.get(i)+" FREQ: "+sexualorientationvf.get(i));
			sexualorientationvf.setElementAt((Integer)0, i);
		}
		for(i=0;i<likevf.size();i++)
		{
			System.out.println("likes: "+like1v.get(i)+"/"+like2v.get(i)+"/"+like3v.get(i)+" FREQ: "+likevf.get(i));
			likevf.setElementAt((Integer)0, i);
		}
		
		

		
		
		
	} // ewhile
	

	return 0;
	
} //fin de funcion 'rAssignDataGeneralizedSensAttr4'

} // end of public class rAssignDataGeneralizedSensAttr4