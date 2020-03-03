package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Rolese;
import com.zt.po.ShippingReceipt;

/**
 * @author wl
 * @date 2019年4月26日 
 */
@RepositoryRestResource(collectionResourceRel = "shipreceipt", path="shipreceipt")
public interface ShippingReceiptDao extends JpaRepository<ShippingReceipt, Long>{

	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from ShippingReceipt u where u.enabled=true and u.deName like %?1% or u.shName like %?2% ")
    Page<ShippingReceipt> findSearch(String query, Pageable pageable);
    
    ShippingReceipt findById(long id);
}
