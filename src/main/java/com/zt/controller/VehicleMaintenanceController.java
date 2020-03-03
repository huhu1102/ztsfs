/**
 * 
 */
package com.zt.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.VehicleMaintenance;
import com.zt.po.VehicleRecord;
import com.zt.service.VehicleMaintenanceService;
import com.zt.serviceImp.VehicleMaintenanceServiceImp;

/**
 * @author yh
 * @date 2019年4月19日
 */
@RestController
@RequestMapping(value="/vehicleRepair")
public class VehicleMaintenanceController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private  VehicleMaintenanceService vehiclService;
	@RequestMapping("/findVehiclemn")
	 public ResultObject<VehicleMaintenance> findvehicleNumberAll(String  vehicleNumber){
		 ResultObject<VehicleMaintenance> ro=new ResultObject<>();
		 
		 List<VehicleMaintenance> user = null;
		 try {
			 user = vehiclService.findByvehicleNumber(vehicleNumber);
			 ro.setData(user);
     } catch (Exception e) {
			e.printStackTrace();
			ro.setSuccess(false);
			ro.setMsg("查询失败");
		}
	        return ro;
	    }
	//修改表的某些属性
	@RequestMapping("/updatevehiclemnone")
	public ResultObject<VehicleMaintenance> updateVehicleMaintenanceByvehicleNumber(String drivingkilometres, String bookingMileage, String vehicleNumber){
		ResultObject<VehicleMaintenance> result = new ResultObject<>();
		 Boolean bl=false; 
		 try {
			bl=vehiclService.updateByvehicleNumber(drivingkilometres, bookingMileage, vehicleNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(bl.valueOf(true)) {
			result.setMsg("修改成功！");
		}else{
			result.setMsg("失败");
		}
		result.setSuccess(bl);
        return result;
	}
	//修改保养表的某些信息
	@RequestMapping("/updatevehicleMte")
	public void update(@RequestBody VehicleMaintenance  vmte) {
		
		vehiclService.updatevehicleMaintenance(vmte);

	} 
	@RequestMapping(value="/page",method=RequestMethod.GET)
	  public ResultPage<VehicleMaintenance> saveEmployee(int  page,int size){
		  ResultPage<VehicleMaintenance> ro=new ResultPage<>();	  
		 String sort="DESC";
		 try {
			 ro = vehiclService.findbyPage(page, size, sort);
			 ro.setSuccess(true);
		} catch (Exception e) {
	         ro.setSuccess(false);
		}
	      return ro;
	  }
	
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<VehicleMaintenance> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		ResultPage<VehicleMaintenance> ro=new ResultPage<VehicleMaintenance>();
		PageRequest pageRequest = new PageRequest(page-1,size);
		Page<VehicleMaintenance> empPage=null;
		List<VehicleMaintenance> list=null;
		try {
			/**
			 * 调用service层 页面查找方法 findSearch
			 */
			empPage=  vehiclService.findSearch(queryName, pageRequest);
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
	
	//此方法要写
	@RequestMapping(value="/delevmt",method=RequestMethod.GET)
	  public ResultObject<VehicleMaintenance> deletvf(long id){
	     return vehiclService.deletRepair(id);
	  }
	//路径对应  /vehicleRepair 父路径 车辆保养   子路径/addvmt  车辆保养增加
	//此方法要写
	@RequestMapping(value="/addvmt",method=RequestMethod.POST)
	  public ResultObject<VehicleMaintenance> savevf(VehicleMaintenance carsg){
	     return vehiclService.addRepair(carsg);
	     
	  }
	//此方法要写
	@RequestMapping(value="/findbypages",method=RequestMethod.GET)
	public ResultPage<VehicleMaintenance> findbypages(HttpServletRequest request,int page,int size,String queryName) {
		return vehiclService.findbypage(queryName, page, size);
	}


}

