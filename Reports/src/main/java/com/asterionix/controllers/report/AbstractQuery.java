package com.asterionix.controllers.report;



abstract class AbstractQuery {

	protected String time1;
	protected String time2;
	protected int pagesize;
	
	public void setTime1(String s){
		this.time1 = s;
	}
	public String getTime1(){
		return this.time1;
	}
	public void setTime2(String s){
		this.time2 = s;
	}
	public String getTime2(){
		return this.time2;
	}
	public void setPagesize(int p){
		this.pagesize = p;
	}
	public int getPagesize(){
		return this.pagesize;
	}
}
