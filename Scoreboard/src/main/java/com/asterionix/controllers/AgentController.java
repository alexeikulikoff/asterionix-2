package com.asterionix.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.config.AsterionixProperties;
import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.controllers.util.Response;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.AgentRepository;
import com.asterionix.dao.UserEntity;
import com.asterionix.exception.EntitySaveException;

@Controller
@EnableConfigurationProperties(AsterionixProperties.class)
public class AgentController extends AbstractController {

	@Autowired
	private AgentRepository repository;
	
	 @Autowired
	 private AsterionixProperties props;

	 private static class Data{
			
		 	private String name;
			public Data(){}
			public String getName(){
				return this.name;
			}
			public void setName(String s){
				this.name = s;
			}
		}
	 
	 
	class Agent{
		private int id;
		private String name;
		public Agent(int id, String name)
		{
			this.id = id;
			this.name = name;
			
		}
		public int getId(){
			return this.id;
		}
		public String getName(){
			return this.name;
		}
	
	}
	class Agents{
		
		private List<Agent> agents;
		
		public Agents(){
			
			agents = new ArrayList<Agent>();
		}
		public void addAgent(Agent a){
			
			agents.add(a);
		}
		public List<Agent> getAgents(){
			
			return agents;
		}
	}

	
	
	
	 @RequestMapping("/agents")
	 public @ResponseBody Agents agents(Model model) {
		 Agents ags = new Agents();
		 List<AgentEntity> agents = repository.findAll();
		 for(int i=0; i < agents.size(); i++){
			 ags.addAgent(new Agent( agents.get(i).getId(), agents.get(i).getName()));
		 }
		 return ags;
	  }
	
	 @RequestMapping("/deleteAgent")
	 public  @ResponseBody AjaxResponse deleteAgent(@RequestBody Param param) {
		
			AjaxResponse response = null;
			try {
				int id = Integer.parseInt(param.getValue());
				
				AgentEntity entity = repository.findById(id);
				repository.delete(entity);
				response = new AjaxResponse("agent_deleted");
			}catch(Exception e){
				logger.info("Error " + e.toString());
				response = new AjaxResponse("Error_agent_delete");
			}
		
			return response;
			
	 }
	 @RequestMapping("/saveAgent")
	 public @ResponseBody AjaxResponse saveAgent(@RequestBody Params params){
		 
		 AjaxResponse response = null;
		 
		 try {
				saveEntity(AgentEntity.class, repository, params );
				
				response = new AjaxResponse("Saved");
				
			} catch (EntitySaveException e) {
				
				response = new AjaxResponse(e.toString());
			}
		 return response;
	 }
	
}
