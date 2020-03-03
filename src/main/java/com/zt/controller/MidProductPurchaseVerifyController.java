package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductPurchaseVerify;
import com.zt.service.MidProductPurchaseVerifyService;

/**
 * @author wl
 * @date 2019年6月28日 
 */
@RestController
@RequestMapping("/midPruchaseVerify")
public class MidProductPurchaseVerifyController {
		
	@Autowired
	MidProductPurchaseVerifyService verifyService;
	
	/*
	 * 新增采购确认
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	  public ResultObject<MidProductPurchaseVerify> add(long purchasePlanDetailId,Integer confirmStatus,float  quantity,String notes){
	     return verifyService.addNew(purchasePlanDetailId,confirmStatus, quantity, notes);
	  }
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<MidProductPurchaseVerify> findbypage(HttpServletRequest request,int page,int size,String identifier) {
		return verifyService.findVerifyDetail(identifier, page, size);
	}
	
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<MidProductPurchaseVerify> withDrow(long id){
		return verifyService.withdraw(id);
	}
	
	
	
}
