package com.asterionix.controllers.custom;

import org.springframework.context.ApplicationEvent;

import com.asterionix.controllers.socket.AsteriskSocket;

public class AsteriskLogoffEvent extends ApplicationEvent{

	private AsteriskSocket socket;
	
	public AsteriskLogoffEvent(Object source, AsteriskSocket socket) {
		super(source);
		this.socket = socket;
	}
	public AsteriskSocket getSocket(){
		return this.socket;
	}

}
