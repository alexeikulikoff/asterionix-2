package com.asterionix.controllers.report;

import java.util.List;

public interface ReportService {
	Report findByDispositionCalldateBetween(IQuery query);
	Report findBySrcDispositionCalldateBetween(IQuery query);
	Report findByDstDispositionCalldateBetween(IQuery query);
	Report findBySrcDstDispositionCalldateBetween(IQuery query);
	
}
