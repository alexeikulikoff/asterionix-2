package com.asterionix.controllers.event;

public class BridgeEvent  extends AbstractEvent{

	private static final long serialVersionUID = 1L;

	private String bridgestate;
	private String bridgetype;
	private String channel1;
	private String channel2;
	private String uniqueid1;
	private String uniqueid2;
	private String callerid1;
	private String callerid2;
	
	public void setbridgestate(String bridgestate){
		
		this.bridgestate = bridgestate;
	}
	public String getbridgestate(){
		
		return this.bridgestate;
		
	}
	public void setbridgetype(String bridgetype){
		
		this.bridgetype = bridgetype;
	}
	public String getbridgetype(){
		
		return this.bridgetype;
		
	}
	public void setchannel1(String channel1){
		
		this.channel1 = channel1;
	}
	public String getchannel1(){
		
		return this.channel1;
		
	}
	public void setchannel2(String channel2){
		
		this.channel2 = channel2;
	}
	public String getchannel2(){
		
		return this.channel2;
		
	}
	public void setuniqueid1(String uniqueid1){
		
		this.uniqueid1 = uniqueid1;
	}
	public String getuniqueid1(){
		
		return this.uniqueid1;
		
	}
	public void setuniqueid2(String uniqueid2){
		
		this.uniqueid2 = uniqueid2;
	}
	public String getuniqueid2(){
		
		return this.uniqueid2;
		
	}
	public void setcallerid1(String callerid1){
		
		this.callerid1 = callerid1;
	}
	public String getcallerid1(){
		
		return this.callerid1;
		
	}
	public void setcallerid2(String callerid2){
		
		this.callerid2 = callerid2;
	}
	public String getcallerid2(){
		
		return this.callerid2;
		
	}
	public BridgeEvent(Object source) {
		
		super(source);
		
		
	}

}
