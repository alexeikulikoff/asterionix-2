package com.asterionix.response;

import java.util.ArrayList;
import java.util.EventObject;

import com.asterionix.main.AsteriskClient;






/*  Response have to be implemented for Response Classes  
 *  returned by responseBuilder method
 */	

public interface AsteriskResponse {
	
	String getMessage();
	String getResponse();
	void onResponse(AsteriskClient target, AsteriskResponse response);
	
}
