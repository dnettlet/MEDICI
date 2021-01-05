// Copyright (C) 2018  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.doCalcs;

import java.lang.Math;
import java.util.Collections;
import java.util.Vector;

import java.lang.Math;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Enumeration;

import genDataNOapplication.RV.RV;
import genDataNOapplication.User.User;

public class doCalcs{
	
//********************** Do Calculations Global for all nodes in Graph ***********************/
public static double doCalcsq(Hashtable Users){
	int user1=0,user2=0;
	double degree;
	User nw, nw2;

	int count=0;
	double sumalldegrees=0;
	
	Enumeration en1 = Users.keys();
	while (en1.hasMoreElements()){

		nw = (User)Users.get((Integer)en1.nextElement());
		user1 = nw.getUser();
		degree = nw.friends.size();

		if (degree > RV.max_degreeL)
		{
			RV.max_degreeL = degree;
		}
		else if (degree < RV.min_degreeL)
		{
			RV.min_degreeL = degree;
		}
		
		RV.Users.remove(nw.getUser());
		RV.Users.put(nw.getUser(),nw);

		RV.max_avgL = 0;   RV.min_avgL = 0;
		RV.max_stdevL = 0; RV.min_stdevL = 0;
		
		nw.setnumEdges(1);
		RV.min_edgesL = 1; RV.max_edgesL = 2;
		nw.setCC(1);
		RV.max_ccL=1; RV.min_ccL = 0;
		
	} //ewhile get max min
	
	Enumeration en2 = Users.keys();
	double deg_norm   = 0.0;
	while (en2.hasMoreElements()){

		nw = (User)Users.get((Integer)en2.nextElement());
		user1 = nw.getUser();
		degree = nw.friends.size();
		
		deg_norm   = (degree - RV.min_degreeL)    / (RV.max_degreeL - RV.min_degreeL);

        nw.setNormDegree(deg_norm); 
		
		RV.Users.remove(nw.getUser());
		RV.Users.put(nw.getUser(),nw);

	} //ewhile 

	return 0;
} //edoCalcs

}