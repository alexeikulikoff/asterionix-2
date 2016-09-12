package com.asterionix.websocket;
import java.util.Arrays;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


import com.asterionix.controllers.custom.AsteriskDisconnectHandler;
import com.asterionix.controllers.custom.AsteriskLogoffEvent;
import com.asterionix.controllers.response.LogOffAction;
import com.asterionix.controllers.response.LogOffResponse;
import com.asterionix.controllers.response.ResponseBuilderImpl;
import com.asterionix.controllers.socket.AsteriskSocket;
import com.asterionix.controllers.util.AsteriskHandler;
import com.asterionix.dao.ActiveWebSocketUser;
import com.asterionix.dao.ActiveWebSocketUserRepository;

public class WebSocketDisconnectHandler<S>  implements ApplicationListener<SessionDisconnectEvent> {

		private ActiveWebSocketUserRepository repository;
		
		private AsteriskSocket socket;
		private SimpMessageSendingOperations messagingTemplate;
		
		private ResponseBuilderImpl responseBuilder;
		
		public void setResponseBuilder(ResponseBuilderImpl responseBuilder){
			this.responseBuilder = responseBuilder;
		}
		public void setSocket(AsteriskSocket socket){
			this.socket = socket;
		}
		public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate,ActiveWebSocketUserRepository repository) 
		{
				super();
				this.messagingTemplate = messagingTemplate;
				this.repository = repository;
		}

		public void onApplicationEvent(SessionDisconnectEvent event) 
		{
			
			String id = event.getSessionId();
			if (id == null) {
				return;
			}
			ActiveWebSocketUser user = this.repository.findOne(id);
			if (user == null) {
				return;
			}

			this.repository.delete(id);
			LogOffAction action = new LogOffAction(socket.getSocket());
			
			action.setResponseClass(LogOffResponse.class);
			
			responseBuilder.setAction(action);
			
			action.sendAction();
			
			this.messagingTemplate.convertAndSend("/topic/disconnect",Arrays.asList(user.getUsername()));
			
			}

}
