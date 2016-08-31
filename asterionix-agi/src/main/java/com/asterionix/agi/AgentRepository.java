package com.asterionix.agi;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AgentRepository extends CrudRepository<AgentEntity, Long> {

	public AgentEntity findByName(String name);
	
	List<AgentEntity> findAll();
	public AgentEntity findById(int id);
	
}


