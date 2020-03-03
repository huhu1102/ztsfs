package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingReceipt;

/**
 * @author wl
 * @date 2019年4月28日 
 * 发货回执单
 */

public interface ShippingReceiptService {
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<ShippingReceipt> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 信息的修改
	 */
	public  ResultObject<ShippingReceipt> savePosition(ShippingReceipt shippingReceipt)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	public ResultObject<ShippingReceipt> deletDel(long id)throws BusinessRuntimeException;
}
