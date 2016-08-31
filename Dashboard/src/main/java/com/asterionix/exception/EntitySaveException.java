package com.asterionix.exception;

public class EntitySaveException extends Exception{

	private static final long serialVersionUID = 1L;

	public EntitySaveException(String message){
		super(message);
	}
	public EntitySaveException(Throwable cause){
		super(cause);
	}
	public EntitySaveException(String message, Throwable cause){
		super(message,cause);
	}
}
