package com.asterionix.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.config.AsterionixProperties;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.controllers.util.User;
import com.asterionix.controllers.util.Users;
import com.asterionix.dao.UserEntity;
import com.asterionix.dao.UserRepository;
import com.asterionix.exception.EntitySaveException;

@Controller
@EnableConfigurationProperties(AsterionixProperties.class)
public class UserController extends AbstractController{
	static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	public static class AjaxResponse{
		private String response;
		public AjaxResponse(String response){
			this.response = response;
		}
		public String getResponse(){
			return this.response;
		}
	}
	@RequestMapping("/users")
	public  @ResponseBody  Users  findAll() {
		Users users = new Users();
		List<UserEntity> ue = userRepository.findAll();
		for(UserEntity u : ue){
			users.addUser(new User(u.getId(),u.getFirstname(),u.getPassword()));
		}
		return users;
		
	}
	@RequestMapping("/saveUser")
	public  @ResponseBody AjaxResponse saveUser(@RequestBody Params params) {
	
		AjaxResponse response = null;
		
		try {
			saveEntity(UserEntity.class, userRepository, params );
			
			response = new AjaxResponse("Saved");
			
		} catch (EntitySaveException e) {
			
			response = new AjaxResponse(e.toString());
		}
		
		return response;
		
	}
	@RequestMapping("/deleteUser")
	public  @ResponseBody AjaxResponse deleteUser(@RequestBody Param param) {
	
		AjaxResponse response = null;
		try {
			long id = Long.parseLong(param.getValue());
			UserEntity entity = userRepository.findById(id);
			userRepository.delete(entity);
			response = new AjaxResponse("user_deleted");
		}catch(Exception e){
			response = new AjaxResponse("Error_user_delete");
		}
	
		return response;
		
	}
}
