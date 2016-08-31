package com.asterionix.agi;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.exceptions.AgiServerException;
import com.asterionix.main.AsteriskClientImpl;


public class AgiServer {
	
	static Logger logger = LoggerFactory.getLogger(AgiServer.class);
	 
	private  int port ;
	private  int backlog ;
	private String host;
	private AsteriskClientImpl client;
	
	private CoursesRepository coursesRepository;
	private AbandonRepository abandonRepository;
	private CustomersRepository customersRepository;
	
	private ServerSocket listener ;

	
	public AgiServer(int port, int backlog,String host, AsteriskClientImpl client,CoursesRepository coursesRepository,
								AbandonRepository abandonRepository, CustomersRepository customersRepository){
		this.port = port;
		this.backlog = backlog;
		this.host = host;
		this.client = client;
		this.coursesRepository 		= coursesRepository;
	    this.abandonRepository 		= abandonRepository;
	    this.customersRepository 	= customersRepository;
	    
	}
	public void start() throws AgiServerException{
		
	
		
		InetAddress bindAddr;
		SocketExcecutor excecutor =  new SocketExcecutor(client,coursesRepository,abandonRepository,customersRepository );
		try {
			bindAddr = InetAddress.getByName(host);
			try {
				listener = new ServerSocket(port,backlog,bindAddr);
				try {
			          while (true) {
			        	
			        	 Socket socket =  listener.accept();
		        		 excecutor.Execute(socket);
			        	
			        	
			          }
			    }    
			    finally {
			           listener.close();
			    }
			} catch (IOException e) {
				
				logger.error(e.getMessage());
				
				throw new  AgiServerException("Can't bind Ip Address");
			}
		
		} catch (UnknownHostException e) {
			
			logger.error(e.getMessage());
			
			throw new  AgiServerException("Can't create Socket");
		}
	}
	
}
