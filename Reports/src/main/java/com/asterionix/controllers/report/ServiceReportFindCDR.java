package com.asterionix.controllers.report;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import com.asterionix.controllers.CDRController;
import com.asterionix.dao.AgentEntity;
import com.asterionix.dao.AgentRepository;
import com.asterionix.dao.CDRFindEntity;
import com.asterionix.dao.CDRFindRepository;
import com.asterionix.dao.PhoneEntity;
import com.asterionix.dao.PhoneRepository;
import com.asterionix.dao.QueueLogEntity;
import com.asterionix.dao.QueueLogPagedRepository;
import com.asterionix.dao.QueueLogRepository;
import com.asterionix.dao.QueueloginEntity;
import com.asterionix.dao.QueueloginRepository;

public class ServiceReportFindCDR extends AbstractServiceReport {

	static Logger logger = LoggerFactory.getLogger(ServiceReportFindCDR.class);
	
	private CDRFindRepository 		repository;
	private QueueLogRepository   	queueLogRepository;
	private QueueloginRepository 	queueloginRepository;
	private PhoneRepository 		phoneRepository;
	private AgentRepository 		agentRepository;
	
	public ServiceReportFindCDR(CrudRepository<CDRFindEntity, Long> repository, 
								QueueLogRepository 		queueLogRepository,
								QueueloginRepository 	queueloginRepository, 
								PhoneRepository 		phoneRepository,
								AgentRepository 		agentRepository) {
		
		super(repository);

		this.repository = (CDRFindRepository)repository;
		this.queueLogRepository		= queueLogRepository;
		this.queueloginRepository 	= queueloginRepository;
		this.phoneRepository 		= phoneRepository;
		this.agentRepository 		= agentRepository;
		
	}
	public ReportFindCDR createReportFindCDR(QueryCDR query){
	
		List<CDRFindRecord> cdrFindRecords = new ArrayList<CDRFindRecord>();
		
		Page<CDRFindEntity> entity = repository.findBySrcContainingAndLastappAndCalldateBetween(query.getSrc(), "Queue",  query.getTime1(), query.getTime2(), new PageRequest(query.getPage()-1, query.getPagesize()));
		
		for(CDRFindEntity e : entity){
				CDRFindRecord record = new CDRFindRecord(e.getId(),e.getuniqueid(),e.getCalldate(),e.getCalldate(),e.getSrc(),e.getDst(),e.getduration(),e.getdisposition());
				
				List<QueueLogEntity> queueLogEntities  = queueLogRepository.findByCallid(e.getuniqueid());
				
				if (queueLogEntities.size() > 0 ){
					 QueueLogFindRecords queueLogFindRecords = new QueueLogFindRecords();
					 for(QueueLogEntity q : queueLogEntities){
						
						 if (q.getAgent().equals("NONE")){
							 continue;
						 }
						 try {
							 PhoneEntity phoneEntity = phoneRepository.findByPnumber(q.getAgent());	
							
							 
							 try{
								 List<QueueloginEntity> queueloginEntity = queueloginRepository.findByPidAndActiontimeLessThanOrderById(phoneEntity.getId(), e.getCalldate());
								
								 int aid = queueloginEntity.get(queueloginEntity.size()-1).getAid(); 
								 try{
									 AgentEntity a = agentRepository.findById(aid);
									 String name = a.getName();
									 QueueLogFindRecord r = new QueueLogFindRecord(q.getId(),q.getTime(), q.getQueuename(),q.getAgent(),q.getEvent(),name);
									 queueLogFindRecords.addRecord(r); 
									 record.setQueueLogFindRecord(queueLogFindRecords);
									 
									
									 
								  }catch(Exception ex){
									 logger.info("AgentRepository not found: " + ex.toString());  
								 }
							 }catch(Exception ex){
								 logger.info("QueueloginEntity not found: " + ex.toString());  
							 }
						
						 }catch(Exception ex){
							 logger.info("Phone not found: " + ex.toString()); 
						 }
					 }
					
				}
				 cdrFindRecords.add(record); 
			}
		return new ReportFindCDR(cdrFindRecords,getTabs(entity.getTotalPages(),query.getPage()));
		
	}

}
