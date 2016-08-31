package com.asterionix.controllers.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class ResponseBuilderImpl implements ResponseBuilder{

	private Action action; 
	
	public ResponseBuilderImpl(){
	}
	@Override
	public AsteriskResponse buildActionResponse(String s, BufferedReader reader) {
		
		action.fillResponseBuffer(s, reader);
	
		AsteriskResponse response = action.buildActionResponse();
		
		return response;
	}


	@Override
	public void setAction(Action action) {
		
		this.action = action;
		
	}


	
}
