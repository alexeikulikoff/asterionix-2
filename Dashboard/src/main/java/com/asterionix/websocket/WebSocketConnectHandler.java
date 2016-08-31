package com.asterionix.websocket;

import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;



import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.asterionix.dao.ActiveWebSocketUser;
import com.asterionix.dao.ActiveWebSocketUserRepository;


public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {
	
	private ActiveWebSocketUserRepository repository;
	
	//private SimpMessageSendingOperations messagingTemplate;
	
	private SimpMessagingTemplate simpMessagingTemplate; 

	public WebSocketConnectHandler(SimpMessagingTemplate messagingTemplate,ActiveWebSocketUserRepository repository) {
			
		super();
	//	this.messagingTemplate = messagingTemplate;
		this.simpMessagingTemplate = messagingTemplate;
		this.repository = repository;
	}

	public void onApplicationEvent(SessionConnectEvent event) 
	{
			MessageHeaders headers = event.getMessage().getHeaders();
			
			Principal user = SimpMessageHeaderAccessor.getUser(headers);
			
			if (user == null) {
				return;
			}
			String id = SimpMessageHeaderAccessor.getSessionId(headers);
			
			this.repository.save(new ActiveWebSocketUser(id, user.getName(), Calendar.getInstance()));
		
			this.simpMessagingTemplate.convertAndSend("/topic/connect", Arrays.asList(user.getName()));
	} 

}
