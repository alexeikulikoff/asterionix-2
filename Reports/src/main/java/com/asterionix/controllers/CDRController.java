package com.asterionix.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asterionix.config.ConfigBean;
import com.asterionix.controllers.report.QueryCDR;
import com.asterionix.controllers.report.QueryCGR;
import com.asterionix.controllers.report.QueryQDR;
import com.asterionix.controllers.report.QueryQueueLogin;
import com.asterionix.controllers.report.RecordQDR;
import com.asterionix.controllers.report.RecordQueueLogin;
import com.asterionix.controllers.report.ReportCDR;
import com.asterionix.controllers.report.ReportCGR;
import com.asterionix.controllers.report.ReportFindCDR;
import com.asterionix.controllers.report.ReportQDR;
import com.asterionix.controllers.report.ReportQueueLogin;
import com.asterionix.controllers.report.ServiceReportCDR;
import com.asterionix.controllers.report.ServiceReportCGR;
import com.asterionix.controllers.report.ServiceReportFindCDR;
import com.asterionix.controllers.report.ServiceReportQDR;
import com.asterionix.dao.AgentRepository;
import com.asterionix.dao.CDRFindEntity;
import com.asterionix.dao.CDRFindRepository;
import com.asterionix.dao.CDRPagingAndSortingRepository;
import com.asterionix.dao.CDRRepository;
import com.asterionix.dao.PhoneEntity;
import com.asterionix.dao.PhoneRepository;
import com.asterionix.dao.QueueLogEntity;
import com.asterionix.dao.QueueLogPagedRepository;
import com.asterionix.dao.QueueLogRepository;
import com.asterionix.dao.QueueloginEntity;
import com.asterionix.dao.QueueloginRepository;


@Controller
public class CDRController extends AbstractController{
	
	static Logger logger = LoggerFactory.getLogger(CDRController.class);
	@Autowired
	private CDRRepository repositoryCGR;
	@Autowired
	private CDRPagingAndSortingRepository repositoryCDR;

	@Autowired
	private QueueloginRepository queueloginRepository;
	
	@Autowired
	private QueueLogRepository queueLogRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Autowired
	private QueueLogPagedRepository queueLogPagedRepository;
	
	@Autowired
	private CDRFindRepository cdrFindRepository;
	
	@Autowired
	private AgentRepository agentRepository;
	
	 @Autowired
	 private ConfigBean props;
	
	public CDRController() {
		super();
	}
	
	@RequestMapping("/reports")
	public String reports(Model model) {
		 
		return "reports";
		
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
	@RequestMapping("/test-report")
	public @ResponseBody ReportCDR test(@RequestBody QueryCDR query) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ReportCDR report  = (ReportCDR) createReport(ServiceReportCDR.class, query, repositoryCDR);
		return report;
	
	}
	@RequestMapping("/report-cgr")
	public @ResponseBody ReportCGR cgrReport(@RequestBody QueryCGR query) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ReportCGR report  = (ReportCGR) createReport(ServiceReportCGR.class, query, repositoryCGR);
		return report;
	}
	
	@RequestMapping("/report-cdr")
	public @ResponseBody ReportCDR cdrReport(@RequestBody  QueryCDR query) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ReportCDR report  = (ReportCDR) createReport(ServiceReportCDR.class, query, repositoryCDR);
		return report;
	}
	@RequestMapping("/report-queuelogin")
	public  @ResponseBody ReportQueueLogin  queueloginReport(@RequestBody QueryQueueLogin query){

		ServiceReportQDR service = new ServiceReportQDR(queueloginRepository);
		
		ReportQueueLogin report = service.createReportQGR(query,phoneRepository,queueLogRepository) ;
		
		return report ;
	}

	@RequestMapping("/report-qdr")
	public @ResponseBody ReportQDR roportQDR(@RequestBody QueryQDR query){
		
		logger.info("page size: " + query.getPagesize());
		ServiceReportQDR service = new ServiceReportQDR(queueLogPagedRepository);
		
		ReportQDR report = service.createReportQDR(query);
		
		return report;
		
	}
	
	@RequestMapping("/report-find")
	public @ResponseBody ReportFindCDR roportFindCDR(@RequestBody QueryCDR query){
		
		ServiceReportFindCDR service = new ServiceReportFindCDR(cdrFindRepository, queueLogRepository,queueloginRepository,phoneRepository,agentRepository );
		
		ReportFindCDR report = service.createReportFindCDR(query);
		
		return report;
		
	}
	@RequestMapping("/report-find-test")
	public @ResponseBody QueryCDR roportFindCDRTest(@RequestBody QueryCDR query){
		
	
		
		return query;
		
	}
	
}	
	
