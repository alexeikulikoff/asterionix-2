package com.asterionix.controllers.response;

import com.asterionix.controllers.dashboard.DashBoard;

import com.asterionix.controllers.util.Queues;

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
	public void onResponse(DashBoard target, AsteriskResponse response) {
		
		target.OnQueueShowResponse(response);
		
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
