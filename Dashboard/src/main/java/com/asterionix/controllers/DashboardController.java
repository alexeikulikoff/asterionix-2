package com.asterionix.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.asterionix.config.AsterionixProperties;
import com.asterionix.config.ConfigBean;
import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.dashboard.*;
import com.asterionix.controllers.event.BridgeEvent;
import com.asterionix.controllers.event.DashBoardEventListener;
import com.asterionix.controllers.event.DialEvent;
import com.asterionix.controllers.event.JoinEvent;
import com.asterionix.controllers.event.QueueMemberAddedEvent;
import com.asterionix.controllers.event.QueueMemberRemovedEvent;
import com.asterionix.controllers.event.QueueMemberStatusEvent;
import com.asterionix.controllers.event.WsEvents;
import com.asterionix.controllers.response.AddAgentToQueueAction;
import com.asterionix.controllers.response.AddAgentToQueueResponse;
import com.asterionix.controllers.response.AsteriskResponse;
import com.asterionix.controllers.response.ErrorResponse;
import com.asterionix.controllers.response.LogOffAction;
import com.asterionix.controllers.response.LogOffResponse;
import com.asterionix.controllers.response.LoginAction;
import com.asterionix.controllers.response.LoginResponse;
import com.asterionix.controllers.response.OriginateAction;
import com.asterionix.controllers.response.OriginateResponse;
import com.asterionix.controllers.response.QueueShowAction;
import com.asterionix.controllers.response.QueueShowResponse;
import com.asterionix.controllers.response.RemoveAgentFromQueueAction;
import com.asterionix.controllers.response.RemoveAgentFromQueueResponse;
import com.asterionix.controllers.response.ResponseBuilderImpl;
import com.asterionix.controllers.socket.AsteriskSocketImpl;

import com.asterionix.controllers.util.Agent;
import com.asterionix.controllers.util.Callback;
import com.asterionix.controllers.util.HelloMessage;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Peer;
import com.asterionix.controllers.util.Queue;
import com.asterionix.controllers.util.QueueContainer;
import com.asterionix.controllers.util.Queues;
import com.asterionix.controllers.util.Response;
import com.asterionix.controllers.util.WsMessage;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.AgentRepository;
import com.asterionix.dao.CoursesEntity;
import com.asterionix.dao.CoursesRepository;
import com.asterionix.dao.PhoneEntity;
import com.asterionix.dao.PhoneRepository;
import com.asterionix.dao.QueueloginEntity;
import com.asterionix.dao.QueueloginRepository;
import com.asterionix.exception.AgentNotFoundException;
import com.asterionix.exception.SocketExceptionExt;
import com.asterionix.security.CustomUserDetails;
import com.asterionix.security.UserRepositoryUserDetailsService;
import com.asterionix.websocket.WebSocketDisconnectHandler;


@Controller
//@EnableConfigurationProperties(AsterionixProperties.class)
public class DashboardController extends AbstractDashBoard implements DashBoard{

	 static Logger logger = LoggerFactory.getLogger(DashboardController.class);
	 
	 /* License */	 
	 private boolean licensed = false;
	 private static volatile int MAX_AGENT_ALLOWED = 2;
	 private int agentCount = 0;
	 
	
	// private AsterionixProperties props;
	 @Autowired
	 private ConfigBean props;
	 
	 private static volatile boolean flag = true;
	 private static volatile int counter = 0;
	
	 private AsteriskSocketImpl asteriskSocket = null;
	 private DashBoardEventListener dashBoardEventListener;
	 private ResponseBuilderImpl responseBuilder;

	 private int agentLoggedId;
	 @Autowired
	 private AgentRepository repository;
	 
	 @Autowired
	 private PhoneRepository phoneRepository;
	 @Autowired
	 private QueueloginRepository queueloginRepository;
	 
	 @Autowired
	 private CoursesRepository coursesRepository;
	
	 private Properties prop = new Properties();
	 private  InputStream input = null;
	 
	 private QueueContainer queueContainer;
	 
	 @Autowired
	 private WebSocketDisconnectHandler<?> webSocketDisconnectHandler;
	 
	 private  CustomUserDetails currentUser;
	 private static class AgentQueue{
		 	private int id;
			private String queue;
			private String pnumber;
			
			public AgentQueue(){}
			public int getid(){
				return this.id;
			}
			public String getQueue(){
				return this.queue;
			}
			public void setId(int i){
				this.id = i;
			}
			public void setQueue(String s){
				this.queue= s;
			}
			public void setPnumber(String s){
				this.pnumber = s;
			}
			public String getPnumber(){
				return this.pnumber;
			}
		}
	
	
	 @Autowired
	  public DashboardController(SimpMessagingTemplate template)	  
	  {
		  super(template);
		  this.dashBoardEventListener = new DashBoardEventListener(this);
		  this.responseBuilder = new ResponseBuilderImpl();
		  
		  this.agentLoggedId = 0;
	
	  }
	
