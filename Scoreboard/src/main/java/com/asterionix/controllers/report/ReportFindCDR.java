package com.asterionix.controllers.report;


import java.util.List;

public class ReportFindCDR  implements Report {

	private List<CDRFindRecord> records;
	private List<PageTab> tabs;
	
	public ReportFindCDR(List<CDRFindRecord> records,List<PageTab> tabs ){
		
		this.records = records;
		
		this.tabs = tabs;
		
	}
	public void addRecord(CDRFindRecord record){
		
		this.records.add(record);
		
	}
	@Override
	public List<CDRFindRecord> getRecords() {
		
		return this.records;
	}
	public List<PageTab> getTabs(){
		
		return this.tabs;
	}
	
}
