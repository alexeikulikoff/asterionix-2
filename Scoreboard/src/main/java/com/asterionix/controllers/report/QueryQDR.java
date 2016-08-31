package com.asterionix.controllers.report;

public class QueryQDR extends AbstractQuery implements IQuery {
	private String agent;
	private String event;
	private int page;
	
	private String coursename; 
	
	public QueryQDR(){}
	
	public String getAgent(){
		return this.agent;
	}
	public void setAgent(String s){
		this.agent = s;
	}
	public String getEvent(){
		
		return this.event;
	}
	public void setEvent(String s){
		this.event = s;
	}
	public void setPage(int p){
		this.page = p;
	}
	public int getPage(){
		return this.page;
	}
	
	public String getCoursename(){
		return this.coursename;
	}
	public void setCoursename(String s){
		this.coursename = s;
		
	}
}
