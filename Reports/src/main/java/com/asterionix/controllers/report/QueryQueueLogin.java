package com.asterionix.controllers.report;

public class QueryQueueLogin {

	private String actiontime1;
	private String actiontime2;
	private int agentid;
	private int pagesize;
	private String coursename; 
	
	public QueryQueueLogin(){}
	
	public String getActiontime1(){
		return this.actiontime1;
	}
	public String getActiontime2(){
		return this.actiontime2;
	}
	public void setActiontime1(String t){
		this.actiontime1 =  t;
	}
	public void setActiontime2(String t){
		this.actiontime2 =  t;
	}
	public int getAgentid(){
		return this.agentid;
	}
	public void setAgentid(int i){
		this.agentid = i;
		
	}
	public int getPagesize(){
		return this.pagesize;
	}
	public void setPagesize(int i){
		this.pagesize = i;
		
	}
	public String getCoursename(){
		return this.coursename;
	}
	public void setCoursename(String s){
		this.coursename = s;
		
	}
}
