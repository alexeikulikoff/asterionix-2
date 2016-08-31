package com.asterionix.controllers.report;

import java.util.List;

public class ReportQDR implements Report{

	private List<RecordQDR> records ;
	
	private List<PageTab> tabs;
	
	public ReportQDR(List<RecordQDR> records,List<PageTab> tabs ){
		
		this.records = records;
		
		this.tabs = tabs;
 		
 	}
	@Override
	public List<RecordQDR> getRecords() {
		
		return records;
	}
	public List<PageTab> getTabs(){
		
		return this.tabs;
	}
	

}
