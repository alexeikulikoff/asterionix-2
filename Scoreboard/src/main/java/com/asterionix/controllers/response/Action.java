package com.asterionix.controllers.response;

import java.io.BufferedReader;
import java.util.ArrayList;

public interface Action {
	
	String getCommand();
	
	void fillResponseBuffer(String line, BufferedReader reader);
			
	AsteriskResponse buildActionResponse();
	
	void testBuffer();
}
