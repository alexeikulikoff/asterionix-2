package com.asterionix.controllers.util;

public class Phone {

	private Long id;
	private String pnumber;
	private String comments;
	
	public Phone(Long i, String pnumber, String comments){
		this.id = i;
		this.pnumber = pnumber;
		this.comments = comments;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPnumber() {
		return this.pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
