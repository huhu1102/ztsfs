package com.zt.dao;

import com.zt.po.ProductManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.zt.po.ProductManageDetails;

import java.util.List;

/**
 * @author wl
 * @date 2019年5月17日 
 */
@RepositoryRestResource(collectionResourceRel = "productmanagedetails", path="productmanagedetails")
public interface ProductManageDetailsDao extends JpaRepository<ProductManageDetails, Long>{

	@Query("select p from ProductManageDetails p where p.enabled=true and p.id=?1")
	ProductManageDetails findById(long id);

	/*
	 * 自定义分页模糊条件查询
	 */
	@Query("select u from ProductManageDetails u where u.enabled=true order by u.upsDate desc")
	Page<ProductManageDetails> findSearch(Pageable pageable);

	/*
	 * 根据工序步骤id查询数量
	 */
	@Query(value = "SELECT  SUM(z.qualifiedNo),z.productProcess_id FROM `zt_productmanagedetails` z WHERE z.productManage_id=:productManageId GROUP BY z.productProcess_id;",nativeQuery = true)
    Object findEnd(@Param("productManageId")long productManageId);
	@Query(value = "SELECT * from zt_productmanagedetails p where   " +
			"       IF( :start != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 ) " +
			"      AND IF( :end != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 ) " +
			"      and  p.enabled=true and p.productManage_id=:manageId ORDER BY  p.createDate desc",
			countQuery = "SELECT count(*) from zt_productmanagedetails p where " +
			"       IF( :start != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 ) " +
			"       AND IF( :end != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 ) " +
			"       and p.enabled=true and p.productManage_id=:manageId ORDER BY  p.createDate desc" ,nativeQuery = true)
    Page<ProductManageDetails> findByMangeId(@Param("manageId") long manageId, @Param("start")String start, @Param("end")String end, Pageable pageable);

    @Modifying
    @Query(value = "update zt_productmanagedetails p set p.qualifiedNo=:qualifiedNo,p.notes=:notes where p.id=:id",nativeQuery = true)
    int updateData(@Param("qualifiedNo") float qualifiedNo, @Param("notes")String notes, @Param("id")long id);
	@Query(value = "SELECT * FROM `zt_productmanagedetails` p  LEFT JOIN zt_productmanage  m  on m.id=p.productManage_id  LEFT JOIN zt_productionplandetails  de on de.id=m.planDetails_id WHERE p.enabled=true and de.salesPlan_id=?1",nativeQuery = true)
	List<ProductManageDetails> findBySalesId(long id);
}
