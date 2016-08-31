package com.asterionix.controllers.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.asterionix.controllers.event.AsteriskEvent;
import com.asterionix.controllers.event.DashBoardEventListener;
import com.asterionix.controllers.event.EventBuilder;
import com.asterionix.controllers.response.AsteriskResponse;
import com.asterionix.controllers.response.ErrorResponse;
import com.asterionix.controllers.response.ResponseBuilder;
import com.asterionix.exception.SocketExceptionExt;


public class AsteriskSocketImpl extends AbstractAsteriskSocket implements Runnable{

	public static final String EVENT_RTCPSENT = "Event: Hangup";
	public static final String EVENT_BRIDGE = "Event: Dial";
	
	static Logger logger = LoggerFactory.getLogger(AsteriskSocketImpl.class);
	
	private DashBoardEventListener listener;

	
	public static volatile boolean die = true;

	private BufferedReader reader;
	
	private EventBuilder eventBuilder;
	
	private ResponseBuilder responseBuilder;
	
	public AsteriskSocketImpl(String host, int port, DashBoardEventListener listener, ResponseBuilder responseBuilder) throws  SocketExceptionExt {
		super(host, port);
		
		this.listener = listener;
		
		this.eventBuilder = new EventBuilder();
		
		this.responseBuilder =  responseBuilder;
		
		openSocket();
		
		try {
		
			reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
	
		} catch (IOException e) {
			
			throw new SocketExceptionExt("Socket connection error");
		}
	}

	private void dispatchEvent(AsteriskEvent event)
	{
		if (event != null){
			
			listener.dispatchEvent(event);
				
		}else{
			 // logger.info("Unable to build event");
		 }
	}
	private void dispatchEvent(AsteriskResponse response)
	{
		if (response != null){
			
			listener.dispatchEvent(response);
				
		}else{
			 // logger.info("Unable to build event");
		 }
	}
	@Override
	public void run() {
		String responseLine = null;
		AsteriskEvent event;

		while(die){
		  try {
			  responseLine = reader.readLine();
			 
			  if (responseLine != null){
				  if (responseLine.startsWith("Event:")  ){
					 event = eventBuilder.buildEvent(responseLine,reader);
					 dispatchEvent(event);
				  }
				 if (responseLine.startsWith("Response:")){
					 if (responseLine.endsWith("Error")){
						 ErrorResponse err = new ErrorResponse(this);
						 err.setMessage("Error");
						 listener.dispatchEvent(err);
						 die=false;
						 continue;
					 }
					 AsteriskResponse response = responseBuilder.buildActionResponse(responseLine, reader);
					 dispatchEvent(response);
					
				 }
			  }
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}