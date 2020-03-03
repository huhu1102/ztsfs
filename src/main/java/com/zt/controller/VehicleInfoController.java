/**
 * 
 */
package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.VehicleInfo;
import com.zt.service.VehicleInfoService;
import com.zt.serviceImp.VehicleInfoServiceImpl;

/**
 * @author yh
 * @date 2019年4月12日
 */
@RestController
@RequestMapping(value="/vehicle")
public class VehicleInfoController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private VehicleInfoService carService;
	
	 @RequestMapping("/findvehicle")
	    public ResultObject<VehicleInfo> findvehicleAll(String  status){
		 ResultObject<VehicleInfo> ro=new ResultObject<>();
		 
		 List<VehicleInfo> list = null;
		 list  = carService.findByStatus(status);
		 if(null!=list) {
			 new BusinessRuntimeException(ResultCode.SUCCESS);
			 ro.setSuccess(true);
			 ro.setMsg("查询成功！");
		 }else {
			 new BusinessRuntimeException(ResultCode.OPER_FAILED);
			 ro.setMsg("查询失败");
			 ro.setSuccess(false);
		 }
	        return ro;
	    }
	 
//	//修改车辆基础信息
//	 @RequestMapping(value = "/updateStudent",method=RequestMethod.POST)
//	    public ResultObject<VehicleInfo>  updateStudentById(@RequestBody VehicleInfo vhc) {
//		 ResultObject<VehicleInfo> result = new ResultObject<>();
//		 Boolean bl=false; 
//		 try {
//			 bl=  carService.updateVehicleInfo(vhc);
//		} catch (Exception e) {
//			 e.printStackTrace();
//		}
//	            if(bl) {
//	            	result.setMsg("修改成功！");
//	            }else {
//	            	result.setMsg("失败");
//	            }
//	        result.setSuccess(bl);
//	        return result;
//	    }
	//修改车辆基础信息
	 @RequestMapping(value="/updatevehicleInfo",method=RequestMethod.POST)
		public void update(@RequestBody VehicleInfo  veci) {
			
			carService.updateVehicleInfoms(veci);

		}
	 
	
		 //分页查询
		 /*
		@RequestMapping(value="/page",method=RequestMethod.GET)
		  public ResultPage<VehicleInfo> saveEmployee(int  page,int size){
			  ResultPage<VehicleInfo> ro=new ResultPage<>();	  
			 String sort="DESC";
			 try {
				 ro = carService.findbyPage(page, size, sort);
				 ro.setSuccess(true);
			} catch (Exception e) {
		         ro.setSuccess(false);
			}
		      return ro;
		  }
		
		// 分页模糊条件查询
		 
	 
	 
		@RequestMapping(value="/findbypage",method=RequestMethod.GET)
		public ResultPage<VehicleInfo> findbypage(HttpServletRequest request,int page,int size,String queryName) {
			ResultPage<VehicleInfo> ro=new ResultPage<VehicleInfo>();
			PageRequest pageRequest = new PageRequest(page-1,size);
			Page<VehicleInfo> empPage=null;
			List<VehicleInfo> list=null;
			try {
				
				 // 调用service层 页面查找方法 findSearch
				
				empPage=  carService.findSearch(queryName, pageRequest);
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
		*/
	//此方法要写
		@RequestMapping(value="/deletvf",method=RequestMethod.GET)
		  public ResultObject<VehicleInfo> deletvf(long id){
		     return carService.deletVehicleInfo(id);
		  }
		
		//此方法要写
		@RequestMapping(value="/addupvf",method=RequestMethod.POST)
		  public ResultObject<VehicleInfo> savevf(VehicleInfo carsg){
		     return carService.saveVehicleInfos(carsg);
		  }
		//此方法要写
		@RequestMapping(value="/findbypage",method=RequestMethod.GET)
		public ResultPage<VehicleInfo> findbypage(HttpServletRequest request,int page,int size,String queryName) {
			return carService.findSearch(queryName, page,size);
		}
		
		
}
