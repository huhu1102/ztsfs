package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ProductionPlanModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlan;
import com.zt.po.ProductionPlanDetails;

/**
 * @author wl
 * @date 2019年5月6日 
 */
public interface ProductionPlanService {
	/*
	 */
	 ResultPage<ProductionPlan> findByPage(int page, int size, String productName, String empName, String endDate, String startDate, Integer status, String clientName)throws BusinessRuntimeException;

	/*
	 * 信息的删除
	 */
	 ResultObject<ProductionPlan> deletDel(long id)throws BusinessRuntimeException;
	/*
    页面初始化
     */
    ResultObject<ProductionPlan> basedata()throws BusinessRuntimeException;
    /*
    新建生产计划
     */
    ResultObject<ProductionPlan> addNew(ProductionPlanModel productionPlanModel)throws  Exception;
	/*
	修改生产计划
	 */
	ResultObject<ProductionPlan> updatePlan(long id,String notes)throws BusinessRuntimeException;
	/**
	 *根据客户Id查最近的编码详情
	 */
    ResultObject<ProductionPlanDetails> findPlanDetails(long clientId)throws BusinessRuntimeException;

	ResultPage<ProductionPlanDetails> findByNewPlan(int page, int size, String productName, String empName, String endDate, String startDate, String clientName);

    ResultObject<ProductionPlan> findStatus()throws BusinessRuntimeException;
}
