package com.asterionix.dao;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

public interface QueueLogRepository extends CrudRepository<QueueLogEntity, Long>{
	
	List<QueueLogEntity> findByEventAndAgentAndQueuenameAndTimeBetweenOrderById(String event, String agent, String queuename, String date1, String date2);
	List<QueueLogEntity> findByCallid(String callid);
}
