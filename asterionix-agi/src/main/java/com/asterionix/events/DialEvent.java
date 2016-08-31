package com.asterionix.events;

public class DialEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;
	private String subevent;
	private String channel;
	private String destination;
	private String destuniqueid;
	private String dialstring;
	protected String connectedlinenum;
    protected String connectedlinename;
    protected String uniqueid;   
    protected String calleridnum;
    protected String calleridname;
   
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
	public void setsubevent(String subevent){
		this.subevent = subevent;
	}
	public String getsubevent(){
		return this.subevent;
	}
	public void setchannel(String channel){
		
		this.channel = channel;
	}
	public String getchannel(){
		
		return this.channel;
	}
	public void setdestination(String destination){
		this.destination = destination;
	}
	public String getdestination(){
		return this.destination;
	}
	
	public void setdestuniqueid(String destuniqueid){
		this.destuniqueid = destuniqueid;
	}
	public String getdestuniqueid(){
		return this.destuniqueid;
	}
	public void setdialstring(String dialstring){
		this.dialstring = dialstring;
	}
	public String getdialstring(){
		return this.dialstring;
	}
	
	public DialEvent(Object source) {
		super(source);
		
	}

}
