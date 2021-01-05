// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.Dijkstra;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
//import java.util.Random;
import java.util.Vector;

import genDataNOapplication.RV.RV;
import genDataNOapplication.User.User;
import genDataNOapplication.community.Community;

public class AssignSeeds2{


public int AssignSeeds2(Hashtable Users, int maxid, int seedsize) 
{
	Enumeration en1 = RV.Communities.keys();
	Community co;
	
    int ids=0;
    
	RV.seeds.clear();		RV.invalids.clear();
	
	co = (Community)RV.Communities.get((Integer)en1.nextElement());
	int co1 = co.getCommunity();
	
    
    //OBTAIN THE FIRST SEED from community zero
	ids = obtainseed(co); // this is the node id from the medoid vector

	if (RV.seeds.contains(ids) == false)
	{
		RV.seeds.add(ids);
		int nosa = co.numberofseedassigned;
		++nosa;
		co.numberofseedassigned = nosa;
		RV.Communities.remove(co.getCommunity());
		RV.Communities.put(co.getCommunity(),co);
	}
	else 
		ids = -1;
    
    // ok, got first seed in vector "seeds"
    
    int ubercounter=0;
    int maxtries = 50, tries=0;
    
    int besttonow=0;
    System.out.println("seedsize="+seedsize+" tries="+tries);
    while ((RV.seeds.size() < seedsize) && (tries < maxtries))  // loop until got 'seedsize' seeds
    {
    	//System.out.println("P1");
		//see the seeds
		//System.out.print("seedsbesttonow: ");
		//for(int i=0;i<RV.seedsbesttonow.size();i++)
			//System.out.print(" "+RV.seedsbesttonow.get(i)+" ");
		//System.out.println();
    	if (tries == (maxtries -1))
    	{
    		--seedsize;
    		tries = 0;
    	}
    	//System.out.println("seedsize="+seedsize+" tries="+tries+" seeds assigned="+RV.seeds.size());
    	
    	if (RV.seeds.size() > besttonow)
    	{
    		besttonow = RV.seeds.size();
    		RV.seedsbesttonow.clear();
    		for (int i=0;i<RV.seeds.size();i++)
    			RV.seedsbesttonow.add(i, RV.seeds.get(i));
    	}
    	
    	if (RV.seedsbesttonow.size() >= seedsize) //stop
    	{
    		RV.seeds.clear();
    		for (int i=0;i<RV.seedsbesttonow.size();i++)
    			RV.seeds.add(i, RV.seedsbesttonow.get(i));
    		return 0;
    	}
    		
    	++ubercounter;
    	if (ubercounter > (maxid * 2)) // start again
    	{
    		ubercounter = 0;
    		
    		RV.seeds.clear();     		RV.invalids.clear();
    		++tries;
    		ids = -1;
    		
    		//Need to reinitialize the community seed counters
    		Enumeration enc = RV.Communities.keys();
    		while(enc.hasMoreElements())
    		{
        		co = (Community)RV.Communities.get((Integer)enc.nextElement());
   
        		co.numberofseedassigned = 0;
        		RV.Communities.remove(co.getCommunity());
        		RV.Communities.put(co.getCommunity(),co);
    		}

    		//OBTAIN THE FIRST SEED from community zero
    		en1 = RV.Communities.keys();
    		co = (Community)RV.Communities.get((Integer)en1.nextElement());
    		co1 = co.getCommunity();
    		ids = obtainseed(co); // this is the node id from the medoid vector

    		
    		if (RV.seeds.contains(ids) == false)
    		{
    			RV.seeds.add(ids);
    			int nosa = co.numberofseedassigned;
    			++nosa;
    			co.numberofseedassigned = nosa;
				RV.Communities.remove(co.getCommunity());
				RV.Communities.put(co.getCommunity(),co);
    		}
    		else 
    			ids = -1;
      	}
        ids = -1;
        
    	//System.out.println("P2");
    	
        //get THE next SEED for the next community
		if ( en1.hasMoreElements() )
			co = (Community)RV.Communities.get((Integer)en1.nextElement());
		else
		{
    		en1 = RV.Communities.keys();
			co = (Community)RV.Communities.get((Integer)en1.nextElement());
		}
   		ids = obtainseed(co); // this is the node id from the medoid vector
   		
 		//System.out.println("node ids returned from obtainseed for community "+co1+" is="+ids);
    	
		if ((RV.seeds.contains(ids) == true) || (RV.invalids.contains(ids) == true))
		{
			ids = -1;
		}
		
    	//System.out.println("P3");
    	
		/*System.out.println("ids is="+ids);
		System.out.print("seeds: ");
		for(int i=0;i<RV.seeds.size();i++)
			System.out.print(" "+RV.seeds.get(i)+" ");
		System.out.println();
		System.out.print("invalids: ");
		for(int i=0;i<RV.invalids.size();i++)
			System.out.print(" "+RV.invalids.get(i)+" ");
		System.out.println();*/
 
        // got a candidate seed, now check if its at at least distance 3 from any other seed

 		//System.out.println("node to check is="+current.name);
    	if (ids != -1)
    	{
        	//System.out.println("P4");
    		//System.out.println("node="+ids);
    		int ok = check(ids);
    		//System.out.println("ok="+ok);

    		// if ids was at least distance three from any other seed, then add it as a seed
    		if (ok == 0)
    		{
    			RV.seeds.add((Integer)ids);
    			int nosa = co.numberofseedassigned;
    			++nosa;
    			co.numberofseedassigned = nosa;
				RV.Communities.remove(co.getCommunity());
				RV.Communities.put(co.getCommunity(),co);
    			// assign same community as seed to 70% of neighbors
    			//assigncommunitytoneighbours(ids);
    			
        		//System.out.println("hola1");
    		}
    		else 
    		{
    			boolean alreadyinvalid = RV.invalids.contains((Integer)ids);
        		//System.out.println("hola2: "+alreadyinvalid);
    			
    			if (alreadyinvalid == false)
        			RV.invalids.add((Integer)ids);
    			/*System.out.print("invalids: ");
    			for(int i=0;i<RV.invalids.size();i++)
    				System.out.print(" "+RV.invalids.get(i)+" ");
    			System.out.println();*/
    		}
        	//System.out.println("P5");
    		
    	}
    	//System.out.println("P99");

    }
    //for (int i=0; i<RV.seeds.size();++i)
		//System.out.println("seed "+i+" is "+RV.seeds.get(i));

return 0;
} //fin de AssignSeeds

public static int obtainseed(Community co)
{
	int i=0, id=0;
	boolean notfree=false, invalid=false;

	int co1 = co.getCommunity();

	float best = 0, auth = 0;
	int bestid=-1;
	for (i=0;(i<co.vertexids.size()) && (bestid == -1);i++)
	{
		id = (Integer)co.vertexids.get(i);
		
		notfree = RV.seeds.contains(id);
		invalid = RV.invalids.contains(id);
		
		if((notfree == false) && (invalid == false))
			bestid = id;
	}
    return(bestid); //returns the index or -1
} // fin check

public static int check(int candidateseedid)
{
	int i=0, ret = 0, seedid = 0;
	//System.out.println("PC1");
	for (i=0; (i < RV.seeds.size()) && (ret == 0); i++)
	{
		//System.out.println("PC2");
		seedid = (Integer)RV.seeds.get(i);
		ret = distm3(seedid, candidateseedid);
		//System.out.println("matched seed id: "+si+" with candidate seed id: "+sx+" and it gave a return code of: "+ret);
	}
	//System.out.println("PC99");

 
    return(ret);
} // fin computePaths

public static int distm(int seedid, int candidateseedid)
{
	User nw1=null, nw2=null, nw3=null;;
	int j=0, k=0, ret = 0, sij = 0, sik = 0;
	
	nw3 = (User)RV.Users.get(candidateseedid);
	
	if (RV.Users.containsKey(seedid) != false)
	{
		nw1 = (User)RV.Users.get(seedid); // this is an existing seed

		for (j=0; (j < nw1.friends.size()) && (ret == 0); j++)  // seeds neighbors
		{
			sij = nw1.friends.get(j);
			nw2 = (User)RV.Users.get(sij); // this is the seed
			
			if (sij == candidateseedid)
				ret = -1;
			for (k=0; (k < nw3.friends.size()) && (ret == 0); k++)  // candidate seeds neighbors
			{
				sik = nw3.friends.get(k);
				if (sij == sik)
					ret = -1;
			}

		} //efor
				
	} // eif
	
    return(ret);
} // fin distm

public static int distm3(int seedid, int candidateseedid)
{
	User nw1=null, nw2=null;
	int j=0, ret = 0, sij = 0;
	
	if (RV.Users.containsKey(seedid) != false) // check that its not a neighbor
	{
		nw1 = (User)RV.Users.get(seedid); // this is an existing seed
		//int com1 = nw1.getMOD();
		double notin=0.0, totf=0.0;
	
		for (j=0, notin=0.0; (j < nw1.friends.size()) && (ret == 0); j++)  // seeds neighbors
		{
			sij = nw1.friends.get(j);
			
			if (sij == candidateseedid) //cant be an immediate neighbor
				return -1;
		} //efor
	} // eif
	
	// Now check that max 30% of neighbors outside community
	/*if (RV.Users.containsKey(candidateseedid) != false)
	{
		nw1 = (User)RV.Users.get(candidateseedid);
		int com1 = nw1.getMOD();
		double notin=0.0, totf=0.0;
	
		for (j=0, notin=0.0; (j < nw1.friends.size()) && (ret == 0); j++)  // neighbors
		{
			sij = nw1.friends.get(j);
			
			nw2 = (User)RV.Users.get(sij); // this is an existing seed neighbor
			int com2 = nw2.getMOD();

			
			if (com1 != com2)
				++notin;
		} //efor
		totf=nw1.friends.size();
		notin = notin/totf;
		if (notin > 0.50)  // only candidates with at least 50% of their neighbors in the same community.
			return -1;
	} // eif
	*/
	
	// Now check that max 30% of neighbors overlap between actual seed and candidate seed
	/*if (RV.Users.containsKey(candidateseedid) != false)
	{
		nw1 = (User)RV.Users.get(candidateseedid);
		nw2 = (User)RV.Users.get(seedid);
		double notin=0.0, in=0.0, totf=0.0;
		
		for (j=0, notin=0.0; (j < nw1.friends.size()) && (ret == 0); j++)  // neighbors
		{
			sij = nw1.friends.get(j);
			
            if(nw2.friends.contains(sij) == true)
				++in;
		} //efor
		totf=nw2.friends.size();
		in = in/totf;
		if (in > 0.50)  // only candidates with at most 50% overlap of neighbors with existing seed
			return -1;
	} // eif
	*/
	
	
    return(ret);
} // fin distm

public static int distm2(int seedid, int candidateseedid)
{
	User nw1=null, nw2=null, nw3=null;;
	int j=0, k=0, ret = 0, sij = 0, sik = 0;
	
	nw3 = (User)RV.Users.get(candidateseedid);
	
	if (RV.Users.containsKey(seedid) != false)
	{
		nw1 = (User)RV.Users.get(seedid); // this is an existing seed

		for (j=0; (j < nw1.friends.size()) && (ret == 0); j++)  // seeds neighbors
		{
			sij = nw1.friends.get(j);
			nw2 = (User)RV.Users.get(sij); // this is the seed
			
			if (sij == candidateseedid)
				ret = -1;
		} //efor
				
	} // eif
	
    return(ret);
}


public static void assigncommunitytoneighbours(int idseed)
{
	User nw1=null, nw2=null;
	int j=0, ret = 0, idseedneighbour = 0;
	
	if (RV.Users.containsKey(idseed) != false)
	{
		nw1 = (User)RV.Users.get(idseed); // this is the seed
		int com1 = nw1.getMOD();
		
		double numf = nw1.friends.size();
		double ixf = 0;
		
		for (j=0; j < nw1.friends.size(); j++)  // first count how many neighbours are NOT in same community as seed
		{
			idseedneighbour = nw1.friends.get(j);
			nw2 = (User)RV.Users.get(idseedneighbour); // this is the seed
			int com2 = nw2.getMOD();
			
			if (com1 != com2)
               ++ixf;
		} //efor
		
		ixf = ixf * 0.7; // this is the number to assign when community not same
		double ixfdone = 0.0;
	
		for (j=0; j < nw1.friends.size(); j++)  // seeds neighbors
		{
			idseedneighbour = nw1.friends.get(j);
			nw2 = (User)RV.Users.get(idseedneighbour); // this is the seed
			int com2 = nw2.getMOD();
			
			if ((com1 != com2) && (ixfdone < ixf))
			{
               nw2.setMOD(com1);
			   ++ixfdone;
			}

		} //efor
		//System.out.println("friends: "+nw1.friends.size()+" diff community: "+ixf+" done: "+ixfdone);
		
		
	} // eif
} //end assigncommunitytoneighbours

} // fin de clase AssignSeeds
