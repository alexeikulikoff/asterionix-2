package com.asterionix.agi;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customers implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String number;
	
	@OneToOne
	@JoinColumn(name = "courses_id")
	private CoursesEntity coursesEntity;
	
	public CoursesEntity getCoursesEntity(){
		
		return this.coursesEntity;
	}	

	public void setCoursesEntity(CoursesEntity coursesEntity ){
		
		this.coursesEntity = coursesEntity;
	}
	public Customers(){}
	
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
	
}
