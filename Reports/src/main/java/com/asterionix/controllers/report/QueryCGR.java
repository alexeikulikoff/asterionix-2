package com.asterionix.controllers.report;

import com.asterionix.controllers.util.FindBy;

public class QueryCGR extends AbstractQuery  implements IQuery {
	
	private String src;
	private String dst;
	
	public QueryCGR(){}
	
	public void setSrc(String s){
		this.src = s;
	}
	@FindBy(name="Src")
	public String getSrc(){
		return this.src;
	}
	public void setDst(String s){
		this.dst = s;
	}
	
	@FindBy(name="Dst")
	public String getDst(){
		return this.dst;
	}
	
	

	
}
