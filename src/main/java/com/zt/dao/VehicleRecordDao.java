/**
 * 
 */
package com.zt.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.model.BusinessRuntimeException;
import com.zt.po.VehicleMaintenance;
import com.zt.po.VehicleRecord;



/**
 * @author yh
 * @date 2019年4月17日
 */
//根据车牌查询行程记录
@RepositoryRestResource(collectionResourceRel = "vehiclerecord", path="vehiclerecord")
public interface VehicleRecordDao extends JpaRepository<VehicleRecord, Long> {
	//根据行程记录单车辆字段查询
	@Query("from VehicleRecord v where v.vehicleInfo.vehicleType=:vehicleNumber")
	 public  List<VehicleRecord> findByvehicleNumber(@Param("vehicleNumber")String vehicleNumber);
	//根据车牌号更新车辆信息
	@Transactional
	 @Modifying
	 @Query(value = "UPDATE VehicleRecord vr set vr.vehicleInfo.cause=?1 where vr.vehicleInfo.id=?2",nativeQuery = true)//加上native表示的就是原生的sql语句
	 public int updateById(String licensePlateNumber,long id);
	//自定义分页查询
	@Query("from VehicleRecord v where v.vehicleInfo.vehicleType=:vehicleNumber")  
	VehicleRecord findvehicleRecord(@Param("vehicleNumber") String vehicleNumber);
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select v from VehicleRecord v where v.vehicleInfo.vehicleType like %?1% ")
    Page<VehicleRecord> findSearch(String query, Pageable pageable);
    /*
     	*     修改更新信息
     * */
    //根据提交时间更新行车记录表
//    @Transactional
//	 @Modifying
//	 @Query(value="UPDATE VehicleRecord vr  where vr.complate=?1",nativeQuery = true)
//    
//    public VehicleRecord saveVehicleRecord(Date complate)throws BusinessRuntimeException;
	//新自定义方法
  //新自定义模糊查询  //模糊查找  行车记录  根据车牌号
    
    @Query("select v from VehicleRecord v where v.enabled=true and v.vehicleInfo.plateNumber like %?1% ") 
    Page<VehicleRecord> findbypage(String query, Pageable pageable);
    VehicleRecord findById(long id);
    //根据使用者  出行时间  车辆查询车辆行驶记录表
//    VehicleRecord  findBycarNoAndtravellingPeopleAndcreateDate(String carNo, String travellingPeople,Date createDate);
    //@Transactional
	 //@Modifying
	 //@Query(value="UPDATE VehicleRecord vr  where vr.id=?1",nativeQuery = true)
    	//public int updateById(long id);
}
