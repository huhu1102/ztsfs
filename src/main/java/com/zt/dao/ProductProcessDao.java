package com.zt.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.ProductProcess;

/**
 * @author wl
 * @date 2019年4月17日 
 * 工序库
 */
@RepositoryRestResource(collectionResourceRel = "productprocess", path="productprocess")
public interface ProductProcessDao extends JpaRepository<ProductProcess,Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from ProductProcess u where u.enabled=true and u.name like %?1% ")
    Page<ProductProcess> findSearch(String query, Pageable pageable);

    @Query("select p from ProductProcess p where p.enabled=true and p.id=?1")
    ProductProcess findById(long id);
}
