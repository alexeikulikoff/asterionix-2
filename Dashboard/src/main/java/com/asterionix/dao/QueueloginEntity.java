package com.asterionix.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "queuelogin")
public class QueueloginEntity implements IEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long pid;
	private int aid;
	private int action;
	private String actiontime;
	
	@Column(name="queue_id")
	private Long qid;
	
	@ManyToOne
	@JoinColumn(name = "pid", insertable=false, updatable=false)
	private PhoneEntity phoneEntity;
	
	@ManyToOne
	@JoinColumn(name = "aid", insertable=false, updatable=false)
	private AgentEntity agentEntity;
	
	public QueueloginEntity(){}
	
	public PhoneEntity getPhoneEntity(){
		return this.phoneEntity;
	}
	public AgentEntity getAgentEntity(){
		return this.agentEntity;
	}
	public void setAgentEntity(AgentEntity a){
		this.agentEntity = a;
	}
	public void setPhoneEntity(PhoneEntity ent){
		this.phoneEntity = ent;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid(){
		return this.pid;
	}
	public void setPid(Long p){
		this.pid = p;
	}
	public int getAid(){
		return this.aid;
	}
	public void setAid(int a){
		this.aid = a;
	}
	public int getAction(){
		return this.action;
	}
	public void setAction(int a){
		this.action = a;
	}
	public String getActiontime(){
		return this.actiontime;
	}
	public void setActiontime(String a){
		this.actiontime = a;
	}

	public Long getQid(){
		return this.qid;
	}
	public void setQid(Long a){
		this.qid = a;
	}

	

}
