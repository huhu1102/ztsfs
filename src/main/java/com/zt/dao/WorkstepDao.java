package com.zt.dao;

import com.zt.po.Workstep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author wl
 * @date 2019年4月17日 
 * 工步库
 */
@RepositoryRestResource(collectionResourceRel = "workstep", path="workstep")
public interface WorkstepDao extends JpaRepository<Workstep, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from Workstep u where u.enabled=true and u.workstepName like %?1% ")
    Page<Workstep> findSearch(String query, Pageable pageable);

	/**
	 * @return
	 */
    @Query("select u from Workstep u where u.enabled=true")
	List<Workstep> findAllsteps();

	/**
	 * @param prlist
	 * @return
	 */
    @Query("select u from Workstep u where u.enabled=true and u.id in :prlist  ORDER BY INSTR(:sqlStr,CONCAT(',',u.id,','))")
	List<Workstep> findSteps(@Param("prlist") List<Long> prlist,@Param("sqlStr") String sqlStr);
    
    Workstep findById(long id);
}
