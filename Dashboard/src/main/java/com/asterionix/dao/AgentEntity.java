package com.asterionix.dao;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "agents")
public class AgentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull	
	private int id;
	@NotEmpty(message = "Name is required.")
	private String name;
	public AgentEntity(){}
	public AgentEntity(int id, String name)
	{
		this.id = id;
		this.name = name;
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
	
	

}
