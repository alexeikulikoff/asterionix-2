package com.asterionix.controllers.util;

public class Param {
	private String name;
	private String value;
	public Param(){}
	public String getName(){
		return this.name;
	}
	public String getValue(){
		return this.value;
	}
	public void setName(String s){
		this.name = s;
	}
	public void setValue(String s){
		this.value =s;
	}
}
