package com.asterionix.controllers.custom;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.asterionix.controllers.socket.AsteriskSocket;

public class AsteriskLogoffService implements ApplicationEventPublisherAware{
	
	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher  publisher) {
		this.publisher = publisher;
	}
	public void fireAsteriskLogoff(AsteriskSocket socket){
		AsteriskLogoffEvent event = new AsteriskLogoffEvent(this, socket);
		publisher.publishEvent(event);
		return;
		
	}
	

}
