package com.zt.controller;

import com.zt.model.ProductionPlanDetailsModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlanDetails;
import com.zt.service.ProductionPlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wl
 * @date 2019年5月10日 
 */
@RestController
@RequestMapping("/productionplandetails")
public class ProductionPlanDetailsController {
	@Autowired
	ProductionPlanDetailsService productionPlanDetailsService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ProductionPlanDetails> findbypage(HttpServletRequest request,
														String productName,
														String empName,
														String endDate,
														String startDate,
														String clientName,
														String status,
														int page, int size) {
		return productionPlanDetailsService.findSearch(page,size,  productName, empName,endDate, startDate,clientName,status);
	}
	//查询合同状态为1和3的
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ResultObject<ProductionPlanDetails> find(String productName,
											 String empName,
											 String endDate,
											 String startDate,
											 String clientName,
											 Integer status) {
		return productionPlanDetailsService.find(productName, empName,endDate, startDate, status,clientName);
	}
	/*
	根据销售计划Id查询
	*/
	@RequestMapping(value = "/findPlan",method = RequestMethod.GET)
	public ResultPage<ProductionPlanDetails> findPlan(int page, int size, long salesPlanId){
		return productionPlanDetailsService.findPlan(page, size,salesPlanId);
	}
	/**
	 * 查询未被合并的所有生产计划
	*/
	@RequestMapping(value = "/findForOrder",method = RequestMethod.GET)
	public ResultPage<ProductionPlanDetails> findForOrder(int page, int size, long clientId){
		return productionPlanDetailsService.findForOrder(page, size,clientId);
	}

	/*
	状态查询
	 */
	@RequestMapping(value="/findbystatus",method=RequestMethod.GET)
	public ResultPage<ProductionPlanDetails> findbystatus(HttpServletRequest request, int page, int size, String queryName) {
		return productionPlanDetailsService.findBystatus(queryName, page,size);
	}
	/**
	  查询新增生产计划 个数
	 */
	@RequestMapping(value = "/findcount",method = RequestMethod.GET)
	public ResultObject<ProductionPlanDetails> findCount(){
		return productionPlanDetailsService.findCount();
	}

	/*
	修改生产计划详情
 	*/
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResultObject<ProductionPlanDetails> update(ProductionPlanDetailsModel productionPlanDetailsModel){
		return productionPlanDetailsService.update(productionPlanDetailsModel);
	}
	/*
	 * 删除
	 */
	@RequestMapping(value="/deletmark",method=RequestMethod.GET)
  	public ResultObject<ProductionPlanDetails> deletDeli(long id){
	 	return productionPlanDetailsService.deletDel(id);
	}
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResultObject<ProductionPlanDetails> add(ProductionPlanDetailsModel productionPlanDetailsModel){
		return productionPlanDetailsService.add(productionPlanDetailsModel);
	}
	@RequestMapping(value="/addSalesAndplan",method = RequestMethod.POST)
	public ResultObject<ProductionPlanDetails> addSalesAndplan(ProductionPlanDetailsModel productionPlanDetailsModel){
		return productionPlanDetailsService.addSalesAndplan(productionPlanDetailsModel);
	}

	//修改查看状态状态
	@RequestMapping(value="/checkState/{id}",method=RequestMethod.GET)
	public ResultObject<ProductionPlanDetails> checkState(@PathVariable long id){
		return productionPlanDetailsService.checkState(id);
	}

	/**
	 * 根据生产计划Id查询生产管理Id
	 */
	@RequestMapping(value="/findbyMangeId/{detailId}",method=RequestMethod.GET)
	public Long findbyMangeId(@PathVariable long detailId){
		return productionPlanDetailsService.findbyMangeId(detailId);
	}
}
