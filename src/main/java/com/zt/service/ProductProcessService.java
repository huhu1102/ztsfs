package com.zt.service;


import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;
import com.zt.po.ProductProcess;

/**
 * @author wl
 * @date 2019年4月17日 
 */
public interface ProductProcessService {
	/*
	 * 根据产品查询工序
	 */
	public ResultObject<ProductProcess> findProductProcess(String productName);
	
	/*
	 * 修改工序
	 */
//	public ResultObject<ProductProcess> saveProductProcess(ProductProcess productProcess);


	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<ProductProcess> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 信息的修改
	 */
	public  ResultObject<ProductProcess> saveProductProcess(ProductProcess productProcess)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	public ResultObject<ProductProcess> deletDep(long id)throws BusinessRuntimeException;
}
