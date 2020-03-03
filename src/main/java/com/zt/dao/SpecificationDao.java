package com.zt.dao;

import com.zt.po.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;
@RepositoryRestResource(collectionResourceRel = "specification", path="specification")
public interface SpecificationDao extends JpaRepository<Specification,Long> {

    Specification findById(long id);

    /*
     * 自定义分页模糊条件查询
     */
    @Query("select u from Specification u where u.enable=true and u.name like %?1% ")
    Page<Specification> findSearch(String query, Pageable pageable);
    @Query("select u from Specification u where u.enable=true ")
    Set<Specification> findAllspec();
    @Query(value = "select GROUP_CONCAT(DISTINCT z.name separator '/')  from zt_specification  z WHERE z.id in :ids ",nativeQuery = true)
    List<String> findbyids(@Param("ids")List<Long> ids);
    @Query(" from Specification  z WHERE z.id in :ids ")
    List<Specification> findSpecs(@Param("ids")List<Long> ids);
    @Query("select s from Specification s where s.name=?1")
    String findName(String name);
}
