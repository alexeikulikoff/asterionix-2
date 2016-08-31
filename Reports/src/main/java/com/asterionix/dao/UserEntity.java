package com.asterionix.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "First name is required.")
	private String firstname;

	@NotEmpty(message = "Password is required.")
	private String password;

	public UserEntity() {
	}

	public UserEntity(UserEntity user) {
		this.id = user.id;
		this.firstname = user.firstname;
		this.password = user.password;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		
	//	BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		
	//	this.password = enc.encode(password) ;
		
		this.password = password;
				
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
}
