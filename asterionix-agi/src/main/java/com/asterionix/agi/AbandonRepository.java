package com.asterionix.agi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AbandonRepository extends CrudRepository<Abandon, Long>{

	Abandon findByCallernumber(String callernumber);
	String findByUniqueid(String uniqueid);
	List<Abandon> findByCallernumberAndCalldateGreaterThan(String callernumber, String calldate);
}
