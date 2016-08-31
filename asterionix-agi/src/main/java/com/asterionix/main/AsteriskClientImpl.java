package com.asterionix.main;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asterionix.agi.AgentEntity;
import com.asterionix.agi.AgentRepository;
import com.asterionix.agi.AgentsCoursesEntity;
import com.asterionix.agi.AgentsCoursesRepository;
import com.asterionix.agi.CoursesEntity;
import com.asterionix.agi.CoursesRepository;
import com.asterionix.agi.PhoneEntity;
import com.asterionix.agi.PhoneRepository;
import com.asterionix.agi.QueueloginEntity;
import com.asterionix.agi.QueueloginRepository;

import com.asterionix.events.QueueMemberAddedEvent;
import com.asterionix.events.QueueMemberRemovedEvent;
import com.asterionix.events.QueueMemberStatusEvent;
import com.asterionix.response.AsteriskResponse;
import com.asterionix.response.QueueShowResponse;

import utils.Agent;
import utils.Queue;
import utils.User;
public class AsteriskClientImpl extends AbstractAsteriskClient implements AsteriskClient{
	
	static Logger logger = LoggerFactory.getLogger(AsteriskClientImpl.class);
	private String extension;
	private String agi_callerid;
	private AgentRepository agentRepository;
	private CoursesRepository coursesRepository;
	private QueueloginRepository queueloginRepository;
	private AgentsCoursesRepository agentsCoursesRepository;
	private PhoneRepository phoneRepository;
	
	public  int soundflag;
	private Socket serverSocket;

	private volatile int current_agent_id=0;
	
	public AsteriskClientImpl(SocketConnection con, User user, AgentRepository agentRepository, 
							  CoursesRepository coursesRepository,
							  QueueloginRepository queueloginRepository,
							  AgentsCoursesRepository agentsCoursesRepository,
							  PhoneRepository phoneRepository) {
		super(con, user);
		
		this.agentRepository= agentRepository;
		this.coursesRepository = coursesRepository;
		this.queueloginRepository = queueloginRepository;
		this.agentsCoursesRepository = agentsCoursesRepository;
		this.phoneRepository = phoneRepository;
		addAsteriskClientEventListener(this);
	}

	public void setServerSocket(Socket socket){
		this.serverSocket = socket;
	}
	@Override
	public void OnLoginResponse(AsteriskResponse response) {
		  logger.info("Logging on Asterisk Successful");
	}

	@Override
	public void OnLogOffResponse(AsteriskResponse response) {
		 logger.info("Logging off Asterisk Successful");
	}

	@Override
	public void OnQueueMemberAddedEvent(QueueMemberAddedEvent event) {
		
		if (current_agent_id > 0){
			
			String queueName =  event.getqueue().replaceAll("\\s+","");
			
			CoursesEntity coursesEntity = coursesRepository.findByCoursename(queueName);
			 
			PhoneEntity phoneEntity = phoneRepository.findByPnumber(event.getmembername().toUpperCase()) ;
			QueueloginEntity queueloginEntity  = new QueueloginEntity();
			queueloginEntity.setAid(current_agent_id);
			queueloginEntity.setPid(phoneEntity.getId());
			queueloginEntity.setAction(1);
			queueloginEntity.setQueue_id(coursesEntity.getId());
			queueloginRepository.save(queueloginEntity);
			current_agent_id = 0;
		}
	
	}

	@Override
	public void OnQueueMemberRemovedEvent(QueueMemberRemovedEvent event) {
		if (current_agent_id > 0){
			
			String queueName =  event.getqueue().replaceAll("\\s+","");
			CoursesEntity coursesEntity = coursesRepository.findByCoursename(queueName);
			
			PhoneEntity phoneEntity = phoneRepository.findByPnumber(event.getmembername().toUpperCase()) ;
			QueueloginEntity queueloginEntity  = new QueueloginEntity();
			queueloginEntity.setAid(current_agent_id);
			queueloginEntity.setPid(phoneEntity.getId());
			queueloginEntity.setAction(0);
			queueloginEntity.setQueue_id(coursesEntity.getId());
			queueloginRepository.save(queueloginEntity);
			current_agent_id = 0;
		}
		
	}

	@Override
	public void OnQueueMemberStatusEvent(QueueMemberStatusEvent event) {
	
		
	}

	@Override
	public void OnQueueShowResponse(AsteriskResponse response) {
		 QueueShowResponse resp = (QueueShowResponse)response;
		 
		 String clearExtension = extension.replaceAll("\\s+","");
		 String clear_agi_callerid =  agi_callerid.replaceAll("\\s+","");
				 
		 AgentsCoursesEntity agentsCoursesEntity =  agentsCoursesRepository.findByExtensionContaining(clearExtension);
		 
		 CoursesEntity coursesEntity = coursesRepository.findById(agentsCoursesEntity.getCourses_id());
		 
		 boolean found ;
		 for(Queue qs : resp.getQueues().getQueues()){
			 if (qs.getQueueName().equals(coursesEntity.getCoursename())){
				 found = false;
				 for (Agent  a : qs.getAgents()){
					 String agentName = a.getAgentName().split("/")[1];
					 if (agentName.contains(clear_agi_callerid))
					 {
						 found = true;
						 logger.info("Remove Agent: [" + "SIP/"+clear_agi_callerid+" ] from queue: [" + coursesEntity.getCoursename() +"]" );
						 
						 current_agent_id  = agentsCoursesEntity.getAgent_id();
					
						
						 soundflag = 1;
						 
						 removeFromQueue("SIP/"+clear_agi_callerid, coursesEntity.getCoursename());

						 break;
					 }
				 }
				 if (found == false){
					 logger.info("Add Agent: [" + "SIP/"+clear_agi_callerid+" ] to queue: [" + coursesEntity.getCoursename() +"]" );
					 
					 current_agent_id  = agentsCoursesEntity.getAgent_id();
					 
					 soundflag = 0;
					 
					 addToQueue("SIP/"+clear_agi_callerid, coursesEntity.getCoursename(),agentsCoursesEntity.getPenalty());
					
				 }
			 }
		 }
	}

	@Override
	public void OnOriginateResponse(AsteriskResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAddAgentToQueueResponse(AsteriskResponse response) {
		try {
			PrintStream os = new PrintStream(serverSocket.getOutputStream());
			os.println("SET VARIABLE QSOUND agent-loginok\n");
		} catch (IOException e) {
			
			logger.error("Socket has already beed closed!");
			
		}  finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				
				logger.error("Can't close socket!");
				
			}
		}
	}

	@Override
	public void OnRemoveAgentFromQueueResponse(AsteriskResponse response) {
	
		try {
			PrintStream os = new PrintStream(serverSocket.getOutputStream());
			os.println("SET VARIABLE QSOUND agent-loggedoff\n");
		} catch (IOException e) {
			
			logger.error("Socket has already beed closed!");
		}
		finally {
				try {
					serverSocket.close();
				} catch (IOException e) {
					
					logger.error("Can't close socket!");
				}
			}
	}

	@Override
	public void OnErrorResponse(AsteriskResponse response) {
		
	}
	public void setExtension(String s){
		this.extension = s;
	}
	public String getExtension(){
		return this.extension;
	}
	public void setAgi_callerid(String s){
		this.agi_callerid = s;
	}
	public String getAgi_callerid(){
		return this.agi_callerid;
	}
	
	

}
