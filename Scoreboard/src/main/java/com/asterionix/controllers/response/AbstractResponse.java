package com.asterionix.controllers.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.EventObject;

abstract class AbstractResponse extends EventObject implements AsteriskResponse{
	
	private static final long serialVersionUID = 1L;

	private String response;

	public String getResponse(){
		
		return this.response;
	}
	public void setResponse(String resp){
		
		this.response = resp;
	}
	public AbstractResponse(Object source) {
		
		super(source);
	}
}
