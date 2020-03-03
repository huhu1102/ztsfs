package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ProcessModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductPreProcess;

/**
 * @author wl
 * @date 2019年5月7日 
 */
public interface ProductPreProcessService {
	/*
	 * 分页模糊条件查询
	 */
	ResultPage<ProductPreProcess> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;

	ResultObject<ProductPreProcess> findById(long id);
	/*
	 * 信息的修改
	 */
	ResultObject<ProductPreProcess> save(ProcessModel derec)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	ResultObject<ProductPreProcess> deletDel(long id)throws BusinessRuntimeException;
	/**
	 * @param progeress
	 * @return
	 */
	ResultObject<ProductPreProcess> update(ProcessModel progeress);
}
