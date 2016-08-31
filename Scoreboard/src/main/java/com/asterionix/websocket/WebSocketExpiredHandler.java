package com.asterionix.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.session.events.SessionExpiredEvent;

public class WebSocketExpiredHandler<S>implements ApplicationListener<ContextRefreshedEvent> {

	
	public WebSocketExpiredHandler(){
		super();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		
	}
	

}
