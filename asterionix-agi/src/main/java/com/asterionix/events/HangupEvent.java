package com.asterionix.events;

public class HangupEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;
	private String cause;
	private String causetxt;
	private String channel;
	private String accountcode;
	protected String calleridnum;
	protected String uniqueid;
	
	public void setuniqueid(String uniqueid){
		
		this.uniqueid = uniqueid;
	}
	public String getuniqueid(){
		
		return this.uniqueid;
	}
	public void setcause(String cause){
		this.cause = cause;
	}
	public String getcause(){
		return this.cause;
	}
	public void setcausetxt(String causetxt){
		this.cause = causetxt;
	}
	public String getcausetxt(){
		return this.causetxt;
	}
	public void setaccountcode(String accountcode){
		this.accountcode = accountcode;
	}
	public String getaccountcode(){
		return this.accountcode;
	}
	
	public void setchannel(String channel){
		this.channel = channel;
	}
	public String getchannel(){
		return this.channel;
	}
	public String getcalleridnum()
	{
	        return calleridnum;
	}

	public void setcalleridnum(String calleridnum)
	{
	        this.calleridnum = calleridnum;
	}
	public HangupEvent(Object source) {
		super(source);
		
	}

}
