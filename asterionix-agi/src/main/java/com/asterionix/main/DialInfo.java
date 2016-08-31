package com.asterionix.main;

public class DialInfo {

	String srcNumber;
	String dstNumber;
	
	public DialInfo(String srcNumber, String dstNumber){
		
		this.srcNumber = srcNumber;
		
		this.dstNumber = dstNumber;
	}
	public String getsrcNumber(){
		
		return this.srcNumber;
	}
	public String getdstNumber(){
		
		return this.dstNumber;
	}
}
