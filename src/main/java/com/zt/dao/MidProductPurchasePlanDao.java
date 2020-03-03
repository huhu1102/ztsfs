/**
 * 
 */
package com.zt.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.MidProductPurchasePlan;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "midProductPurchasePlan", path = "midProductPurchasePlan")
public interface MidProductPurchasePlanDao extends JpaRepository<MidProductPurchasePlan, Long> {
	
	@Query("from MidProductPurchasePlan p where p.enabled=true order by p.id desc") 
    Page<MidProductPurchasePlan> findbypage(String identifier, Pageable pageable);

	MidProductPurchasePlan findById(long id);

	/**
	 * @return
	 */
    @Query("select max(p.id)  from MidProductPurchasePlan p") 
	Long getMaxId();
    
    /*
     * 查询采购计划的编号
     */
    @Query("SELECT m.serialNumber as c FROM MidProductPurchasePlan m WHERE m.serialNumber=:number")
    List<String> selectNumber(@Param("number")String  serialNumber);
}
