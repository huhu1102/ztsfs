package com.zt.dao;

import com.zt.po.RowMaterialOutRecieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RowMaterialOutRecievingDao extends JpaRepository<RowMaterialOutRecieving,Long>{

    RowMaterialOutRecieving findById(long id);

    /*
     * 自定义分页模糊条件查询
     */
    @Query("select u from RowMaterialOutRecieving u where u.enable=true and u.name like %?1% order by u.id desc")
    Page<RowMaterialOutRecieving> findSearch(String query, Pageable pageable);
}
