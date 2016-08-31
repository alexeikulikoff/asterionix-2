package com.asterionix.controllers.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.asterionix.dao.CDREntity;
import com.asterionix.dao.CDRRepository;

public class ServiceReportCGR extends AbstractServiceReport implements ReportService{

	private CDRRepository repository;
	
	public ServiceReportCGR(CrudRepository<CDREntity, Long> repository) {
		super(repository);
		
		this.repository = (CDRRepository)repository;
	}

	@Override
	public Report findByDispositionCalldateBetween(IQuery query) {
		
		QueryCGR q = (QueryCGR)query;
		
		List<RecordCGR> cgrReports = new ArrayList<RecordCGR>();
		
		for (Disposition d : Disposition.values() ){
			
			List<CDREntity> cdrs =  repository.findByDispositionAndCalldateBetween(d.getDispositoin(), q.getTime1(), q.getTime2());
			String action = "'','','" + d.getDispositoin() +"','"+ q.getTime1() + "','" +  q.getTime2() + "','1','" + q.getPagesize() + "'";
			cgrReports.add(new RecordCGR(d.getDispositoin(),cdrs.size(),action));
		 }
	
		ReportCGR report = new ReportCGR(cgrReports);
		
		return report;
		
	}

	@Override
	public Report findBySrcDispositionCalldateBetween(IQuery query) {
		
		QueryCGR q = (QueryCGR)query;
		
		List<RecordCGR> cgrReports = new ArrayList<RecordCGR>();
		
		for (Disposition d : Disposition.values()  ){
			List<CDREntity> cdrs = repository.findBySrcAndDispositionAndCalldateBetween(q.getSrc(), d.getDispositoin(), q.getTime1(), q.getTime2());
			String action = "'" + q.getSrc() +"','','" + d.getDispositoin() +"','"+ q.getTime1() + "','" +  q.getTime2() + "','1','" + q.getPagesize() + "'";
			cgrReports.add(new RecordCGR(d.getDispositoin(),cdrs.size(),action));
	
		 }
	
		ReportCGR report = new ReportCGR(cgrReports);
		
		return report;
	}

	@Override
	public Report findByDstDispositionCalldateBetween(IQuery query) {
		
		QueryCGR q = (QueryCGR)query;
		
		List<RecordCGR> cgrReports = new ArrayList<RecordCGR>();
		
		
		for (Disposition d : Disposition.values() ){
			List<CDREntity> cdrs = repository.findByDstAndDispositionAndCalldateBetween(q.getDst(), d.getDispositoin(), q.getTime1(), q.getTime2());
			String action = "'','" + q.getDst() +"','" + d.getDispositoin() +"','"+ q.getTime1()+ "','" +  q.getTime2()+ "','1','" + q.getPagesize() + "'";
			cgrReports.add(new RecordCGR(d.getDispositoin(),cdrs.size(),action));
	
		 }
		ReportCGR report = new ReportCGR(cgrReports);
		
		return report;
	}

	@Override
	public Report findBySrcDstDispositionCalldateBetween(IQuery query) {
		
		QueryCGR q = (QueryCGR)query;
		
		List<RecordCGR> cgrReports = new ArrayList<RecordCGR>();
		
		for (Disposition d : Disposition.values() ){
			List<CDREntity> cdrs = repository.findBySrcAndDstAndDispositionAndCalldateBetween(q.getSrc(), q.getDst(), d.getDispositoin(), q.getTime1(), q.getTime2());
			String action = "'" + q.getSrc() +"','" +  q.getDst() + "','" + d.getDispositoin() +"','"+ q.getTime1() + "','" +  q.getTime2() + "','1','" + q.getPagesize() + "'";
			cgrReports.add(new RecordCGR(d.getDispositoin(),cdrs.size(),action));
	
		 }
		ReportCGR report = new ReportCGR(cgrReports);
		
		return report;
	}

}
