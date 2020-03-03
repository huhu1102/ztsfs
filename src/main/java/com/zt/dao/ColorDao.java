package com.zt.dao;

import com.zt.po.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "color", path="color")
public interface ColorDao extends JpaRepository<Color,Long> {
   /*
   查询所有
    */
   @Query("select c from Color c where c.enabled=true")
    List<Color> findAll();

    Color findById(long id);
    @Query("select c from Color c where c.enabled=true and c.id in :ids")
    List<Color> findColors(@Param("ids")List<Long> ids);
}
