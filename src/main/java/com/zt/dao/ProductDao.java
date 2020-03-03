package com.zt.dao;

import com.zt.po.Product;
import com.zt.po.ProductManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

/**
 * @author wl
 * @date 2019年4月17日 
 * 产品库
 */
@RepositoryRestResource(collectionResourceRel = "product", path="product")
public interface ProductDao extends JpaRepository<Product, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
//    @Query("select u from Product u where u.enabled=true and u.producteName like %?1%  order by u.id desc")
//    Page<Product> findSearch(String query, Pageable pageable);

	/*
	 * 自定义分页模糊条件查询
	 */
	@Query(value = "SELECT  DISTINCT p1.* FROM zt_products p1 WHERE p1.enabled=true and " +
            "( IF (ISNULL(:colorName) || LENGTH(trim(:colorName)),1 = 1,p1.colorName LIKE %:colorName%) )and " +
            "( IF (ISNULL(:productName) || LENGTH(trim(:productName)),1 = 1,p1.producteName LIKE %:productName% ) )and " +
            "( IF (ISNULL(:proType) || LENGTH(trim(:proType)),1 = 1,p1.proType  like %:proType% ) )and " +
            " ( IF (ISNULL(:specName) || LENGTH(trim(:specName))  ,1 = 1,p1.specName LIKE %:specName% ) ) ORDER BY p1.id DESC",
            countQuery = "SELECT  count(*) FROM zt_products p1 WHERE p1.enabled=true and " +
					"(IF (ISNULL(:colorName) || LENGTH(trim(:colorName)),1 = 1,p1.colorName LIKE %:colorName% ) )and " +
					"(IF (ISNULL(:productName) || LENGTH(trim(:productName)),1 = 1,p1.producteName LIKE %:productName% ) )and  " +
					"(IF (ISNULL(:proType) || LENGTH(trim(:proType)),1 = 1,p1.proType  like %:proType% ) )and " +
					"(IF (ISNULL(:specName) || LENGTH(trim(:specName)),1 = 1,p1.specName LIKE %:specName%   ) )",
            nativeQuery = true)
	Page<Product> findSearch(@Param("productName")String productName,@Param("colorName")String colorName,@Param("proType") String proType, @Param("specName")String specName,Pageable pageable);
    @Query(value="select * from zt_products as v where v.enabled=true and v.producteName like %?1% order by v.createDate desc limit ?2,?3",
    		nativeQuery =true)
	List<Product> findAllByPage(String queryName, Integer pageNumber,Integer pageSize);

    @Query(value="select count(*) from zt_products as v where v.enabled=true and v.producteName like %?1%",
 		   nativeQuery =true) //
	int countAllData(String queryName);
    
    /**
     * 根据id查
     */
    Product findById(long id);

	/**
	 * 
	 */
    @Query("select u from Product u where u.enabled=true ")
	Set<Product> findAlldatas();

	@Query("select p.id,p.producteName,p.specification,p.proType,p.color from Product p where p.enabled=true")
	Set<Object[]> findProduct();

	/**
	查询最大的产品编号
	 */
	@Query("select max(p.serialNumber) from Product p where p.enabled=true")
	String getMaxNumber();

	/*
    *根据销售计划查询产品
  */
	@Query(value = " select  p.* from  zt_salesplan   s  inner  join  zt_products   p  on s.proId=p.id  where p.enabled=true and s.id=:id",nativeQuery = true)
	Product findClient(@Param("id")long id);

	@Query(value = "select \n" +
			"\tp.producteName AS producteName,\n" +
			"\tp.colorName AS colorName,\n" +
			"\tp.color AS colorIds,\n" +
			"\tp.specName AS specName,\n" +
			"\tp.specification AS specificationIds\n" +
			"from \n" +
			"\tzt_products AS p\n" +
			"\twhere p.enabled=true",nativeQuery = true)
	Page<Object> selectAll(Pageable pageable);
}
