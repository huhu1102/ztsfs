package com.zt.dao;

import com.zt.po.WorkShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkShopDao extends JpaRepository<WorkShop,Long> {

    /*
    查询
     */
    @Query(value = "select s.* from zt_workshop s where s.enabled=true  and if(:name !='',s.name like %:name%,1=1) and if(:code !='',s.code like %:code%,1=1) )",nativeQuery = true)
    Page<WorkShop> find(@Param("name") String name,
                         @Param("code")String code,
                         Pageable pageable);

    WorkShop findById(long id);
}
