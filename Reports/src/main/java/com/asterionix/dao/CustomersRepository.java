package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<CustomersEntity, Long>{
	CustomersEntity findByNumber(String number);
	CustomersEntity findById(int id);
	List<CustomersEntity> findAll();

}
