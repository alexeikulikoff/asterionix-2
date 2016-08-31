package com.asterionix.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.asterionix.dao.QueueloginEntity;
import com.asterionix.dao.QueueloginRepository;


@Controller
public class TestController {
	 static Logger logger = LoggerFactory.getLogger(TestController.class);
	 
	 @Autowired
	 private QueueloginRepository queueloginRepository;
	 
	 @RequestMapping("/cdr-page-test")
	 public String  cdrPageTestt() {
		
			return "cdrs";
	  }
	 @RequestMapping("/queue-page-test")
	 public String  queuePageTestt() {
		
			return "queues";
	  }
	 @RequestMapping("/user-page-test")
	 public String  userPageTestt() {
		
			return "users";
	}
	 @RequestMapping("/agent-page-test")
	 public String  agentNewTest() {
		
			return "agents";
	  }	
	 @RequestMapping("/phone-page-test")
	 public String  phoneTest() {
		
		 List<QueueloginEntity> queueloginEntity  = queueloginRepository.findByPidAndAidAndActiontimeBetween(16, 2, "2016-05-23 17:34:28", "2016-05-24 17:34:28");
		 for(QueueloginEntity qL : queueloginEntity){
			logger.info("Test :" + qL.getId() + " Phone:  " + qL.getPhoneEntity().getPnumber()); 
		 }
		 return "phones";
	  }	
	 @RequestMapping(value = "/sounds/{id}")
	  public void downloadRecipientFile(@PathVariable("id") String id,ModelMap model, HttpServletResponse response) throws IOException,
		        ServletException {
		  String filePathToBeServed = "../../sounds/"+id + ".mp3";
		  File fileToDownload = new File(filePathToBeServed);
		  InputStream inputStream = new FileInputStream(fileToDownload);
		  response.setContentType("application/force-download");
	      response.setHeader("Content-Disposition", "attachment; filename="+id + ".mp3"); 
	      IOUtils.copy(inputStream, response.getOutputStream());
	      
	      response.flushBuffer();
	      
	      inputStream.close();
	      
		  
		}
}
