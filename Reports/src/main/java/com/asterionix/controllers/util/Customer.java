package com.asterionix.controllers.util;

public class Customer {

	private int id;
	private String name;
	private String number;
	private String course;
	
	public Customer(){}
	
	public Customer(int id, String name,String number,  String course){
		this.id = id;
		this.name = name;
		this.number = number;
		this.course = course;
	}
	public void setId(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}
	public void setName(String s){
		this.name = s;
	}
	public String getName(){
		return this.name;
	}
	public void setNumber(String s){
		this.number = s;
	}
	public String getNumber(){
		return this.number;
	}
	public void setCourse(String s){
		this.course = s;
	}
	public String getCourse(){
		return this.course;
	}
}
