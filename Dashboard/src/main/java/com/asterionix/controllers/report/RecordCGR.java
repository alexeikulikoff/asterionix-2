package com.asterionix.controllers.report;

public class RecordCGR implements Record {
	private String disposition;
	private int value;
	private String action;
	public RecordCGR(String d, int v, String a){
		this.disposition = d;
		this.value = v;
		this.action =a;
	}
	public String getDisposition(){
		return this.disposition;
	}
	public int getValue(){
		return this.value;
	}
	public String getAction(){
		return this.action;
	}

}
