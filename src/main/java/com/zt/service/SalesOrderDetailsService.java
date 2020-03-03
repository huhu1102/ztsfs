package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.SalesOrderDetails;

/**
 * @author wl
 * @date 2019年5月9日 
 */
public interface SalesOrderDetailsService {
	/*
	 * 修改信息
	 */
	ResultObject<SalesOrderDetails> save(SalesOrderDetails salesOrderDetails)throws BusinessRuntimeException;
	
	/*
	 * 查询所有状态
	 */
	ResultObject<SalesOrderDetails> findAll()throws BusinessRuntimeException;
	
	/*
	 * 删除
	 */
	ResultObject<SalesOrderDetails> delet(long id)throws BusinessRuntimeException;
}
