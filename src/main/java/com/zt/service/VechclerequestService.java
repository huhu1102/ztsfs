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
import com.zt.model.VechclerequestModel;

import com.zt.po.Vechclerequest;
import com.zt.po.VehicleInfo;




/**
 * @author yh
 * @date 2019年4月19日
 */
public interface VechclerequestService {
	public  List<Vechclerequest> findByvehicleNumber(String vehicleNumber);
	public boolean updatematterCycleById(String matterCycle ,long id);
	
	public  ResultObject<Vechclerequest> saveVechclerequest(Vechclerequest asks)throws BusinessRuntimeException;
	public boolean update(Vechclerequest askds);
	
	ResultPage<Vechclerequest> findbyPage(Integer page,Integer size,String sort);
	
	Page<Vechclerequest> findSearch(String vehicleNumber,Pageable pageable);
	
	//4.27  13.59
	  public ResultPage<Vechclerequest> findVechclerequest(String queryName, int page, int size) throws BusinessRuntimeException;
	     
	      //此方法要加发送消息事件  
	     public  ResultObject<Vechclerequest> addVechclerequest(Vechclerequest asd)throws BusinessRuntimeException;
	 	/**
	 	 * @param id
	 	 * @return
	 	 */
	     //根据申请单id删除申请单
	 	public ResultObject<Vechclerequest> deletVechclerequest(long id)throws BusinessRuntimeException;
	 	
	 	
	 	//2019/4/28/15.24
	   	//仅仅显示空闲状态的车
	   	public ResultObject <VehicleInfo> findVehicleInfos()throws BusinessRuntimeException;
	   	//查找已分配的申请单
	   //	public ResultObject <Asked> findByasddd()throws BusinessRuntimeException;
	   	//显示除使用中和维护中所有的车辆信息
	   	
	   //根据所选的日期查询请求单子
	   //	public ResultObject <Vechclerequest> findVehicleInfos()throws BusinessRuntimeException;
	   	/*	
	   	信息的修改
		 */
	   
		public  ResultObject<Vechclerequest> save(VechclerequestModel vcrqm)throws BusinessRuntimeException;
		//车辆信息的修改
		//public  ResultObject<Vechclerequest> upd(Vechclerequest vcrqm)throws BusinessRuntimeException;
		
			
}
