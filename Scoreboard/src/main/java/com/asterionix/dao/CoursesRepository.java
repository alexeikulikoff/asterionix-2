package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CoursesRepository extends CrudRepository<CoursesEntity, Long>{

	CoursesEntity findById(Long id);
	List<CoursesEntity> findAll();
}
