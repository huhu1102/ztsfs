package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductPurchaseVerify;

/**
 * @author wl
 * @date 2019年6月20日 
 */
public interface MidProductPurchaseVerifyService {
	
	/*
	 * 分页查询
	 */
	ResultPage<MidProductPurchaseVerify> findVerifyDetail(String identifier, int page, int size) throws BusinessRuntimeException;
	
	/*
	 * 新增
	 */
	ResultObject<MidProductPurchaseVerify> addNew(long purchasePlanDetailId,Integer confirmStatus,float  quantity,String notes) throws BusinessRuntimeException;

	/*
	 * 撤回
	 */
	ResultObject<MidProductPurchaseVerify> withdraw(long id) throws BusinessRuntimeException;
	
}
