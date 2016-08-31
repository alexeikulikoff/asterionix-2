package utils;

public class Agent {
	private String agentName;
	private String agentNum;
	private String cssClass;
	
	private String inuse;
	
	public Agent(String agentName, String agentNum, String cssClass){
		this.agentName = agentName;
		this.agentNum = agentNum;
		this.cssClass = cssClass;
	}
	public String getAgentName(){
		return this.agentName;
	}
	public String getAgentNum(){
		return this.agentNum;
	}
	public void setAgentName(String a){
		this.agentName = a;
	}
	public void setAgentNum(String a){
		this.agentNum = a;
	}
	public String getcssClass(){

		return this.cssClass;
		
		
	}
}