	 @RequestMapping(value = "/sounds/{filename}", method = RequestMethod.GET)
	 public ResponseEntity<InputStreamResource> downloadStuff(@PathVariable String filename)
	                                                                   throws IOException {
	    try{
	    	File file = new File(props.getSounds()+filename+".mp3");
		     HttpHeaders respHeaders = new HttpHeaders();
		     InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		     return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
	    }catch(Exception e){
	    	return null;
	    }
	 }

	  @RequestMapping("/view")
	  public String dashboardView(Model model, HttpSession session) {

		
	      
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  
		  session.setMaxInactiveInterval(9999999);
		  logger.info("Session max interval: [" + session.getMaxInactiveInterval() + "]");
			
		  currentUser = (CustomUserDetails) auth.getPrincipal();
		
		  model.addAttribute("currentUser", currentUser.getFirstname());
		  
		  model.addAttribute("configError", true);

		
	      model.addAttribute("license_demo", true);  
	      licensed = true;
	    
  	  return "dashboard";
	  }
	  
	  @RequestMapping("/callback")
	  public  @ResponseBody AjaxResponse callBack(@RequestBody Callback callback) {
		  AjaxResponse response = null;
		  try{
			  OriginateAction action = new OriginateAction(asteriskSocket.getSocket());
			  action.setChannel(callback.getCallerid1());
			  action.setContext(props.getCallbackContext());
			  action.setExten(callback.getCallerid2());
			  action.setPriority(1);
			  action.setTimeout(30000);
			  action.setCallerid(callback.getCallerid1().split("/")[1]);
			  action.setResponseClass(OriginateResponse.class);
			  responseBuilder.setAction(action);
			
			  action.sendAction();
			  logger.info("CalBack to [" + callback.getCallerid2() + "]");
			  response = new AjaxResponse("callback_ok");
		  }catch(Exception e){
			  response = new AjaxResponse("callback_fail");
		  }
		  return response;
	  }
	  
