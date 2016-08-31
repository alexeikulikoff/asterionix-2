package com.asterionix.controllers.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class RemoveAgentFromQueueAction extends AbstractAction implements Action{

	private String agent;
	private String queue;
	
	public RemoveAgentFromQueueAction(Socket socket,String agent, String queue) {
		super(socket);
		
		action = this;
		
		this.agent = agent;
		
		this.queue = queue;
	}

	@Override
	public String getCommand() {
		
		return "Action: COMMAND\r\ncommand: queue remove member " + agent + " from " + queue + "\r\n\r\n";
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
			//	System.out.println(s);
				buffer.add(s);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	

}
