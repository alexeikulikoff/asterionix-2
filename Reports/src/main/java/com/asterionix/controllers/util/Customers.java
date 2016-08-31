package com.asterionix.controllers.util;

import java.util.ArrayList;
import java.util.List;

public class Customers {

	private List<Customer> customers;
	public Customers(){
		customers =  new ArrayList<Customer>();
	}
	public List<Customer> getCustomers(){
		return customers;
	}
	public void AddCustomer(Customer customer){
		customers.add(customer);
	}
	
}
