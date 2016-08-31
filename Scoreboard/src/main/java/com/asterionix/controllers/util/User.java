package com.asterionix.controllers.util;

public class User {
	private long id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	public User(long id, String firstname, String lastname, String email,  String password)
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		
	}
	public long getId(){
		return this.id;
	}
	public String getFirstname(){
		return this.firstname;
	}
	public String getLastname(){
		return this.lastname;
	}
	public String getEmail(){
		return this.email;
	}
	public String getPassword(){
		return this.password;
	}
}
