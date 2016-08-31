package com.asterionix.dao;

import org.springframework.data.repository.CrudRepository;

public interface AgentsCoursesRepository extends CrudRepository<AgentsCoursesEntity, Long>{

	AgentsCoursesEntity findById(int id);
	AgentsCoursesEntity findByExtensionContaining(String extension);
}
