package genDataNOapplication.model;

import java.util.ArrayList;
import java.util.List;

public class ProfileModel {
	
	private List<String> paramList;
	
	private double frequency;
	
	public ProfileModel() {
		
		paramList = new ArrayList<String>();
		frequency = 0;
		
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	

}
