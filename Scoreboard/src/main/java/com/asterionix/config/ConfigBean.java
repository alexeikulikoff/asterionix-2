package com.asterionix.config;

public class ConfigBean {
	 
	    private String host;
	    private String port;   
	    private String license;
	    private String fontsize;
	    private String username;
	    private String password;

	    public String getFontsize(){
	    	return this.fontsize;
	    }
	    public void setFontsize(String s){
	    	this.fontsize = s;
	    }
	    public String getUsername(){
	    	return this.username;
	    }
	    public void setUsername(String s){
	    	this.username = s;
	    }
	    public String getPassword(){
	    	return this.password;
	    }
	    public void setPassword(String s){
	    	this.password = s;
	    }
	    public String getHost(){
	    	return this.host;
	    }
	    public void setHost(String s){
	    	this.host = s;
	    }
	   
	    public String getPort(){
	    	return this.port;
	    }
	    public void setPort(String p){
	    	this.port = p;
	    }
	    public String getLicense(){
	    	return this.license;
	    }
	    public void setLicense(String s){
	    	this.license = s;
	    }
}
