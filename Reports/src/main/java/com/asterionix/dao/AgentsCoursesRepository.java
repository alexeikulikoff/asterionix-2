package com.asterionix.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AgentsCoursesRepository extends CrudRepository<AgentsCoursesEntity, Long>{

	AgentsCoursesEntity findById(int id);
	AgentsCoursesEntity findByExtension(String extension);
}
