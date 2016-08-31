package com.asterionix.controllers.util;

public class User {
	private long id;
	private String firstname;
	private String password;
	public User(long id, String firstname, String password)
	{
		this.id = id;
		this.firstname = firstname;
		this.password = password;
	}
	public long getId(){
		return this.id;
	}
	public String getFirstname(){
		return this.firstname;
	}
	public String getPassword(){
		return this.password;
	}
}
