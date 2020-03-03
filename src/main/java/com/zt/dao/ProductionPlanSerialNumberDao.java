package com.zt.dao;

import com.zt.po.ProductionPlanSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "productionserialNumber",path = "productionserialNumber")
public interface ProductionPlanSerialNumberDao extends JpaRepository<ProductionPlanSerialNumber,Long> {

    /*
    根据客户Id查上次计划结束的编码
     */
    @Query("select m from ProductionPlanSerialNumber  m where m.clientId=?1 and m.productId=?2 and m.enabled=true order by m.id  desc")
    List<ProductionPlanSerialNumber> findNoParent(long clientId, long productId);

    /*
    有父公司的情况
     */
    @Query("SELECT d from  ProductionPlanSerialNumber d  WHERE d.clientId  in(SELECT c.id from Client c WHERE c.parentClientId=?1) and d.productId=?2 order by d.id desc")
    List<ProductionPlanSerialNumber> findParent(long parentId,long productId);

    @Query(value = "SELECT * FROM zt_productionplanserialnumber z WHERE z.clientId in(?1) ORDER BY z.createDate DESC LIMIT 3",nativeQuery = true)
    List<ProductionPlanSerialNumber> findallParent(List<Long> childIds);
}
