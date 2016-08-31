package com.asterionix.controllers.report;



public class CDRFindRecord extends RecordCDR{
	
	private QueueLogFindRecords queueLogFindRecords;
	
	private String disposition;
	
	public CDRFindRecord(int id, String uniqueid, String date,String time,String src,String dst, String duration,String disposition){
		
		super(id,uniqueid, date, time,  src,  dst, duration);
		
		this.disposition = disposition;
		
	}
	public String getdisposition(){
		
		return this.disposition;
	}
	public void setdisposition(String s){
		
		this.disposition = s;
	}
	public QueueLogFindRecords getQueueLogFindRecords(){
		
		return this.queueLogFindRecords;
	}
	public void setQueueLogFindRecord(QueueLogFindRecords records){
		
		this.queueLogFindRecords = records;
	}
}
