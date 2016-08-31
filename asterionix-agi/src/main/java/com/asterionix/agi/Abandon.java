package com.asterionix.agi;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "abandon")
public class Abandon implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String callernumber;
	private String calldate;
	private String uniqueid;
	private String queue;
	public Abandon(){}
	
	public void setId(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}
	public void setCallernumber(String s){
		this.callernumber = s;
	}
	public String getCallernumber(){
		return this.callernumber;
	}
	public void setCalldate(String s){
		this.calldate = s;
	}
	public String getCalldate(){
		return this.calldate;
	}
	public void setUniqueid(String s){
		this.uniqueid = s;
	}
	public String getUniqueid(){
		return this.uniqueid;
	}
	public void setQueue(String s){
		this.queue = s;
	}
	public String getQueue(){
		return this.queue;
	}
}
