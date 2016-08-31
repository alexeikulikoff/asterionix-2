package com.asterionix.controllers.util;

import com.asterionix.controllers.event.WsEvents;

public class WsMessage {

	  private WsEvents messageType;
	  private WsObject wsObject;
	  
	  public WsMessage(WsEvents onqueuememberadded, WsObject o){
		  this.messageType = onqueuememberadded;
		  this.wsObject = o;
	  }
	  public WsObject getWsObject(){
		 return wsObject;
	  }
	  public WsEvents getMessageType(){
		  return this.messageType;
	  }
	  
}
