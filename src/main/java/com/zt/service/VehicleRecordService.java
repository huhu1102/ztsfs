/**
 * 
 */
package com.zt.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.VehicleRecordModel;
import com.zt.po.Employee;
import com.zt.po.VehicleInfo;
import com.zt.po.VehicleRecord;

/**
 * @author yh
 * @date 2019年4月19日
 */
public interface VehicleRecordService {
	 public  List<VehicleRecord> findByvehicleNumber(String vehicleNumber);
	 public boolean  updateById(String licensePlateNumber,long id);
	 public  ResultObject<VehicleRecord> saveVehicleRecord(VehicleRecord carrecord);
	 
	 ResultPage<VehicleRecord> findbyPage(Integer page,Integer size,String sort);
	 
	 Page<VehicleRecord> findSearch(String vehicleNumber,Pageable pageable);
	 
	 ResultPage<VehicleRecord> findBylicensePlateNumber(String licensePlateNumber);
	 
	public ResultObject<VehicleRecord> saveVehicleRecord(Date complate)throws BusinessRuntimeException;
	
	
	//4.27  13.59
	  public ResultPage<VehicleRecord> findbypage(String queryName, int page, int size) throws BusinessRuntimeException;
	     
	     //消息通知  前后两次都的加
	     public  ResultObject<VehicleRecord> addRecord(VehicleRecord vrd)throws BusinessRuntimeException;
	 	/**
	 	 * @param id
	 	 * @return
	 	 */
	 	public ResultObject<VehicleRecord> deletRecord(long id)throws BusinessRuntimeException;
		/**
		 * @return
		 */
		public ResultObject<Object> findBaseData()throws BusinessRuntimeException;
	 	
	
}
