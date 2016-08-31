package com.asterionix.agi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.asterionix.exceptions.AgiServerException;
import com.asterionix.main.AsteriskClientImpl;
import com.asterionix.main.SocketConnection;
import com.asterionix.net.AsteriskSocketImpl;

import utils.User;

@Component
public class AgiApplicationRunner implements ApplicationRunner{

	@Autowired
	private CustomersRepository customersRepository;

	@Autowired
	private CoursesRepository coursesRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private QueueloginRepository queueloginRepository;
	
	@Autowired
	private AgentsCoursesRepository agentsCoursesRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Autowired
	private AbandonRepository abandonRepository;
	
	@Autowired
	private ApplicationContext context;
	
	static   Logger logger = LoggerFactory.getLogger(AgiApplicationRunner.class);
	private  SocketConnection conection;
	private  User user;
	private  AsteriskClientImpl client;
	private  AsteriskSocketImpl asteriskSocket = null;
	
	private  AgiServer server;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	
		Configs co = (Configs) context.getBean("Configs");
		conection = new SocketConnection(co.getConfig().getAsterisk_host(),Integer.parseInt(co.getConfig().getAsterisk_port())) ;
		user = new User(co.getConfig().getUsername(),co.getConfig().getPassword());
		
		client = new AsteriskClientImpl(conection,user,agentRepository, coursesRepository,queueloginRepository, 
				agentsCoursesRepository,phoneRepository);
		try {
			
			client.openAsteriskSocket();

			client.doAsteriskLogin();
			
			try {
				   String agi_server_host 	= co.getConfig().getAgi_server_host();
				   int agi_server_port 	= Integer.parseInt(co.getConfig().getAgi_server_port());
				   int agi_server_backlog 	= Integer.parseInt(co.getConfig().getAgi_server_backlog());
				    	
				   server = new AgiServer(agi_server_port,agi_server_backlog,agi_server_host,client,coursesRepository,abandonRepository,customersRepository);
				   
				   server.start();
						   
				  
						
				} catch (AgiServerException e) {
						logger.error("Agi Server Error: " + e );
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	}

}
