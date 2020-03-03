package com.zt.dao;

import com.zt.po.RowMaterialInRecieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



/**
 * @author wl
 * @date 2019年5月30日 
 */
@RepositoryRestResource(collectionResourceRel = "rowmaterialinrecieve", path="rowmaterialinrecieve")
public interface RowMaterialInRecievingDao extends JpaRepository<RowMaterialInRecieving, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from RowMaterialInRecieving u where u.enable=true and u.name like %?1% order by u.id desc")
    Page<RowMaterialInRecieving> findSearch(String query, Pageable pageable);

    RowMaterialInRecieving findById(long id);
}
