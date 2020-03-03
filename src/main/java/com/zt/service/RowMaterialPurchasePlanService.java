/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialPurchasePlan;
import com.zt.vo.PlanModel;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface RowMaterialPurchasePlanService {
	
	 public ResultPage<RowMaterialPurchasePlan> findpurchasePlan(String identifier, int page, int size) throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<RowMaterialPurchasePlan> deletpurchasePlan(long id)throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<RowMaterialPurchasePlan> getSerialNumber()throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<RowMaterialPurchasePlan> basedata()throws BusinessRuntimeException;

	/*
	 * 新增
	 */
	public ResultObject<RowMaterialPurchasePlan> andNewPlan(PlanModel plan)throws BusinessRuntimeException;


	/*
	 * 修改
	 */
	public ResultObject<RowMaterialPurchasePlan> update(PlanModel plan)throws BusinessRuntimeException;
		
}
