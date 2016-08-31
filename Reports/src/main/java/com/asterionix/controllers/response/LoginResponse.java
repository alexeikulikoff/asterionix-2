package com.asterionix.controllers.response;

import com.asterionix.controllers.dashboard.DashBoard;



public class LoginResponse extends AbstractResponse {
	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public LoginResponse(Object source) {
		
		super(source);
	}

	public void setMessage(String msg){
		
		this.message = msg;
	}

	@Override
	public void onResponse(DashBoard target, AsteriskResponse response) {

		target.OnLoginResponse(response);
		
	}


	@Override
	public String getMessage() {
		
		return this.message;
	}

}
