package com.asterionix.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "agents_courses")
public class AgentsCoursesEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "`agent_id`")
	private int agent_id;
	@Column(name = "`courses_id`")
	private Long courses_id;
	@Column(name = "`extension`")
	private String extension;
	@Column(name = "`penalty`")
	private String penalty;
	
	public AgentsCoursesEntity(){};
	
	public AgentsCoursesEntity(int agent_id, Long courses_id, String extension){
		this.agent_id = agent_id;
		this.courses_id = courses_id;
		this.extension = extension;
	}
	public void setId(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}
	public int getAgent_id(){
		return this.agent_id; 
	}
	public Long getCourses_id(){
		return this.courses_id; 
	}
	public String getExtension(){
		return this.extension; 
	}
	public void setAgent_id(int a){
		this.agent_id = a;
	}
	public void setCourses_id(Long c){
		this.courses_id = c;
	}
	public void setExtension(String s){
		this.extension = s;
	}
	public void setPenalty(String penalty){
		this.penalty = penalty;
	}
	public String getPenalty(){
		return this.penalty;
	}	

}
