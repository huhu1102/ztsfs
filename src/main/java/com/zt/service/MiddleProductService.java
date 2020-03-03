/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.MiddleProduct;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface MiddleProductService  {
	public ResultPage<MiddleProduct> findMiddleProduct(String queryName, int page, int size) throws BusinessRuntimeException;
    
    
    public  ResultObject<MiddleProduct> addMiddleProduct(MiddleProduct product)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<MiddleProduct> deletMiddleProduct(long id)throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<MiddleProduct> basedata()throws BusinessRuntimeException;


	/**
	 * @return
	 */
	public ResultObject<MiddleProduct> getCurrentPlan()throws BusinessRuntimeException;


	/**
	 * @param mdt
	 * @return
	 */
	public ResultObject<MiddleProduct> updateMiddleProduct(MiddleProduct mdt)throws BusinessRuntimeException;
}
