/**
 * 
 */
package com.zt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.Bidding;
import com.zt.po.DeviceInfo;
import com.zt.po.Employee;



/**
 * @author yh
 * @date 2019年4月17日
 */
@RepositoryRestResource(collectionResourceRel = "deviceinfo", path="deviceinfo") 
public interface DeviceInfoDao  extends JpaRepository<DeviceInfo, Long>{
	//根据设备状态查询设备
	public  List<DeviceInfo> findBydeviceStatus(String deviceStatus);
	//修改设备信息
	@Transactional
	@Modifying
	@Query(value = "UPDATE deviceinfo dvi set dvi.deviceStatus=?1 where dvi.deviceName=?2",nativeQuery = true)
	public int updateBydeviceName(String deviceStatus,String deviceName);
	
	
	@Query("from DeviceInfo d where d.deviceName=:deviceName")  
    Employee finddeviceInfo(@Param("deviceName") String deviceName);
	/*
	 * 自定义分页模糊条件查询
	 */
	//此处传的qiery语句
    @Query("from DeviceInfo deviceif where deviceif.deviceName like %?1% ")
    Page<DeviceInfo> findSearch(String query, Pageable pageable);
    
   //下面为新增方法
    @Query("select dvcif  from DeviceInfo dvcif where dvcif.enabled=true and dvcif.deviceName like %?1% ") 
    Page<DeviceInfo> findbypage(String deviceName, Pageable pageable);
  //
    
    @Query(value="select * from zt_deviceinfo as dvcif where dvcif.enabled=true and dvcif.deviceName like %?1% order by dvcif.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<DeviceInfo> findAllByPage(String deviceName,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_deviceinfo  as devcf where devcf.enabled=true and devcf.deviceName like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String deviceName);
    DeviceInfo  findById(long id);
    
}
