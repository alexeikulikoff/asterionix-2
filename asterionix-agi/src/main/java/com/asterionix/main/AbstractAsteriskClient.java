package com.asterionix.main;


import java.io.PrintStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.exceptions.SocketExceptionExt;
import com.asterionix.net.AsteriskSocketImpl;
import com.asterionix.response.AddAgentToQueueAction;
import com.asterionix.response.AddAgentToQueueResponse;
import com.asterionix.response.LogOffAction;
import com.asterionix.response.LogOffResponse;
import com.asterionix.response.LoginAction;
import com.asterionix.response.LoginResponse;
import com.asterionix.response.QueueShowAction;
import com.asterionix.response.QueueShowResponse;
import com.asterionix.response.RemoveAgentFromQueueAction;
import com.asterionix.response.RemoveAgentFromQueueResponse;
import com.asterionix.response.ResponseBuilderImpl;

import utils.User;


public class AbstractAsteriskClient {

	Logger logger = LoggerFactory.getLogger(AbstractAsteriskClient.class);
	
	private ResponseBuilderImpl responseBuilder;
	
	private AsteriskSocketImpl asteriskSocket = null;
	
	private AsteriskClientEventListener listener;
	
	private SocketConnection con;
	private User user;
	
	public AbstractAsteriskClient(SocketConnection con, User user){
		super();
		this.con = con;
		this.responseBuilder = new ResponseBuilderImpl();
		this.user = user;
		
	}

	public void addAsteriskClientEventListener(AsteriskClient target){
		
		listener = new AsteriskClientEventListener(target);
	}
	public void openAsteriskSocket() throws SocketExceptionExt  {
		try {
		
			asteriskSocket = new AsteriskSocketImpl(con.getHost(), con.getPort(), listener, responseBuilder);
			
			asteriskSocket.die = true;
			
			Thread t = new Thread(asteriskSocket);
			t.start();
			
		} catch (Exception e) {
			
			throw new SocketExceptionExt("Error socket");
		}
	}
	public void closeAsteriskSocket() throws SocketExceptionExt{
		
		try {
		
			asteriskSocket.closeSocket();
			
			asteriskSocket = null;
			
		} catch (Exception e) {
			
			throw new SocketExceptionExt("Error socket");
		}
	}
	public void doAsteriskLogin(){
		
		
		LoginAction action = new LoginAction(asteriskSocket.getSocket(),user.getUser(), user.getPassword() );
		
		action.setResponseClass(LoginResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
		
	}
	public void doAsteriskLogoff(){
		
		
		LogOffAction action = new LogOffAction(asteriskSocket.getSocket());
		
		action.setResponseClass(LogOffResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
		
		
	}
	public void doActionQueueShow(){
		
		QueueShowAction action = new QueueShowAction(asteriskSocket.getSocket());
		
		action.setResponseClass(QueueShowResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
	}
	public void removeFromQueue(String callerId, String queueName){
		  
		RemoveAgentFromQueueAction action = new RemoveAgentFromQueueAction(asteriskSocket.getSocket(),callerId,queueName);
			
		action.setResponseClass(RemoveAgentFromQueueResponse.class);
			
		responseBuilder.setAction(action);
			
		action.sendAction();
	}
	public void addToQueue(String callerId, String queueName, String penalty){
		
		AddAgentToQueueAction action = new AddAgentToQueueAction(asteriskSocket.getSocket(),callerId,queueName, penalty);
		
		action.setResponseClass(AddAgentToQueueResponse.class);
			
		responseBuilder.setAction(action);
		
		action.sendAction();
		
			
	}
	public void originateCall(){
	/*	
		OriginateAction action = new OriginateAction(asteriskSocket.getSocket());
		action.setChannel("SIP/" + info.getsrcNumber());
		action.setContext("to-pstn");
		action.setExten(info.getdstNumber());
		action.setPriority(1);
		action.setTimeout(30000);
		action.setCallerid("2440024");
		
		
		action.setResponseClass(OriginateResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
	*/	
		
	}
/*	public void addToQueue(){
	
	AddAgentToQueueAction action = new AddAgentToQueueAction(asteriskSocket.getSocket(),queueMember,aq.getQueue());
	
	action.setResponseClass(QueueShowResponse.class);
		
	responseBuilder.setAction(action);
	
	action.sendAction();
	}
*/	
}
