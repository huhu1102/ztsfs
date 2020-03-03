/**
 * 
 */
package com.zt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.VehicleMaintenance;
import com.zt.po.VehicleRecord;

/**
 * @author yh
 * @date 2019年4月19日
 */
public interface VehicleMaintenanceService {
	 public  List<VehicleMaintenance> findByvehicleNumber(String vehicleNumber);
	 public  boolean  updateByvehicleNumber(String drivingkilometres, String bookingMileage,String vehicleNumber);
	 public boolean updatevehicleMaintenance(VehicleMaintenance vmt);
	 
	
    ResultPage<VehicleMaintenance> findbyPage(Integer page,Integer size,String sort);
    /*
     * 分页模糊条件查询
     */
    Page<VehicleMaintenance> findSearch(String licensePlateNumber,Pageable pageable);
    //下面属于测试类
    public  List<VehicleMaintenance> findBymrks(String mrks);
    
    
  //4.27  13.59
	  public ResultPage<VehicleMaintenance> findbypage(String queryName, int page, int size) throws BusinessRuntimeException;
	     
	     
	     public  ResultObject<VehicleMaintenance> addRepair(VehicleMaintenance vcmte)throws BusinessRuntimeException;
	 	/**
	 	 * @param id
	 	 * @return
	 	 */
	 	public ResultObject<VehicleMaintenance> deletRepair(long id)throws BusinessRuntimeException;
	 	
}
