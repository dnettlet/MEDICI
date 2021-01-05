// Copyright (C) 2018-2021  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package genDataNOapplication.community;
import java.util.*;

import genDataNOapplication.RV.RV;

public class Community {
	private Integer Community;
	
    private int medoidid;
    
    public int numberofseedassigned = 0;
	public Vector seedsc      = new Vector ();

	public Vector vertexids      = new Vector ();
	public Vector vertexidvalues = new Vector ();
	
	public void loadvertexids(Integer vertexid){
		if (this.vertexids.contains(vertexid) == false)
			this.vertexids.add(vertexid);
	}
	public void loadvertexidvalues(Double vertexidvalue){
		this.vertexidvalues.add(vertexidvalue);
	}
		
	public Integer getvertexids(Integer ixid){
		Integer anode=0;
		if (ixid < this.vertexids.size())
			anode = (Integer)this.vertexids.get(ixid);
		return anode;
	}
	public Double getvertexidvalues(Integer ixiv){
		Double avalue=0.0;
		if (ixiv < this.vertexidvalues.size())
			avalue = (Double)this.vertexidvalues.get(ixiv);
		return avalue;
	}
		
	public Integer getCommunity() {
		return Community;
	}
	public void setCommunity(Integer community) {
		Community = community;
	}
	
	public Community(Integer kom) {
		setCommunity(kom);
	}
		
}
