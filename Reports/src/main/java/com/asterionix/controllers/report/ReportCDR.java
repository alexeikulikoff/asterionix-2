package com.asterionix.controllers.report;


import java.util.List;

public class ReportCDR  implements Report{

	private List<RecordCDR> records ;
	
	private List<PageTab> tabs;
 	
	public ReportCDR(List<RecordCDR> records, List<PageTab> tabs ){
		this.records = records;
		this.tabs = tabs;
 	}
	@Override
	public List<RecordCDR> getRecords(){
		return this.records;
	}
	public List<PageTab> getTabs(){
		return this.tabs;
	}
	

}
