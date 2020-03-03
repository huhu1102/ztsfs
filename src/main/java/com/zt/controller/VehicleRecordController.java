/**
 * 
 */
package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.VehicleInfo;
import com.zt.po.VehicleRecord;
import com.zt.service.VehicleRecordService;
import com.zt.serviceImp.VehicleRecordServiceImpl;

/**
 * @author yh
 * @date 2019年4月19日
 */
@RestController
@RequestMapping(value="/vehiclerecord")
public class VehicleRecordController {
	@Autowired
	private VehicleRecordService vRecordService;
	@RequestMapping("/findvehicleRecord")
	
	public ResultObject<VehicleRecord> findvehicleNumberAll(String  vehicleNumber){
		 ResultObject<VehicleRecord> ro=new ResultObject<>();
		 
		 List<VehicleRecord> user = null;
		 if(ro.isSuccess()) {
			   new BusinessRuntimeException(ResultCode.SUCCESS);
		   }else {
			   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
	
	      return ro;
	    }
	
	@RequestMapping(value="/updatevehicleRecord",method=RequestMethod.POST)
	public ResultObject<VehicleRecord> saveEmp(VehicleRecord vcrd){
		
		ResultObject<VehicleRecord> ro=new ResultObject<>();
	
			ro= vRecordService.saveVehicleRecord(vcrd);
		   if(ro.isSuccess()) {
			   new BusinessRuntimeException(ResultCode.SUCCESS);
		   }else {
			   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
	
	      return ro;
	  }
	
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/page",method=RequestMethod.GET)
	  public ResultPage<VehicleRecord> saveEmployee(int  page,int size){
		  ResultPage<VehicleRecord> ro=new ResultPage<>();	  
		 String sort="DESC";
		 try {
			 ro = vRecordService.findbyPage(page, size, sort);
			 ro.setSuccess(true);
		} catch (Exception e) {
	         ro.setSuccess(false);
		}
	      return ro;
	  }
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<VehicleRecord> findbypage(int page,int size,String queryName) {
		ResultPage<VehicleRecord> ro=new ResultPage<VehicleRecord>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<VehicleRecord> empPage=null;
		List<VehicleRecord> list=null;
		try {
			/**
			 * 调用service层 页面查找方法 findSearch
			 */
			empPage=  vRecordService.findSearch(queryName, pageable);
			list=  empPage.getContent();
		    ro.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			 ro.setSuccess(false);
		}
		ro.setData(list);
		ro.setTotal(empPage.getTotalElements());
		ro.setSuccess(true);
		return ro;
	}
	//以下为新的方法
	
	//此方法要写
			@RequestMapping(value="/deletvd",method=RequestMethod.GET)
			  public ResultObject<VehicleRecord> deletvf(long id){
			     return vRecordService.deletRecord(id);
			  }
			
			//此方法要写
			@RequestMapping(value="/addvd",method=RequestMethod.POST)		
			  public ResultObject<VehicleRecord> savevf(VehicleRecord carsg){
			     return vRecordService.addRecord(carsg);
			  }
			//此方法要写
			@RequestMapping(value="/findbypages",method=RequestMethod.GET)
			public ResultPage<VehicleRecord> findbypages(HttpServletRequest request,int page,int size,String queryName) {
				return vRecordService.findbypage(queryName, page, size);
			}
	
			/**
			 * @return 所有车辆信息
			 */
			@RequestMapping(value="/baseData",method=RequestMethod.GET)
			public ResultObject<Object> findBaseData() {
				return vRecordService.findBaseData();
			}
}
