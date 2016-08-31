package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findById(Long id);
	List<UserEntity> findAll();
}
