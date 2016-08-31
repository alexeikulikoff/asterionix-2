package com.asterionix.exception;

import java.net.SocketException;

public class SocketExceptionExt  extends SocketException{

	private static final long serialVersionUID = 1L;
	public SocketExceptionExt(){}
	
	public SocketExceptionExt(String msg){
		super(msg);
	}
}
