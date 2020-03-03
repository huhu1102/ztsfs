package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingReceipt;
import com.zt.service.ShippingReceiptService;

/**
 * @author wl
 * @date 2019年4月28日 
 */
@RestController
@RequestMapping("/shippingreceipt")
public class ShippingReceiptController {
	
	@Autowired
	ShippingReceiptService dereService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ShippingReceipt> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return dereService.findSearch(queryName, page,size);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<ShippingReceipt> saveDeli(ShippingReceipt derec){
	     return dereService.savePosition(derec);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ShippingReceipt> deletDeli(long id){
	     return dereService.deletDel(id);
	  }
}
