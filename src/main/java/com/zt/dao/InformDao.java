package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Inform;

/**
 * @author wl
 * @date 2019年4月15日
 * 
 */
@RepositoryRestResource(collectionResourceRel = "informDao", path = "informDao")
public interface InformDao extends JpaRepository<Inform, Long> {
	/*
	 * 自定义分页模糊条件查询
	 */
	@Query("select i from Inform i where i.enabled=true and i.subject like %?1% ")
	Page<Inform> findSearch(String query, Pageable pageable);
	Inform  findById(long id);
}
