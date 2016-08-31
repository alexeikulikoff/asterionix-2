package com.asterionix.config;

public class ConfigBean {
	 
	   private String host;
	    private String port;   
	    private String sounds;   
	    private String license;

	    public String getHost(){
	    	return this.host;
	    }
	    public void setHost(String s){
	    	this.host = s;
	    }
	   
	    public String getSounds(){
	    	return this.sounds;
	    }
	    public void setSounds(String s){
	    	this.sounds = s;
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
