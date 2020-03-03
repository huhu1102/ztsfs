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

import com.zt.po.RowMaterialPurchasePlan;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "rowMaterialPurchasePlan", path = "rowMaterialPurchasePlan")
public interface RowMaterialPurchasePlanDao extends JpaRepository<RowMaterialPurchasePlan, Long> {
	@Query("from RowMaterialPurchasePlan p where p.enabled=true order by p.id desc") 
    Page<RowMaterialPurchasePlan> findbypage(String identifier, Pageable pageable);

	RowMaterialPurchasePlan findById(long id);

	/**
	 * @return
	 */
    @Query("select max(p.id)  from RowMaterialPurchasePlan p") 
	Long getMaxId();
    
    
    /*
     * 查询采购计划的编号
     */
    @Query("SELECT m.serialNumber as c FROM RowMaterialPurchasePlan m WHERE m.serialNumber=:number")
    List<String> selectNumber(@Param("number")String  serialNumber);
}
