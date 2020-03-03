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
import com.zt.po.Eqmt;
import com.zt.service.EqmteService;

/**
 * @author yh
 * @date 2019年4月18日
 */
@RestController
@RequestMapping(value="/eqpmt")
public class EqpmtController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EqmteService emsp;
	//查询设备维护表
	@RequestMapping(value="/selecteqpmt",method=RequestMethod.GET)
	public void update(String machineName) {
		
		emsp.updateBymachineName(machineName);

	}
	//更新设备维护表某个字段
	@RequestMapping(value="/updateone",method=RequestMethod.GET)
	public void updateByrepairman(String repairResult,String repairman) {
		boolean flag=false;
		flag=emsp.updateByrepairman(repairResult, repairman);
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(@RequestBody Eqmt eqt) {
		
		emsp.update(eqt);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	  public ResultPage<Eqmt> saveEmployee(int  page,int size){
		  ResultPage<Eqmt> ro=new ResultPage<>();	  
		 String sort="DESC";
		 try {
			 ro = emsp.findbyPage(page, size, sort);
			 ro.setSuccess(true);
		} catch (Exception e) {
	         ro.setSuccess(false);
		}
	      return ro;
	  }
	/*
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Eqmt> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		ResultPage<Eqmt> ro=new ResultPage<Eqmt>();
		PageRequest pageRequest = new PageRequest(page-1,size);
		Page<Eqmt> empPage=null;
		List<Eqmt> list=null;
		try {
			
			// * 调用service层 页面查找方法 findSearch
			
			empPage=  emsp.findSearch(queryName, pageRequest);
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
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Eqmt> deletEqmt(long id){
	     return emsp.deletEqmt(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Eqmt> saveEqmt(Eqmt eqt){
	     return emsp.addEqmt(eqt);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Eqmt> findbypage(HttpServletRequest request,int page,int size,String repairman) {
		return emsp.findEqmt(repairman, page, size);
	}
	
}
