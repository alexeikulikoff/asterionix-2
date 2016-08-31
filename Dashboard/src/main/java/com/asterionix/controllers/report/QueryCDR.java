package com.asterionix.controllers.report;


import com.asterionix.controllers.util.FindBy;

public class QueryCDR extends AbstractQuery implements IQuery {
	
	private String src;
	private String dst;
	private String dsp;
	private int page;
	
	public QueryCDR(){}
	
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
	public void setDsp(String s){
		this.dsp = s;
	}
	
	@FindBy(name = "Disposition")
	public String getDsp(){
		return this.dsp;
	}
	
	public void setPage(int p){
		this.page = p;
	}
	public int getPage(){
		return this.page;
	}
	
	@Override
	public String toString(){
		return "CDRQuery: src=[" + src + "] dst = [" + dst + "] dsp = [" + dsp + "] time1 = [" + time1 + "] time2 = [" + time2 + "]";
		
	}

	
}
