package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.SalesOrderModel;
import com.zt.po.SalesOrder;

/**
 * @author wl
 * @date 2019年4月23日 
 */
public interface SalesOrderService {
	/*
	 * 修改
	 */
	ResultObject<SalesOrder> update(SalesOrderModel salesOrderModel) throws Exception;
	/*
	 * 分页模糊条件查询
	 */
	ResultPage<SalesOrder> findSearch(String clientName,String productName,String empName,String start,String end,Integer status,int page, int size) throws BusinessRuntimeException;
	/*
	 * 删除
	 */
	ResultObject<SalesOrder> deletDep(long id)throws BusinessRuntimeException;
	/*
	新建
	 */
	ResultObject<SalesOrder> add(SalesOrderModel salesOrderModel) throws Exception;
	/*
	查询不同状态的数量
	 */
    ResultObject<SalesOrder> findStatus()throws BusinessRuntimeException;

    ResultObject<SalesOrder> annul(long salesOrderId)throws BusinessRuntimeException;

    ResultObject<SalesOrder> findAllOders()throws BusinessRuntimeException;
}
