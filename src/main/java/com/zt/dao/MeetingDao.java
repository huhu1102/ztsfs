package com.zt.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Meeting;

/**
 * @author wl
 * @date 2019年4月15日 
 * JPA库连接(会议记录)
 */
@RepositoryRestResource(collectionResourceRel = "meeting", path="meeting")
public interface MeetingDao extends JpaRepository<Meeting, Long>{
		
	/*
	 * 自定义分页模糊条件查询
	 */
	@Query("select m from Meeting m where m.subject like %?1% ")
	Page<Meeting> findSearch(String query, Pageable pageable);
	
	Meeting findById(long id);
}
