package com.asterionix.dao;

import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customers, Long>{
	Customers findByNumber(String number);

}
