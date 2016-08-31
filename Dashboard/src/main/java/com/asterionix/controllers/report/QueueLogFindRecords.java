package com.asterionix.controllers.report;

import java.util.ArrayList;
import java.util.List;

public class QueueLogFindRecords implements Report{

	private List<QueueLogFindRecord> records;
	
	public QueueLogFindRecords(){
		
		this.records = new ArrayList<QueueLogFindRecord>();
	}
	public void addRecord(QueueLogFindRecord r){
		
		records.add(r);
	}
	@Override
	public List<QueueLogFindRecord> getRecords() {
		
		return this.records;
	}
}
