package com.asterionix.controllers.util;

public class WsConfig implements Config {

	private String fontsize;
	public WsConfig(String f){
		this.fontsize = f;
	}
	public void setFontsize(String f){
		this.fontsize = f;
	}
	public String getFontsize(){
		return this.fontsize;
	}
}

	
