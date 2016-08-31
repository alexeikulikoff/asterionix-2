package com.asterionix.controllers.report;

import java.util.List;

public interface Report {

	List<? extends Record> getRecords();
}
