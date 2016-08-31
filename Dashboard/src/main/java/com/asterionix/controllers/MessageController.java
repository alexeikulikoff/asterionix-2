/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.asterionix.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

import com.asterionix.dao.ActiveWebSocketUserRepository;
import com.asterionix.dao.InstantMessage;
import com.asterionix.dao.UserEntity;
import com.asterionix.security.CurrentUser;


/**
 * Controller for managing {@link Message} instances.
 *
 * @author Rob Winch
 *
 */
@Controller
@RequestMapping("/")
public class MessageController {
    private SimpMessageSendingOperations messagingTemplate;
    private ActiveWebSocketUserRepository activeUserRepository;
    
    @Autowired
    private RedisTemplate<String, String> template;
    
    @Autowired
	private RedisOperationsSessionRepository repository;
    
    @Autowired
    public MessageController(ActiveWebSocketUserRepository activeUserRepository,SimpMessageSendingOperations messagingTemplate) {
        this.activeUserRepository = activeUserRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping("/")
    public String im(HttpSession session) {
    	
        return "login";
    }

    @RequestMapping("/send")
    public void send() {
    	this.messagingTemplate.convertAndSend("/topic/friends/signin", Arrays.asList("ONE"));
    }

    @MessageMapping("/im")
    public void im(InstantMessage im, @CurrentUser UserEntity currentUser) {
        
    	im.setFrom(currentUser.getFirstname());
        
        messagingTemplate.convertAndSendToUser(im.getTo(),"/queue/messages",im);
       
      
        
        messagingTemplate.convertAndSendToUser(im.getFrom(),"/queue/messages",im);
    }

    @SubscribeMapping("/users")
    public List<String> subscribeMessages() throws Exception {
        return activeUserRepository.findAllActiveUsers();
    }
}
