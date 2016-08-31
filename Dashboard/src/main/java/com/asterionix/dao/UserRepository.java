package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByFirstname(String firstname);
	UserEntity findById(Long id);
	List<UserEntity> findAll();
}
