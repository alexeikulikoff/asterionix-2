package com.asterionix.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession>  {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic1/");
		registry.setApplicationDestinationPrefixes("/");
	}
	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/messages1").withSockJS();
		
		
	}
	
	protected boolean sameOriginDisabled() {
	        return true;
	}

}
