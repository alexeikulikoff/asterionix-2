package com.asterionix.response;

import com.asterionix.main.AsteriskClient;

import utils.Queues;

public class QueueShowResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	
	private Queues queues;
	
	public QueueShowResponse(Object source) {
		
		super(source);
		
	}
	public void setQueues(Queues queues){
		
		this.queues = queues;
		
	}
	public Queues getQueues(){
		
		return this.queues;
		
	}
	@Override
	public void onResponse(AsteriskClient target, AsteriskResponse response) {
		
		target.OnQueueShowResponse(response);
		
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
