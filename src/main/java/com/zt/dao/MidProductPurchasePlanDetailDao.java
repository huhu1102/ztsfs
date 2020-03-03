package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.MidProductPurchasePlanDetail;


/**
 * @author wl
 * @date 2019年6月18日 
 */
@RepositoryRestResource(collectionResourceRel = "rowMaterialPurchasePlanDetail", path = "rowMaterialPurchasePlanDetail")
public interface MidProductPurchasePlanDetailDao extends JpaRepository<MidProductPurchasePlanDetail, Long>{
	
	MidProductPurchasePlanDetail findById(long id);

	/**
	 * @return
	 */
	@Query("from MidProductPurchasePlanDetail p where p.enabled=true")
	Set<MidProductPurchasePlanDetail> getCurrentDetail(Long materailId);
	
	
	@Query("select COUNT(p) from MidProductPurchasePlanDetail p where p.enabled=true and p.materialId=?1 and p.confirmStatus=1")
	int  getNotConfirmByMid (Long materailId);

	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */
	@Query("select p.name,p.specifications,p.quantity, p.confirmStatus,p.units.name,p.createDate,p.plan.serialNumber,p.notes,p.id from MidProductPurchasePlanDetail   p where p.materialId=?1 and  p.confirmStatus=1 and p.enabled=true")
	Set<Object[]> findCurretDetail(Long materailId);
	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */                                                  
	@Query("from MidProductPurchasePlanDetail p where p.materialId=?1  and   p.confirmStatus=1 and p.enabled=true")
	List<MidProductPurchasePlanDetail> findConnectDetail(Long materialId);
	
	
	/*
	 * 修改详情中的确认状态
	 */
	@Transactional
	@Modifying
	@Query("update MidProductPurchasePlanDetail p set p.confirmStatus=2 where p.id=?1")
	int updateStatus(long id);

	@Modifying
	@Query(value="update zt_midproductpurchaseplandetail p  LEFT JOIN zt_midproductpurchaseverify  m on p.id=m.purchasePlanDetail_id  SET  p.confirmStatus=1 where m.id=?1",nativeQuery = true)
	int drow(long id);
}
