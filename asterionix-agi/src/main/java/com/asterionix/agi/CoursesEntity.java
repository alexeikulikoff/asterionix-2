package com.asterionix.agi;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class CoursesEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String coursename;
	
	private String weight;
	
	public CoursesEntity(){}
	
	public CoursesEntity(String n, String i)
	{
		this.coursename = n;
		this.weight = i;
		
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getCoursename(){
		return this.coursename;
	}
	public void setCoursename(String s){
		this.coursename = s;
	}
	public String getWeight(){
		return this.weight;
	}
	public void setWeight(String w){
		this.weight = w;
	}

}
