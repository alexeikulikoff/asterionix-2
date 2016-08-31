package com.asterionix.controllers.report;

public class RecordQueueLogin implements Record {
	private String theDate;
	private String loginTime; 
	private String logoffTime;
	private String phoneNumber;
	private int callCount;
	private String args;
	
	public RecordQueueLogin(){}
	
	public RecordQueueLogin(String theDate, String loginTime, String logoffTime, String phoneNumber, int callCount,String args){
		String[] tm ;
		this.theDate = theDate.split(" ")[0];
		tm = loginTime.split(" ")[1].split(":"); 
		this.loginTime = tm[0]+":"+tm[1];
		tm = logoffTime.split(" ")[1].split(":"); 
		this.logoffTime = tm[0]+":"+tm[1];
		this.phoneNumber = phoneNumber;
		this.callCount = callCount;
		this.args = args;
		
	}
	public String getArgs(){
		return this.args;
	}
	public String getTheDate(){
		
		return this.theDate;
	}
	public String getLoginTime(){
		
		return this.loginTime;
		
	}
	public String getLogoffTime(){
		return this.logoffTime;
	}
	public String getPhoneNumber(){
		
		return this.phoneNumber;
	}
	public int getCallCount(){
		return this.callCount;
	}
	public void setTheDate(String date){
		this.theDate = date;
	}
	public void setLoginTime(String t){
		this.loginTime = t;
		
	}
	public void setLogoffTime(String t){
		this.logoffTime = t;
		
	}
	public void setPhoneNumber(String t){
		this.phoneNumber = t;
		
	}
	public void setCallCount(int c){
		this.callCount = c;
		
	}
	


	

	
}
