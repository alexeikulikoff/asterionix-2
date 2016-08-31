package com.asterionix.controllers.response;

import java.util.ArrayList;
import java.util.EventObject;

import com.asterionix.controllers.dashboard.DashBoard;



/*  Response have to be implemented for Response Classes  
 *  returned by responseBuilder method
 */	

public interface AsteriskResponse {
	
	String getMessage();
	String getResponse();
	void onResponse(DashBoard target, AsteriskResponse response);
	
}
