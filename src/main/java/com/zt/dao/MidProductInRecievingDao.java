/**
 * 
 */
package com.zt.dao;

import java.util.Set;

import com.zt.po.MidProductInRecieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author yh
 * @date 2019年5月9日
 */
public interface MidProductInRecievingDao extends JpaRepository<MidProductInRecieving,Long>{
//新自定义模糊查询
    
	/**
	 * @return
	 */
    @Query("select r.unitId,r.id  from MidProductInRecieving r where r.enable=true order by r.id desc")
	Set<MidProductInRecieving> findAllPruduct();
    
	/*
	 * 自定义分页模糊查询
	 */
	@Query("select c from MidProductInRecieving c where c.name like %?1%  order by c.id desc")
	Page<MidProductInRecieving> findSearch(String query, Pageable pageable);

	MidProductInRecieving findById(long id);
}
