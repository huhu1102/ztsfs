package com.zt.dao;

import com.zt.po.FinishedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author wl
 * @date 2019年4月24日 
 */
@RepositoryRestResource(collectionResourceRel = "finishedProduct", path="finishedProduct")
public interface FinishedProductDao extends JpaRepository<FinishedProduct, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
	@Query("select f from FinishedProduct f where f.enabled=true and f.product.producteName like %?1% ")
	Page<FinishedProduct> findSearch(String query, Pageable pageable);

	FinishedProduct findById(long id);

	/*
	根据销售计划ID查
	 */
	@Query("select f from FinishedProduct  f where f.enabled=true and f.productionPlanDetailsId=?1")
	List<FinishedProduct> findSalesPlanId(long id);

	/*
	修改数量
	 */
	@Modifying
	@Query("update FinishedProduct f set f.proNumber=f.proNumber+?1 where f.id=?2")
	int updateQuantity(float amout,long id);
}
