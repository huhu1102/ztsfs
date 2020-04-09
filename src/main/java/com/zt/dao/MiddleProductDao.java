/**
 * 
 */
package com.zt.dao;

import com.zt.po.MiddleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "middleproduct", path="middleproduct")
public interface MiddleProductDao extends JpaRepository<MiddleProduct,Long>{
//新自定义模糊查询

    @Query("from MiddleProduct m where m.enabled=true and m.name like %?1% order by m.buyStatus desc") 
    Page<MiddleProduct> findbypage(String query, Pageable pageable);
    
    @Query(value="select * from zt_middleproduct as mpt where mpt.enabled=true and mpt.name like %?1% order by mpt.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<MiddleProduct> findAllByPage(String marking,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_middleproduct  as mpt where mpt.enabled=true and mpt.name like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String marking);

    @Query("select m from MiddleProduct m where m.enabled=true and m.id=?1")
    MiddleProduct findById(long id);

	/**
	 * @return
	 */
    @Query("select r.name,r.unitId,r.id, s.name,r.standard from MiddleProduct r join SysUnit s on r.unitId=s.id    where r.enabled=true ") 
    List<Object[]>   findAllPruduct();
	/**
	 * @return 产品原料列表
	 */
    @Query("select r.name,r.id from MiddleProduct r where r.enabled=true ") 
	Set<Object[]> findAllPro();
	
	/*
	 * 修改有采购计划的状态
	 */
	@Transactional
	@Modifying
	@Query("update  MiddleProduct  m set m.buyStatus=true where m.id=?1")
	public Integer updateByrepairman(Long id);
	
    /*
     * 查询名称+规格
     */
    @Query("select r from MiddleProduct r where CONCAT(r.name,r.standard)  = ?1 AND r.enabled=1")
    public List<MiddleProduct> findMiddleProduct(String nameAndSpecs);
    
    /*
     * 根据名字查询规格
     */
    @Query(value="select name,GROUP_CONCAT(standard) from zt_middleproduct  GROUP BY name",nativeQuery = true)
    public List<Object[]> findSpecs();
    /**
     * @param id
     * @return 详情确认单相对应的半成品数量数量
     */
    @Query(value="SELECT r.productNo FROM zt_middleproduct r WHERE r.id = (	SELECT	p.middlePproduct_id	FROM zt_midproductpurchaseplandetail p WHERE p.id = (SELECT	m.purchasePlanDetail_id FROM	zt_midproductpurchaseverify m WHERE m.id =?1))",nativeQuery = true)
	float getQuality(long id);
    
	@Modifying
	@Query(value="update  zt_middleproduct  m set m.productNo=?1,m.buyStatus=TRUE where m.id=(SELECT	p.middlePproduct_id	FROM zt_midproductpurchaseplandetail p WHERE p.id = (SELECT	m.purchasePlanDetail_id FROM	zt_midproductpurchaseverify m WHERE m.id =?2))",nativeQuery = true)
	int drow(float midQuality,long id);

	/*
	修改库存数量
	 */
    @Modifying
	@Query("update MiddleProduct m set m.productNo=m.productNo-:productNo where m.id=:midId")
    Integer update(@Param("productNo") float productNo,@Param("midId") long id);

}
