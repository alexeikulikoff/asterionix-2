package com.asterionix.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.main.AbstractAsteriskClient;

public class AddAgentToQueueAction extends AbstractAction implements Action{
	
	Logger logger = LoggerFactory.getLogger(AddAgentToQueueAction.class);
	
	private String agent;
	private String queue;
	private String penalty;
	
	public AddAgentToQueueAction(Socket socket, String agent, String queue, String penalty) {
		super(socket);
		
		action = this;
		
		this.agent = agent;
		
		this.queue = queue;
		
		this.penalty = penalty;
	}

	@Override
	public String getCommand() {
		
		String result = null;
		try{
			  int num = Integer.parseInt(penalty);
			 
			  String pen = Integer.toString(num);
			  
			  result = "Action: COMMAND\r\ncommand: queue add member " + agent + " to " + queue + " penalty  " + pen +"\r\n\r\n";
			  
			} catch (NumberFormatException e) {
				result = "Action: COMMAND\r\ncommand: queue add member " + agent + " to " + queue + "\r\n\r\n";
			}
		return result;
		
	}
	public AsteriskResponse buildActionResponse(){
		
		AddAgentToQueueResponse response = new AddAgentToQueueResponse(this);
		
		for(int k=0 ; k < buffer.size(); k++){
			
			response.addMessage(buffer.get(k));
		}
		
		return response;
		
	}
	public void fillResponseBuffer(String line, BufferedReader reader) {
		
		buffer.clear();
		
		try {
			buffer.add(line);
		
			String s;
			while(!(s = reader.readLine()).contains("--END COMMAND--") ){
		//		System.out.println(s);
				buffer.add(s);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
