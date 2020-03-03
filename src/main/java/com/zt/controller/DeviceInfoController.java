/**
 * 
 */
package com.zt.controller;

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
import com.zt.po.Bidding;
import com.zt.po.DeviceInfo;
import com.zt.po.Employee;
import com.zt.service.DeviceInfoService;
import com.zt.serviceImp.DeviceInfoServiceImpl;

/**
 * @author yh
 * @date 2019年4月18日
 */
@RestController
@RequestMapping(value="/deviceinfo")
public class DeviceInfoController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceInfoService difs;
	@RequestMapping(value="/finddeviceinfo")
	public ResultObject<DeviceInfo> finddeviceinfoAll(String  deviceStatus){ 
		ResultObject<DeviceInfo> rob=new ResultObject<>();
		
		List<DeviceInfo> devi=null;
		try {
			devi=difs.findBydeviceStatus(deviceStatus);
			rob.setData(devi);
		} catch (Exception e) {
			e.printStackTrace();
			rob.setSuccess(false);
			rob.setMsg("设备尚未录入信息");
		}
		
		return rob;
	}
		@RequestMapping(value="/updatedeviceinfo",method=RequestMethod.POST)
		
		public ResultObject<DeviceInfo> updatedeviceinfo(String deviceStatus, String deviceName ){
			ResultObject<DeviceInfo> results = new ResultObject<>();
			 Boolean bl=false; 
			 try {
				 bl=difs.updatedeviceinfo(deviceStatus, deviceName);
			} catch (Exception e) {
				 e.printStackTrace();
			}
		            if(bl) {
		            	results.setMsg("修改成功！");
		            }else {
		            	results.setMsg("失败");
		            }
		            results.setSuccess(bl);
		        return results;
		    }
		@RequestMapping(value="/update",method=RequestMethod.POST)
		public void update(@RequestBody DeviceInfo devcif) {
			
			difs.update(devcif);

		}
		
		/*
		 * 分页查询
		 */
		@RequestMapping(value="/page",method=RequestMethod.GET)
		  public ResultPage<DeviceInfo> saveEmployee(int  page,int size){
			  ResultPage<DeviceInfo> ro=new ResultPage<>();	  
			 String sort="DESC";
			 try {
				 ro = difs.findbyPage(page, size, sort);
				 ro.setSuccess(true);
			} catch (Exception e) {
		         ro.setSuccess(false);
			}
		      return ro;
		  }
		/*
		 * 分页模糊条件查询
		 */
		
		/*@RequestMapping(value="/findbypage",method=RequestMethod.GET)
		public ResultPage<DeviceInfo> findbypage(HttpServletRequest request,int page,int size,String queryName) {
			ResultPage<DeviceInfo> ro=new ResultPage<DeviceInfo>();
			PageRequest pageRequest = new PageRequest(page-1,size);
			Page<DeviceInfo> empPage=null;
			List<DeviceInfo> list=null;
			try {
				
				  //调用service层 页面查找方法 findSearch
				 
				empPage=  difs.findSearch(queryName, pageRequest);
				list=  empPage.getContent();
				ro.setTotal(empPage.getTotalElements());
			    ro.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				 ro.setSuccess(false);
			}
			ro.setData(list);
			ro.setSuccess(true);
			return ro;
		}
		*/
		
		//下面三个方法要写
		
		@RequestMapping(value="/delet",method=RequestMethod.GET)
		  public ResultObject<DeviceInfo> deletvf(long id){
		     return difs.deletDeviceInfo(id);
		  }
		
		//此方法要写
		@RequestMapping(value="/add",method=RequestMethod.POST)
		  public ResultObject<DeviceInfo> savevf(DeviceInfo dcif){
		     return difs.addDeviceInfo(dcif);
		  }
		//此方法要写
		@RequestMapping(value="/findbypage",method=RequestMethod.GET)
		public ResultPage<DeviceInfo> findbypage(HttpServletRequest request,int page,int size,String queryName) {
			return difs.findDeviceInfo(queryName, page, size);
		}
		
		
}