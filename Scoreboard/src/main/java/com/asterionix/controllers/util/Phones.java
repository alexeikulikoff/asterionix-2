package com.asterionix.controllers.util;

import java.util.ArrayList;
import java.util.List;

public class Phones {

	private List<Phone> phones;
	
	public Phones(){
		phones = new ArrayList<Phone>();
	}
	public List<Phone> getPhones(){
		return this.phones;
	}
	public void addPhone(Phone ph){
		this.phones.add(ph);
	}
}
