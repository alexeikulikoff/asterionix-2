package com.asterionix.dao;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany()
	@JoinColumn(name="agent_id")
	private List<AgentsCoursesEntity> agentsCourses = new ArrayList<>();
	
	 public List<AgentsCoursesEntity> getAgentsCourses() {
	       
		 return agentsCourses;
	}
	
	public void setAgentsCourses(List<AgentsCoursesEntity> a){
		
		this.agentsCourses = a;
	}
	
	
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
