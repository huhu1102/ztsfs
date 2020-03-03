package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingBill;
import com.zt.service.ShippingBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wl
 * @date 2019年6月11日 
 */
@RestController
@RequestMapping("/shippingBill")
public class ShippingBillController {
	@Autowired
	ShippingBillService dbService;



	/*
	 * 新增
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public  ResultObject<ShippingBill> savebill(ShippingBill dispatchBill){
		return dbService.savebill(dispatchBill);
	}

	/*
	更新
	 */
	@RequestMapping(value = "/update",method =RequestMethod.POST)
	public ResultObject<ShippingBill> update(ShippingBill dispatchBill){
		return dbService.update(dispatchBill);
	}
    /*
     * 分页模糊条件查询
     */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ShippingBill> findSearch(String queryName, int page, int size) {
		return dbService.findSearch(queryName, page, size);
	}
    
    /*
     * 删除
     */
	@RequestMapping(value="/del",method=RequestMethod.POST)
    public ResultObject<ShippingBill> deletCli(long id){
		return dbService.deletCli(id);
	}

	/*
	根据生产管理Id查询发货记录
	 */
	@RequestMapping(value="/findformanage",method = RequestMethod.GET)
	public ResultPage<ShippingBill> findForManage(long productManageId, int page, int size) {
		return dbService.findForManage(productManageId, page, size);
	}

	/*
	根据生产计划查询发货记录
	 */
	@RequestMapping(value = "/findforplan",method = RequestMethod.GET)
	public ResultPage<ShippingBill> findForPlan(long productPlanId, int page, int size) {
		return dbService.findForPlan(productPlanId, page, size);
	}
}
