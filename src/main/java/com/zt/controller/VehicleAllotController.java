/**
 * 
 */
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.VehicleAllot;
import com.zt.service.VehicleAllotService;
import com.zt.serviceImp.VehicleAllotServiceImp;
import com.zt.vo.CarAssignedModel;


/**
 * @author yh
 * @date 2019年4月28日
 */
@RestController
@RequestMapping(value="/vehicleAllot")
public class VehicleAllotController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private  VehicleAllotService allotService;
	@RequestMapping(value="/delevcalt",method=RequestMethod.GET)
	  public ResultObject<VehicleAllot> deletvct(long id){
	     return allotService.deletvcalt(id);
	  }
	
	@RequestMapping(value="/addvcalt",method=RequestMethod.POST)
	  public ResultObject<VehicleAllot> savevct(VehicleAllot vct){
	     return allotService.addvcalt(vct);
	  }
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResultObject<VehicleAllot> savevct(CarAssignedModel assignedModel){
		return allotService.addAssigned(assignedModel);
	}
	@RequestMapping(value="/findbypages",method=RequestMethod.GET)
	public ResultPage<VehicleAllot> findbypages(HttpServletRequest request,int page,int size,String queryName) {
		return allotService.findbypage(queryName, page, size);
	}
	/**
	 * 用车分配 修改方法
	 * @param allot
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResultObject<VehicleAllot> update(VehicleAllot allot){
		return allotService.update(allot);
	}
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public ResultObject<VehicleAllot> refuse(CarAssignedModel assignedModel){
		return allotService.refuse(assignedModel);
	}
}
