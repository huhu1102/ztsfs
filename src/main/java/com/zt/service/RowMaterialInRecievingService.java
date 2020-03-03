package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialInRecieving;
/**
 * @author wl
 * @date 2019年5月30日 
 */
public interface RowMaterialInRecievingService {
	/*
     * 分页模糊条件查询
     */
	public ResultPage<RowMaterialInRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;

	/*
	 * 撤销出库记录
	 */
	public ResultObject<RowMaterialInRecieving> withdraw(long id)throws BusinessRuntimeException;
	/*
	 * 新增
	 */
	public ResultObject<RowMaterialInRecieving> addNew(long id, float quaity, String notes)throws BusinessRuntimeException;

}
