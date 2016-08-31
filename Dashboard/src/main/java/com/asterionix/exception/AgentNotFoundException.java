package com.asterionix.exception;

public class AgentNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

public AgentNotFoundException(String message) {
       super(message);
}

public AgentNotFoundException(Throwable cause) {
       super(cause);
}

public AgentNotFoundException(String message, Throwable cause) {
       super(message, cause);
}

public AgentNotFoundException(String message, Throwable cause,
           boolean enableSuppression, boolean writableStackTrace) {
       super(message, cause, enableSuppression, writableStackTrace);
}
	
}
