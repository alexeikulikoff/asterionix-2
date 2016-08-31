package com.asterionix.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CDRFindRepository extends CrudRepository<CDRFindEntity, Long> {
	
	Page<CDRFindEntity> findBySrcContainingAndLastappAndCalldateBetween(String src,String lastapp, String calldate1, String calldate2,Pageable pageable);
	Page<CDRFindEntity> findBySrcAndCalldateBetween(String src, String calldate1, String calldate2,Pageable pageable);
	
}
