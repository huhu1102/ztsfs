/**  
* 
*/  
 
package com.zt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.MiddleProduct;
import com.zt.po.Position;

/**
 * @author whl
 * @date 2019年4月16日
 *  职位仓库 
 */
@RepositoryRestResource(collectionResourceRel = "position", path="position")
public interface PositionDao extends JpaRepository<Position, Long>{ 
  
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from Position u where u.enabled=true and u.name like %?1% ")
    Page<Position> findSearch(String query, Pageable pageable);
	/**
	 * @return
	 * 
	 */
    @Query("select u from Position u where u.enabled=true")
	List<Position> findallpost();
    
    Position findById(long id);

	@Query("select u from Position u where u.enabled=true and u.id in ?1")
    List<Position> findbyIds(List<Long> poslist);
}
