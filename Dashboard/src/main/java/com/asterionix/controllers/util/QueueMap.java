package com.asterionix.controllers.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueueMap {
	private Map<String, ArrayList<Agent> > queueContainer;
	
	public QueueMap(){
		queueContainer = new HashMap<String, ArrayList<Agent> >();
	}
	public void addQueue(String queueName){
		queueContainer.put(queueName, null);
	}
	public void addAgentToQueue(String queueName, Agent agent){
		
	}
}
