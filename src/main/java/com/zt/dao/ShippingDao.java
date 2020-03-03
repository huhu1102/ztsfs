package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Shipping;

/**
 * @author wl
 * @date 2019年5月30日 
 */
@RepositoryRestResource(collectionResourceRel = "shipping", path="shipping")
public interface ShippingDao extends JpaRepository<Shipping, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from Shipping u where u.enable=true and u.name like %?1% ")
    Page<Shipping> findSearch(String query, Pageable pageable);

    Shipping findById(long id);
}
