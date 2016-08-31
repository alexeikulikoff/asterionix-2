package com.asterionix.controllers.util;

import java.util.ArrayList;
import java.util.List;



public class Params {
	private List<Param> params;
	
	public Params(){
		params = new ArrayList<Param>();
	}
	public List<Param> getParams(){
		return this.params;
	}
	public void setParams(List<Param> params){
		this.params = params;
	}
}
