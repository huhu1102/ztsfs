/**
 * 
 */
package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zt.po.FinishedRecieving;
import org.springframework.data.repository.query.Param;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface FinishedRecievingDao extends JpaRepository<FinishedRecieving,Long>{

    /*
    修改入库记录
     */
    @Modifying
    @Query("update FinishedRecieving f set f.quantity=f.quantity+:quantity where f.enable=true and  f.productManageDetailsId=:productManageDetailsId")
	int updateReciev(@Param("quantity") float quantity,@Param("productManageDetailsId") long id);

    /*
	根据生产管理详情Id删除记录
	 */
    @Modifying
    @Query(value = "update  zt_finishedrecieving  mp set mp.enable=false where mp.productManageDetailsId=?1",nativeQuery = true)
    int deleteByManageId(long productManageDetailsId);


}
