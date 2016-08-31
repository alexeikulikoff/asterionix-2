package com.asterionix.controllers.report;

public class RecordCDR implements Record {
	private int id;
	private String uniqueid;
	private String date;
	private String time;
	private String src;
	private String dst;
	private String duration;
	
	public RecordCDR(int id, String uniqueid, String date,String time,String src,String dst, String duration) {
	
		this.id = id;
		this.uniqueid = uniqueid;
		this.date = date;
		this.time = time;
		this.src = src;
		this.dst = dst;
		this.duration = duration;
		
	}
	public int getId(){
		
		return this.id;
	}
	public String getSrc(){
		
		return this.src;
	}
	public String getDst(){
		
		return this.dst;
	}
	public String getDuration(){
		
		return this.duration;
	}
	public String getDate(){
		
		return this.date;
	}
	public String getTime(){
		
		return this.time;
	}
	public String getUniqueid(){
		
		return this.uniqueid;
	}
	


}
