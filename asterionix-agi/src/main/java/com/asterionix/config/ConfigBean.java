package com.asterionix.config;

public class ConfigBean {
		private String asterisk_host;
		private String asterisk_port;
	    private	String username;
	    private String password;
	    private String	agi_server_host;
	    private String	agi_server_port;
	    private String	agi_server_backlog;
		private String license;

		public String getAsterisk_host(){
	    	return this.asterisk_host;
	    }
	    public void setAsterisk_host(String s){
	    	this.asterisk_host = s;
	    }
	    public String getAsterisk_port(){
	    	return this.asterisk_port;
	    }
	    public void setAsterisk_port(String p){
	    	this.asterisk_port = p;
	    }
	    public String getUsername(){
	    	return this.username;
	    }
	    public void setUsername(String p){
	    	this.username = p;
	    }
	    public String getPassword(){
	    	return this.password;
	    }
	    public void setPassword(String p){
	    	this.password = p;
	    }
	    public String getAgi_server_host(){
	    	return this.agi_server_host;
	    }
	    public void setAgi_server_host(String p){
	    	this.agi_server_host = p;
	    }
	    public String getAgi_server_port(){
	    	return this.agi_server_port;
	    }
	    public void setAgi_server_port(String p){
	    	this.agi_server_port = p;
	    }
	    public String getAgi_server_backlog(){
	    	return this.agi_server_backlog;
	    }
	    public void setAgi_server_backlog(String p){
	    	this.agi_server_backlog = p;
	    }
	    public String getLicense(){
	    	return this.license;
	    }
	    public void setLicense(String s){
	    	this.license = s;
	    }	    
}
