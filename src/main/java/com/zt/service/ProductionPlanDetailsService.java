package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ProductionPlanDetailsModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlanDetails;

/**
 * @author wl
 * @date 2019年5月10日 
 */
public interface ProductionPlanDetailsService {
	/*
	 * 分页模糊条件查询
	 */
	ResultPage<ProductionPlanDetails> findSearch(int page, int size, String productName, String empName, String endDate, String startDate, String clientName,String status) throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	ResultObject<ProductionPlanDetails> deletDel(long id)throws BusinessRuntimeException;
	/*
	修改
	 */
    ResultObject<ProductionPlanDetails> update(ProductionPlanDetailsModel productionPlanDetailsModel)throws BusinessRuntimeException;
	/*
	状态查询
	 */
    ResultPage<ProductionPlanDetails> findBystatus(String queryName, int page, int size)throws BusinessRuntimeException ;
	/*
	根据销售计划Id查询
	 */
    ResultPage<ProductionPlanDetails> findPlan(int page, int size, long salesPlanId);

    ResultObject<ProductionPlanDetails> findCount();

    ResultObject<ProductionPlanDetails> add(ProductionPlanDetailsModel productionPlanDetailsModel);

    ResultObject<ProductionPlanDetails> checkState(long id);

    ResultObject<ProductionPlanDetails> addSalesAndplan(ProductionPlanDetailsModel productionPlanDetailsModel);

    ResultPage<ProductionPlanDetails> findForOrder(int page, int size, long clientId);

    Long findbyMangeId(long manageId);
}
