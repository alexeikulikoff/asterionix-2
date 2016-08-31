package com.asterionix.agi;

import org.springframework.data.repository.CrudRepository;

public interface AgentsCoursesRepository extends CrudRepository<AgentsCoursesEntity, Long>{

	AgentsCoursesEntity findById(int id);
	AgentsCoursesEntity findByExtensionContaining(String extension);
}
