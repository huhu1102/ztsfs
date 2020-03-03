package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductInRecieving;

/**
 * @author wl
 * @date 2019年7月1日 
 */
public interface MidProductInRecievingService {
	
	/*
	 * 新增
	 */
	public ResultObject<MidProductInRecieving> addNew(long id,float quaity,String notes)throws BusinessRuntimeException;
	
	/*
	 * 撤销入库记录
	 */
	public ResultObject<MidProductInRecieving> withdraw(long id)throws BusinessRuntimeException;
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<MidProductInRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
}
