package com.asterionix.controllers.event;

public class HangupEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;
	private String cause;
	private String causeTxt;
	private String channel;
	private String uniqueid;
	private String accountCode;
	

	public void setcause(String cause){
		this.cause = cause;
	}
	public String getcause(){
		return this.cause;
	}
	public void setcausetxt(String causeTxt){
		this.cause = causeTxt;
	}
	public String getcausetxt(){
		return this.causeTxt;
	}
	public void setaccountcode(String accountCode){
		this.accountCode = accountCode;
	}
	public String getaccountcode(){
		return this.accountCode;
	}
	public void setuniqueid(String uniqueID){
		this.uniqueid = uniqueID;
	}
	public String getuniqueid(){
		return this.uniqueid;
	}
	public void setchannel(String channel){
		this.channel = channel;
	}
	public String getchannel(){
		return this.channel;
	}
	
	public HangupEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
