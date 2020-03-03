package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.MidProductPurchaseVerify;

/**
 * @author wl
 * @date 2019年6月20日 
 */
public interface MidProductPurchaseVerifyDao extends JpaRepository<MidProductPurchaseVerify,Long>{

	/*
	 *  分页查询所有
	 */
	@Query("from MidProductPurchaseVerify p where p.enabled=true order by p.serialNumber desc") 
    Page<MidProductPurchaseVerify> findbypage(String identifier, Pageable pageable);
	
	MidProductPurchaseVerify findById(long id);
	
	@Transactional
	@Modifying
	@Query("update MidProductPurchaseVerify p set p.verifyStatus=2 where p.id=?1") 
	int drow(long id);
	@Query("select p.practicalQuantity from MidProductPurchaseVerify p  where p.id=?1") 
	float findActualQuality(long id);
}
