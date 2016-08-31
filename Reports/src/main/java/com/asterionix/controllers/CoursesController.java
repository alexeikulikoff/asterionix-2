package com.asterionix.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.dao.CoursesEntity;
import com.asterionix.dao.CoursesRepository;
import com.asterionix.exception.EntitySaveException;

@Controller
public class CoursesController extends AbstractController{

	@Autowired
	private CoursesRepository repository;
	
	 @RequestMapping("/queues")
	 public @ResponseBody Courses findAll() {
		 Courses courses = new Courses();
		 List<CoursesEntity> queueEntity  =  repository.findAll();
		 for(CoursesEntity e : queueEntity){
			 courses.add(new Course(e.getId(),e.getCoursename(), e.getWeight()));
		 }
		 return courses;
	  }
	 @RequestMapping("/deleteCourse")
	 public  @ResponseBody AjaxResponse deleteAgent(@RequestBody Param param) {
		
			AjaxResponse response = null;
			try {
				Long id = Long.parseLong(param.getValue());
				
				CoursesEntity entity = repository.findById(id);
				repository.delete(entity);
				response = new AjaxResponse("queue_deleted");
			}catch(Exception e){
				logger.info("Error " + e.toString());
				response = new AjaxResponse("Error_agent_delete");
			}
		
			return response;
			
	 }
	 @RequestMapping("/saveCourses")
	 public @ResponseBody AjaxResponse saveQourses(@RequestBody Params params) {
		 
		 AjaxResponse response = null;
		 
		 try {
				saveEntity(CoursesEntity.class, repository, params );
				
				response = new AjaxResponse("Queue with name ['" + params.getParams().get(0).getValue() + "'] saved");
				
		//	} catch (EntitySaveException e) {
			} catch (Exception e) {
				System.out.println(e);
				response = new AjaxResponse(e.toString());
			}
		 return response;
	  }
	 
	 class Courses{
		 List<Course> courses;
		 public Courses(){
			 this.courses = new ArrayList<Course>();
		 }
		 public List<Course> getCourses(){
			 return courses;
		 }
		 public void add(Course c){
			 courses.add(c);
		 }
	 }
	 class Course{
		private Long id;
		private String coursename;
		private String weight;

		public Course(Long id, String coursename, String weight){
			this.id = id;
			this.coursename = coursename;
			this.weight = weight;
		}
		public Long getId() {
			return this.id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getCoursename(){
			return this.coursename;
		}
		public void setCoursename(String s){
			this.coursename = s;
		}
		public String getWeight(){
			return this.weight;
		}
		public void setWeight(String w){
			this.weight = w;
		}
	 }
}
