package com.zt.dao;

import com.zt.po.ProductUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "producteduse", path="producteduse")
public interface ProductedUseDao extends JpaRepository<ProductUse,Long> {
   /*
   查询所有
    */
   @Query("select c from ProductUse c where c.enabled=true")
    List<ProductUse> findAll();
    ProductUse findById(long id);
    @Query("select c from ProductUse c where c.enabled=true and c.id in :ids")
    List<ProductUse> findAllUse(@Param("ids") List<Long> ids);
    @Modifying
    @Query("update  ProductUse  p  set p.enabled=false where p.id=?1")
    int deleteUse(long id);
//     查询所有产品种类的数量
    @Query(value = "SELECT u.id, COUNT(p_q.u_id) as num,u.name FROM zt_productuse  u  LEFT JOIN  zt_produt_use  p_q  ON p_q.u_id=u.id GROUP BY u.id",nativeQuery = true)
    Set<Object> findallprodutType();
    @Query("from ProductUse c where c.enabled=true and c.name in :resultList")
    List<ProductUse> findByUseName(List<String> resultList);
    @Query(value = "select COUNT(*)  from zt_productuse c where c.enabled=true and c.name=?1",nativeQuery = true)
    int finddfByName(String name);
    @Query(value = "select COUNT(*)  from zt_productuse c where c.enabled=true and c.name=?1 and c.id!=?2",nativeQuery = true)
    int finddfByNameAndId(String trim, long id);
}
