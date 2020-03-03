package com.zt.dao;

import com.zt.po.ProductPreProcess;
import com.zt.po.ProductProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author wl
 * @date 2019年5月7日 
 */
@RepositoryRestResource(collectionResourceRel = "productpreprocess", path="productpreprocess")
public interface ProductPreProcessDao extends JpaRepository<ProductPreProcess, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("from ProductPreProcess u where u.enabled=true ")
    Page<ProductPreProcess> findSearch(String query, Pageable pageable);

	/**
	 * @param stepNumber
	 * @return
	 */
    @Query("select u from ProductProcess u where u.stepNumber=:stepNumber")
	ProductProcess findbystepnumber(@Param("stepNumber") long stepNumber);
    /*
    判断code值是否存在
     */
    @Query("select count(p) from ProductPreProcess p where p.code=?1 and p.enabled=true")
	Integer findCode(String code);

    @Query("select count(p) from ProductPreProcess p where p.code=?1 and p.id!=?2 and p.enabled=true")
	Integer findCodeId(String code,long id);

	@Query("from ProductPreProcess  z WHERE z.id in :ids")
	List<ProductPreProcess> findSpecs(@Param("ids")List<Long> ids);

    ProductPreProcess findById(long id);
}
