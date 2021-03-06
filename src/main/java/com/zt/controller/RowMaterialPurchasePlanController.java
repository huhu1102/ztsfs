/**
 * 
 */
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialPurchasePlan;
import com.zt.service.RowMaterialPurchasePlanService;
import com.zt.vo.PlanModel;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/rowBuyPlan")
public class RowMaterialPurchasePlanController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RowMaterialPurchasePlanService  planService;
	/*
	 * 删除
	 */
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<RowMaterialPurchasePlan> deletPurchasePlan(long id){
	     return planService.deletpurchasePlan(id);
	  }
	/*
	 * 新增方法
	 */
	@RequestMapping(value="/addnew",method=RequestMethod.POST)
	public ResultObject<RowMaterialPurchasePlan> andNewPlan(PlanModel plan){
		return planService.andNewPlan(plan);
	}
	/*
	 * 修改
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResultObject<RowMaterialPurchasePlan> update(PlanModel plan){
		return planService.update(plan);
	}
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<RowMaterialPurchasePlan> findbypage(HttpServletRequest request,int page,int size,String identifier) {
		return planService.findpurchasePlan(identifier, page, size);
	}
	/**
	 * @return  编号
	 */
	@RequestMapping(value="/getSerialNumber",method=RequestMethod.GET)
	public ResultObject<RowMaterialPurchasePlan> getSerialNumber() {
		return planService.getSerialNumber();
	}
	
	/**
	 * @return  页面初始化数据
	 */
	@RequestMapping(value="/basedata",method=RequestMethod.GET)
	public ResultObject<RowMaterialPurchasePlan> basedata() {
		return planService.basedata();
	}
	
}
