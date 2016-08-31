package com.asterionix.agi;

import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customers, Long>{
	Customers findByNumber(String number);

}
