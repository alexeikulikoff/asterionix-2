package com.asterionix.config;




import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;




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
		.simpMessageDestMatchers("/queue/**", "/topic1/**").anonymous()
		.simpSubscribeDestMatchers("/queue/**/*-user*", "/topic/**/*-user*").anonymous()
		.simpSubscribeDestMatchers("/queue/**/*-user*", "/topic/**/*-user*").anonymous()
		.anyMessage().anonymous();
		
    }
    @Override
    protected boolean sameOriginDisabled() {
        return true;
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