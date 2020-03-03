package com.zt.dao;

import com.zt.po.ProductManageProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "productmanageprogress", path="productmanageprogress")
public interface ProductManageProgressDao extends JpaRepository<ProductManageProgress, Long> {


    @Modifying
    @Query("update ProductManageProgress p set p.quantity=p.quantity+:quantity,p.createDate=now()  where p.productManage.id=:productManageId and p.productProcess.id=:productProcessId")
    int updateQuantity(@Param("quantity") float quantity, @Param("productManageId") long productManageId,@Param("productProcessId") long productProcessId);
}