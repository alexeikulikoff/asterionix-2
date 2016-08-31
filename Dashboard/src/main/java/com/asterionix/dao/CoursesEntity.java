package com.asterionix.dao;

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

}