	  @RequestMapping("/addToQueue")
	  public @ResponseBody Response  addToQueue(@RequestBody  AgentQueue aq) {
		  
			AgentEntity ae = repository.findById(aq.getid());
			PhoneEntity pe =  phoneRepository.findByPnumber(aq.getPnumber());
			if (pe != null){
				if (aq.getQueue().length()> 0){
					QueueloginEntity queueloginEntity = new QueueloginEntity();
					queueloginEntity.setAction(1);
					queueloginEntity.setAid(ae.getId());
					queueloginEntity.setPid(pe.getId());
					queueloginRepository.save(queueloginEntity);
					
					String queueMember = pe.getPnumber();
						
					AddAgentToQueueAction action = new AddAgentToQueueAction(asteriskSocket.getSocket(),queueMember,aq.getQueue());
						
					action.setResponseClass(QueueShowResponse.class);
						
					responseBuilder.setAction(action);
					
					action.sendAction();
						
					agentLoggedId = aq.getid();
						
				}
			}
			return  new Response("success", "Agent Id:  [" + aq.getid() + "] added.");
	  }
	
 	
	  @MessageMapping("/messages")
 	  @SendTo("/topic/greetings")
	  public WsMessage greeting(HelloMessage msg) throws Exception {
		
		  	if (msg.getName().equals(WsEvents.REMOVE_AGENT.toString())){
		  	  
		  		removeAgentFromQueue(msg.getParam1(),msg.getParam2());
		  	
		    }
		    if (msg.getName().equals(WsEvents.LOGIN.toString())){
		  	  	Thread.sleep(500); // simulated delay
		  	  	openAsteriskSocket();
		  		
		  	  	Thread.sleep(1000); // simulated delay
		  		
		  		doAsteriskLogin();
		  	
		    }
		 
	        if (msg.getName().equals(WsEvents.START.toString())){
	        	  Thread.sleep(500); // simulated delay
	        	  
	        	  if (asteriskSocket == null){
	        		  openAsteriskSocket();
	        		  startAsteriskSocketThread();
	        		  doAsteriskLogin();
	        	  }
	        	  return new WsMessage(WsEvents.LOGGED_IN,null);
	        }
	        if (msg.getName().equals(WsEvents.QUEUE_SHOW.toString())){
	        	
	        	doActionQueueShow();
	        	
	        
	        	return new WsMessage(WsEvents.QUEUE_SHOW_OK,null);
	        }
	        if (msg.getName().equals(WsEvents.STOP.toString())){
	        	 
	        	
	        	//doAsteriskLogoff();
	        	
	        	//Thread.sleep(2000); 
	        	
	        	StopAsteriskSocketThread();
	        	
	        	closeAsteriskSocket();
	        
	        	return new WsMessage(WsEvents.STOP,null);
	        }
	        return new WsMessage(WsEvents.DONE,null);
	  }
	private void removeAgentFromQueue(String pnumber, String queue){
		
		RemoveAgentFromQueueAction action = new RemoveAgentFromQueueAction(asteriskSocket.getSocket(),pnumber,queue);
		
		action.setResponseClass(RemoveAgentFromQueueResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
		
		PhoneEntity phoneEntity = phoneRepository.findByPnumber(pnumber.toUpperCase());
		
		List<QueueloginEntity> savedEntity = queueloginRepository.findByPidOrderById(phoneEntity.getId());
		
		QueueloginEntity lastEntity = savedEntity.get(savedEntity.size()-1);
		
		AgentEntity agentEntity = repository.findById(lastEntity.getAid());
		
		QueueloginEntity queueloginEntity = new QueueloginEntity();
		queueloginEntity.setAction(0);
		queueloginEntity.setAid(agentEntity.getId());
		queueloginEntity.setPid(phoneEntity.getId());

		queueloginRepository.save(queueloginEntity);
	}
	private void openAsteriskSocket() throws SocketExceptionExt {
		try {
			int port = Integer.parseInt(props.getPort());
			asteriskSocket = new AsteriskSocketImpl(props.getHost(), port, dashBoardEventListener, responseBuilder);
	
			webSocketDisconnectHandler.setSocket(asteriskSocket);
			
			webSocketDisconnectHandler.setResponseBuilder(responseBuilder);
			
			asteriskSocket.die = true;
			
			Thread t = new Thread(asteriskSocket);
			t.start();
			
		} catch (SocketExceptionExt e) {
			
			throw new SocketExceptionExt("Error socket");
		}
	}
	private void closeAsteriskSocket() throws SocketExceptionExt, InterruptedException{
		
		try {
		
			Thread.sleep(2000);
			asteriskSocket.closeSocket();
			asteriskSocket = null;
			
		} catch (SocketExceptionExt e) {
			
			throw new SocketExceptionExt("Error socket");
		}
	}
	private void startAsteriskSocketThread(){
		
		if (asteriskSocket !=null){
			
			asteriskSocket.die = true;
		}
		
	}
	private void StopAsteriskSocketThread(){
		
		if (asteriskSocket !=null){
		
			asteriskSocket.die = false;
		}
		
	}
	private void doActionQueueShow(){
		QueueShowAction action = new QueueShowAction(asteriskSocket.getSocket(), repository, phoneRepository, queueloginRepository );
		action.setResponseClass(QueueShowResponse.class);
		responseBuilder.setAction(action);
		action.sendAction();
	}
	private void doAsteriskLogin(){
	//	LoginAction action = new LoginAction(asteriskSocket.getSocket(),props.getUsername(), props.getPassword() );
		LoginAction action = new LoginAction(asteriskSocket.getSocket(),currentUser.getFirstname(), currentUser.getPassword() );
	
		action.setResponseClass(LoginResponse.class);
		responseBuilder.setAction(action);
		action.sendAction();
		agentCount = 0;
	}
	public void doAsteriskLogoff(){
		
		logger.info("try to logoff");
		
		LogOffAction action = new LogOffAction(asteriskSocket.getSocket());
		
		action.setResponseClass(LogOffResponse.class);
		
		responseBuilder.setAction(action);
		
		action.sendAction();
		
		
	}
	public  void sendMessage(WsEvents msg){
		
		simpMessagingTemplate.convertAndSend("/topic/greetings",  new WsMessage(msg,null));
		
	}
	public  void sendMessage(WsMessage msg){
		simpMessagingTemplate.convertAndSend("/topic/greetings",  msg);
	}
	@Override
	public void OnAgentEnterQueue() {
		
	//	sendMessage("OnNewAgent");
		
	}
	
	@Override
	public void OnDialEvent(DialEvent event) {

		DialEvent ev = new DialEvent(getPureClassName(event));
	
		reAssign(event,ev);
		
		simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
		
	}
	@Override
	public void OnLoginResponse(AsteriskResponse response) {
		LoginResponse resp = new LoginResponse(getPureClassName(response));
		reAssign(response,resp);
		simpMessagingTemplate.convertAndSend("/topic/greetings",  resp);
	}
	@Override
	public void OnLogOffResponse(AsteriskResponse response) {
		LogOffResponse resp = new LogOffResponse(getPureClassName(response));
		
		reAssign(response,resp);
		
		simpMessagingTemplate.convertAndSend("/topic/greetings",  resp);

		try {
			
			closeAsteriskSocket();
			
			StopAsteriskSocketThread();
			
		} catch (Exception e) {
			
			logger.info("Unable to handle log off response:  " + response.getClass().getName() );
		}
		
	}
	@Override
	public void OnQueueShowResponse(AsteriskResponse response) {
		QueueShowResponse resp = new QueueShowResponse(getPureClassName(response));
		QueueShowResponse r = (QueueShowResponse)response;
		resp.setQueues(r.getQueues());
		resp.setResponse("success");
		if (licensed == false){
			for(int i = 0; i < resp.getQueues().getQueues().size(); i++){
				for(int j=0; j < resp.getQueues().getQueues().get(i).getAgents().size(); j++){
				  if (j > MAX_AGENT_ALLOWED-1){
					 resp.getQueues().getQueues().get(i).getAgents().get(j).setAgentName("DEMO");
					 resp.getQueues().getQueues().get(i).getAgents().get(j).setAgentNum("DEMO");
					}
				}
			}
		}
		simpMessagingTemplate.convertAndSend("/topic/greetings",  resp);
	}
	
	@RequestMapping("/getPeers")
	public @ResponseBody ArrayList<Peer>  getPeers(@RequestParam(value="queueName", required = true) String queueName ) {
	
		return this.queueContainer.getAllPeersByQueueName(queueName);
	}
	private void _OnQueueMemberAddedEvent(QueueMemberAddedEvent event){
		String aname = null;
		QueueMemberAddedEvent ev = new QueueMemberAddedEvent(getPureClassName(event));
		reAssign(event,ev);
		
		try{
			
			PhoneEntity phone =  phoneRepository.findByPnumber(ev.getmembername().toUpperCase(Locale.ENGLISH));
			 
			CoursesEntity course = coursesRepository.findByCoursename(ev.getqueue());
			 
			List<QueueloginEntity> queueLogin = queueloginRepository.findByPidAndQidAndActionOrderById(phone.getId(), course.getId(), 1);
			AgentEntity agentEntity = repository.findById(queueLogin.get(queueLogin.size()-1).getAid());
			aname = agentEntity.getName();
			ev.setagentname(aname);
			simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
		}catch(Exception e){
			aname = "not found";
			ev.setagentname(aname);
			simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
		}
	}
	@Override
	public void OnQueueMemberAddedEvent(QueueMemberAddedEvent event) {
		
		if (licensed == true){
		
			_OnQueueMemberAddedEvent(event);
		}else{
			agentCount++;
			if (agentCount <= MAX_AGENT_ALLOWED){
				_OnQueueMemberAddedEvent(event);
			}
		}
		
	}
	@Override
	public void OnQueueMemberRemovedEvent(QueueMemberRemovedEvent event) {
		QueueMemberRemovedEvent ev = new QueueMemberRemovedEvent(getPureClassName(event));
		reAssign(event,ev);
		simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
		
		if (!licensed){
			agentCount--;
		}


	}
	@Override
	public void OnQueueMemberStatusEvent(QueueMemberStatusEvent event) {
		QueueMemberStatusEvent ev = new QueueMemberStatusEvent(getPureClassName(event));
		reAssign(event,ev);
		simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
	}
	@Override
	public void OnJoinEvent(JoinEvent event) {
		JoinEvent ev = new JoinEvent(getPureClassName(event));
		reAssign(event,ev);
	
		simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
	}
	@Override
	public void OnBridgeEvent(BridgeEvent event) {
		BridgeEvent ev = new BridgeEvent(getPureClassName(event));
		reAssign(event,ev);
		simpMessagingTemplate.convertAndSend("/topic/greetings",  ev);
	}

	@Override
	public void OnAddAgentToQueueResponse(AsteriskResponse response) {
		AddAgentToQueueResponse resp = new AddAgentToQueueResponse(getPureClassName(response));
		AddAgentToQueueResponse r = (AddAgentToQueueResponse)response;
		resp.addMessage(r.getMessage());
		resp.setResponse("success");
		simpMessagingTemplate.convertAndSend("/topic/greetings",  resp);
	}
	@Override
	public void OnRemoveAgentFromQueueResponse(AsteriskResponse response) {

	}
	@Override
	public void OnErrorResponse(AsteriskResponse response) {
		
		ErrorResponse resp = new ErrorResponse(getPureClassName(response));
		resp.setMessage(response.getMessage());
		simpMessagingTemplate.convertAndSend("/topic/greetings",  resp);
		
	}

	@Override
	public void OnOriginateResponse(AsteriskResponse response) {
		OriginateResponse resp = (OriginateResponse) response;
		logger.info("Originate response: [" + resp.getResponse() + "]");
	
		
	}
	
	
}