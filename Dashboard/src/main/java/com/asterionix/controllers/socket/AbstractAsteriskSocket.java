package com.asterionix.controllers.socket;

import java.io.IOException;
import java.net.Socket;

import com.asterionix.exception.SocketExceptionExt;



abstract class AbstractAsteriskSocket implements AsteriskSocket {
	
	private String host;
	
	private int port;
	
	protected Socket socket = null;  
	
	AbstractAsteriskSocket(String host, int port){
		this.host = host;
		this.port = port;
		
	}
	@Override
	public void openSocket() throws SocketExceptionExt {
		try {
			  socket = new Socket(host, port);
			} catch (IOException e) {
					throw new SocketExceptionExt("Error socket");
			}
		}
	@Override
	public void closeSocket()  throws SocketExceptionExt{
		try {
			socket.close();
		} catch (IOException e) {
			throw new SocketExceptionExt("Error socket");
		}   	
	}
	@Override
	public
	Socket getSocket(){
		return socket;
	}

}
