package com.asterionix.controllers.report;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import com.asterionix.dao.CoursesEntity;
import com.asterionix.dao.CoursesRepository;
import com.asterionix.dao.IEntity;
import com.asterionix.dao.PhoneEntity;
import com.asterionix.dao.PhoneRepository;
import com.asterionix.dao.QueueLogEntity;
import com.asterionix.dao.QueueLogPagedRepository;
import com.asterionix.dao.QueueLogRepository;
import com.asterionix.dao.QueueloginEntity;
import com.asterionix.dao.QueueloginRepository;

public class ServiceReportQDR extends AbstractServiceReport{

	
	
	static Logger logger = LoggerFactory.getLogger(ServiceReportQDR.class);
	
	public ServiceReportQDR(CrudRepository<? extends IEntity, Long> crudRepository) {
		super(crudRepository);
	}
	public ReportQueueLogin createReportQGR(QueryQueueLogin query,PhoneRepository phoneRepository,QueueLogRepository queueLogRepository){
		
		QueueloginRepository repository = (QueueloginRepository)crudRepository;
		
		RecordQueueLogin record = new RecordQueueLogin();
		
		List<QueueloginEntity> entities = repository.findByAidAndActiontimeBetweenOrderById(query.getAgentid(), query.getActiontime1(), query.getActiontime2());
		
		TimePeriods timePeriods = new TimePeriods(entities);
		
		List<RecordQueueLogin> records = new ArrayList<RecordQueueLogin>();
		
		for(TimePeriod t : timePeriods.getTimePeriod()){
		
			PhoneEntity phoneEntity = phoneRepository.findById(t.getPid());
			
			List<QueueLogEntity> queueLog =  queueLogRepository
											.findByEventAndAgentAndQueuenameAndTimeBetweenOrderById("CONNECT", phoneEntity.getPnumber(),query.getCoursename(),t.getLoginTime(), t.getLogoffTime());
			String args = "'" + t.getLoginTime() + "','" + t.getLoginTime() + "','" + t.getLogoffTime() + "','" + phoneEntity.getPnumber() + "','1','"+ query.getPagesize()+"','" + query.getCoursename() + "'" ;
			
			records.add(new RecordQueueLogin(t.getLoginTime(),t.getLoginTime(),t.getLogoffTime(),phoneEntity.getPnumber(), queueLog.size(),args));
//			logger.info("LoginTime  : " + t.getLoginTime() + "   LogoffTime : " + t.getLogoffTime() + " count " +  queueLog.size() );
		}
		ReportQueueLogin report = new ReportQueueLogin(records);
		return report;
	}
	public ReportQDR createReportQDR(QueryQDR query){
		QueueLogPagedRepository repository = (QueueLogPagedRepository)crudRepository;
		Page<QueueLogEntity> entity = repository
									  .findByAgentAndEventAndQueuenameAndTimeBetweenOrderById(query.getAgent(),query.getEvent(),query.getCoursename(),query.getTime1(),query.getTime2(),
												  new PageRequest(query.getPage()-1, query.getPagesize()));
		
		List<RecordQDR> records = new ArrayList<RecordQDR>();
		for(QueueLogEntity e : entity){
			records.add(new RecordQDR(e.getId(),e.getCallid(),e.getTime(),e.getTime(),e.getCDR().getSrc(),e.getCDR().getDst(),e.getCDR().getduration()));
		}
		ReportQDR report = new ReportQDR(records,getTabs(entity.getTotalPages(),query.getPage()));
		return report;
		
	}
	class TimePeriod{
		private String loginTime;
		private String logoffTime;
		private Long pid;
	
		public void setLoginTime(String t){
			this.loginTime = t;
		}
		public void setLogoffTime(String t){
			this.logoffTime = t;
		}
		public String getLoginTime(){
			
			return this.loginTime;
		}
		public String getLogoffTime(){
			
			return this.logoffTime;
		}
		public Long getPid(){
			return this.pid;
		}
		public void setPid(Long p){
			this.pid = p;
		}
	}
	class TimePeriods {
		private List<TimePeriod> timePeriods;
		private List<QueueloginEntity> entities;
		public TimePeriods(List<QueueloginEntity> entities){
			this.entities = entities;
			timePeriods = new ArrayList<TimePeriod>();
		}
		
		public void add(TimePeriod t){
			timePeriods.add(t);
		}
		public List<TimePeriod> getTimePeriod(){
			String LoginTime = null;
			String logoffTime = null;
			int j = 0;
			while(j < entities.size()-1){
				if (entities.get(j).getAction()== 1){
					LoginTime = entities.get(j).getActiontime();
					if (entities.get(j+1).getAction()== 0){
						logoffTime = entities.get(j+1).getActiontime();
						TimePeriod t = new TimePeriod();
						t.setLoginTime(LoginTime);
						t.setLogoffTime(logoffTime);
						t.setPid(entities.get(j).getPid());
						timePeriods.add(t);
					}
				}
				j++;
			}
			return this.timePeriods;
		}
	}
	

}
