package com.asterionix.controllers.report;

public class QueueLogFindRecord implements Record{
	
	private int id;
	private String time;
	private String queuename;
	private String agent;
	private String event;
	private String name;
	
	public QueueLogFindRecord(int id, String time,String queuename, String agent, String event, String name){
		this.id = id;
		this.time = time;
		this.queuename = queuename;
		this.agent = agent;
		this.event = event;
		this.name= name;
	}
	public int getId(){
		return this.id;
	}
	public String getTime(){
		return this.time;
	}
	public String getQueuename(){
		return this.queuename;
	}
	public String getAgent(){
		return this.agent;
	}
	public String getEvent(){
		return this.event;
	}
	public String getName(){
		return this.name;
	}
}
