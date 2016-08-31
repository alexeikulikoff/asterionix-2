package com.asterionix.controllers.custom;

import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



public class AsteriskDisconnectHandler  implements ApplicationListener<AsteriskLogoffEvent>{

	@Override
	public void onApplicationEvent(AsteriskLogoffEvent event) {
		// TODO Auto-generated method stub
		
	}

	
}
