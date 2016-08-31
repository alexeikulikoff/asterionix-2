package com.asterionix.controllers.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.asterionix.dao.CDREntity;
import com.asterionix.dao.CDRPagingAndSortingRepository;

public class ServiceReportCDR extends AbstractServiceReport implements ReportService {
	
	private CDRPagingAndSortingRepository repository;

	public ServiceReportCDR(CrudRepository<CDREntity, Long> repository) {
		
		super(repository);
		
		this.repository = (CDRPagingAndSortingRepository)repository;
		
	}

	@Override
	public Report findByDispositionCalldateBetween(IQuery query) {
	
		QueryCDR q = (QueryCDR) query; 
		
		Page<CDREntity> cdrs =  repository.findByDispositionAndCalldateBetween(q.getDsp(), q.getTime1(), q.getTime2(),
									new PageRequest(q.getPage()-1, q.getPagesize()));
		
		return createReport(cdrs,q);
		
	}

	@Override
	public Report findBySrcDispositionCalldateBetween(IQuery query) {

		QueryCDR q = (QueryCDR) query; 
	  
		Pageable pageable = new PageRequest(q.getPage()-1, q.getPagesize());
		
		Page<CDREntity> cdrs = repository.findBySrcAndDispositionAndCalldateBetween(q.getSrc(),q.getDsp(), q.getTime1(), q.getTime2(),pageable);
		
		return createReport(cdrs,q);
	}

	@Override
	public Report findByDstDispositionCalldateBetween(IQuery query) {
		QueryCDR q = (QueryCDR) query; 
		  
		Pageable pageable = new PageRequest(q.getPage()-1, q.getPagesize());
		Page<CDREntity> cdrs = repository.findByDstAndDispositionAndCalldateBetween(q.getDst(),q.getDsp(), q.getTime1(), q.getTime2(),pageable);
		
		return createReport(cdrs,q);
	}

	@Override
	public Report findBySrcDstDispositionCalldateBetween(IQuery query) {
		
		QueryCDR q = (QueryCDR) query; 
		  
		Pageable pageable = new PageRequest(q.getPage()-1, q.getPagesize());
		
		Page<CDREntity> cdrs = repository.findBySrcAndDstAndDispositionAndCalldateBetween(q.getSrc(),q.getDst(),q.getDsp(), q.getTime1(), q.getTime2(),pageable);
		
		return createReport(cdrs,q);
	}
	private ReportCDR createReport(Page<CDREntity> cdrs, QueryCDR q){
		
		List<RecordCDR> records = new ArrayList<RecordCDR>();
		
		for(CDREntity cdr : cdrs){
			records.add(new RecordCDR(cdr.getId(),cdr.getuniqueid(),cdr.getCalldate(),cdr.getCalldate(),cdr.getSrc(),cdr.getDst(),cdr.getduration()));
		}
		
		ReportCDR report = new ReportCDR(records,getTabs(cdrs.getTotalPages(),q.getPage()));
		
		return report;
	}

}
