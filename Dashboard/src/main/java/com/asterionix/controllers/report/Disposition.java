package com.asterionix.controllers.report;

public enum Disposition {
	
	ANSWERED("ANSWERED"),
	
	BUSY("BUSY"),
	
	NO_ANSWER("NO ANSWER");

	private String dispositoin;
	
	private Disposition(String dispositoin){
	
		this.dispositoin = dispositoin;
	}
	public String getDispositoin(){
		
		return this.dispositoin;
		
	}
}
