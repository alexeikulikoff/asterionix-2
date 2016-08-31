package com.asterionix.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CDRRepository extends CrudRepository<CDREntity, Long> {
	
	List<CDREntity> findBySrcAndDispositionAndCalldateBetween(String src,String disposition, String calldate1, String calldate2);
	List<CDREntity> findByDstAndDispositionAndCalldateBetween(String dst,String disposition, String calldate1, String calldate2);
	List<CDREntity> findByDispositionAndCalldateBetween(String disposition, String calldate1, String calldate2);
	List<CDREntity> findBySrcAndDstAndDispositionAndCalldateBetween(String dst, String src, String disposition, String answer1, String answer2);
}
