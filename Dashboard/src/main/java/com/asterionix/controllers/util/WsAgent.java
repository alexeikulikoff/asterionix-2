package com.asterionix.controllers.util;

public class WsAgent implements WsObject{

	private String agentName;
	private String agentNum;
	private String agentCssClass;
	public WsAgent(String agentName, String agentNum, String agentCssClass){
		this.agentName = agentName;
		this.agentNum = agentNum;
		this.agentCssClass = agentCssClass;
	}
	public String getAgentName(){
		return this.agentName;
	}
	public String getAgentNum(){
		return this.agentNum;
	}
	public String getAgentCssClass(){
		return this.agentCssClass;
	}
	
}
