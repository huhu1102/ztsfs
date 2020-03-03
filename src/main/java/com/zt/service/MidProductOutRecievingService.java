package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductOutRecieving;

/**
 * @author wl
 * @date 2019年7月1日 
 */
public interface MidProductOutRecievingService {
	
	/*
	 * 撤销入库记录
	 */
	public ResultObject<MidProductOutRecieving> withdraw(long id)throws BusinessRuntimeException;
	/*
	 * 分页模糊条件查询
	 */
	
	public ResultPage<MidProductOutRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 新增
	 */
	public ResultObject<MidProductOutRecieving> addNew(long id, float quaity, String notes)throws BusinessRuntimeException;
}
