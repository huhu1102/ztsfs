/**
 * 
 */
package com.zt.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;


import com.zt.po.Vechclerequest;
import com.zt.po.VehicleAllot;




/**
 * @author yh
 * @date 2019年4月17日
 */
@RepositoryRestResource(collectionResourceRel = "vechclerequest", path="vechclerequest")
public interface VechclerequestDao extends JpaRepository<Vechclerequest,Long>{
	//根据车牌号查询车辆记录使用表
	@Query("from Vechclerequest v where v.vehicleInfo.vehicleType=:vehicleNumber")
	public  List<Vechclerequest> findByvehicleNumber(@Param("vehicleNumber")String vehicleNumber);
	
	
	@Query("from Vechclerequest v where v.vehicleInfo.vehicleType=:vehicleNumber")
	Vechclerequest findAsked(@Param("vehicleNumber") String vehicleNumber);
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("from Vechclerequest v where v.vehicleInfo.vehicleType like %?1% ")
    Page<Vechclerequest> findSearch(String name, Pageable pageable);
    
    //新自定义模糊查询
    
    @Query("select a  from Vechclerequest a where a.enabled=true and a.carNo like %?1% ") 
    Page<Vechclerequest> findbypage(String query, Pageable pageable);
    
    
    Vechclerequest findById(long id);
    //查询车辆已分配的车辆
   // @Query(" from Asked dsk where dek.requestStatus='MANAGED' ")
   //public  Asked findByasddd(@Param("requestStatus") String requestStatus);
    //根据车牌号 和车辆申请日期  查询车辆的状态
    //@Query("from Asked asd where asd.vcif.vehicleType=:vehicleNumber")
    //根据所选车牌号   查询申请人所选择的车牌号查询该车辆所有申请记录
    
    //@Query(value="SELECT * FROM zt_asked__vechclerequest where carNo=?1 Between 'preStartDate=?2' AND 'preEndDate=?3'",nativeQuery = true)
    		
   // Page <Vechclerequest> findByUsernameAndPassword(String carNo, Date preStartDate,  Date  preEndDate);
    //先查询详情表的所有vcrt_Id，然后和主表进行匹配    相等的出现  不相等的删除
    
    @Query(value="select *  from `zt_vechclerequest` where `zt_vechclerequest`.id IN (select 'zt_vechclerequestdetails'.vcrt_Id from 'zt_vechclerequestdetails') ",nativeQuery = true)
    Page<Vechclerequest> find(long id,Pageable pageable);
    
    @Query("from Vechclerequest v where v.vehicleInfo.plateNumber=:carNo")
     List<Vechclerequest> findVechclerequest(@Param("carNo") String carNo);
    
    @Query("from Vechclerequest v where v.empName=:empName")
    List<Vechclerequest> findVechclerequests(@Param("empName") String empName);
    
   
    
//   List <Vechclerequest> findByduration(String duration);
    
}
