package com.asterionix.controllers.util;

import java.util.List;

import org.springframework.data.domain.Page;

import com.asterionix.dao.CDREntity;

public interface QueryRepository {

	List<Class<?> > getQueryResult();
}
