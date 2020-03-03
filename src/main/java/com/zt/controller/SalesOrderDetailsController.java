package com.zt.controller;


import com.zt.po.SalesOrderDetails;
import com.zt.service.SalesOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;

/**
 * @author wl
 * @date 2019年5月9日 
 */
@RestController
@RequestMapping("/salesorderdetails")
public class SalesOrderDetailsController {
	@Autowired
	SalesOrderDetailsService salesOrderDetailsService;
	
	/*
	 * 查询所有
	 */
	@RequestMapping(value="/findAll",method=RequestMethod.POST)
	public ResultObject<SalesOrderDetails> findAll(){
		return salesOrderDetailsService.findAll();
	}
	/*  
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<SalesOrderDetails> saveEmp(SalesOrderDetails cs){
	     return salesOrderDetailsService.save(cs);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<SalesOrderDetails> deletEmp(long id){
	     return salesOrderDetailsService.delet(id);
	  }
}
