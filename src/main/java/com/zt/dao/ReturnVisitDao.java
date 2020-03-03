package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.ReturnVisit;

import java.util.List;

/**
 * @author wl
 * @date 2019年5月9日 
 */
@RepositoryRestResource(collectionResourceRel = "returnvisit", path="returnvisit")
public interface ReturnVisitDao extends JpaRepository<ReturnVisit, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from ReturnVisit u where u.enabled=true and u.recorder.name like %?1%")// or u.rname like %?2%
    Page<ReturnVisit> findSearch(String query, Pageable pageable);

    @Query("select u from ReturnVisit u where u.enabled=true and u.id=?1")
    ReturnVisit findById(long id);
    @Query("select u from ReturnVisit u where u.enabled=true and u.billId=?1")
    List<ReturnVisit> findByBillId(long billId);

    @Query(value = "select max(r.billstatus) from zt_returnvisit r where r.enabled = true and r.billId=?1",nativeQuery = true)
    Integer findBillId(long billId);
}
