package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialPurchaseVerify;
import com.zt.service.RowMaterialPurchaseVerifyService;

/**
 * @author wl
 * @date 2019年6月28日 
 */
@RestController
@RequestMapping("/rowPruchaseVerify")
public class RowMaterialPurchaseVerifyController {
		
	@Autowired
	RowMaterialPurchaseVerifyService verifyDetailService;
	
	/*
	 * 新增采购确认
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	  public ResultObject<RowMaterialPurchaseVerify> add(long purchasePlanDetailId,Integer confirmStatus,float  quantity,String notes){
	     return verifyDetailService.addNew(purchasePlanDetailId, confirmStatus, quantity, notes);
	  }
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<RowMaterialPurchaseVerify> findbypage(HttpServletRequest request,int page,int size,String identifier) {
		return verifyDetailService.findVerifyDetail(identifier, page, size);
	}
	
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<RowMaterialPurchaseVerify> withDrow(long id){
		return verifyDetailService.withdraw(id);
	}
	
	
	
}
