package com.asterionix.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:reports.properties",  prefix = "reports")
public class AsterionixProperties {
    
    private String host;
    private int port;  
 
    private String license;

    public String getHost(){
    	return this.host;
    }
    public void setHost(String s){
    	this.host = s;
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
}
