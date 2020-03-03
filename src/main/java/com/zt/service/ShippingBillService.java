package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingBill;

/**
 * @author wl
 * @date 2019年6月11日 
 */
public interface ShippingBillService {
	/*
	 * 新增
	 */
	ResultObject<ShippingBill> savebill(ShippingBill dispatchBill)throws BusinessRuntimeException;
	/*
	修改
	 */
	ResultObject<ShippingBill> update(ShippingBill dispatchBill)throws BusinessRuntimeException;
    /*
     * 分页模糊条件查询
     */
	ResultPage<ShippingBill> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
    
    /*
     * 删除
     */
    ResultObject<ShippingBill> deletCli(long id)throws BusinessRuntimeException;

	//根据生产管理Id查询发货记录
    ResultPage<ShippingBill> findForManage(long productManageId, int page, int size);
	//根据生产计划ID查询发货记录
    ResultPage<ShippingBill> findForPlan(long productPlanId, int page, int size);
}
