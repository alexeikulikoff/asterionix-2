package com.asterionix.exceptions;

import java.net.SocketException;

public class SocketExceptionExt  extends Exception{

	private static final long serialVersionUID = 1L;
	public SocketExceptionExt(){}
	
	public SocketExceptionExt(String msg){
		super(msg);
	}
}
