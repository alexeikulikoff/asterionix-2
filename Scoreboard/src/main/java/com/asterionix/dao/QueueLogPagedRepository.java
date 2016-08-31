package com.asterionix.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QueueLogPagedRepository extends PagingAndSortingRepository<QueueLogEntity, Long>{
	Page<QueueLogEntity> findByAgentAndEventAndQueuenameAndTimeBetweenOrderById(String agent, String event, String queuename, String t1, String t2,Pageable pageble);
	
}
