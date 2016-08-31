package com.asterionix.net;

import java.io.BufferedReader;
import java.io.IOException;

import com.asterionix.exceptions.SocketExceptionExt;



public class AsteriskSocket extends AbstractAsteriskSocket implements Runnable{

	private  BufferedReader reader;
	
	public static volatile boolean die = true;
	
	AsteriskSocket(String host, int port) {
		super(host, port);
		
		try {
			openSocket();
			
		} catch (SocketExceptionExt e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String responseLine = null;
		while(die){
			 try {
				responseLine = reader.readLine();
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			 
		}
		
	}

}
