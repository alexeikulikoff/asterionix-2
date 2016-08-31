package com.asterionix.controllers.util;


public class Peer {
	private String peerNum;
	private String peerName;
	private String cssClass;
	private String peer_queue;
	
	private String inuse;
	
	public Peer(String peerNum, String peerName, String cssClass,String peer_queue){
		this.peerNum = peerNum;
		this.peerName = peerName;
		this.cssClass = cssClass;
		this.peer_queue = peer_queue;
	}
	public String getPeerNum(){
		return this.peerNum;
	}
	public String getPeerName(){
		return this.peerName;
	}
	public void setPeerName(String peerName){
		this.peerName = peerName;
	}
	
	public String getcssClass(){
		return this.cssClass;
	}
	public void setcssClass(String css){
		this.cssClass = css;
	}
	public String getPeer_queue(){
		return this.peer_queue;
	}
}
