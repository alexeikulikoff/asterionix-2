package com.asterionix.controllers.event;

public class JoinEvent extends AbstractEvent{


	private static final long serialVersionUID = 1L;
	
	private String queue;
	private String position;
	private String count;	
	private String channel;
	private String connectedlinenum;
	private String connectedlinename;
	private String uniqueid;   
	private String calleridnum;
	private String calleridname;	
	
	public void setqueue(String queue){
		this.queue = queue;
	}
	public String getqueue(){
		return this.queue;
	}
	public void setcount(String count){
		this.count = count;
	}
	public String getcount(){
		
		return this.count;
	}
	
	public void setposition(String position){
		this.position = position;
	}
	public String getposition(){
		
		return this.position;
	}
	public void setchannel(String channel){
		
		this.channel = channel;
	}
	public String getchannel(){
		
		return this.channel;
	}
	public String getcalleridname()
	{
	        return calleridname;
	}

	public void setcalleridname(String calleridname)
	{
	       this.calleridname = calleridname;
	}

	public String getcalleridnum()
	{
	        return calleridnum;
	}

	public void setcalleridnum(String calleridnum)
	{
	        this.calleridnum = calleridnum;
	}
	public void setuniqueid(String uniqueid){
		
		this.uniqueid = uniqueid;
	}
	public String getuniqueid(){
		
		return this.uniqueid;
	}
	public String getconnectedlinenum()
	{
	        return connectedlinenum;
	}

	public void setconnectedlinenum(String connectedlinenum)
	{
	        this.connectedlinenum = connectedlinenum;
	}

	public String getconnectedlinename()
	{
	        return connectedlinename;
	}
	public void setconnectedlinename(String connectedlinename)
	{
	      this.connectedlinename = connectedlinename;
	}


	public JoinEvent(Object source) {
		super(source);
		
	}

}
