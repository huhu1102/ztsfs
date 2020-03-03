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


import com.zt.po.VehicleMaintenance;



/**
 * @author yh
 * @date 2019年4月17日
 */
@RepositoryRestResource(collectionResourceRel = "maintain", path="maintain")
public interface VehicleMaintenanceDao extends JpaRepository<VehicleMaintenance,Long>{
	//根据车牌号查询车辆维护记录表
	//数据库对象名  from
	@Query("from VehicleMaintenance vc where vc.vech.vehicleType=:vehicleNumber")
	 public  List<VehicleMaintenance> findByvehicleNumber(@Param("vehicleNumber")String vehicleNumber);
	//修改车辆信息状态
	@Transactional
	 @Modifying
	 @Query(value="UPDATE VehicleMaintenance vc set vc.drivingkilometres=?1,vc.bookingMileage=?2 where vc.vech.vehicleType=?3",nativeQuery = true)
	public  int  updateByvehicleNumber(String drivingkilometres, String bookingMileage,String vehicleType);
	//修改车辆使用记录表
	VehicleMaintenance findById(long id);
//	public boolean updateVehicleMaintenance(VehicleMaintenance vmt);
	 
	/*
	 * 自定义分页数
	 * */
	//传参定义分页
	@Query("from VehicleMaintenance vmt  where vmt.vech.plateNumber=:licensePlateNumber")  
	VehicleMaintenance findvehicleMaintenance(@Param("licensePlateNumber") String licensePlateNumber);
	/*
	 * 自定义分页模糊条件查询
	 */
   @Query("select vmt from VehicleMaintenance vmt where vmt.carNo like %?1% ")
   Page<VehicleMaintenance> findSearch(String carNo, Pageable pageable);
   
   //测试方法  下面全是测试方法
  public  VehicleMaintenance findBymks(String mks);
  
  //新自定义模糊查询
  
  @Query("select vcmte from VehicleMaintenance vcmte where vcmte.enabled=true and vcmte.carNo like %?1% ") 
  Page<VehicleMaintenance> findbypage(String query, Pageable pageable);
  
	//根据车牌号 和维护时间  返回一个车辆维护记录表
 List<VehicleMaintenance>  findBycarNo(String carNo);
 // @Query(value="SELECT carmaintence.carNo,MAX(carmaintence.nowMileage) AS nowMileage FROM VehicleMaintenance carmaintence  GROUP BY carmaintence.`carNo`",nativeQuery=true);
 	@Query(value="select MAX(v.maintenanceMile) from zt_vehiclemaintenance v WHERE v.carNo=?1",nativeQuery=true)
 	Float findMaintenances(String carNo);
}
