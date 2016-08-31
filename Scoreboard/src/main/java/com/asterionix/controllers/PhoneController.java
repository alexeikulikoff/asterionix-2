package com.asterionix.controllers;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.config.AsterionixProperties;
import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.util.Param;
import com.asterionix.controllers.util.Params;
import com.asterionix.controllers.util.Phone;
import com.asterionix.controllers.util.Phones;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.PhoneEntity;
import com.asterionix.dao.PhoneRepository;
import com.asterionix.exception.EntitySaveException;

@Controller
@EnableConfigurationProperties(AsterionixProperties.class)
public class PhoneController extends AbstractController
{

	@Autowired
	private PhoneRepository repository;
	
	
		
	@RequestMapping("/phones")
	public @ResponseBody Phones  findAll() {

		Phones phones  = new Phones();
		List<PhoneEntity> phoneEntity = repository.findAll();
		for(PhoneEntity pe : phoneEntity){
			phones.addPhone(new Phone(pe.getId(),pe.getPnumber(),pe.getComments()));
		}
		return phones;
	  }
	@RequestMapping("/savePhone")
	public @ResponseBody AjaxResponse savePhone(@RequestBody Params params) throws NoSuchMethodException, SecurityException {

		AjaxResponse response = null;
		
		try {
			saveEntity(PhoneEntity.class, repository, params );
			
			response = new AjaxResponse("Saved");
			
		} catch (EntitySaveException e) {
			
			response = new AjaxResponse(e.toString());
		}
		
		return response;
	  }
	//deletePhone
	 @RequestMapping("/deletePhone")
	 public  @ResponseBody AjaxResponse deletePhone(@RequestBody Param param) {
		
			AjaxResponse response = null;
			try {
				Long id = Long.parseLong(param.getValue());
				
				PhoneEntity entity = repository.findById(id);
				repository.delete(entity);
				response = new AjaxResponse("phone_deleted");
			}catch(Exception e){
				logger.info("Error " + e.toString());
				response = new AjaxResponse("Error_phone_delete");
			}
		
			return response;
			
	 }
	
}
