package com.asterionix.events;

public class QueueMemberAddedEvent extends AbstractEvent{
	
	private static final long serialVersionUID = 1L;
	private String privilege;
	private String queue;
	private String location;
	private String membername;
	private String stateinterface;
	private String membership;
	private String penalty;
	private String callstaken;
	private String lastcall;
	private String status;
	private String paused;
	
	private String agentname;

	public void setprivilege(String privilege){
		this.privilege = privilege;
	}
	public String getprivilege(){
		return this.privilege;
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
	public void setstateinterface(String stateInterface){
		this.stateinterface = stateInterface;
	}
	public String getstateinterface(){
		
		return this.stateinterface;
	}
	
	public void setmembership(String membership){
		this.membership = membership;
	}
	public String getmembership(){
		return this.membership;
	}
	public void setpenalty(String penalty){
		this.penalty = penalty;
	}
	public String getpenalty(){
		return this.penalty;
	}
	public void setcallstaken(String callstaken){
		this.callstaken = callstaken;
	}
	public String getcallstaken(){
		return this.callstaken;
	}
	public void setlastcall(String lastcall){
		this.lastcall = lastcall;
	}
	public String getlastcall(){
		return this.lastcall;
	}	
	public void setstatus(String status){
		this.status = status;
	}
	public String getstatus(){
		return this.status;
	}	
	public void setpaused(String paused){
		this.paused = paused;
	}
	public String getpaused(){
		return this.paused;
	}	
	
	public void setagentname(String s){
		
		this.agentname = s;
	}
	
	public String getagentname(){
		
		return this.agentname;
	}
	public QueueMemberAddedEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
