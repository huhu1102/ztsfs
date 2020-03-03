package com.zt.controller;


import com.zt.model.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.po.ShippingRequestDetails;
import com.zt.service.ShippingRequestDetailsService;

/**
 * @author wl
 * @date 2019年4月28日 
 */
@RestController
@RequestMapping("/shippingrequestdetails")
public class ShippingRequestDetailsController {
	@Autowired
	ShippingRequestDetailsService derService;
	
	/*
	 * 查询所有
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ShippingRequestDetails> findByPage(String empName,String clientName,String productName, int page, int size) {
		return derService.findByPage(empName,clientName,productName,page,size);
	}

	@RequestMapping(value="/findById",method=RequestMethod.GET)
	public ResultObject<ShippingRequestDetails> findById(long id) {
		return derService.findsById(id);
	}
	/*
	查询状态数量
	 */
	@RequestMapping(value = "findStatus",method = RequestMethod.GET)
	public ResultObject<ShippingRequestDetails> findStatus(){
		return derService.findStatus();
	}
	/*
	新增
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResultObject<ShippingRequestDetails> add(long salesPlanId,ShippingRequestDetails shippingRequestDetails){
        String type="sales";
	    return derService.add(salesPlanId,shippingRequestDetails, type);
	}

	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	  public ResultObject<ShippingRequestDetails> updateDetail(ShippingRequestDetails derec){
        String type="sales";
	     return derService.update(derec, type);
	  }


    /**
     * 生产计划新增发送请求
     * 其中salesPlanId未生产计划Id
     */
    @RequestMapping(value="/updatePlan",method=RequestMethod.POST)
    public ResultObject<ShippingRequestDetails> updatePlan(ShippingRequestDetails derec){
        String type="productPlan";
        return derService.update(derec,type);
    }

    /*
    新增
     */
    @RequestMapping(value="/addPlan",method=RequestMethod.POST)
    public ResultObject<ShippingRequestDetails> addPlan(long salesPlanId,ShippingRequestDetails shippingRequestDetails){
        String type="productPlan";
        return derService.add(salesPlanId,shippingRequestDetails,type);
    }
    /*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ShippingRequestDetails> deletDeli(long id){
		return derService.deletDre(id);
	}
	/*
	 * 撤销
	 */
	@RequestMapping(value="/revokebeg",method=RequestMethod.GET)
	  public ResultObject<ShippingRequestDetails> revoke(long id){
        String type="";
		return derService.revoke(id, type);
	}
    /*
     * 撤销
     */
    @RequestMapping(value="/revokePlanBeg",method=RequestMethod.GET)
    public ResultObject<ShippingRequestDetails> revokePlanBeg(long id){
        String type="plan";
        return derService.revoke(id,type);
    }

}
