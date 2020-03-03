package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingRequestDetails;
/**
 * @author wl
 * @date 2019年4月28日 
 */

public interface ShippingRequestDetailsService {
	/*
	 * 信息的修改
	 */
	ResultObject<ShippingRequestDetails> update(ShippingRequestDetails dere, String type)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	ResultObject<ShippingRequestDetails> deletDre(long id)throws BusinessRuntimeException;
	/*
	新增
	 */
	ResultObject<ShippingRequestDetails> add(long salesPlanId, ShippingRequestDetails shippingRequestDetails, String type)throws BusinessRuntimeException;
    /*
    分页查询
     */
    ResultPage<ShippingRequestDetails> findByPage(String empName,String clientName,String productName, int page, int size)throws BusinessRuntimeException;

    ResultObject<ShippingRequestDetails> findStatus()throws BusinessRuntimeException;

	ResultObject<ShippingRequestDetails> findsById(long id);

	ResultObject<ShippingRequestDetails> revoke(long id, String type);


}
