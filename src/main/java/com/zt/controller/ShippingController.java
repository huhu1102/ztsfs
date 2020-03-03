package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Shipping;
import com.zt.service.ShippingService;

/**
 * @author wl
 * @date 2019年5月30日 
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {
	@Autowired
	ShippingService shService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Shipping> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return shService.findSearch(queryName, page,size);
	}

	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<Shipping> deletDeli(long id){
	     return shService.deletSh(id);
	  }
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public ResultObject<Shipping> updateship(Shipping shipp){
	     return shService.updateSh(shipp);
	  }
}
