package com.asterionix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.ExpiringSession;

import com.asterionix.dao.ActiveWebSocketUserRepository;
import com.asterionix.websocket.WebSocketConnectHandler;
import com.asterionix.websocket.WebSocketDisconnectHandler;
import com.asterionix.websocket.WebSocketExpiredHandler;

@Configuration
public class WebSocketHandlersConfig <S extends ExpiringSession> {

	@Bean
	public WebSocketConnectHandler<S> webSocketConnectHandler( SimpMessagingTemplate messagingTemplate, ActiveWebSocketUserRepository repository) {
		
		return new WebSocketConnectHandler<S>(messagingTemplate, repository);
	}

	@Bean
	public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate, ActiveWebSocketUserRepository repository) {
		
		return new WebSocketDisconnectHandler<S>(messagingTemplate, repository);
	}
	@Bean
	public WebSocketExpiredHandler<S> webSocketExpiredHandler(){
		return new WebSocketExpiredHandler<S>();
	}

}
