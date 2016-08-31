package com.asterionix.response;

import com.asterionix.main.AsteriskClient;

public class LogOffResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public LogOffResponse(Object source) {
		
		super(source);
		
	}
	@Override
	public void onResponse(AsteriskClient target, AsteriskResponse response) {
		
		target.OnLogOffResponse(response);
	}
	public void setMessage(String msg){
		
		this.message = msg;
		
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

}
