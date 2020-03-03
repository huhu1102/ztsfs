/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductPurchasePlan;
import com.zt.vo.PlanModel;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface MidProductPurchasePlanService {
	
	 public ResultPage<MidProductPurchasePlan> findpurchasePlan(String identifier, int page, int size) throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<MidProductPurchasePlan> deletpurchasePlan(long id)throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<MidProductPurchasePlan> getSerialNumber()throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<MidProductPurchasePlan> basedata()throws BusinessRuntimeException;

	/*
	 * 新增
	 */
	public ResultObject<MidProductPurchasePlan> andNewPlan(PlanModel plan)throws BusinessRuntimeException;


	/*
	 * 修改
	 */
	public ResultObject<MidProductPurchasePlan> update(PlanModel plan)throws BusinessRuntimeException;
		
}
