package com.asterionix.controllers.response;

import java.io.BufferedReader;


public interface ResponseBuilder {

	void setAction(Action action);
	
	AsteriskResponse buildActionResponse( String s, BufferedReader reader);
	
}
