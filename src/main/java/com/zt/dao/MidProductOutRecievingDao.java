/**
 * 
 */
package com.zt.dao;

import com.zt.po.MidProductInRecieving;
import com.zt.po.MidProductOutRecieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface MidProductOutRecievingDao extends JpaRepository<MidProductOutRecieving,Long>{
    
	/*
	 * 自定义分页模糊查询
	 */
	@Query("select c from MidProductOutRecieving c where c.name like %?1% order by c.id desc")
	Page<MidProductOutRecieving> findSearch(String query, Pageable pageable);

	@Query("select c from MidProductOutRecieving c where c.enable=true and c.id=?1")
	MidProductOutRecieving findById(long id);

	/*
	修改半成品出库记录
	 */
	@Modifying
	@Query("update MidProductOutRecieving m set m.quantity=:quantity where m.productManageDetailsId=:productManageDetailsId")
	int updateReciev(@Param("quantity") float quantity,@Param("productManageDetailsId") long id);

	/*
	根据生产管理详情Id删除记录
	 */
	@Modifying
	@Query(value = "delete from zt_midproductoutrecieving mp where mp.productManageDetailsId=?1",nativeQuery = true)
	int deleteByManageId(long productManageDetailsId);
}
