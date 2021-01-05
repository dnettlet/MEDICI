// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.Dijkstra;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Collections;
import java.util.Vector;

import genDataNOapplication.RV.RV;
import genDataNOapplication.User.User;
import genDataNOapplication.community.Community;

public class FindMedoid{
	private double simid=0;
	private int id=0;
		
	public static Vector vertexidsO      = new Vector ();
	public static Vector vertexidvaluesO = new Vector ();
		
	public int getid()
	{
		return id;
	};
	public double getsim()
	{
		return simid;
	};
	
	private static double alpha=2.8, beta=0.7, gamma=2.0, delta=0.003, epsilon=-0.3;
	
	public static double calcdist(User us1, double normDegree1, User us2)
	{
		double dist=0;
		double normDegree2=0;
		
		normDegree2       = us2.getNormDegree();
		
		dist = (Math.abs(normDegree1 - normDegree2));
		
		return(dist);
	}
	

public static void FindMedoid(Community co, int com) // finds the medoid of community 'com'
{

	User nw1, nw2;
	
	double dist=99999999;
	int us1=0,us2=0;
	
	int umax=0, imax=0; double dmax=0.0;
	int umin=0, imin=0; double dmin=0.0;
	
	double normDegree1=0, normCC1=0, normAuth1=0;
	double normDegree2=0, normCC2=0, normAuth2=0;
	
	double minsumdist=999999; int minsumdistid=-1;
		
		double distattr=0.0;
		
		/**************************************************************************************************/
		/************* Find the vertex with minimum sum of distances to all other vertices   **************/
		/**************************************************************************************************/
		nw1 = new User(1);
		nw2 = new User(1);
		float deg=0;
		int i=0, j=0;
		double sumdist = 0.0;

		int numusersincommunity = co.vertexids.size();
		
		//System.out.println("kgroupsize="+kgroupsize);
		int degg=0;
		if (numusersincommunity > 0)
		{
		
		for (i=0;i<numusersincommunity;++i){    // outer loop
			
			us1 = (Integer)co.vertexids.get(i);
			nw1 = (User)RV.Users.get(us1);
			
			//degg = degg + nw1.friends.size();
		
			normDegree1       = nw1.getNormDegree();

			
			sumdist = 0;
			
			for (j=0;j<numusersincommunity;++j){    // inner loop
				
				us2 = (Integer)co.vertexids.get(j);
				nw2 = (User)RV.Users.get(us2);
	
				if (us1 != us2 && nw1 != null && nw2!=null) 
				{
					// First the topological distance
					dist = calcdist(nw1, normDegree1, nw2);
				
					//System.out.println("CHECKPOINT.CHECKPOINT.CHECKPOINT. Distance between "+us1+" and "+us2+" top: "+dist+" attr: "+distattr);
				
					sumdist = sumdist + dist;


				} // eif us1 != us2
			} // efor inner
			
			co.vertexidvalues.set(i,(Double)sumdist);
			
			
		} // efor outer
		} // eif kgroupsize > 0
		
		// NOW ORDER THEM......
		
		//if(1==1)return;
		
		double smallestdistance = 99999.9; int smallestid = -1; int smallestix = -1;
		double usv1 = 0.0;
		
		while (co.vertexids.size() > 0)
		{
		
			numusersincommunity = co.vertexids.size();
		
			smallestdistance = 99999.9; smallestid = -1; smallestix = -1;
			usv1 = 0.0;
		
			for (i=0;i<numusersincommunity;++i){    // take a look
			
				us1  = (Integer)co.vertexids.get(i);
				usv1 = (Double)co.vertexidvalues.get(i);
				if (usv1 < smallestdistance)
				{
					smallestdistance = usv1;
					smallestid = us1;
					smallestix = i;
				}

			}
			// got a smallest distance, now add it to the temporary vector
		
			vertexidsO.add(smallestid);
			vertexidvaluesO.add(smallestdistance);
			co.vertexids.remove(smallestix);
			co.vertexidvalues.remove(smallestix);
			RV.Communities.remove(co.getCommunity());
			RV.Communities.put(co.getCommunity(),co);
		
		} // ewhile vector not empty
		
		
		
		// Now load the ordered values back into the community structure
		co.vertexids.clear();
		co.vertexidvalues.clear();
		
		RV.Communities.remove(co.getCommunity());
		RV.Communities.put(co.getCommunity(),co);
		
		numusersincommunity = vertexidsO.size();
		int id=0; double distid = 0.0;
		for (i=0;i<numusersincommunity;++i)
		{
			id = (Integer)vertexidsO.get(i);
			distid = (Double)vertexidvaluesO.get(i);
			co.vertexids.add(id);
			co.vertexidvalues.add(distid);
			RV.Communities.remove(co.getCommunity());
			RV.Communities.put(co.getCommunity(),co);
		}
			
		vertexidsO.clear();
		vertexidvaluesO.clear();
		
		RV.Communities.remove(co.getCommunity());
		RV.Communities.put(co.getCommunity(),co);
		

} //end of FindMedoid

} // end of class FindMedoid
