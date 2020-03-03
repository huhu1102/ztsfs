package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zt.po.RowMaterialPurchaseVerify;

/**
 * @author wl
 * @date 2019年6月20日 
 */
public interface RowMaterialPurchaseVerifyDao extends JpaRepository<RowMaterialPurchaseVerify,Long>{

	/*
	 *  分页查询所有
	 */
	@Query("from RowMaterialPurchaseVerify p where p.enabled=true order by p.serialNumber desc") 
    Page<RowMaterialPurchaseVerify> findbypage(String identifier, Pageable pageable);
	
	RowMaterialPurchaseVerify findById(long id);
}
