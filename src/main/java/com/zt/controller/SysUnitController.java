/**  
* 
*/  
 
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.SysUnit;
import com.zt.service.SysUnitService;

/**
 * @author whl
 * @date 2019年6月5日 
 *  单位
 */
@RestController
@RequestMapping("/sysunit")
public class SysUnitController {
  
	@Autowired
	SysUnitService unitService;

	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	public ResultPage<SysUnit> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return unitService.findbypages(queryName, page,size);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/addAndUpdate",method=RequestMethod.POST)
	  public ResultObject<SysUnit> saveEmp(SysUnit unit){
	     return unitService.save(unit);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<SysUnit> deletEmp(long id){
	     return unitService.delete(id);
	  }
	
	
}
