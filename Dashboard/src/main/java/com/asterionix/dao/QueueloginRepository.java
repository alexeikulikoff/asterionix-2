package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QueueloginRepository  extends CrudRepository<QueueloginEntity, Long> {

	List<QueueloginEntity> findByPidAndAidAndActiontimeBetween(int pid, int aid, String actiontime1, String actiontime2);
	List<QueueloginEntity> findByPidAndActiontimeLessThanOrderById(Long long1,  String actiontime1);
	List<QueueloginEntity> findByPidOrderById(Long pid);
	List<QueueloginEntity> findByPidAndActiontimeGreaterThanOrderById(Long pid, String actionTime);
	List<QueueloginEntity> findByPidAndQidAndActionOrderById(Long pid, Long qid, int action);
	List<QueueloginEntity> findByAidAndActiontimeBetweenOrderById(int aid, String actiontime1, String actiontime2);
		
}
