/**
 * 
 */
package com.zt.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.zt.po.VehicleAllot;


/**
 * @author yh
 * @date 2019年4月28日
 */
public interface VehicleAllotDao extends JpaRepository<VehicleAllot, Long> {
	@Query("from VehicleAllot v where v.enabled=true and v.carNo like %?1% ") 
	  Page<VehicleAllot> findbypage(String query, Pageable pageable);
	
	VehicleAllot findById(long id);
	//分配者可以更改单子 例如人员   车辆  时间
	
	//Page<VehicleAllot>
//	@Transactional
//	 @Modifying
//	@Query("UPDATE zt_vehiallot  SET zt_vehiallot.carNo= :carNo WHERE carallot.id = :id")
//	void updateVehicleAllotcarNo(@Param("id") long id, @Param("carNo") String carNo);
	//返回唯一一个分配单
	VehicleAllot findByCarNoAndEmpNameAndPreStartDateAndPreEndDate(String carNo,String empName,Date preStartDate,Date preEndDate);
}
