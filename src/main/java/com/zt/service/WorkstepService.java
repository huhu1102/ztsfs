package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Workstep;

/**
 * @author wl
 * @date 2019年4月17日 
 */
public interface WorkstepService {
	/*
	 * 修改信息
	 */
	public ResultObject<Workstep> saveWorkstep(Workstep workstep);
	
	/*
	 * 查询所有工步
	 */
	public ResultObject<Workstep> findAll();
	
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<Workstep> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;

	/*
	 * 删除
	 */
	public ResultObject<Workstep> deletWor(long id)throws BusinessRuntimeException;
}
