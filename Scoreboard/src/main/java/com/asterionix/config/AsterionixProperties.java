package com.asterionix.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:scoreboard.properties",  prefix = "scoreboard")
public class AsterionixProperties {
    
    private String host;
    private int port;  
    private String username;
    private String password;
    
    private String license;
    private String fontsize;
    
    public String getHost(){
    	return this.host;
    }
    public void setHost(String s){
    	this.host = s;
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
    public int getPort(){
    	return this.port;
    }
    public void setPort(int p){
    	this.port = p;
    }
    public String getLicense(){
    	return this.license;
    }
    public void setLicense(String s){
    	this.license = s;
    }
    public String getFontsize(){
    	return this.fontsize;
    }
    public void setFontsize(String f){
    	this.fontsize = f;
    }
}
