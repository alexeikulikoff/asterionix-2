package com.asterionix.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CDRPagingAndSortingRepository extends PagingAndSortingRepository<CDREntity, Long> {
	Page<CDREntity> findBySrcAndDispositionAndCalldateBetween(String src,String disposition, String calldate1, String calldate2,Pageable pageable);
	Page<CDREntity> findByDstAndDispositionAndCalldateBetween(String dst,String disposition, String calldate1, String calldate2,Pageable pageable);
	Page<CDREntity> findByDispositionAndCalldateBetween(String disposition, String calldate1, String calldate2,Pageable pageable);
	Page<CDREntity> findBySrcAndDstAndDispositionAndCalldateBetween(String dst, String src, String disposition, String answer1, String answer2,Pageable pageable);
}
