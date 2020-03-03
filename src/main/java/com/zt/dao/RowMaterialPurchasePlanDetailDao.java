package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.RowMaterialPurchasePlanDetail;

/**
 * @author wl
 * @date 2019年6月18日 
 */
@RepositoryRestResource(collectionResourceRel = "rowMaterialPurchasePlanDetail", path = "rowMaterialPurchasePlanDetail")
public interface RowMaterialPurchasePlanDetailDao extends JpaRepository<RowMaterialPurchasePlanDetail, Long>{
	
	RowMaterialPurchasePlanDetail findById(long id);

	/**
	 * @return
	 */
	@Query("from RowMaterialPurchasePlanDetail p where p.enabled=true")
	Set<RowMaterialPurchasePlanDetail> getCurrentDetail(Long materailId);
	
	
	@Query("select COUNT(p) from RowMaterialPurchasePlanDetail p where p.enabled=true and p.materialId=?1 and p.confirmStatus=1")
	int  getNotConfirmByMid (Long materailId);

	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */
	@Query("select p.name,p.specifications,p.quantity, p.confirmStatus,p.units.name,p.createDate,p.plan.serialNumber,p.notes,p.id from RowMaterialPurchasePlanDetail   p where p.materialId=?1 and  p.confirmStatus=1 and p.enabled=true")
	Set<Object[]> findCurretDetail(Long materailId);
	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */                                                  
	@Query("from RowMaterialPurchasePlanDetail p where p.materialId=?1  and  p.confirmStatus=1 and p.enabled=true")
	List<RowMaterialPurchasePlanDetail> findConnectDetail(Long materialId);
	
	
	/*
	 * 修改详情中的确认状态
	 */
	@Transactional
	@Modifying
	@Query("update RowMaterialPurchasePlanDetail p set p.confirmStatus=2 where p.id=?1")
	int updateStatus(long id);
}
