package com.asterionix.controllers.util;

public class Assign {
	 private String name;
	 private String extension ;
	 private String curses ;
	 private String penalty;
	
	 public void setName(String s){
		 this.name = s;
	 }
	 public void setExtension(String s){
		 this.extension = s;
	 }
	 public void setCurses(String s){
		 this.curses = s;
	 }
	 public String getName(){
		 return this.name;
	 }
	 public String getExtension(){
		 return this.extension;
	 }
	 public String getCurses(){
		 return this.curses;
	 }
	 public void setPenalty(String penalty){
			this.penalty = penalty;
		}
	public String getPenalty(){
			return this.penalty;
	}

}
