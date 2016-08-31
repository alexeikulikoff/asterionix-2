package com.asterionix.controllers.event;

public class QueueMemberRemovedEvent extends AbstractEvent{
	
	private static final long serialVersionUID = 1L;
	private String queue;
	private String location;
	private String membername;
	
	public QueueMemberRemovedEvent(Object source) {
		super(source);
		
	}
	public void setqueue(String queue){
		this.queue = queue;
	}
	public String getqueue(){
		return this.queue;
	}
	public void setlocation(String location){
		this.location = location;
	}
	public String getlocation(){
		return this.location;
	}
	public void setmembername(String memberName){
		this.membername = memberName;
	}
	public String getmembername(){
		return this.membername;
	}
}
