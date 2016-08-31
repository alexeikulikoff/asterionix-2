package com.asterionix.controllers.response;

import java.net.Socket;

public class OriginateAction extends AbstractAction implements Action{

	private String channel;
	private String context;
	private String exten;
	private int priority;
	private String callerid;
	private int timeout;
	
	public void setChannel(String channel){
		
		this.channel = channel;
		
	}
	public String getChannel(){
		
		return this.channel;
		
	}
	public String getContext(){
		
		return this.context;
		
	}
	public void setContext(String context){
		
		this.context = context;
		
	}
	
	public String getExten(){
		
		return this.exten;
		
	}
	public void setExten(String exten){
		
		this.exten = exten;
		
	}
	public int getPriority(){
		
		return this.priority;
		
	}
	
	public void setPriority(int priority){
		
		this.priority = priority;
		
	}
	public String getCallerid(){
		
		return this.callerid;
		
	}
	public void setCallerid(String callerid){
		
		this.callerid = callerid;
		
	}
	public void setTimeout(int timeout){
		
		this.timeout = timeout;
	}
	public int getTimeout(){
		
		return this.timeout;
		
	}
	public OriginateAction(Socket socket) {
		
		super(socket);
		
		action = this;
		
	}
	@Override
	public String getCommand() {
	
		return "Action: Originate\r\nChannel:" + channel + "\r\nContext:" + context +"\r\nExten:" + exten + "\r\nPriority:" + priority+"\r\nCallerid:"+ callerid + "\r\nTimeout:"+timeout + "\r\n\r\n";
		
	}

}
