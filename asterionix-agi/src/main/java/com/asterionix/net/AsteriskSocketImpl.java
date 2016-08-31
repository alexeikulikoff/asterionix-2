package com.asterionix.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.events.AsteriskEvent;
import com.asterionix.events.EventBuilder;
import com.asterionix.exceptions.SocketExceptionExt;
import com.asterionix.main.AsteriskClientEventListener;
import com.asterionix.response.AsteriskResponse;
import com.asterionix.response.ResponseBuilder;



public class AsteriskSocketImpl extends AbstractAsteriskSocket implements Runnable{

	Logger logger = LoggerFactory.getLogger(AsteriskSocketImpl.class);
	
	private AsteriskClientEventListener listener;

	public static volatile boolean die = true;

	private BufferedReader reader;
	
	private EventBuilder eventBuilder;
	
	private ResponseBuilder responseBuilder;
	
	public AsteriskSocketImpl(String host, int port, AsteriskClientEventListener listener, ResponseBuilder responseBuilder) throws  SocketExceptionExt, com.asterionix.exceptions.SocketExceptionExt {
		
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
			
			//  logger.info("Unable to build event");
		 }
	}
	private void dispatchResponse(AsteriskResponse response)
	{
		if (response != null){
			
			listener.dispatchEvent(response);
				
		}else{
			
			//  logger.info("Unable to build response");
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
					 AsteriskResponse response = responseBuilder.buildActionResponse(responseLine, reader);
					 dispatchResponse(response);
				 }
					 
			  }
		 } catch (IOException e) {
				e.printStackTrace();
		}
	}
 }

}
