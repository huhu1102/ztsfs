package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.FinishedProduct;

/**
 * @author wl
 * @date 2019年5月13日 
 */
public interface FinishedProductService {
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<FinishedProduct> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	public ResultObject<FinishedProduct> deletDel(long id)throws BusinessRuntimeException;
	/*
	 * 信息的更新
	 */
	public ResultObject<FinishedProduct> saveClient(FinishedProduct finishedProduct)throws BusinessRuntimeException;
	/**
	 * @return
	 */
	public ResultObject<FinishedProduct> basedata()throws BusinessRuntimeException;
}
