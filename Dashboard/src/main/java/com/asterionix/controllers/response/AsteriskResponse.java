package com.asterionix.controllers.response;

import com.asterionix.controllers.dashboard.DashBoard;


public interface AsteriskResponse {
	
	String getMessage();
	String getResponse();
	void onResponse(DashBoard target, AsteriskResponse response);
	
}
