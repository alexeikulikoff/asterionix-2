package com.asterionix.controllers.util;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private List<User> users;
	public Users(){
		users = new ArrayList<User>();
	}
	public void addUser(User user){
		users.add(user);
	}
	public List<User> getUsers(){
		return this.users;
	}
	
}
