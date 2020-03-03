package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductManageDetails;

/**
 * @author wl
 * @date 2019年5月17日 
 */
public interface ProductManageDetailsService {
	/*
	分页查询
	 */
	ResultPage<ProductManageDetails> findSearch(int page, int size) throws BusinessRuntimeException;
	/*
	新增
	 */
	ResultObject<ProductManageDetails> add(long manageId, String manageDetails)throws BusinessRuntimeException;
	/*
	修改
	 */
    ResultObject<ProductManageDetails> update(long manageId, long manageDetailId, float quantity, float junkedNo,String plant,String notes)throws BusinessRuntimeException;

    ResultObject<ProductManageDetails> findEnd(long productManageId)throws BusinessRuntimeException;

	ResultObject<ProductManageDetails> addFinish(long manageId, ProductManageDetails manageDetails);

	ResultObject<ProductManageDetails> updateFinish(long manageId,ProductManageDetails manageDetails);

	ResultPage<ProductManageDetails> findByMangeId(long manageId, int page, int size, String start, String end);

    ResultObject<ProductManageDetails> deleteFinish(long manageDetailsId);
}
