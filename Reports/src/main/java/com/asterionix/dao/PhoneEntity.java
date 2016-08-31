package com.asterionix.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "phone")
public class PhoneEntity  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "Number is required.")
	private String pnumber;
	@NotEmpty(message = "Comments is required.")
	private String comments;
	public PhoneEntity(){}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPnumber() {
		return this.pnumber;
	}
	public void setPnumber(String number) {
		this.pnumber = number;
	}
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}


}
