/**
 * 
 */
package com.zt.service;

import java.util.List;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.VehicleInfo;
import com.zt.po.VehicleMaintenance;

/**
 * @author yh
 * @date 2019年4月12日
 */
public interface VehicleInfoService {
	//根据状态查询车辆信息
	public 	List<VehicleInfo>findByStatus(String  Status)throws BusinessRuntimeException;
	
	//
    public  boolean  updateVehicleInfo(String carNo,long Id)throws BusinessRuntimeException;
    //	
   public boolean  deleteVehicleInfo(long id )throws BusinessRuntimeException;
   //修改车辆信息
   public boolean updateVehicleInfoms(VehicleInfo vci)throws BusinessRuntimeException;
   //
   
   
   
   //修改车辆信息
   public  ResultObject<VehicleInfo> saveVehicleInfo(VehicleInfo vci)throws BusinessRuntimeException;
   //根据车牌号查找车辆信息
   public ResultObject<VehicleInfo> findVehicleInfo(String  licensePlateNumber)throws BusinessRuntimeException;
   
   //根据状态查询车辆信息
   public ResultObject <VehicleInfo>findByBStatus(String  Status)throws BusinessRuntimeException;
   

/**
 * @param vci
 * @return
 */
     ResultObject<VehicleInfo> saveEmpoyee(VehicleInfo vci);
     
     public ResultPage<VehicleInfo> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
     
     
     public  ResultObject<VehicleInfo> saveVehicleInfos(VehicleInfo vhcf)throws BusinessRuntimeException;
 	/**
 	 * @param id
 	 * @return
 	 */
 	public ResultObject<VehicleInfo> deletVehicleInfo(long id)throws BusinessRuntimeException;
 	
 	//下面属于后台测试类 测试是都关联起来
 	public  List<VehicleInfo> findByplateNumber(String plateNumber);
 	
 	
 	
}
