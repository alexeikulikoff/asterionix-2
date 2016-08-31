package com.asterionix.agi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<PhoneEntity, Long>{
	PhoneEntity findById(Long id);
	PhoneEntity findByPnumber(String pnumber);
	List<PhoneEntity> findAll();
	

}
