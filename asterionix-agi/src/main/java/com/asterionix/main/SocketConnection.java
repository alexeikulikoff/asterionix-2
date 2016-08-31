package com.asterionix.main;

public class SocketConnection {

	private String host;
	private int port;
	public SocketConnection(String host, int port){
		
		this.host = host;
		
		this.port = port;
	}
	
	public String getHost(){
		return this.host;
	}
	
	public int getPort(){
		
		return this.port;
		
	}
}
