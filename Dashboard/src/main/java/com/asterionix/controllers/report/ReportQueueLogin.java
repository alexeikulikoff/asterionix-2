package com.asterionix.controllers.report;

import java.util.List;

public class ReportQueueLogin implements Report{

	private List<RecordQueueLogin> records;
	
	public ReportQueueLogin(List<RecordQueueLogin> records ){
		this.records = records;
		
	}
	@Override
	public List<RecordQueueLogin> getRecords() {
		
		return records;
	}
	
	
}
