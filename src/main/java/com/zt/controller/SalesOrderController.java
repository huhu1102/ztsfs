package com.zt.controller;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.SalesOrderModel;
import com.zt.po.SalesOrder;
import com.zt.service.SalesOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wl
 * @date 2019年4月23日 
 */
@RestController
@RequestMapping("/orders")
public class SalesOrderController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SalesOrderService salesOrderService;

	/**
	     合同新建订单
	 */
	@RequestMapping(value = "/addnew",method = RequestMethod.POST)
	public ResultObject<SalesOrder> addNew(String orderDetails,String note){
		return salesOrderService.addNew(orderDetails,note);
	}

	/*
	修改订单
	 */
	@RequestMapping(value="/updatenew",method = RequestMethod.POST)
	public ResultObject<SalesOrder> updateNew(long id,String orderDetails,String note) throws Exception{
		return salesOrderService.updateNew(id,orderDetails,note);
	}

	/*
	新建
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResultObject<SalesOrder> add(SalesOrderModel contractModel)throws Exception{
		return salesOrderService.add(contractModel);
	}
	/*
	 * 修改
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResultObject<SalesOrder> update(SalesOrderModel contractModel) throws Exception{
		return salesOrderService.update(contractModel);
	}
	
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<SalesOrder> findbypage(String clientName,String productName,String empName,String start,String end,Integer status,int page,int size) {
		return salesOrderService.findSearch(clientName,productName,empName,start,end,status,page,size);
	}

	/*
	根据未归档合同查询所有订单
	 */
	@RequestMapping(value = "/findalloders",method = RequestMethod.GET)
	public ResultObject<SalesOrder> findAllOders(){
		return salesOrderService.findAllOders();
	}
	/*
	根据状态查询每个状态的数量
	 */
	@RequestMapping(value = "/findStatus",method = RequestMethod.GET)
	public ResultObject<SalesOrder> findStatus(){
		return salesOrderService.findStatus();
	}
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<SalesOrder> deletEmp(long id){
	     return salesOrderService.deletDep(id);
	  }
	  /*
	  撤销
	   */
	  @RequestMapping(value = "/annul",method = RequestMethod.GET)
	public ResultObject<SalesOrder> annul(long salesOrderId)throws BusinessRuntimeException {
	  	return salesOrderService.annul(salesOrderId);
	  }

}
