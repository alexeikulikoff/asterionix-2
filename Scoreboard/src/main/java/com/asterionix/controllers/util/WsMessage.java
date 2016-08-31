package com.asterionix.controllers.util;

import com.asterionix.controllers.event.WsEvents;

public class WsMessage {

	  private WsEvents messageType;
	  private WsConfig config;
	  
	  public WsMessage(WsEvents onqueuememberadded, WsConfig o){
		  this.messageType = onqueuememberadded;
		  this.config = o;
	  }
	  public WsConfig getConfig(){
		 return config;
	  }
	  public WsEvents getMessageType(){
		  return this.messageType;
	  }
	  
}
