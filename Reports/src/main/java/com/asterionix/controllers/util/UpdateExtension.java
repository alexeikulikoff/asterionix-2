package com.asterionix.controllers.util;

public class UpdateExtension {
	
	 private  int id;
	 private String extension ;
	 private String penalty;
	
	 public void setId(int i){
		 this.id = i;
	 }
	 public int getid(){
		 return this.id;
	 }
	 public void setExtension(String s){
		 this.extension = s;
	 }
	 public String getExtension(){
		 return this.extension;
	 }
	 public String getPenalty(){
		 return this.penalty;
	}
	public void setPenalty(String penalty){
		this.penalty = penalty;
	}

	
	
}
