package com.asterionix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.controllers.UserController.AjaxResponse;
import com.asterionix.controllers.util.Customer;
import com.asterionix.controllers.util.Customers;
import com.asterionix.controllers.util.Param;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.CoursesEntity;
import com.asterionix.dao.CoursesRepository;
import com.asterionix.dao.CustomersEntity;
import com.asterionix.dao.CustomersRepository;



@Controller
public class CustomersController extends AbstractController {

	@Autowired
	private CustomersRepository customersRepository;
	@Autowired
	private CoursesRepository coursesRepository;
	
	 @RequestMapping("/customers")
	 public  @ResponseBody Customers getAll(){
		 
		 Customers customers = new Customers();
		 
		 List<CustomersEntity> customersEntity = customersRepository.findAll();
		
		 for(CustomersEntity c : customersEntity){
			 customers.AddCustomer(new Customer(c.getId(),c.getName(),c.getNumber(),c.getCoursesEntity().getCoursename()));
		 }
		
		 return customers;
	 }
	 @RequestMapping("/getCustomer") 
	 public @ResponseBody Customer getCustomer(@RequestParam(value = "id", required=true) int id){
		 CustomersEntity c = customersRepository.findById(id);
		 Customer cunstomer = new Customer(c.getId(),c.getName(),c.getNumber(),c.getCoursesEntity().getCoursename());
		 return cunstomer;
		 
	 }
	 @RequestMapping("/saveCustomer") 
	 public @ResponseBody AjaxResponse saveCustomer(@RequestBody Customer c){
		
		 AjaxResponse response = null;
		 
		 try{
			 CoursesEntity course = coursesRepository.findByCoursename(c.getCourse());
			 CustomersEntity customer = new CustomersEntity();
			 customer.setName(c.getName());
			 customer.setNumber(c.getNumber());
			 customer.setCoursesEntity(course);
			 customersRepository.save(customer);
			 response = new AjaxResponse("Saved");
		 }catch(Exception e){
			 response = new AjaxResponse("Error");
		 }
	
		 return response;
		 
	 }
	 @RequestMapping("/updateCustomer") 
	 public @ResponseBody AjaxResponse updateCustomer(@RequestBody Customer c){
		 AjaxResponse response = null;
		 try{
			 CustomersEntity customer  = customersRepository.findById(c.getId());
			 customer.setName(c.getName());
			 customer.setNumber(c.getNumber());
			 customersRepository.save(customer);
			 response = new AjaxResponse("Saved");
		 }catch(Exception e){
			 response = new AjaxResponse("Error");
		 }
		 return response;
	 }
	 @RequestMapping("/deleteCustomer")
	 public  @ResponseBody AjaxResponse deleteAgent(@RequestBody Param param) {
		
			AjaxResponse response = null;
			try {
				int id = Integer.parseInt(param.getValue());
				
				CustomersEntity entity = customersRepository.findById(id);
				
				customersRepository.delete(entity);
				
				response = new AjaxResponse("Deleted");
				
			}catch(Exception e){
				
				logger.info("Error " + e.toString());
				
				response = new AjaxResponse("Error");
			}
		
			return response;
			
	 }
}
