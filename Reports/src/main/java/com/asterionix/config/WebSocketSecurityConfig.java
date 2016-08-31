package com.asterionix.config;

import java.util.List;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.messaging.access.intercept.ChannelSecurityInterceptor;



/**
 * Controller for managing {@link Message} instances.
 *
 * @author Rob Winch
 *
 */
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
    	messages
		.simpMessageDestMatchers("/queue/**", "/topic/**").denyAll()
		.simpSubscribeDestMatchers("/queue/**/*-user*", "/topic/**/*-user*").denyAll()
		.anyMessage().authenticated();
    }
//
   // @Override
   // protected void configureOutbound(MessageSecurityMetadataSourceRegistry messages) {
   //     messages
  //          .anyMessage().hasRole("USER");
  //  }


//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.configureBrokerChannel().setInterceptors(securityContextChannelInterceptor(),outboundChannelSecurity());
//    }


}