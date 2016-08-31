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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.config.AsterionixProperties;
import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.util.Assign;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.controllers.util.Response;
import com.asterionix.controllers.util.UpdateExtension;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.AgentRepository;
import com.asterionix.dao.AgentsCoursesEntity;
import com.asterionix.dao.AgentsCoursesRepository;
import com.asterionix.dao.CoursesEntity;
import com.asterionix.dao.CoursesRepository;
import com.asterionix.dao.UserEntity;
import com.asterionix.exception.EntitySaveException;

@Controller
@EnableConfigurationProperties(AsterionixProperties.class)
public class AgentController extends AbstractController {

	@Autowired
	private AgentRepository repository;
	
	@Autowired
	private CoursesRepository coursesRepository;
	
	@Autowired
	private AgentsCoursesRepository agentsCoursesRepository;
	
	@Autowired
	private AsterionixProperties props;

/*	
	 @RequestMapping("/agents")
	 public @ResponseBody Agents agents(Model model) {
		 Agents ags = new Agents();
		 List<AgentEntity> agents = repository.findAll();
		 for(int i=0; i < agents.size(); i++){
			 ags.addAgent(new Agent( agents.get(i).getId(), agents.get(i).getName()));
		 }
		 return ags;
	  }
*/	
	 @RequestMapping("/agents")
	 public @ResponseBody Agents agents(Model model) {
		 Agents ags = new Agents();
		 List<AgentEntity> agents = repository.findAll();
		 for(int i=0; i < agents.size(); i++){
			 
			 List<CoursesExtension> CoursesExtensions = new ArrayList<CoursesExtension>();
			 
			 List<AgentsCoursesEntity> agentsCoursesEntities = agents.get(i).getAgentsCourses();
			 
			
			 for(int j=0 ; j < agentsCoursesEntities.size(); j++){
				
				 CoursesEntity CoursesEntity = agentsCoursesEntities.get(j).getCoursesEntity();
				 
				 String courseName = CoursesEntity.getCoursename();
				
				 String extension = agentsCoursesEntities.get(j).getExtension();
				 
				 String penalty  = agentsCoursesEntities.get(j).getPenalty();
				 
				 Integer id = agentsCoursesEntities.get(j).getId();
				 
				 CoursesExtension CoursesExtension = new CoursesExtension(id, courseName,extension,penalty);
				 
				 CoursesExtensions.add(CoursesExtension);
			 }
					 
			 ags.addAgent(new Agent( agents.get(i).getId(), agents.get(i).getName(),CoursesExtensions));
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
				
				response = new AjaxResponse("Deleted");
				
			}catch(Exception e){
				
				logger.info("Error " + e.toString());
				
				response = new AjaxResponse("Error");
			}
		
			return response;
			
	 }
	 @RequestMapping("/deleteExtension")
	 public  @ResponseBody AjaxResponse deleteExtension(@RequestBody Param param) {
		
		 AjaxResponse response = null;
		try{
			 AgentsCoursesEntity courses = agentsCoursesRepository.findByExtension(param.getValue());
			 
			 agentsCoursesRepository.delete(courses);
			 
			 response = new AjaxResponse("Deleted");
			 
			 
		}catch(Exception e){
			System.out.println(e);
			response = new AjaxResponse("Error");
		}
		 
		
		 return response;
			
			
	 }
	 @RequestMapping("/getAgentName")
	 public  @ResponseBody Agent getAgentName(@RequestParam(value = "id", required=true) int id) {
		
		   AgentEntity a = repository.findById(id);
		   Agent agent = new Agent(a.getId(),a.getName(),null);
		   
		   return agent;
			
	 }
	 @RequestMapping("/getExtension")
	 public  @ResponseBody AjaxResponse getExtension(@RequestParam(value = "id", required=true) int id) {
		
		 AjaxResponse response = null;
		
		 AgentsCoursesEntity courses = agentsCoursesRepository.findById(id);
		 
		 response = new AjaxResponse(courses.getExtension());
		
		 return response;
			
	 }
	 @RequestMapping("/assignAgent")
	 public @ResponseBody AjaxResponse assignAgent(@RequestBody Assign assign){
		 
		 AjaxResponse response = null;
		 
		 try {
			 AgentEntity agent = repository.findByName(assign.getName());
			 
			 CoursesEntity courses = coursesRepository.findByCoursename(assign.getCurses());
			 
			 AgentsCoursesEntity entity = new AgentsCoursesEntity(agent.getId(), courses.getId(),assign.getExtension(),assign.getPenalty());
			 
			 agentsCoursesRepository.save(entity);
				
			  response = new AjaxResponse("Saved");
				
			} catch (Exception e) {
				
		
				response = new AjaxResponse("Error");
			}
		 return response;
	 }
	 @RequestMapping("/updateExtension")
	 public @ResponseBody AjaxResponse updateExtension(@RequestBody UpdateExtension updateextension){
		 
		 AjaxResponse response = null;
		 
		 try {
			 
			 AgentsCoursesEntity agentsCoursesEntity = agentsCoursesRepository.findById(updateextension.getid());
			 
			 agentsCoursesEntity.setExtension(updateextension.getExtension());
			
			 agentsCoursesEntity.setPenalty(updateextension.getPenalty());
			
			 agentsCoursesRepository.save(agentsCoursesEntity);
			 
			
				
			  response = new AjaxResponse("Updated");
				
			} catch (Exception e) {
				
		
				response = new AjaxResponse("Error");
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
	class CoursesExtension{
		private int id;
		private String courseName;
		private String extension;
		private String penalty;
		public CoursesExtension(int id, String courseName,String extension,String penalty){
			this.id = id;
			this.courseName = courseName;
			this.extension = extension;
			this.penalty = penalty;
		}
		public int getId(){
			return this.id;
		}
		public String getCourseName(){
			return this.courseName;
		}
		public String getExtension(){
			return this.extension;
		}
		public String getPenalty(){
			return this.penalty;
		}
	}
	 
	class Agent{
		private int id;
		private String name;
		
		private List<CoursesExtension> coursesExtension;
		
		public Agent(int id, String name,List<CoursesExtension> coursesExtension)
		{
			this.id = id;
			this.name = name;
			this.coursesExtension = coursesExtension;
			
		}
		public int getId(){
			return this.id;
		}
		public String getName(){
			return this.name;
		}
		public  List<CoursesExtension> getCoursesExtension(){
			return this.coursesExtension;
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


	 
	
}
