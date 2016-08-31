package com.asterionix.agi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CoursesRepository extends CrudRepository<CoursesEntity, Long>{

	CoursesEntity findById(Long id);
	CoursesEntity findByCoursename(String name);
	CoursesEntity findByWeight(String weight);
	List<CoursesEntity> findAll();
}
