package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Vechclerequest;
import com.zt.po.VehicleInfo;
import com.zt.service.VechclerequestService;


/**
 * @author yh
 * @date 2019年4月19日
 */
@RestController
@RequestMapping(value="/askedvehicle")
public class VechclerequestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
		VechclerequestService asi;
	 @RequestMapping("/findasked")
	 public ResultObject<Vechclerequest> findvehicleNumberAll(String  vehicleNumber){
		 ResultObject<Vechclerequest> ro=new ResultObject<>();
		 
		 List<Vechclerequest> user = null;
		 try {
			 user = asi.findByvehicleNumber(vehicleNumber);
			 ro.setData(user);
		 } catch (Exception e) {
			e.printStackTrace();
			ro.setSuccess(false);
			ro.setMsg("查询失败");
		}
	        return ro;
	    }
	 //修改申请表
	 @RequestMapping(value="/askedupdate",method=RequestMethod.POST)
	  public ResultObject<Vechclerequest> saveEmp(Vechclerequest askd){
		ResultObject<Vechclerequest> ro=new ResultObject<>();
		try {
			ro=asi.saveVechclerequest(askd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	      return ro;
	  }
	 
	 @RequestMapping(value="/page",method=RequestMethod.GET)
	  public ResultPage<Vechclerequest> saveEmployee(int  page,int size){
		  ResultPage<Vechclerequest> ro=new ResultPage<>();	  
		 String sort="DESC";
		 try {
			 ro = asi.findbyPage(page, size, sort);
			 ro.setSuccess(true);
		} catch (Exception e) {
	         ro.setSuccess(false);
		}
	      return ro;
	  }
	 
	 @RequestMapping(value="/findbypage",method=RequestMethod.GET)
		public ResultPage<Vechclerequest> findbypage(HttpServletRequest request,int page,int size,String queryName) {
			ResultPage<Vechclerequest> ro=new ResultPage<Vechclerequest>();
			PageRequest pageRequest = PageRequest.of(page-1,size);
			Page<Vechclerequest> empPage=null;
			List<Vechclerequest> list=null;
			try {
				/**
				 * 调用service层 页面查找方法 findSearch
				 */
				empPage=  asi.findSearch(queryName, pageRequest);
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
		@RequestMapping(value="/deletask",method=RequestMethod.GET)
		  public ResultObject<Vechclerequest> deletvf(long id){
		     return asi.deletVechclerequest(id);
		  }
		
		//此方法要写
		@RequestMapping(value="/addask",method=RequestMethod.POST)
		  public ResultObject<Vechclerequest> savevf(Vechclerequest rqt){
		     return asi.saveVechclerequest(rqt);
		  }
		//此方法要写
		@RequestMapping(value="/findbypages",method=RequestMethod.GET)
		public ResultPage<Vechclerequest> findbypages(HttpServletRequest request,int page,int size,String queryName) {
			return asi.findVechclerequest(queryName, page, size);
		}
		//16:00
		@RequestMapping(value="/basedata",method=RequestMethod.GET)
		public ResultObject<VehicleInfo> findbasedatas() {
			return asi.findVehicleInfos();
		}
}
		

