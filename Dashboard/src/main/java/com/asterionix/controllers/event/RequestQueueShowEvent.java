package com.asterionix.controllers.event;

import java.util.List;

import com.asterionix.controllers.util.Queues;



public class RequestQueueShowEvent implements AsteriskEvent{

	private List<Queues> asteriskQueues;
	
	public RequestQueueShowEvent(List<Queues> q){
		
		this.asteriskQueues = q;
	}
	
	public void addAsteriskQueue(Queues q){
		
		asteriskQueues.add(q);
		
	}
	public List<Queues> getAsteriskQueue(){
		
		return this.asteriskQueues;
	}

	@Override
	public void onEvent(Object source, AsteriskEvent ev) {
		
		
	}

	
	
}
