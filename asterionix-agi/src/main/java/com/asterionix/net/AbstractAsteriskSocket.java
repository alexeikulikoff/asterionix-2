package com.asterionix.net;

import java.io.IOException;
import java.net.Socket;

import com.asterionix.exceptions.SocketExceptionExt;



abstract class AbstractAsteriskSocket  {
	
	private String host;
	private int port;
	
	protected Socket socket = null;  
	
	AbstractAsteriskSocket(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void openSocket() throws SocketExceptionExt {
		try {
			  socket = new Socket(host, port);
			} catch (IOException e) {
					throw new SocketExceptionExt("Error socket");
			}
		}

	public void closeSocket()  throws SocketExceptionExt{
		try {
			socket.close();
		} catch (IOException e) {
			throw new SocketExceptionExt("Error socket");
		}   	
	}

	public Socket getSocket(){
		return socket;
	}

}
