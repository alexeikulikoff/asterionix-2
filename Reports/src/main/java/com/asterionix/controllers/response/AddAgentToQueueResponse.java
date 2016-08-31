package com.asterionix.controllers.response;

import java.util.ArrayList;

import com.asterionix.controllers.dashboard.DashBoard;

public class AddAgentToQueueResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;

	private ArrayList<String> message; 
	
	public AddAgentToQueueResponse(Object source) {
		super(source);
		message  = new ArrayList<String>();
	}

	public void addMessage(String s){
		
		message.add(s);
	}
	
	
	@Override
	public String getMessage() {
		
		return message.get(message.size()-1);
	}

	@Override
	public void onResponse(DashBoard target, AsteriskResponse response) {
		
		target.OnAddAgentToQueueResponse(response);
		
	}

}
