package com.asterionix.controllers.report;
import java.util.List;

public class ReportCGR implements Report {

	private List<RecordCGR> records ;
	
	public ReportCGR(List<RecordCGR> records ){
		this.records = records;
 	}

	@Override
	public List<RecordCGR> getRecords() {
		
		return records;
	}
	
	
	
}
