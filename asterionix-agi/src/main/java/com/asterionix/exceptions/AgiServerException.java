package com.asterionix.exceptions;

public class AgiServerException extends Exception{

	private static final long serialVersionUID = 1L;

	public AgiServerException(){}
	
	public AgiServerException(String msg){
		super(msg);
	}

}
